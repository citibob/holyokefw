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
package citibob.swing.table;

import javax.swing.event.*;
import javax.swing.table.*;


/** Used by derived TableModels that modify the column model to pass through (while permuting) events. */
public class ColDependTableModelListener implements TableModelListener
{

JTypeTableModel model;
int[][] dependMap;

public ColDependTableModelListener(JTypeTableModel model, int[][] dependMap)
{
	this.model = model;
	this.dependMap = dependMap;
//System.err.println("ColPermute: dependMap = " + dependMap);
//if (dependMap == null) System.exit(-1);
}

public void tableChanged(TableModelEvent e) 
{

//System.out.println("ColPermute: " + e);

	int oldCol = e.getColumn();
	int type = e.getType();
	if (type == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS) {
		// Re-map the columns.
		int[] cols = dependMap[e.getColumn()];
		if (cols == null) return;
		for (int i = 0; i < cols.length; ++i) {
System.out.println(model + ": UPDATE: (" + cols[i] + ", " + e.getFirstRow() + ", " + e.getLastRow() + ")");
			model.fireTableChanged(new TableModelEvent(
				model, cols[i], e.getFirstRow(), e.getLastRow(), type));
		}
	} else {
System.out.println(model + ": INSERT/DELETE: (" + e.getColumn()	 + ", " + e.getFirstRow() + ", " + e.getLastRow() + ")");
		// INSERT or DELETE: Just pass through row info...
		model.fireTableChanged(new TableModelEvent(
			model, e.getColumn(), e.getFirstRow(), e.getLastRow(), type));
	}
}


}
