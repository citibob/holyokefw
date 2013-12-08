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
 * TypedWidgetRenderer.java
 *
 * Created on November 7, 2007, 8:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swingers;

import citibob.swing.typed.*;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.text.*;
import citibob.swing.calendar.*;
import citibob.swing.table.*;
import java.awt.*;

/**
 *
 * @author citibob
 */
public class TypedWidgetRenderer implements TableCellRenderer
{
	protected TypedWidget tw;
	public TypedWidgetRenderer(TypedWidget tw) { this.tw = tw;}
	public Component getTableCellRendererComponent(
	JTable table, Object value , boolean isSelected, boolean hasFocus,
	int row, int column) {
		tw.setValue(value);
		return (Component)tw;
	}

}
