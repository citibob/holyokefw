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
 * SFormat.java
 *
 * Created on February 26, 2007, 12:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import javax.swing.SwingConstants;

/**
 * SFormat = Simple Format or String Format.  A subset of the JFormattedTextString.AbstractFormatter functionality.
 * @author citibob
 */
public interface SFormat
{
	public static int LEFT = SwingConstants.LEFT;
	public static int CENTER = SwingConstants.CENTER;
	public static int RIGHT = SwingConstants.RIGHT;
	public static int LEADING = SwingConstants.LEADING;
	public static int TRAILING = SwingConstants.TRAILING;
	
	public Object stringToValue(String text) throws java.text.ParseException;
	public String valueToString(Object value) throws java.text.ParseException;

	/** Should equal valueToString(null); */
	public String getNullText();
	
	/** Returns how we prefer this text to be aligned. */
	public int getHorizontalAlignment();
}
