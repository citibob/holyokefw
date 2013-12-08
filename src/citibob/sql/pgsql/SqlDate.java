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

import java.text.*;
import java.util.*;
import java.sql.*;

public class SqlDate extends citibob.types.JDate
implements citibob.sql.SqlDateType
{
// Assumes SQL dates are stored without a timezone.
DateFormat sqlFmt;

// -----------------------------------------------------
private void setFmt() {
	sqlFmt = new SimpleDateFormat("yyyy-MM-dd");
	sqlFmt.setCalendar(cal);
}
public SqlDate(Calendar cal, boolean nullable) {
	super(cal,nullable);
	setFmt();
}
//public SqlDate(boolean nullable) {
//	super(nullable);
//	setFmt();
//}

/** @param stz TimeZone of dates stored in database. */
public SqlDate(String stz, boolean nullable) {
	super(stz, nullable);
	setFmt();
}
public SqlDate(TimeZone tz, boolean nullable) {
	super(tz, nullable);
	setFmt();
}
//public SqlDate() {
//	super();
//	setFmt();
//}
// -----------------------------------------------------
// -----------------------------------------------------

/** Convert an element of this type to an Sql string for use in a query */
public String toSql(Object o)
{
	java.util.Date ts = (java.util.Date)o;
	return ts == null ? "null" : '\'' + sqlFmt.format(ts) + '\'';
}
	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "date"; }

public boolean isInstance(Object o)
{
	if (o == null) return nullable;
	if (!(o instanceof java.util.Date)) return false;
//
//	return true;
// TODO: This is too strict, it just results in class
	java.util.Date dt = (java.util.Date)o;
	cal.setTime(dt);
	return (
		cal.get(Calendar.HOUR_OF_DAY) == 0 &&
		cal.get(Calendar.MINUTE) == 0 &&
		cal.get(Calendar.SECOND) == 0 &&
		cal.get(Calendar.MILLISECOND) == 0);
}
// ==================================================	
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, int col) throws SQLException
{
	try {
		String s = rs.getString(col);
		if (s == null) return null;
		return sqlFmt.parse(s);
	} catch(java.text.ParseException e) {
		throw new SQLException(e.getMessage());
	}
}
/** Reads the date with the appropriate timezone. */
public java.util.Date get(java.sql.ResultSet rs, String col) throws SQLException
{
	return get(rs, rs.findColumn(col));
}
// ==========================================================
//public static List makeDateList(Date first, Date last, long periodMS)
//{
//	ArrayList ret = new ArrayList();
//	Date dt = (Date)first.clone();
//	while (dt.getTime() <= last.getTime()) {
//		ret.add(dt);
//		dt = new java.sql.Date(dt.getTime() + periodMS);
//	}
//	return ret;
//}



}

	

