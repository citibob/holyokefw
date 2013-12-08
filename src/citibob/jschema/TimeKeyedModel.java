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
 * TimeKeyedModel.java
 *
 * Created on February 11, 2006, 11:48 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.jschema;

//import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.*;
import citibob.types.KeyedModel;

/**
 * Sets up a keyed model with a bunch of SqlTime objects as keys, Strings as display objects.
 * @author citibob
 */
public class TimeKeyedModel extends KeyedModel
{

static DateFormat fmt = new SimpleDateFormat("HH:mm");

/** Creates a new instance of TimeKeyedModel */
public TimeKeyedModel(Date first, Date last, long periodMS)
{
	init(first, last, periodMS);
}
void init(Date first, Date last, long periodMS)
{
	Date dt = (Date)first.clone();
	while (dt.getTime() <= last.getTime()) {
		String lab = fmt.format(dt);
		this.addItem(dt, lab, null);
System.out.println("Adding: " + lab + " (key = " + dt);
		dt.setTime(dt.getTime() + periodMS);
	}
}

public TimeKeyedModel(int firstHr, int firstMin, int lastHr, int lastMin, long periodMS)
{
	Calendar cal = new GregorianCalendar();
	cal.setTimeInMillis(0);
	
	cal.set(Calendar.HOUR_OF_DAY, firstHr);
	cal.set(Calendar.MINUTE, firstMin);
	java.util.Date first = cal.getTime();
	
	cal.set(Calendar.HOUR_OF_DAY, lastHr);
	cal.set(Calendar.MINUTE, lastMin);
	java.util.Date last = cal.getTime();

	init(first, last, periodMS);
	
}

public static void main(String[] args)
{
	TimeKeyedModel tt = new TimeKeyedModel(0, 15, 10, 30, 15L*60L*1000L);
}

}
