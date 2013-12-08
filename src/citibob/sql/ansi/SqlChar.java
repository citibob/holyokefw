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

import java.sql.*;
	
public class SqlChar implements citibob.sql.SqlType
{

	boolean nullable = true;

	public SqlChar(boolean nullable)
	{
		this.nullable = nullable;
	}
	public SqlChar()
	{
		this(true);
	}
	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "character"; }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Character.class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
	{
		if (o == null) return "null";
		if (o instanceof Character) return SqlChar.sql((Character)o);
		return SqlChar.sql(((String)o).charAt(0));
	}

	public Object get(java.sql.ResultSet rs, int col) throws SQLException
		{ return rs.getObject(col); }
	public Object get(java.sql.ResultSet rs, String col) throws SQLException
		{ return rs.getObject(col); }

/** Converts a Java String to a form appropriate for inclusion in an
Sql query.  This is done by single-quoting the input and repeating any
single qoutes found in it (Sql convention for quoting a quote).  If
the input is null, the string "null" is returned. */
	public static String sql(Character c, boolean quotes)
	{
		if (c == null) return "null";
		char ch = c.charValue();
		return sql(ch, quotes);
	}
	public static String sql(Character s)
		{ return sql(s, true); }
	public static String sql(char s)
		{ return sql(s, true); }

	public static String sql(char ch, boolean quotes)
	{
		StringBuffer str = new StringBuffer();
		if (quotes) str.append('\'');
		switch(ch) {
			case '\'' : str.append("''"); break;
			default: str.append(ch); break;
		}
		if (quotes) str.append('\'');
		return str.toString();
	}
	public boolean isInstance(Object o)
	{
		if (nullable && o == null) return true;
		return (o instanceof Character);
}

}
