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

import javax.swing.*;
import java.lang.reflect.*;

/**
 *
 * @author citibob
 */
public class FocusArrowButton extends javax.swing.plaf.basic.BasicArrowButton
{
boolean xFocusTraversable = false;

  public FocusArrowButton(int direction)
  { super(direction); }

public void setFocusTraversable(boolean b)
{ xFocusTraversable = b; }
//public boolean isFocusTraversable()
//{
////	Class c = this.getClass();
////	c = c.getSuperclass();
////	c = c.getSuperclass();		// JButton
////	try {
////		Method m = c.getMethod("isFocusTraversable", null);
////		Boolean b = (Boolean)m.invoke(this, null);
////		return b.booleanValue();
////	} catch(Exception e) {
////		return false;
////	}
//
//	return xFocusTraversable;
//}
	
}
