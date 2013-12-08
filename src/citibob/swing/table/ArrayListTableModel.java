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

import citibob.types.JType;
import java.util.*;
import javax.swing.table.*;

public abstract class ArrayListTableModel<TT> extends FixedColTableModel
{

protected ArrayList<TT> data = new ArrayList();	// ArrayList<Record>

public ArrayListTableModel(String[] colNames, JType[] types, boolean[] editable)
{
	super(colNames, types, editable);
}

public TT getRow(int rowIx) { return data.get(rowIx); }

public int getRowCount()
	{ return data.size(); }

public void clear() {
//	data.clear();
	data = new ArrayList();
	super.fireTableDataChanged();
}

//public Object getValueAt(int row, int column)
//{
//	if (column >= getColumnCount()) return null;
//	Object[] r = (Object[])rowData.get(row);
//	if (r == null) return null;
//	return r[column];
//}
//
//public void setValueAt(Object val, int row, int col)
//{
//	// if (col >= numCols || row >= getColumnCount) return;
//	if (!types[col].isInstance(val))
//		throw new ClassCastException("Bad class " + val.getClass() + "(expected JType " + types[col]);
//	Object[] r = (Object[])rowData.get(row);
//	if (r == null) return;
//	r[col] = val;
//	fireTableCellUpdated(row, col);
//}
//
///** Inserts rowData from [firstRow to lastRow], inclusive.  Fires tableRowsInserted. */
//public void insertRowsNoFire(int firstRow, int lastRowI)
//{
//	int lastRow = lastRowI + 1;
//	int oldSize = rowData.size();
//	int sizeDiff = lastRow - firstRow;
//	int numMoved = oldSize - firstRow;
//	int newSize = oldSize + sizeDiff;
//	rowData.ensureCapacity(newSize);
//	for (int i = numMoved-1; i >= 0; --i) {
//		int oldPlace = firstRow + i;
//		int newPlace = oldPlace + sizeDiff;
//		Object[] row = (Object[])rowData.get(oldPlace);
////		row[ROWNUM] = new Integer(newPlace);
////		rowData.set(newPlace, row);
//	}
//	for (int i = firstRow; i < lastRow; ++i) {
//		Object[] row = new Object[numCols];
////		row[ROWNUM] = new Integer(i);
////		rowData.set(i, row);
//	}
//}
//public void insertRows(int firstRow, int lastRowI)
//{
//	insertRowsNoFire(firstRow, lastRowI);
//	fireTableRowsInserted(firstRow, lastRowI);
//}
//
///** Returns row # of added row. */
//public int appendRow()
//{
//	int newr = getRowCount();
//	insertRows(newr, newr);
//	return newr;
//}
//
//public int addRowNoFire(Object[] row)
//{
//	int r = rowData.size();
////	row[ROWNUM] = new Integer(r);
//	rowData.add(row);
//	return r;
//}
//public int addRow(Object[] row)
//{
//	int r = addRowNoFire(row);
//	fireTableRowsInserted(r, r);
//	return r;
//}
//
///** Removes rows from [firstRow to lastRow], inclusive.  Fires tableRowsDeleted. */
//public void deleteRowsNoFire(int firstRow, int lastRowI)
//{
//	int lastRow = lastRowI + 1;
//	int sizeDiff = lastRow - firstRow;
//	int oldSize = rowData.size();
//	for (int i = firstRow; i < oldSize - sizeDiff; ++i) {
//		int oldPlace = i + sizeDiff;
//		int newPlace = i;
//		Object[] row = (Object[])rowData.get(oldPlace);
////		row[ROWNUM] = new Integer(newPlace);
//		rowData.set(newPlace, row);
//	}
//}
//public void deleteRows(int firstRow, int lastRowI)
//{
//	deleteRowsNoFire(firstRow, lastRowI);
//	fireTableRowsDeleted(firstRow, lastRowI);
//}
//
//
//public Object[] getRow(int r)
//	{ return (Object[])rowData.get(r); }

}
