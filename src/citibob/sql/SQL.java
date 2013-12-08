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
package citibob.sql;

import java.sql.*;
import java.text.*;
import java.util.*;
import citibob.jschema.*;

/** Low-level utilities to help generate SQL query string on the fly.  Example of the use of this class:<pre>
java.sql.Connection db;
java.sql.Statement st = db.createStatement();
String city;
...
String sql =
    " select * from places" +
    " where city = " + SQL.sString(city);
ResultSet rs = st.executeQuery(sql);
</pre>

<p>Note: similar functionality may be obtained using the java.sql.PreparedStatement class.</p>

 */
public class SQL
{

///** Converts a Java String to a form appropriate for inclusion in an
//SQL query.  This is done by single-quoting the input and repeating any
//single qoutes found in it (SQL convention for quoting a quote).  If
//the input is null, the string "null" is returned. */
//	public static String sString(String s)
//	{
//		if (s == null) return "null";
//		StringBuffer str = new StringBuffer();
//		str.append('\'');
//		int len = s.length();
//		for (int i = 0; i < len; ++i) {
//			char ch = s.charAt(i);
//			switch(ch) {
//				case '\'' : str.append("''"); break;
//				default: str.append(ch); break;
//			}
//		}
//		str.append('\'');
//		return str.toString();
//	}
//
//
//static final DateFormat dateFormat =
//	new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
///** Converts a Java java.util.Date to a form appropriate for inclusion in an SQL query.  The date is written in standard SQL fashion (single-quoted): dd-MMM-yyyy HH:mm:ss.  If the input is null, the string "null" is returned.  Careful of the difference between java.util.Date and java.sql.Date */
//public static String sDate(java.util.Date d)
//{
//	if (d == null) return "null";
//	return "'" + dateFormat.format(d) + "'";
//}
//
// /** Converts a Java java.util.Date (date &amp; time) to a form
//appropriate for inclusion in an SQL query.  The timestamp is written
//in standard SQL fashion (single-quoted): dd-MMM-yyyy HH:mm:ss.   If the input is null, the string "null" is returned. */
// public static String sDTime(java.util.Date t)
//{
//	if (t == null) return "null";
//	return "'" + timestampFormat.format(t) + "'";
//}
//
// static final DateFormat timestampFormat =
//	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////	new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
// /** Converts a Java java.util.Timestamp (date &amp; time) to a form
//appropriate for inclusion in an SQL query.  The timestamp is written
//in standard SQL fashion (single-quoted): dd-MMM-yyyy HH:mm:ss.   If the input is null, the string "null" is returned. */
// public static String sTimestamp(Timestamp t)
//{
//	if (t == null) return "null";
//	return "'" + timestampFormat.format(t) + "'";
//}
//
// /** Converts a Java boolean to a form appropriate for inclusion in an SQL query.  The result is either "true" or "false" */
//public static String sBool(boolean i)
//{ return i ? "true" : "false"; }
//
// /** Converts a Java Boolean object to a form appropriate for inclusion in an SQL query.  The result is either "true" or "false", or "null" if the input is null. */
//public static String sBool(Boolean i)
//{
//	if (i == null) return "null";
//	return sBool(i.booleanValue());
//}
//
// /** Converts a Java integer value to a form appropriate for inclusion in an SQL query.  This is done by simply converting to a String. */
//public static String sInt(int i)
//{ return ""+i; }
//
// /** Converts a Java Integer object to a form appropriate for inclusion in an SQL query.  This is done by simply converting to a string, or by returning the string "null" if the input is null. */
//public static String sInt(Integer i)
//{
//	if (i == null) return "null";
//	return i.toString();
//}
//
// /** Converts a Java long value to a form appropriate for inclusion in an SQL query.  This is done by simply converting to a String. */
//public static String sLong(long i)
//{ return ""+i; }
//
// /** Converts a Java integer value to a form appropriate for inclusion in an SQL query.  This is done by simply converting to a String.  This conversion is special for database ID fields: the string "null" is returned if the input is less than or equal to zero. */
//public static String sID(int i)
//	{ return (i <= 0 ? "null" : ""+i); }
//
///** Convert timestamp from getTimestamp() --> display form */
///*
//public static String vTimestamp(Timestamp t, java.text.DateFormat df)
//	{ return (t == null ? "" : df.format(t)); }
//*/
//// ----------------------------------------------------------
///** @deprecated */
//public static Connection openDB(String driver,
//String url, String user, String password)
//{
//    try {
//            // Load the JDBC PostgreSQL driver.
//            Class.forName(driver);
//    } catch(ClassNotFoundException e) {
//            System.out.println("Cannot load the JDBC driver: " + driver);
//            e.printStackTrace();
//            System.exit(-1);
//    }
//
//    try {
//            // Open a long-lived connection to the database;
//            // it is used on every interaction, to look up cookies.
//            return DriverManager.getConnection(url, user, password);
//    } catch(SQLException e) {
//            System.out.println("Cannot open JcpServer database: " + url);
//            e.printStackTrace();
//            System.exit(-1);
//    }
//	return null;
//}
// -----------------------------------------------------------
public static int readInt(Statement st, String sql) throws SQLException
{
	ResultSet rs = null;
	rs = st.executeQuery(sql);
	rs.next();
	int ret = rs.getInt(1);
	return ret;
}
public static String readString(Statement st, String sql) throws SQLException
{
	ResultSet rs = null;
	rs = st.executeQuery(sql);
	rs.next();
	String ret = rs.getString(1);
	return ret;
}
// -----------------------------------------------------------
/** Adds a bunch of key/value pairs to a query; this happens when we're
 making INSERT queries from Wizard data. */
public static void addColumns(ConsSqlQuery sql, HashMap v, SqlSchema schema)
{
	int ncol = schema.size();
	for (int i=0; i<ncol; ++i) {
		SqlCol col = (SqlCol)schema.getCol(i);
		Object val = v.get(col.getName());
		if (val == null) val = col.getDefault();
		if (val == null) continue;
		sql.addColumn(col.getName(), col.toSql(val));
	}
}

public static ConsSqlQuery newInsertQuery(String mainTable, HashMap v, SqlSchema schema)
{
	ConsSqlQuery sql = new ConsSqlQuery(mainTable, ConsSqlQuery.INSERT);
	addColumns(sql, v, schema);
	return sql;
}

public static String intList(int[] ids)
{
	if (ids == null || ids.length == 0) return null;
	StringBuffer sb = new StringBuffer("(" + ids[0]);
	for (int i=1; i<ids.length; ++i) sb.append("," + ids[i]);
	sb.append(")");
	return sb.toString();
}

public static String intList(Set<Integer> ids)
{
	if (ids == null || ids.size() == 0) return null;
	Iterator<Integer> ii = ids.iterator();
	StringBuffer sb = new StringBuffer("(" + ii.next());
	for (; ii.hasNext();) sb.append("," + ii.next());
	sb.append(")");
	return sb.toString();
}

}
