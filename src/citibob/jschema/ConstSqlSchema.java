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
import citibob.types.JType;
import java.util.*;

public class ConstSqlSchema
extends ConstSchema
implements SqlSchema
{

protected String table;
protected int schemaType = ST_TABLE;

//public List getPrototypes()
//{
//	ArrayList ls = new ArrayList(cols.length);
//	for (int i = 0; i < cols.length; ++i) {
//		ls.add(cols[i].getType().getPrototype());
//	}
//	return ls;
//}

public ConstSqlSchema() {
	// Default table name same as class
	table = getClass().getSimpleName();
}
/** @Deprecated */
public ConstSqlSchema(Column[] cols, String table)
{
	this(table, cols);
}

public ConstSqlSchema(String table, Column... cols)
{
	this.table = table;
	this.cols = cols;
}

public ConstSqlSchema(Column... cols)
{
	this.table = null;
	this.cols = cols;
}

/** Give it a series of (name,jtype) pairs */
public static ConstSqlSchema newSchema(Object... objs)
{
	Column[] cols = new Column[objs.length / 2];
	for (int i=0; i<objs.length; i += 2) {
		String name;
		JType jtype = null;
		SqlType sqltype = null;
		boolean sql = false;
		if (objs[i] instanceof String) {
			name = (String)objs[i];
			if (objs[i+1] instanceof SqlType)
			{
				sqltype = (SqlType) objs[i+1];
				sql = true;
			}
			else
			{
				jtype = (JType)objs[i+1];
				sql = false;
			}
		} else {
			if (objs[i] instanceof SqlType)
			{
				sqltype = (SqlType) objs[i];
				sql = true;
			}
			else
			{
				jtype = (JType)objs[i];
				sql = false;
			}			
			name = (String)objs[i+1];
		}
		if (sql) cols[i/2] = new SqlCol(sqltype, name);
		else cols[i/2] = new Column(name, jtype);
	}
	return new ConstSqlSchema(cols);
}

public String getDefaultTable()
	{ return table; }

public int getType() { return schemaType; }
}
