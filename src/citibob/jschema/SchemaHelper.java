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

import citibob.sql.ConsSqlQuery;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/** Extra methods for Schema, but not included in the interface. */
public class SchemaHelper
{

/** @deprecated */
public static void getSelectCols(Schema schema, ConsSqlQuery q, String table, String colPrefix)
{
	for (int col = 0; col < schema.size(); ++col) {
		SqlCol c = (SqlCol)schema.getCol(col);
		q.addColumn(table + "." + c.getName() +
			(colPrefix == null ? "" : " as " + colPrefix + "_" + c.getName()));
	}
}
// --------------------------------------------------------
/** Adds key fields to where clause; for delete and select queries.
 * For columns, this will just check the isKey() field and add itself
 * or not.  For records, will call getWhereKey on children
 * (unless the record has special knowledge about itself.) */
/** @deprecated */
public static void getWhereKey(Schema schema, ConsSqlQuery q, String table, Object[] whereKey)
{
	for (int col = 0; col < schema.size(); ++col) {
		SqlCol c = (SqlCol)schema.getCol(col);
		if (c.isKey()) {
			q.addWhereClause(table + "." + c.getName() + " = " +
				 c.toSql(whereKey[col]));
		}
	}
}
// ---------------------------------------------------------------------
/** @returns map[] such that column i in a is column map[i] i b */
public static int[] newSchemaMap(Schema a, Schema b)
{
	int[] map = new int[a.size()];
	for (int i=0; i<a.size(); ++i)
		map[i] = b.findCol(a.getCol(i).getName());
	return map;
}
/** @param colPrefix a prefix used in rs, but not in b */
public static int[] newSchemaMap(ResultSet rs, Schema b)
throws SQLException
{
	ResultSetMetaData md = rs.getMetaData();
	int[] map = new int[md.getColumnCount()+1];
	map[0] = -1;
	for (int i=1; i<=md.getColumnCount(); ++i)
		map[i] = b.findCol(md.getColumnLabel(i));
	return map;
}
public static int[] newSchemaMap(Schema a, ResultSet rs)
throws SQLException
{
	int[] map = new int[a.size()];
	for (int i=0; i<a.size(); ++i)
		map[i] = rs.findColumn(a.getCol(i).getName());
	return map;
}

}
