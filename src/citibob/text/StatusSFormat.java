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
 * StatusSFormat.java
 *
 * Created on December 30, 2007, 11:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import static citibob.jschema.RowStatusConst.*;

/** Used to format the status column */
public class StatusSFormat extends BaseSFormat
{
	public String valueToString(Object value) throws java.text.ParseException {
		if (value instanceof Integer) {
			String s = "";
			int status = ((Integer)value).intValue();
			if ((status & INSERTED) != 0) s += "I";
			if ((status & DELETED) != 0) s += "D";
			if ((status & CHANGED) != 0) s += "*";
			return s;
		} else {
			return "<ERROR>";
		}
	}
}
