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
public class DefaultSFormatMap extends SFormatMap
{

HashMap<Object,Object> makerMap = new HashMap();
	
// ===========================================================
//protected void addConst(SqlSFormat swing)
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


private SFormat getSFormatObj(Object o, JType t)
{
	if (o instanceof SFormat) return (SFormat)o;
	return ((Maker)o).newSFormat(t);	
}

/** Gets a new SFormat for a cell of a certain type, depending on whether or not it is editable. */
public SFormat newSFormat(JType t, String colName)
//public SFormat newSFormat(JType t, boolean editable)
{
	// Try by name
	if (colName != null) {
		Object o = makerMap.get(colName);
		if (o != null) return getSFormatObj(o, t);
	}
	
	// Index on general class of the JType,
	// or on its underlying Java Class (for JavaJType)
	Class klass = t.getClass();
	if (klass == JavaJType.class) klass = ((JavaJType) t).getObjClass();
System.err.println("newSFormat: " + klass);

	for (;;) {
System.err.println("     trying: " + klass);
		Object o = makerMap.get(klass);
		if (o != null) return getSFormatObj(o, t);
		
		// Increment the loop...
		klass = klass.getSuperclass();
		if (klass == null || klass == Object.class) break;
	}

	// No SFormat found, punt...
System.err.println("Failed to find a SFormat");
	return null;
}





}
