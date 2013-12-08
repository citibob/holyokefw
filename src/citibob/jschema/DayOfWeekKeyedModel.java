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
 * Sets up a keyed model for days of the week, 1 = Sunday (Java convention)
 * @author citibob
 */
public class DayOfWeekKeyedModel extends KeyedModel
{

static DateFormat fmt = new SimpleDateFormat("HH:mm");

/** Creates a new instance of TimeKeyedModel */
public DayOfWeekKeyedModel()
{
	this.addItem(new Integer(-1), "<none>", null);
	this.addItem(new Integer(1), "Sun", null);
	this.addItem(new Integer(2), "Mon", null);
	this.addItem(new Integer(3), "Tue", null);
	this.addItem(new Integer(4), "Wed", null);
	this.addItem(new Integer(5), "Thr", null);
	this.addItem(new Integer(6), "Fri", null);
	this.addItem(new Integer(7), "Sat", null);
}

}
