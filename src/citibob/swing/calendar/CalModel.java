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
 * CalModel.java
 *
 * Created on February 12, 2006, 12:15 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.calendar;


import java.util.*;

/**
 * Serves as the model class for the JCalendar editor --- both standalone and dropdown (in JDateChooser).
 * @author citibob
 */
public class CalModel extends CalModelMVC
{

Calendar cal;
//, finalCal;
int tmpDay;
boolean nullValue;			// true if we're holding a null date value now...
boolean nullable = true;			// Are we even allowed to set null values?

//private static Calendar getCalInstance()
//{
//	Calendar c = Calendar.getInstance();
//	c.set(Calendar.HOUR_OF_DAY, 0);
//	c.set(Calendar.MINUTE, 0);
//	c.set(Calendar.SECOND, 0);
//	c.set(Calendar.MILLISECOND, 0);
//	return c;
//}


public void setNullable(boolean n)
{
	nullable = n;
	if (!nullable) nullValue = false;
}
public boolean isNullable() { return nullable; }

// ============================================================
public CalModel(TimeZone tz, boolean nullable)
{
	this.cal = Calendar.getInstance((tz));
	this.nullable = nullable;
}
public CalModel(citibob.types.JDateType jdt)
{
	this(jdt.getTimeZone(), jdt.isInstance(null));
}
// ============================================================
public Calendar getCal()
	{ return cal; }
public Date getCalTime()
	{ return cal.getTime(); }
public Date getTime()
{
	if (nullValue) return null;
	return cal.getTime();
}

/** Clear the null field, setting date back to what it was before
 *this was nulled. */
public void setNullNoFire(boolean nll)
{
	if (!nullable) return;	// Not legal if we're not nullable...
	if (nll == nullValue) return;
	nullValue = nll;
}
public void setNull(boolean nll)
{
	setNullNoFire(nll);
	fireNullChanged();
}
public boolean isNull() { return nullValue; }
		
//public Calendar getFinalCal()
//{ return finalCal; }

public void setTmpDay(int day)
{ tmpDay = day; }
public void useTmpDay()
{ if (tmpDay != -1) set(Calendar.DAY_OF_MONTH, tmpDay); }

// ===========================================================
boolean inCalChanged = false;
public void fireCalChanged()
{
	if (inCalChanged) return;
	inCalChanged = true;
	super.fireCalChanged();
	inCalChanged = false;
}
boolean inNullChanged = false;
public void fireNullChanged()
{
	if (inNullChanged) return;
	inNullChanged = true;
	super.fireNullChanged();
	inNullChanged = false;
}
//public void fireNullChanged()
//{
////	System.out.println("Null changed to: " + getTime());
//	super.fireNullChanged();
//}
// ===========================================================
// ==== Stuff from Calendar
public void  add(int field, int amount)
{
	cal.add(field, amount);
	fireCalChanged();
}
public void clear()
{
	cal.clear();
	fireCalChanged();
}
public void clear(int field) 
{
	cal.clear();
	fireCalChanged();
}
public void  roll(int field, boolean up)
{
	cal.roll(field,up);
	fireCalChanged();
}
public   void  roll(int field, int amount) 
{
	cal.roll(field,amount);
	fireCalChanged();
}
public   void  set(int field, int value) 
{
	cal.set(field,value);
	fireCalChanged();
}
public   void  set(int year, int month, int date) 
{
	cal.set(year,month,date);
	fireCalChanged();
}
public   void  set(int year, int month, int date, int hour, int minute) 
{
	cal.set(year,month,date,hour,minute);
	fireCalChanged();
}
public   void  set(int year, int month, int date, int hour, int minute, int second) 
{
	cal.set(year,month,date,hour,minute,second);
	fireCalChanged();
}
/** Set time in the underlying Calendar, don't affect null status. */
public void setCalTime(Date date)
{
	cal.setTime(date);
	fireCalChanged();
}
public void setTime(Date date)
{
	if (date == null) {
		setNull(true);
	} else {
		setTimeInMillis(date.getTime());
	}
}
public void setTimeInMillis(long ms)
{
// THis caused a bug when going from null to non-null; caused it to not redisplay
//	if (ms == cal.getTimeInMillis()) return;

	cal.setTimeInMillis(ms);
	setNullNoFire(false);
	fireNullChanged();
	fireCalChanged();
}
//   void  setTimeInMillis(long millis) 
//{
//	cal.setTimeInMillis(millis);
//	fireCalChanged();
//}
	
}
