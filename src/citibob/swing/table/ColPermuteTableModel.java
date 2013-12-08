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

import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.*;
import citibob.swing.typed.*;

import citibob.types.JType;
import java.io.*;

public class ColPermuteTableModel<TT>
extends AbstractJTypeTableModel<TT>
implements TableModelListener
{

protected JTypeTableModel<TT> modelU;

//final ArrayList prototypes = new ArrayList(NUMCOLS);

String[] colNames;
int[] colMap;		// Column map: i in this table --> colMap[i] in underlying
int[] iColMap;
//boolean[] editable;	// Is each column editable?  (NULL ==> use underlying default)
// ---------------------------------------------------------

protected ColPermuteTableModel() {}

/** Clone the bheavior of the underlying TableModel. */
public ColPermuteTableModel(JTypeTableModel modelU)
{
	this(modelU, null, (int[])null);
//	int ncol = modelU.getColumnCount();
//	String[] colNames = new String[ncol];
//	int[] colMap = new int[ncol];
//	for (int i=0; i<ncol; ++i) {
//		colNames[i] = modelU.getColumnName(i);
//		colMap[i] = i;
//	}
//	init(modelU, colNames, colMap, null);
}
/** Constructor
 @param Column i in this table maps to column colMap[i] in underlying table. */
public ColPermuteTableModel(JTypeTableModel modelU, String[] colNames, int[] colMap)
{
	init(modelU, colNames, colMap, true);
}
public ColPermuteTableModel(JTypeTableModel modelU, String[] sColNames, String[] colMap)
{
	init(modelU, sColNames, colMap, true);
}

///** @param modelU Underlying table model
// @param colNames Display names -- Null if you wish to just use names of underlying columns
// @param sColMap Names of underlying columns --- Null if wish to use all underlying columns
// @param editable Is each column editable?  If null, use underlying table's isEditable() function. */
//public ColPermuteTableModel(JTypeTableModel modelU,
//String[] colNames,			// Display names
//String[] sColMap,			// Underlying names
//boolean[] editable)			// Is each column editable?
//{
//	init(modelU, colNames, sColMap, editable, true);
//}




/** @param modelU Underlying table model
 @param colNames Display names -- Null if you wish to just use names of underlying columns.  If null,
underlying columns starting with "__" will not be used.
 @param sColMap Names of underlying columns --- Null if wish to use all underlying columns
 @param editable Is each column editable?  If null, use underlying table's isEditable() function.
@param forwardEvents If true, this should forward events when modelU changes. */
protected void init(JTypeTableModel modelU,
String[] colNames,			// Display names
String[] sColMap,			// Underlying names
//boolean[] editable,			// Is each column editable?
boolean forwardEvents)
{
//System.out.println("ColPermuteTableModel: this = " + this);
	int[] colMap;
	if (sColMap == null) {
		colMap = null;
	} else {
		colMap = new int[sColMap.length];
		for (int i = 0; i < colMap.length; ++i) {
//			for (int j = 0; j < modelU.getColumnCount(); ++j) {
				colMap[i] = (sColMap[i] == null ? -1 : modelU.findColumn(sColMap[i]));
				if (colMap[i] < 0) {
					System.out.println("Cannot find column " + i + ": " + sColMap[i]);
				}
				if (colMap[i] < 0 && sColMap[i] != null) {
					throw new ArrayIndexOutOfBoundsException("Cannot find column " + sColMap[i]);
				}
//if (colMap[i] < 0) {
//	System.out.println("hoi");
//}
//			}
		}
	}

	init(modelU, colNames, colMap, forwardEvents);
}
/** @param modelU Underlying table model
 @param xColNames Display names
 @param xColMap Index in underlying table of each column
 @param editable Is each column editable? */
private void init(JTypeTableModel modelU, String[] xColNames, int[] xColMap, boolean forwardEvents)
{
	if (xColMap == null) xColMap = TableModelUtils.defaultColMap(modelU);
	if (xColNames == null) {
		xColNames = new String[xColMap.length];
		for (int i=0; i<xColMap.length; ++i) {
			int colU = xColMap[i];
			if (colU >= 0) xColNames[i] = modelU.getColumnName(colU);
		}
	}

//	// Remove null columns -- they can get here if a null was included in colNames or colMap
//	int ncol = 0;
//	for (int i=0; i<xColMap.length; ++i) if (xColNames[i] != null && xColMap[i] != -1) ++ncol;
//	String[] colNames = new String[ncol];
//	int[] colMap = new int[ncol];
//	int j = 0;
//	for (int i=0; i<xColMap.length; ++i) {
//		if (xColNames[i] != null && xColMap[i] != -1) {
//			colNames[j] = xColNames[i];
//			colMap[j] = xColMap[i];
//			++j;
//		}
//	}
	colMap = xColMap;
	colNames = xColNames;
	
	this.modelU = modelU;
//	this.editable = editable;

	// Set up inverse column map
	iColMap = new int[modelU.getColumnCount()];
	for (int i=0; i < modelU.getColumnCount(); ++i) iColMap[i] = -1;
	for (int i=0; i < colMap.length; ++i) {
		if (colMap[i] != -1) iColMap[colMap[i]] = i;
	}


	if (forwardEvents) modelU.addTableModelListener(this);

//for (int i = 0; i < colMap.length; ++i) System.out.println("colMap["+i+"] = "+colMap[i]);
}
// -------------------------------------------------------------------
public int findColumn(String name)
{
	for (int i = 0; i < colNames.length; ++i) {
		if (colNames[i].equals(name)) return i;
	}
	return -1;
}
/** Finds a column by name in the UNDERLYING table model, then
reports its location in THIS table model.  This allows one to refer
to columns by their UNDERLYING name, not their display name.
 TODO: Maybe add a separate concept for actual name and display name in 
 the table model... */
public int findColumnU(String s)
{
	int col_u = modelU.findColumn(s);
	if (col_u < 0) {
		System.err.println("WARNING: ColPermuteTableModle.findColumnU: cannot find column named " + s);
		return col_u;
	}
	int col_t = iColMap[col_u];
	return col_t;
}

///** Given a column in the underlying table, returns the column in the outer table. */
//public int getColumnU(int col_u)
//{
//	int col_t = iColMap[col_u];
//	return col_t;	
//}

/** Column map: i in this table --> colMap[i] in underlying */
public int getColU(int col) { return colMap[col]; }

public int colU2col(int col_u) { return iColMap[col_u]; }

/** Gets the column class of a column named ``name'' in the underlying model. */
public Class getColumnClassU(String s)
	{ return modelU.getColumnClass(modelU.findColumn(s)); }
public JTypeTableModel getModelU() { return modelU; }


public String getColumnName(int col)
	{ return colNames[col]; }
public Class getColumnClass(int col)
{
	Class klass = modelU.getColumnClass(colMap[col]);
//System.out.println("ColPermuteTableModel.getColumnClass(" + col + ") = " + klass);
	return klass;
}

public boolean isCellEditable(int rowIndex, int columnIndex)
	{ return modelU.isCellEditable(rowIndex, colMap[columnIndex]); }

public int getColumnCount()
	{ return colMap.length; }
public int getRowCount()
	{ return modelU.getRowCount(); }

//public List getPrototypes()
//{
//	List pold = modelU.getPrototypes();
//	if (pold == null) return null;
//
//	// TODO: This could be inefficient with LinkedList.
//	ArrayList pnew = new ArrayList(colMap.length);
//	for (int i = 0; i < colMap.length; ++i) pnew.add(pold.get(colMap[i]));
//	return pnew;
//}

public TT getValueAt(int row, int column)
{
	int colU = colMap[column];
	if (colU < 0) return null;
	return modelU.getValueAt(row, colU);
}

public void setValueAt(Object val, int row, int column)
{
//System.out.println("ColpermuteTableModel: setValueAt(" + val + ", " + row + ", " + column);
	modelU.setValueAt(val, row, colMap[column]);
}
public boolean isVisible(int col) { return true; }

public JType getJType(int row, int col) {
	return modelU.getJType(row, colMap[col]);
}
// ------------------------------------------------------
public Object getValueAt(int row, String col)
	{ return getValueAt(row, findColumn(col)); }
public void setValueAt(Object val, int row, String col)
	{ setValueAt(val, row, findColumn(col)); }
// --------------------------------------------------------

// =========================================================
public void tableChanged(TableModelEvent e_u) 
{
	TableModelEvent e_t;
// _u = underlying table, _t = this table
//System.out.println("ColPermute: " + e_u);

	int type = e_u.getType();
	int col_u = e_u.getColumn();
	switch(type) {
	case TableModelEvent.UPDATE :
		if (col_u == TableModelEvent.ALL_COLUMNS) {
			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
		} else {
			// Re-map the columns.
//System.out.println("col_u = " + col_u);
//for (int i = 0; i < colMap.length; ++i) System.out.println(colMap[i] + " " + modelU.getColumnName(colMap[i]));
			int col_t = iColMap[col_u];
//System.out.println("ColPermuteTableModel: col_u = " + col_u + ", col_t = " + col_t + " " + this);
//System.out.println("rows = " +e_u.getFirstRow() + ", " + e_u.getLastRow());
			if (col_t == -1) return;
			for (int i=e_u.getFirstRow(); i <= e_u.getLastRow(); ++i)
				this.fireTableCellUpdated(i, col_t);
//			this.fireTableChanged(new TableModelEvent(
//				this, col_t, e_u.getFirstRow(), e_u.getLastRow(), type));
//			this.fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
		}
	break;
	case TableModelEvent.INSERT :
		this.fireTableRowsInserted(e_u.getFirstRow(), e_u.getLastRow());
	case TableModelEvent.DELETE :
		this.fireTableRowsDeleted(e_u.getFirstRow(), e_u.getLastRow());
	break;
	}
}



}
