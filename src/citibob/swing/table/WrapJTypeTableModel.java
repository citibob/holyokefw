/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.types.JType;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author citibob
 */
public class WrapJTypeTableModel implements JTypeTableModel
{
protected JTypeTableModel sub;
	
public WrapJTypeTableModel(JTypeTableModel sub)
{ this.sub = sub; }

// =========================== TableModel
public int getRowCount() { return sub.getRowCount(); }

public int getColumnCount() { return sub.getColumnCount(); }

public String getColumnName(int columnIndex)
{ return sub.getColumnName(columnIndex); }

public Class<?> getColumnClass(int columnIndex)
{ return sub.getColumnClass(columnIndex); }

public boolean isCellEditable(int rowIndex, int columnIndex)
{ return sub.isCellEditable(rowIndex, columnIndex); }

public Object getValueAt(int rowIndex, int columnIndex)
{ return sub.getValueAt(rowIndex, columnIndex); }

public void setValueAt(Object aValue, int rowIndex, int columnIndex)
{ sub.setValueAt(aValue, rowIndex, columnIndex); }

public void addTableModelListener(TableModelListener l)
{ sub.addTableModelListener(l); }

public void removeTableModelListener(TableModelListener l)
{ sub.removeTableModelListener(l); }

// =========================== CitibobTableModel
/** These, you will get for free if you subclass AbstractTableModel. */
public void fireTableChanged(TableModelEvent e)
{ sub.fireTableChanged(e); }

/** Finds a column's index by name --- also implemented in AbstractTableModel.
 Returns -1 if column names doesn't exist or is null. */
public int findColumn(String name)
{ return sub.findColumn(name); }

/** Gets the value at a specific column and row, with column referenced by name */
public Object getValueAt(int row, String col)
{ return sub.getValueAt(row, col); }

public void setValueAt(Object val, int row, String col)
{ sub.setValueAt(val, row, col); }

public boolean isVisible(int col)
{ return sub.isVisible(col); }
// ========================== JTypeTableModel
/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
public JType getJType(int row, int col)
{ return sub.getJType(row,col); }

/** Convenience function */
public JType getJType(int row, String col)
{ return sub.getJType(row, col); }
	
public JTypeTableModel getModelU() { return sub; }

public int findColumnU(String colU) { return sub.findColumnU(colU); }

public int getColU(int col) { return sub.getColU(col); }

public int colU2col(int colU) { return sub.colU2col(colU); }

}


	

