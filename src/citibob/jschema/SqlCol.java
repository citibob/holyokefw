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

import citibob.sql.SqlType;

/** Represents one column in a Schema. */
public class SqlCol extends Column
{

//protected boolean key;

public SqlCol(String name, SqlType type)
	{ this(type, name, false); }
public SqlCol(SqlType type, String name)
	{ this(type, name, false); }
public SqlCol(SqlType type, String name, boolean key)
{
	super(name, type, key);
}
// --------------------------------------------------------------------
/** Copies pertinent information from another column. */
public void copyFrom(Column col)
{
	super.copyFrom(col);
	SqlCol scol = (SqlCol)col;
	key = scol.key;
}

/** Type of this column */
public SqlType getSqlType()
	{ return (SqlType)jType; }

///** Is this a key column? */
//public boolean isKey()
//	{ return key; }

/** Default value for column when inserting new row in buffers. 
 This method will be overridden. */
public Object getDefault()
	{ return null; }

// ====================================================================
// Convenience Functions
/** Convenience function */
public String toSql(Object o) { return ((SqlType)jType).toSql(o); }


}
