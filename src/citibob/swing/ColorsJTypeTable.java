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
 * ColorsJTypeTable.java
 *
 * Created on September 17, 2006, 11:18 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import citibob.sql.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import java.awt.*;

/**
 * TODO: use prepareRenderer instead
 * See: http://www.exampledepot.com/egs/javax.swing.tabe/Tips.html?l=rel
 * @author citibob
 * @deprecated
 */
public abstract class ColorsJTypeTable extends JTypeTable implements TableCellRenderer
{
	
public abstract Color getBack(boolean isSelected, boolean hasFocus, int row, int col);
public abstract Color getFore(boolean isSelected, boolean hasFocus, int row, int col);

public TableCellRenderer getCellRenderer(int row, int col)
{
	return this;
}


// Implementation of TableCellRenderer interface
public Component getTableCellRendererComponent(JTable table, Object value,
boolean isSelected, boolean hasFocus, int row, int column) {
	TableCellRenderer rend = super.getCellRenderer(row, column);
	Component c = rend.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	c.setBackground(getBack(isSelected, hasFocus, row, column));
	c.setForeground(getFore(isSelected, hasFocus, row, column));
	return c;
}
}
