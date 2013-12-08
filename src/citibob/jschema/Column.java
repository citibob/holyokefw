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
package citibob.jschema;

import citibob.types.JType;
import citibob.types.JDateType;
//import citibob.sql.JType;

/** Represents one column in a Schema. */
public class Column
{

protected JType jType;
protected String name;
protected boolean key;
	
public boolean isKey() { return key; }

/** Copies pertinent information from another column. */
public void copyFrom(Column scol)
{
	jType = scol.getType();
	key = scol.isKey();
}

public Column(String name, JType type)
	{ this(type, name, false); }
public Column(JType type, String name)
	{ this(type, name, false); }
public Column(String name, JType type, boolean key)
	{ this(type, name, key); }
public Column(JType type, String name, boolean key)
{
	this.jType = type;
	this.name = name;
	this.key = key;
}
//public Column(JType type, String name, String label)
//{
//	this.jType = type;
//	this.name = name;
////	this.label = label;
//}
// --------------------------------------------------------------------
/** Type of this column */
public JType getType()
	{ return jType; }

/** Name of column in Sql */
public String getName()
	{ return name; }

//public String getLabel()
//	{ return label; }


// ====================================================================
// Convenience Functions
/** Convenience function */
public java.util.TimeZone getTimeZone() { return ((JDateType)getType()).getTimeZone(); }
/** Convenience function */
public JDateType getJDateType() { return ((JDateType)getType()); }
/** Convenience function */
public java.util.Date newDate() { return getJDateType().truncate(new java.util.Date()); }

public static Column[] newColumns(String[] names)
{
	Column[] cols = new Column[names.length];
	for (int i=0; i<names.length; ++i) {
		cols[i] = new Column(null, names[i]);
	}
	return cols;
}

}
