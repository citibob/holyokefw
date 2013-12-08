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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.typed;

import citibob.types.JDate;
import citibob.swing.calendar.JCalendarDateOnly;
import citibob.text.DateSFormat;
import citibob.types.JType;
import citibob.util.Day;
import citibob.util.DayConv;

/**
 *
 * @author fiscrob
 */
public class JTypedDayChooser extends JTypedDateChooser
{

//JDateType jType;
JType dayType;
DateSFormat sformat;

public void setJType(JType dayType,
DateSFormat sformat)
{
	this.dayType = dayType;
	this.sformat = sformat;
	super.setJType(new JDate(dayType.isInstance(null)), sformat,
		new JCalendarDateOnly());
}

// TypedWidget
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
	return new Day(DayConv.midnightToDayNum(
		super.getValueInMillis(), sformat.getDisplayTZ()));
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o)
{
	Day day = (Day)o;
	super.setValueInMillis(DayConv.toMS(
		day.getDayNum(), sformat.getCalendar()));
}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o) { return dayType.isInstance(o); }


}
