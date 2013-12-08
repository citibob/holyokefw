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
 * JavaSwingerMap.java
 *
 * Created on March 18, 2006, 8:00 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swingers;

import citibob.types.JDate;
import citibob.sql.*;
import citibob.swing.typed.*;
import citibob.swing.typed.Swinger;
import citibob.types.*;
import citibob.text.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class JavaSwingerMap extends BaseSwingerMap
{



public JavaSwingerMap(final TimeZone tz)
{
Maker maker;

	// =========== Standard Java classes
	this.addMaker(String.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JStringSwinger();
	}});

	// Integer
	maker = new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JIntegerSwinger();
	}};
	this.addMaker(Integer.class, maker);
	maker = new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JIntegerSwinger();
	}};
	this.addMaker(int.class, maker);
	
	// Double
	maker = new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new TypedTextSwinger(new JavaJType(Double.class), new DivDoubleSFormat());
	}};
	this.addMaker(Double.class, maker);
	maker = new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new TypedTextSwinger(new JavaJType(Double.class),
			new NoNullSFormat(new DivDoubleSFormat(), new Double(0)));
	}};
	this.addMaker(double.class, maker);

	// Boolean
	maker = new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new BoolSwinger();
	}};
	this.addMaker(Boolean.class, maker);
	this.addMaker(boolean.class, maker);
	
	// File
	this.addMaker(JFile.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JFileSwinger((JFile)jType);
	}});

	// ================== Other Java Classes
	// TimeZone
	this.addMaker(java.util.TimeZone.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new TypedTextSwinger(new JavaJType(TimeZone.class), new TimeZoneSFormat());
	}});

	
	// SqlDate --- stored in native TimeZone, render in same TimeZone as is stored.
	this.addMaker(JDate.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		JDateType jt = (JDateType)jType;
		return new JDateSwinger(jt,
			new String[] {"MM/dd/yyyy", "yyyy-MM-dd", "MM/dd/yy", "MMddyy", "MMddyyyy"},
			"", tz,
			citibob.swing.calendar.JCalendarDateOnly.class);
	}});
	
//	// SqlDate --- stored in native TimeZone, render in same TimeZone as is stored.
//	this.addMaker(JTimestamp.class, new BaseSwingerMap.Maker() {
//	public Swinger newSwinger(JType jType) {
//		JDateType jt = (JDateType)jType;
//		return new JDateSwinger(jt,
//			new String[] {"MM/dd/yyyy", "yyyy-MM-dd", "MM/dd/yy", "MMddyy", "MMddyyyy"},
//			"", tz,
//			citibob.swing.calendar.JCalendarDateOnly.class);
//	}});
	
	// SqlDate --- stored in native TimeZone, render in same TimeZone as is stored.
	this.addMaker(JDay.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		JDay jt = (JDay)jType;
		return new JDaySwinger(jt,
			new String[] {"MM/dd/yyyy", "yyyy-MM-dd", "MM/dd/yy", "MMddyy", "MMddyyyy"},
			"",
			citibob.swing.calendar.JCalendarDateOnly.class);
	}});

	// Short class names
	this.addMaker(Class.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new TypedTextSwinger(new JavaJType(Class.class), new ClassSFormat());
	}});
	
	// =========== JTypes
	this.addMaker(JEnum.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JEnumSwinger((JEnum)jType);
	}});
	this.addMaker(JEnumMulti.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new JEnumMultiSwinger((JEnumMulti)jType);
	}});
	
	// =========== SQL Types
	// SqlNumeric
	this.addMaker(SqlNumeric.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new SqlNumericSwinger((SqlNumeric)jType);
	}});
	
	// SqlEnum
	this.addMaker(SqlEnum.class, new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new SqlEnumSwinger((SqlEnum)jType);
	}});
	
	
	// =========== Special Named Columns
	this.addMaker("__status__", new BaseSwingerMap.Maker() {
	public Swinger newSwinger(JType jType) {
		return new citibob.swingers.TypedTextSwinger(JavaJType.jtInteger, new StatusSFormat());
	}});

}
	
}
