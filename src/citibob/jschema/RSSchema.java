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
 * RSSchema.java
 *
 * Created on December 30, 2007, 6:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.sql.SqlType;
import citibob.sql.SqlTypeSet;
import java.sql.*;

/**
 *
 * @author citibob
 */
public class RSSchema extends ConstSqlSchema
{

/** Creates a new instance of RSSchema */
public RSSchema(ResultSet rs, SqlSchema[] typeSchemas, String[] keyFields, SqlTypeSet tset)
throws SQLException
{
	ResultSetMetaData md = rs.getMetaData();
	table = null;
	cols = new Column[md.getColumnCount()];
	for (int i=0; i<md.getColumnCount(); ++i) {
		int i1 = i+1;
		SqlType sqlType = tset.getSqlType(md, i1);
		if (sqlType == null) System.out.println(
			"ERROR RSSchema(): no SqlType for column " + md.getColumnName(i1) + "(JDBC Type #" + md.getColumnType(i1) + " " + md.getColumnTypeName(i1) + ")");
		cols[i] = new SqlCol(sqlType,
			md.getColumnLabel(i1), false);
	}
	
	// Set up data types (and key fields)
	copyFrom(typeSchemas);

	// Set up key fields
	if (keyFields != null) {
		for (int i=0; i<cols.length; ++i) {
			SqlCol col = (SqlCol)cols[i];
			col.key = false;
		}
		for (int i=0; i<keyFields.length; ++i) {
	//System.out.println("Find col key field: " + keyFields[i]);
			SqlCol col = (SqlCol)cols[findCol(keyFields[i])];
			col.key = true;
		}
	}
}

}
