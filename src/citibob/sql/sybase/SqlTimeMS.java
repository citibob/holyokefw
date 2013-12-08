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
package citibob.sql.sybase;

import java.text.*;
import java.util.*;
import java.sql.*;

/** Represents times as an Integer --- ms since the beginning of the day */
public class SqlTimeMS implements citibob.sql.SqlType
{
boolean nullable = true;

// -----------------------------------------------------
public SqlTimeMS(boolean nullable)
{
	this.nullable = nullable;
}
public SqlTimeMS()
	{ this(true); }
// -----------------------------------------------------
/** Java class used to represent this type */
public Class getObjClass()
	{ return Integer.class; }

/** Convert an element of this type to an Sql string for use in a query */
public String toSql(Object o)
{
	if (o == null) return "null";
	System.out.println("o.class = " + o.getClass());
	return SqlTimeMS.sql((Integer)o);
}
	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "int"; }

public boolean isInstance(Object o)
{
	if (o == null) return nullable;
	return (o instanceof Integer);
}
// ==================================================	
private static DateFormat sqlFmt;
static {
	try {
		sqlFmt = new SimpleDateFormat("HH:mm:ss.SSS");
		sqlFmt.setTimeZone(TimeZone.getTimeZone("GMT"));
	} catch(Exception e) {
		e.printStackTrace();
		System.exit(-1);
	}
}
public static String sql(Integer Time)
{
	return Time == null ? "null" : sql(Time.intValue());
}
public static String sql(int time)
	{ return '\'' + sqlFmt.format(new java.util.Date((long)time)) + '\''; }


public Object get(java.sql.ResultSet rs, String col) throws SQLException
{ return get(rs, rs.findColumn(col)); }

/** Reads the time */
public Object get(java.sql.ResultSet rs, int col) throws SQLException
{
	try {
		String s = rs.getString(col);
		if (s == null) return null;
		return (int)(sqlFmt.parse(s).getTime());
	} catch(java.text.ParseException e) {
		throw new SQLException(e.getMessage());
	}
}
///** Reads the date with the appropriate timezone. */
//public int get(java.sql.ResultSet rs, String col) throws SQLException
//{
//	return (Integer)get(rs, rs.findColumn(col));
//}

//public static void main(String[] args) throws Exception
//{
//	String s = "24:00:00.000";
//	java.util.Date dt = sqlFmt.parse(s);
//	System.out.println(dt);
//	long l = dt.getTime();
//	int i = (int)l;
//	System.out.println(l);
//	System.out.println(i);
//}
}


