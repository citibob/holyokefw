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
 */

package citibob.swing.table;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;

/* * Class JavaDoc */
public class RowHeaderRenderer extends JLabel implements ListCellRenderer {
private JTable table;
private Border selectedBorder;
private Border normalBorder;
private Font selectedFont;
private Font normalFont;
RowHeaderRenderer(JTable table) {
	this.table = table;
	normalBorder = UIManager.getBorder("TableHeader.cellBorder");
	selectedBorder = BorderFactory.createRaisedBevelBorder();
	final JTableHeader header = table.getTableHeader();
	normalFont = header.getFont();
	selectedFont = normalFont.deriveFont(normalFont.getStyle() | Font.BOLD);
	setForeground(header.getForeground());
	setBackground(header.getBackground());
	setOpaque(true);
	setHorizontalAlignment(CENTER);
}

public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	if (table.getSelectionModel().isSelectedIndex(index)) {
		setFont(selectedFont);
		setBorder(selectedBorder);
	} else {
		setFont(normalFont);
		setBorder(normalBorder);
	}
//	String label = String.valueOf(index + 1);
	String label = "*";
	setText(label);
	return this;
}
}