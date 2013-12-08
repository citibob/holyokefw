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
 * BaseSqlTypeFactory.java
 *
 * Created on January 28, 2007, 9:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.*;
import java.util.TimeZone;
import static java.sql.Types.*;

/**
 *
 * @author citibob
 */
public abstract class SqlTypeSet
{
// ------------------------------------------------------
/** @param col the first column is 1, the second is 2, ...
 @returns an SqlType, given one of the basic types in java.sql.Types.  If N/A,
 or not yet implemented as an SqlType, returns null. */
public abstract SqlType getSqlType(int type, int precision, int scale, boolean nullable);
// ------------------------------------------------------
protected int dateStyle, timeStyle, tsStyle;
public static final int DS_DATE = 0;
public static final int DS_MS = 1;
public static final int DS_DAY = 2;		// not valid for tsStyle


protected TimeZone tz;
public SqlTypeSet(TimeZone tz, int dateStyle, int timeStyle, int tsStyle)
{
	this.tz = tz;
	this.dateStyle = dateStyle;
	this.timeStyle = timeStyle;
	this.tsStyle = tsStyle;

	date = newDate(true);
	dateNotNullable = newDate(false);
	timestamp = newTimestamp(true);
	timestampNotNullable = newTimestamp(false);
}
	
public SqlType getSqlType(ResultSet rs, int col) throws SQLException
{
	return getSqlType(rs.getMetaData(), col);
}
public SqlType getSqlType(ResultSetMetaData md, int col) throws SQLException
{
	boolean nullable = (md.isNullable(col) != ResultSetMetaData.columnNoNulls);
//System.out.println("col = " + col);
	return getSqlType(md.getColumnType(col), md.getPrecision(col), md.getScale(col), nullable);
}

public SqlDateType newTimestamp(boolean nullable)
	{ return (SqlDateType)getSqlType(TIMESTAMP,0,0, nullable); }
public SqlDateType newDate(boolean nullable)
	{ return (SqlDateType)getSqlType(DATE, 0,0, nullable); }

SqlDateType date, dateNotNullable, timestamp, timestampNotNullable;

public SqlDateType date() { return date; }
public SqlDateType dateNotNullable() { return dateNotNullable; }
public SqlDateType timestamp() { return timestamp; }
public SqlDateType timestampNotNullable() { return timestampNotNullable; }

}
