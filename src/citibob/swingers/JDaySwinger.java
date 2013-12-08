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

package citibob.swingers;

import citibob.sql.*;
import citibob.swing.typed.*;
import citibob.types.JDateType;
import javax.swing.text.*;
import java.text.*;
import java.util.*;
import citibob.swing.calendar.*;
import javax.swing.*;
import citibob.text.*;
import citibob.types.JDay;
import citibob.util.Day;

/**
 *
 * @author citibob
 */
public class JDaySwinger extends AbstractSwinger
{
protected Class jcalClass;

// -------------------------------------------------------------------------
/** Creates a new instance of TypedWidgetSTFactory */
public JDaySwinger(JDay jt, String[] sfmt, String nullText, Class jcalClass) {
	super(jt, new DateSFormat(new DateFlexiFormat(sfmt, Day.gmt), nullText), false);
	this.jcalClass = jcalClass;
}

public void configureWidget(TypedWidget tw)
{
	if (tw instanceof JTypedDayChooser) {
		JTypedDayChooser jtdc = (JTypedDayChooser)tw;
		try {
			DateSFormat dsfmt = (DateSFormat)super.getSFormat();
			jtdc.setJType((JDay)jType, dsfmt);
		} catch(Exception e) {
			// Shouldn't happen
			e.printStackTrace();
		}
	} else if (tw instanceof TextTypedWidget) {
		TextTypedWidget tf = (TextTypedWidget)tw;
		tf.setJType(jType, getSFormat());
	}
}


/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget createWidget()
	{ return new JTypedDayChooser(); }

}
