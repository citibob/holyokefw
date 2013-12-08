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
package citibob.text;

import citibob.sql.*;
import citibob.swing.typed.*;
import citibob.swing.table.JTypeTableModel;
import citibob.types.JType;
import citibob.types.JavaJType;
import java.util.*;
import java.text.*;


/**
 * Maps SqlType objects to various formatters, etc. for String output.
 * This is a lot like SwingerMap, but much simpler.
 * @author citibob
 */
public abstract class SFormatMap
{
	
protected static interface Maker
{
//	/** Gets a new SFormat for a cell of a certain type, depending on whether or not it is editable. */
//	SFormat newSFormat(JType sqlType, boolean editable);
	/** Gets a new SFormat for an editable cell. */
	SFormat newSFormat(JType sqlType);
}


public abstract SFormat newSFormat(JType t, String colName);

/** Create SFormat for an entire set of columns */
public SFormat[] newSFormats(JTypeTableModel model)
{
	int n = model.getColumnCount();
	SFormat[] sfmt = new SFormat[n];
	for (int i=0; i<n; ++i) sfmt[i] = newSFormat(model.getJType(0, i), model.getColumnName(i));
	return sfmt;
}

/** Create SFormat for an entire set of columns
@param scol names of columns for exceptions.
@param sfmt The exceptions for those columns. */
public SFormat[] newSFormats(JTypeTableModel model,
String[] scol, SFormat[] SFormats)
{
	int n = model.getColumnCount();
	SFormat[] sfmt2 = new SFormat[n];
	
	// Set up specialized formatters
	if (scol != null)
	for (int i=0; i<scol.length; ++i) {
		int col = model.findColumn(scol[i]);
		sfmt2[col] = SFormats[i];
	}
	
	// Fill in defaults
	for (int i=0; i<n; ++i) if (sfmt2[i] == null) {
		sfmt2[i] = newSFormat(model.getJType(0, i), model.getColumnName(i));
	}

	return sfmt2;
}

/** Intelligent creation of an SFormat from a variety of different types of specificaitons. */
public SFormat newSFormat(Object fmtSpec)
{
	if (fmtSpec == null) return null;
	if (fmtSpec instanceof SFormat)
		return (SFormat)fmtSpec;
	if (fmtSpec instanceof Format)
		return new FormatSFormat((Format)fmtSpec);
	if (fmtSpec instanceof JType)
		return newSFormat((JType)fmtSpec, null);
	if (fmtSpec instanceof Class)
		return newSFormat(new JavaJType((Class)fmtSpec), null);
	return newSFormat(new JavaJType(fmtSpec.getClass()), null);
}
}
