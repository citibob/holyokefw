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
package citibob.sql.pgsql;

import java.sql.*;

public class SqlString extends citibob.sql.ansi.SqlString
//		implements citibob.sql.SqlType
{

	public SqlString(int limit, boolean nullable)
	{
		super(limit, nullable);
	}
	public SqlString(int limit)
	{
		super(limit);
	}
	public SqlString(boolean nullable)
		{ this(0, nullable); }
	public SqlString()
		{ this(0, true); }

//	boolean nullable = true;
//
//	/* Limit on string length */
//	int limit;
//
//	public int getLimit() { return limit; }
//	
//	public SqlString(int limit, boolean nullable)
//	{
//		this.limit = limit;
//		this.nullable = nullable;
//	}
//	public SqlString(int limit)
//	{
//		this(limit, true);
//	}
//	/** Returns the SQL string that encodes this data type. */
//	public String sqlType()
//		{ return "varchar(" + limit + ")"; }
//	public SqlString(boolean nullable)
//		{ this(0, nullable); }
//	public SqlString()
//		{ this(0, true); }
//
//	/** Java class used to represent this type */
//	public Class getObjClass()
//		{ return String.class; }
//
//	/** Convert an element of this type to an Sql string for use in a query */
//	public String toSql(Object o)
//		{ return SqlString.sql((String)o); }
//
//	public Object get(java.sql.ResultSet rs, int col) throws SQLException
//		{ return rs.getObject(col); }
//	public Object get(java.sql.ResultSet rs, String col) throws SQLException
//		{ return rs.getObject(col); }
//
///** Converts a Java String to a form appropriate for inclusion in an
//Sql query.  This is done by single-quoting the input and repeating any
//single qoutes found in it (Sql convention for quoting a quote).  If
//the input is null, the string "null" is returned. */
//	public static String sql(String s, boolean quotes)
//	{
//		if (s == null) return "null";
//		StringBuffer str = new StringBuffer();
//		if (quotes) str.append('\'');
//		int len = s.length();
//		for (int i = 0; i < len; ++i) {
//			char ch = s.charAt(i);
//			switch(ch) {
//				case '\'' : str.append("''"); break;
//				default: str.append(ch); break;
//			}
//		}
//		if (quotes) str.append('\'');
//		return str.toString();
//	}
//	public static String sql(String s)
//		{ return sql(s, true); }
//
//	public boolean isInstance(Object o)
//	{
//		if (nullable && o == null) return true;
//		if (!(o instanceof String)) return false;
//		String s = (String)o;
//		if (limit > 0 && s.length() > limit) return false;
//		return true;
//	}

}
