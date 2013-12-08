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
 * SqlSwingerMap.java
 *
 * Created on March 18, 2006, 8:24 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql.pgsql;

import citibob.swingers.JavaSwingerMap;
import citibob.swing.typed.Swinger;
import citibob.swing.typed.*;
import citibob.sql.*;
import citibob.swing.sql.SqlBoolSwinger;
import citibob.swingers.JDateSwinger;
import citibob.swing.sql.SqlIntegerSwinger;
import citibob.swing.sql.SqlStringSwinger;
//import citibob.swing.pgsql.SqlTimestampSwinger;
import citibob.sql.pgsql.*;
import citibob.swing.sql.SqlLongSwinger;
import citibob.types.JType;
import java.util.*;


/**
 *
 * @author citibob
 */
public class PgsqlSwingerMap extends JavaSwingerMap
{
	
/**
 * Creates a new instance of SqlSwingerMap 
 @param tz The time zone the application is running in (and dates should be DISPLAYED in.)
 */
public PgsqlSwingerMap(final TimeZone tz) {
	super(tz);
	
	// SqlBool
	this.addMaker(SqlBool.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlBoolSwinger((SqlBool)sqlType);
	}});
	
	// SqlDate --- stored in native TimeZone, render in same TimeZone as is stored.
	this.addMaker(SqlDate.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		SqlDateType jt = (SqlDateType)jType;
		return new JDateSwinger(jt,
			new String[] {"MM/dd/yyyy", "yy-MM-dd", "yyyy-MM-dd", "MM/dd/yy", "MMddyy", "MMddyyyy"},
			"", jt.getTimeZone(),
			citibob.swing.calendar.JCalendarDateOnly.class);
	}});

	// SqlInteger
	this.addMaker(SqlInteger.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlIntegerSwinger((SqlInteger)sqlType);
	}});
	
	// SqlLong
	this.addMaker(SqlLong.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlLongSwinger((SqlLong)sqlType);
	}});

	// SqlString
	this.addMaker(SqlString.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new SqlStringSwinger((SqlString)sqlType);
	}});

	// SqlTime
	this.addMaker(SqlTime.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType sqlType) {
		return new JDateSwinger((SqlTime)sqlType, new String[] {"HH:mm:ss"}, "", tz, null);
	}});

	// SqlTimestamp --- always stored in GMT, render in application native TimeZone
	this.addMaker(SqlTimestamp.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JDateSwinger((SqlDateType)jType,
			new String[] {"MM/dd/yyyy hh:mm a", "MM/dd/yy hh:mm a", "MMddyy hh:mm a", "MMddyyyy hh:mm a"},
			"", tz,
			citibob.swing.calendar.JCalendarDateOnly.class);
	}});
}
	
}
