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
//package citibob.swing.table;
//
//import java.util.*;
//import javax.swing.table.*;
//import javax.swing.event.*;
//
//
//
///** Stores a 2-column table; data is [rownum, object].  Quick lookup from obj --> rownum is maintained. */
//public class ObjTableModel extends FixedColTableModel
//{
//
//// Column definitions...
//public static final int OBJ = 1;
//
//IdentityHashMap rowNumByObj = new IdentityHashMap();
//
//public ObjTableModel()
//{
//	super(2,
//		new Class[] {Integer.class, Object.class},
//		new String[] {"rownum", "object"}
//	);
//}
//
//public int addRow(Object obj)
//{
//	Object[] row = new Object[numCols];
//	row[OBJ] = obj;
//	return super.addRow(row);
//}
//
///** poor man's listener to superclass... */
//public void fireTableRowsInserted(int firstRow, int lastRowI)
//{
//System.out.println("ObjTableModel.fireTableRowsInserted: " + firstRow + " " + lastRowI);
//	for (int i = firstRow; i <= lastRowI; ++i) {
//		Object[] row = getRow(i);
//System.out.println("Object " + row[OBJ] + " --> row " + row[ROWNUM]);
//		rowNumByObj.put(row[OBJ], row[ROWNUM]);
//	}
//System.out.println("Now we call " + getListeners(TableModelListener.class).length + " listeners");
//	super.fireTableRowsInserted(firstRow, lastRowI);
//}
//
///** poor man's listener to superclass... */
//public void fireTableRowsDeleted(int firstRow, int lastRowI)
//{
//	for (int i = firstRow; i <= lastRowI; ++i) {
//		Object[] row = getRow(i);
//		rowNumByObj.remove(row[OBJ]);
//	}
//	super.fireTableRowsInserted(firstRow, lastRowI);
//}
//
//public int getRowNum(Object obj)
//{
//	Integer ii = (Integer)rowNumByObj.get(obj);
//	if (ii == null) return -1;
//	return ii.intValue();
//}
//
//}
