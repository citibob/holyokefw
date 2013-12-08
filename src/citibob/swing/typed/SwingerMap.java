/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * SqlTypeMap.java
 *
 * Created on March 15, 2006, 9:22 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.sql.*;
import citibob.types.JType;
import citibob.types.JavaJType;
import java.util.*;
import citibob.text.*;
import citibob.swing.table.*;

/**
 * Maps SqlType objects to various formatters, etc. required by graphical parts
 * of system.  Used to automatically construct GUIs appropriate for a schema.
 * @author citibob
 */
public abstract class SwingerMap extends SFormatMap
{
	public Swinger newSwinger(JType t)
		{ return newSwinger(t, null); }
	
	/** Allows for special column names to be hard-wired with specific Swingers,
	based on their name, not JType. */
	public abstract Swinger newSwinger(JType t, String colName);

	public Swinger newSwinger(Class klass) { return newSwinger(new JavaJType(klass)); }
	public TypedWidget newWidget(JType t) { return newSwinger(t).newWidget(); }
	public TypedWidget newWidget(Class klass) { return newSwinger(klass).newWidget(); }

/** Create Swinger for an entire set of columns */
public Swinger[] newSwingers(JTypeTableModel model)
{
	int n = model.getColumnCount();
	Swinger[] sfmt = new Swinger[n];
	for (int i=0; i<n; ++i) sfmt[i] = newSwinger(model.getJType(0, i), model.getColumnName(i));
	return sfmt;
}

/** Create Swinger for an entire set of columns
@param scol names of columns for exceptions.
@param sfmt The exceptions for those columns. */
public Swinger[] newSwingers(JTypeTableModel model,
String[] scol, Swinger[] swingers)
{
	int n = model.getColumnCount();
	Swinger[] sfmt2 = new Swinger[n];
	
	// Set up specialized formatters
	if (scol != null)
	for (int i=0; i<scol.length; ++i) {
		int col = model.findColumn(scol[i]);
		sfmt2[col] = swingers[i];
	}
	
	// Fill in defaults
	for (int i=0; i<n; ++i) if (sfmt2[i] == null) {
		sfmt2[i] = newSwinger(model.getJType(0, i), model.getColumnName(i));
	}

	return sfmt2;
}
}
