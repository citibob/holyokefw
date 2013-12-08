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

import java.util.*;
import java.sql.*;
import java.text.*;

public class SqlTimestamp extends citibob.sql.ansi.SqlTimestamp
{

public static final SqlTimestamp gmt = new SqlTimestamp(
	TimeZone.getTimeZone("GMT"), true);
public static final SqlTimestamp gmtNotNull = new SqlTimestamp(
	TimeZone.getTimeZone("GMT"), false);
// -----------------------------------------------------
protected void setFmt() {
	sqlFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	sqlFmt.setCalendar(cal);
	sqlParse = sqlFmt;
}
public SqlTimestamp(Calendar cal, boolean nullable) {
	super(cal,nullable);
}

public SqlTimestamp(TimeZone tz, boolean nullable) {
	super(tz, nullable);
}

/** @param stz TimeZone of timestamps stored in database. */
public SqlTimestamp(String stz, boolean nullable)
	{ this(TimeZone.getTimeZone(stz), nullable); }
public SqlTimestamp(String stz) { this(stz, true); }

// -----------------------------------------------------
/** Convert an element of this type to an Sql string for use in a query */
public String toSql(Object o)
{
	java.util.Date ts = (java.util.Date)o;
	return ts == null ? "null" : ("'" + sqlFmt.format(ts) + '\'');
}
// ==================================================	
/** Reads the date with the appropriate timezone. */
}
