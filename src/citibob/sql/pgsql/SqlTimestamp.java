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

import java.util.*;
import java.sql.*;
import java.text.*;

public class SqlTimestamp extends citibob.types.JDate
implements citibob.sql.SqlDateType
{

java.text.DateFormat sqlFmt, sqlParse;
static DateFormat gmtFmt;

static {
	gmtFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	gmtFmt.setTimeZone(TimeZone.getTimeZone("GMT"));
}
// -----------------------------------------------------
private void setFmt() {
	sqlFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	sqlFmt.setCalendar(cal);
	sqlParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	sqlParse.setCalendar(cal);
}
public SqlTimestamp(Calendar cal, boolean nullable) {
	super(cal,nullable);
	setFmt();
}
//public SqlTimestamp(boolean nullable) {
//	super(nullable);
//	setFmt();
//}

public SqlTimestamp(TimeZone tz, boolean nullable) {
	super(tz, nullable);
	setFmt();
}
	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "timestamp"; }
/** @param stz TimeZone of timestamps stored in database. */
public SqlTimestamp(String stz, boolean nullable)
	{ this(TimeZone.getTimeZone(stz), nullable); }
public SqlTimestamp(String stz) { this(stz, true); }

//public SqlTimestamp() {
//	super();
//	setFmt();
//}
// -----------------------------------------------------
/** Convert an element of this type to an Sql string for use in a query */
public String toSql(Object o)
{
	java.util.Date ts = (java.util.Date)o;
	return ts == null ? "null" : ("TIMESTAMP '" + sqlFmt.format(ts) + '\'');
}
// ==================================================	
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, int col) throws SQLException
{
	try {
		String s = rs.getString(col);
		if (s == null) return null;
		int dot = s.indexOf('.');
		if (dot < 0) return sqlParse.parse(s);
		else {
			java.util.Date dt = sqlParse.parse(s.substring(0,dot));
			double frac = Double.parseDouble("0." + s.substring(dot+1));
			int ms = (int)(frac * 1000);
			return new java.util.Date(dt.getTime() + ms);
		}
	} catch(java.text.ParseException e) {
		throw new SQLException(e.getMessage());
	}
//	try {
//		String s = rs.getString(col);
//		if (s == null) return null;
//		return sqlParse.parse(s);
//	} catch(java.text.ParseException e) {
//		throw new SQLException(e.getMessage());
//	}
}
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, String col) throws SQLException
{
	return get(rs, rs.findColumn(col));
}
public java.util.Date truncate(java.util.Date dt)
{ return dt; }
// ===========================================================
public static String gmt(java.util.Date ts)
{
	return ts == null ? "null" : ("TIMESTAMP '" + gmtFmt.format(ts) + '\'');	
}
}
