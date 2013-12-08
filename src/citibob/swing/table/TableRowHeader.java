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
/** JTable Row Headers
 * Taken from: http://www.jguru.com/faq/view.jsp?EID=87579
 * By: Maksim Kovalenko
 * Oct 23, 2001
 * Usage:
 *    Suppose scrollPane contains jTable, then:
 *    scrollPane.setRowHeaderView(new TableRowHeader(jTable));
 */

package citibob.swing.table;

import citibob.swing.table.RowHeaderRenderer;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
/* * Class JavaDoc */

public class TableRowHeader extends JList {
	private JTable table;
	public TableRowHeader(JScrollPane pane, JTable table, int preferredWidth) {
		super(new TableRowHeaderModel(table));
		this.table = table;
		setFixedCellHeight(table.getRowHeight());
//		setFixedCellWidth(preferredHeaderWidth());
		setFixedCellWidth(preferredWidth);
		setCellRenderer(new RowHeaderRenderer(table));
		setSelectionModel(table.getSelectionModel());
		this.setBackground(pane.getBackground());
}

/** * Returns the bounds of the specified range of items in JList * coordinates. Returns null if index isn't valid. * * @param index0 the index of the first JList cell in the range * @param index1 the index of the last JList cell in the range * @return the bounds of the indexed cells in pixels */
public Rectangle getCellBounds(int index0, int index1) {
	Rectangle rect0 = table.getCellRect(index0, 0, true);
	Rectangle rect1 = table.getCellRect(index1, 0, true);
	int y, height;
	if (rect0.y < rect1.y) {
		y = rect0.y;
		height = rect1.y + rect1.height - y;
	} else {
		y = rect1.y;
		height = rect0.y + rect0.height - y;
	} return new Rectangle(0, y, getFixedCellWidth(), height);
}

// assume that row header width should be big enough to display row number Integer.MAX_VALUE completely
private int preferredHeaderWidth() {
	JLabel longestRowLabel = new JLabel("65356");
	JTableHeader header = table.getTableHeader();
	longestRowLabel.setBorder(header.getBorder());
	//UIManager.getBorder("TableHeader.cellBorder"));
	longestRowLabel.setHorizontalAlignment(JLabel.CENTER);
	longestRowLabel.setFont(header.getFont());
	return longestRowLabel.getPreferredSize().width;
}
}


