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
package citibob.sql.ansi;

import citibob.sql.pgsql.*;
import java.sql.SQLException;

public class SqlLong implements citibob.sql.SqlType
{
	
	boolean nullable = true;
	
	public SqlLong(boolean nullable) { this.nullable = nullable; }
	public SqlLong() { this(true); }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Long.class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return (o == null ? "null" : o.toString()); }
	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "bigint"; }

	public boolean isInstance(Object o)
		{ return (o instanceof Long || (nullable && o == null)); }
	public Object get(java.sql.ResultSet rs, int col) throws SQLException
		{ return rs.getObject(col); }
	public Object get(java.sql.ResultSet rs, String col) throws SQLException
		{ return rs.getObject(col); }
// ================================================
	public static String sql(Long ii)
		{ return ii == null ? "null" : ii.toString(); }
	public static String sql(long i)
		{ return ""+i; }

}
