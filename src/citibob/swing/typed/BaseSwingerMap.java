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
public class BaseSwingerMap extends citibob.swing.typed.SwingerMap
{

//HashMap constMap = new HashMap();
HashMap<Object,Maker> makerMap = new HashMap();
	
// ===========================================================
protected static interface Maker
{
//	/** Gets a new swinger for a cell of a certain type, depending on whether or not it is editable. */
//	Swinger newSwinger(JType sqlType, boolean editable);
	/** Gets a new swinger for an editable cell. */
	Swinger newSwinger(JType sqlType);
}
// ===========================================================
//protected void addConst(SqlSwinger swing)
//{
//	constMap.put(swing.getSqlType().getClass(), swing);
//}

/** Stores a maker by JType subclass. */
protected void addMaker(Class klass, Maker maker)
{
	makerMap.put(klass, maker);
}
/** Stores a maker by column name */
protected void addMaker(String colName, Maker maker)
{
	makerMap.put(colName, maker);
}


/** Gets a new swinger for a cell of a certain type, depending on whether or not it is editable. */
public Swinger newSwinger(JType t, String colName)
//public Swinger newSwinger(JType t, boolean editable)
{
	Maker m = null;
//if ("DTime".equals(colName)) {
//	System.out.println("hoi");
//}
	
	// Try by name
	if (colName != null) {
		m = makerMap.get(colName);
		if (m != null) return m.newSwinger(t);
	}
	
	// Index on general class of the JType,
	// or on its underlying Java Class (for JavaJType)
	Class klass = t.getClass();
	if (klass == JavaJType.class) klass = ((JavaJType) t).getObjClass();
//System.err.println("newSwinger: " + klass);

	for (;;) {
//System.err.println("     trying: " + klass);
		m = (Maker)makerMap.get(klass);
		if (m != null) break;
		klass = klass.getSuperclass();
		if (klass == null || klass == Object.class) break;
	}
	if (m != null) return m.newSwinger(t);

	// No swinger found, punt...
System.err.println("Failed to find a swinger for column " + colName + "(class = " + klass + ") this = " + this);
	return null;
}



// ==================================================================
// SFormatMap
public SFormat newSFormat(JType t, String colName)
{
	Swinger swinger = newSwinger(t, colName);
	if (swinger == null) return null;
	return swinger.getSFormat();
}





}
