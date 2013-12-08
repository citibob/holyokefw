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
//package citibob.jschema;
//
//import javax.swing.event.*;
//import citibob.swing.table.*;
//import citibob.text.AbstractSFormat;
//import citibob.types.JType;
//import citibob.types.JavaJType;
//import static citibob.jschema.RowStatusConst.*;
//
//public class StatusSchemaBuf extends AbstractJTypeTableModel
//implements TableModelListener, SchemaBuf.Listener
//{
//
//SchemaBuf sb;
//// ---------------------------------------------------------
///** Constructor
// @param Column i in this table maps to column colMap[i] in underlying table. */
//public StatusSchemaBuf(SchemaBuf sb)
//{
//	this.sb = sb;
//	sb.addTableModelListener(this);
//	sb.addSchemaBufListener(this);
//}
//// -------------------------------------------------------------------
////public Object getValueAt(int row, String col)
////	{ return getValueAt(row, findColumn(col)); }
////public void setValueAt(Object val, int row, String col)
////	{ setValueAt(val, row, findColumn(col)); }
////public JType getJType(int row, String col)
////	{ return getJType(row, findColumn(col)); }
//// --------------------------------------------------------
//public int findColumn(String colName)
//{
//	if ("__rowno__".equals(colName)) return 0;
//	if ("__status__".equals(colName)) return 1;
//	return sb.findColumn(colName) + 2;
//}
//public Class getColumnClass(int colIndex)
//{
//	switch(colIndex) {
//		case 0 : return Integer.class;
//		case 1 : return Integer.class;
//		default : return sb.getColumnClass(colIndex-2);
//	}
//}
//public JType getJType(int row, int col)
//{
//	switch(col) {
//		case 0 : return JavaJType.jtInteger;
//		case 1 : return JavaJType.jtInteger;
//		default : return sb.getJType(row, col-2);
//	}
//}
//public String getColumnName(int colIndex)
//{
//	switch(colIndex) {
//		case 0 : return "__rowno__";
//		case 1 : return "__status__";
//		default : return sb.getColumnName(colIndex-2);
//	}
//}
//
///** Allow editing of all non-key fields. */
//public boolean isCellEditable(int rowIndex, int columnIndex)
//{
//	switch(columnIndex) {
//		case 0 : return false;
//		case 1 : return false;
//		default : return sb.isCellEditable(rowIndex, columnIndex-2);
//	}
//}
//
//public int getColumnCount()
//{
//	return sb.getColumnCount() + 2;
//}
//public Object getValueAt(int rowIndex, int colIndex)
//{
//	Object val;
//	switch(colIndex) {
//		case 0 : return new Integer(rowIndex + 1);
//		case 1 : return new Integer(sb.getStatus(rowIndex));
//		default : return sb.getValueAt(rowIndex, colIndex-2);
//	}
//}
//public void setValueAt(Object val, int rowIndex, int colIndex)
//{
//	if (colIndex <= 1) return;
//	sb.setValueAt(val, rowIndex, colIndex-2);
//}
//
//public int getRowCount()
//	{ return sb.getRowCount(); }
//
////public List getPrototypes()
////{
////	List l = sb.getPrototypes();
////	l.add(0, new Integer(0));
////	return l;
////}
//
//// =========================================================
//public void statusChanged(int row)
//{
//	// Pass along as a column-0 event
//	this.fireTableCellUpdated(row, 0);
//}
//public void tableChanged(TableModelEvent e_u) 
//{
//	TableModelEvent e_t;
//
//	int type = e_u.getType();
//	int col_u = e_u.getColumn();
//	switch(type) {
//	case TableModelEvent.UPDATE :
//		if (col_u == TableModelEvent.ALL_COLUMNS) {
//			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
//		} else {
//			// Column in this TableModel is 1 greater than in underlying model
//			int col_t = col_u + 2;
//System.out.println("StatusSchemaBuf.tableChanged: underlying " + col_u +
//	" --> " + col_t + "(" + sb.getColumnName(col_u));
////			this.fireTableChanged(new TableModelEvent(
////				this, col_t, e_u.getFirstRow(), e_u.getLastRow(), type));
//			for (int i = e_u.getFirstRow(); i <= e_u.getLastRow(); ++i) {
//				this.fireTableCellUpdated(i, col_t);
//			}
//		}
//	break;
//	case TableModelEvent.INSERT :
//		this.fireTableRowsInserted(e_u.getFirstRow(), e_u.getLastRow());
//	case TableModelEvent.DELETE :
//		this.fireTableRowsDeleted(e_u.getFirstRow(), e_u.getLastRow());
//	break;
//	}
//}
//
//// ===============================================================
///** Used to format the status column */
//public static class StatusSFormat extends AbstractSFormat {
//public String valueToString(Object value) throws java.text.ParseException {
//	if (value instanceof Integer) {
//		String s = "";
//		int status = ((Integer)value).intValue();
//		if ((status & INSERTED) != 0) s += "I";
//		if ((status & DELETED) != 0) s += "D";
//		if ((status & CHANGED) != 0) s += "*";
//		return s;
//	} else {
//		return "<ERROR";
//	}
//}}
//
//}
