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
 * JDBTextField.java
 *
 * Created on May 15, 2003, 7:14 PM
 */

package citibob.swing.typed;

/**
 Like a JTextArea, except it faithfully represents NULL values.  A <i>null string</i> is chosen to represent null values in the widget for input and output.  If the superclass's value is set to the null string, then this widget will report a value of <i>null</i> (null pointer).
 * @author  citibob
 */
public class JNullTextArea extends javax.swing.JTextArea {
	/** String used to display null values. */
	String nullDisplay = "";
	/** Set string used to display null values. */
	public void setNullDisplay(String nd) { nullDisplay = nd; }
	/** Return string used to display null values. */
	public String getNullDisplay() { return nullDisplay; }
	
	/** Return the text in the widget (overrides method in ancestor class). */
	public String getText()
	{
		String s = super.getText();
		if (nullDisplay.equals(s)) return null;
		return s;
	}
	/** Set the text in the widget. (overrides method in ancestor class). */
	public void setText(String s)
		{ super.setText(s == null ? nullDisplay : s); }

	/** Creates a new instance of JDBTextField */
	public JNullTextArea() {
		super();
	}

}
