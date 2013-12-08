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
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package citibob.sql.ansi;
//
//import citibob.sql.SqlDateType;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.TimeZone;
//
///**
// * Does type conversion to Java long instead of Java Date.
// * @author fiscrob
// */
//public class SqlMSDateWrapper  implements SqlDateType
//{
//	SqlDateType sub;
//
///** Time Zone the data are stored in. */
//public TimeZone getTimeZone() { return sub.getTimeZone(); }
//
///** Returns a truncated version of the input; i.e. if this is a SqlDate,
// *then truncates off hour, minute, second. */
//public java.util.Date truncate(java.util.Date dt)
//{ return sub.truncate(dt); }
//
//public SqlMSDateWrapper(SqlDateType sub)
//	{
//		this.sub = sub;
//	}
//	
//	/** Java class used to represent this type.  NOTE: not all instances of that Java
//	 class will pass isInstance(). */
//	public Class getObjClass() { return Long.class; }
//
//	/** Is an object an instance of this type?  NOTE: isInstance(null) tells
//	 whether or not this field in the DB accepts null values. */
//	public boolean isInstance(Object o) {
//		if (o == null) return sub.isInstance(o);
//		return (o instanceof Long);
//	}
//	
//	/** Convert an element of this type to an Sql string for use in a query */
//	public String toSql(Object o)
//	{
//		if (o == null) return sub.toSql(null);
//		return sub.toSql(new java.util.Date(((Long)o).longValue()));
//	}
//
//	/** Returns the SQL string that encodes this data type. */
//	public String sqlType() { return sub.sqlType(); }
//
//	/** Read element of this type out of the result set (& convert appropriately to Java type). */
//	public Object get(java.sql.ResultSet rs, int col) throws SQLException
//	{
//		Date dt = (Date)sub.get(rs, col);
//		if (dt == null) return null;
//		return new Long(dt.getTime());
//	}
//	public Object get(java.sql.ResultSet rs, String col) throws SQLException
//	{
//		Date dt = (Date)sub.get(rs, col);
//		if (dt == null) return null;
//		return new Long(dt.getTime());
//	}
//}
