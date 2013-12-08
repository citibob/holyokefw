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

/* * Class JavaDoc */
public class TableRowHeaderModel extends AbstractListModel {
	private JTable table;
	public TableRowHeaderModel(JTable table) {
		this.table = table;
	}

	public int getSize() {
		return table.getRowCount();
	}

	public Object getElementAt(int index) {
		return null;
	}
}
