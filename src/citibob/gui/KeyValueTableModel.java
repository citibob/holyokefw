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
package citibob.gui;

import javax.swing.table.*;

/**
 * Displays a bunch of key-value pairs; key in the left-hand column,
 * value in the right-hand column.
 */
public class KeyValueTableModel extends DefaultTableModel
{
	
/** Creates a new instance of KeyValueTableModel */
public KeyValueTableModel() {
	super(new String[] {"Key", "Val"}, 0);
}

public void set(int row, Object val)
{
	//if (getRowCount() <= row) this.setRowCount(row+1);
	setValueAt(val, row, 1);
}
public void set(int row, Object key, Object val)
{
	if (getRowCount() <= row) this.setRowCount(row+1);
	setValueAt(key,row,0);
	setValueAt(val, row, 1);
}
public void set(Object key, Object val)
{
	for (int i=0; i<this.getRowCount(); ++i) {
		Object iKey = this.getValueAt(i, 0);
		if ((iKey == null && key == null) || iKey.equals(key)) {
			this.setValueAt(val, i, 1);
			return;
		}
	}
	this.addRow(new Object[] {key, val});
}
public boolean getBoolean(int row)
{
	return((Boolean)this.getValueAt(row,1)).booleanValue();
}
public java.util.Date getDate(int row)
{
	return((java.util.Date)this.getValueAt(row,1));
}
}
