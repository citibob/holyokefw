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

package citibob.swing.calendar;

/**
 *
 * @author citibob
 */
public class ReverseSpinnerDateModel extends javax.swing.SpinnerDateModel {
	
boolean enabled = true;

public void setEnabled(boolean e)
{
	this.enabled = e;
}
	
public   Object  getNextValue() {
	if (enabled) return super.getPreviousValue();
	return super.getValue();
}
public    Object  getPreviousValue()  {
	if (enabled) return super.getNextValue();
	return super.getValue();
}
   
}
