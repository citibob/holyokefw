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
/*
 * MultiJTypeTableModel.java
 *
 * Created on February 4, 2007, 1:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.types.JType;
import javax.swing.table.*;
import java.util.*;
import javax.swing.event.*;
import citibob.swing.typed.*;

/**
 * Present a bunch of JTypeTableModels as one, concatenating their
 columns together.  Useful for various joins, or for "adding a column"
 to an existing JTypeTableModel.
 * @author citibob
 */
public class MultiJTypeTableModel extends AbstractJTypeTableModel
//implements JTypeTableModel
{

JTypeTableModel[] models;	// Sub-models
//String[] tabNames;	// Name of each sub-table
//String[] prefixes;		// Prefix to column names in subtables
int colCount;		// Total # cols

/** Record for each column */
static class ColRec
{
	int col;		// Column in main table
	int subTab;		// Sub-table we're part of
	int subCol;		// Column index in sub-table
//	int subColU;	// Column index in the sub-table's modelU
}
ColRec[] cols;		// Description of each column in aggregate table
TreeMap<String,ColRec> colMap;		// "tabName.colName" --> ColRec

int[] tabStart;		// Starting column in super-table of each sub-table
int[] tabStartU;		// Starting column in super-table of each sub-table's modelU()

public int getModelStartCol(int i) { return tabStart[i]; }
public int getModelNextCol(int i)
	{ return getModelStartCol(i) + getModel(i).getColumnCount(); }
public JTypeTableModel getModel(int i) { return models[i]; }

//	
///** record for each sub-table */
//static class Tab
//{
//	String name;				// Name of the table
//	JTypeTableModel model;
//}
//	
//JTypeTableModel tables;
//String[] jtables;

//String[] makePrefixes(String[] xprefixes, int len)
//{
//	String[] prefixes = new String[len];
//	if (xprefixes == null) {
//		for (int i=0; i<prefixes.length; ++i) prefixes[i] = "";
//	} else {
//		for (int i=0; i<prefixes.length; ++i) {
//			prefixes[i] = (xprefixes[i] == null ? "" : xprefixes[i] + "_");
//		}
//	}
//	return prefixes;
//}

/** Creates a new instance of MultiJTypeTableModel
 @param sub-models to glom together; all column names must be unique across sub-models. */
public MultiJTypeTableModel(JTypeTableModel... models)
{
	if (models.length == 0) return;
	init(models);
}
public void init(JTypeTableModel... models)
{
//	prefixes = makePrefixes(xprefixes, models.length);
//	this.tabNames = tabNames;
	this.models = models;
	
	// Get total # cols
	colCount = 0;
	for (int i=0; i<models.length; ++i) colCount += models[i].getColumnCount();

	tabStart = new int[models.length];
	tabStartU = new int[models.length];
	tabStart[0] = 0;
	tabStartU[0] = 0;
	cols = new ColRec[colCount];
	colMap = new TreeMap();
	int curCol = 0;
	for (int i=0; ;) {
		JTypeTableModel mod = models[i];
		tabStart[i] = curCol;
		for (int j=0; j<mod.getColumnCount(); ++j) {
			ColRec crec = new ColRec();
			crec.col = curCol;
			crec.subTab = i;
			crec.subCol = j;
//			String name = prefixes[i] + mod.getColumnName(j);
			String name = mod.getColumnName(j);
			colMap.put(name, crec);
			cols[curCol] = crec;
			++curCol;
		}
		++i;
		if (i == models.length) break;
		JTypeTableModel modelU = mod.getModelU();
		tabStartU[i] = tabStartU[i-1] + modelU.getColumnCount();
	}
	
	// Set up listeners
	for (int i=0; i<models.length; ++i)
		models[i].addTableModelListener(new MyListener(i));
	
}

///** Returns name of the ith JTypeTableModel we're gluing together. */
//public String getTableName(int i)
//{ return tabNames[i]; }

/** Given a column in the overriding talbe, gets the sub-table it belongs to */
public int getTableOfCol(int col)
{ return cols[col].subTab; }
//	int ret = Arrays.binarySearch(this.tabStart, col);
//	return (ret >= 0 ? ret : ret-2);
//}
// ===============================================================
// JTypeTableModel
/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
public JType getJType(int row, int col)
{
	int tab = getTableOfCol(col);
	return models[cols[col].subTab].getJType(row, col - tabStart[tab]);
}	
/** If this wraps another TableModel:
Finds a column by name in the UNDERLYING table model, then
reports its location in THIS table model.  Otherwise, same
as findColumn().  */
public int findColumnU(String colU)
{
	int baseCol = 0;
	for (int tab=0; ;) {
		int localCol = models[tab].findColumnU(colU);
		if (localCol != -1) return baseCol + localCol;
		baseCol += models[tab].getModelU().getColumnCount();
		++tab;
		if (tab >= models.length) return -1;
	}
}
/** Given a column in THIS table, reports the column in the UNDERLYING table.
 * Returns -1 if none.
 */
public int getColU(int col)
{
	int tab = this.getTableOfCol(col);
	return tabStartU[tab] + models[tab].getModelU().getColU(col - tabStart[tab]);
}

/** Given a column in the underlying table, returns the corresponding
 * column in the visible table. */
public int colU2col(int colU)
{
	int tab = Arrays.binarySearch(tabStartU, colU);
	if (tab < 0) tab = -(tab + 1) - 1;
	return tabStart[tab] + models[tab].colU2col(colU);
}
// ===============================================================
// CitibobTableModel
/** These, you will get for free if you subclass AbstractTableModel. */
//void fireTableChanged(TableModelEvent e);

/** Finds a column's index by name --- also implemented in AbstractTableModel.
 Returns -1 if column names doesn't exist or is null.
 Name should be tableName.colName*/
public int findColumn(String name)
{
	ColRec rec = colMap.get(name);
	if (rec == null) return -1;
	return rec.col;
}
// ===============================================================
// TableModel

public int getRowCount()
{
	return models[0].getRowCount();
}
public int getColumnCount()
{
	return this.colCount;
}
public Object getValueAt(int row, int col)
{
	int tab = getTableOfCol(col);
//System.out.println("getting value of col " + col +
//" (" + tab + ":" + (col - tabStart[tab]) + ") ncols=" + models[tab].getColumnCount());
	return models[tab].getValueAt(row, col - tabStart[tab]);	
}
public Class  getColumnClass(int columnIndex) 
{
	int tab = getTableOfCol(columnIndex);
	return models[tab].getColumnClass(columnIndex - tabStart[tab]);
}
public String  getColumnName(int column)
{
	int tab = getTableOfCol(column);
	return models[tab].getColumnName(column - tabStart[tab]);
//	return prefixes[tab] + models[tab].getColumnName(column - tabStart[tab]);
}
public boolean  isCellEditable(int rowIndex, int columnIndex)
{
	int tab = getTableOfCol(columnIndex);
	return models[tab].isCellEditable(rowIndex, columnIndex - tabStart[tab]);	
}
public void  setValueAt(Object aValue, int rowIndex, int columnIndex) 
{
	int tab = getTableOfCol(columnIndex);
	models[tab].setValueAt(aValue, rowIndex, columnIndex - tabStart[tab]);	
}
// ===================================================================
class MyListener implements TableModelListener
{
	int tab;		// Index into our tables

	public MyListener(int tab) {
		this.tab = tab;
	}
	public void tableChanged(TableModelEvent e_u) 
	{
		int type = e_u.getType();
		int col_u = e_u.getColumn();
		switch(type) {
		case TableModelEvent.UPDATE :
			if (col_u == TableModelEvent.ALL_COLUMNS) {
				fireTableRowsUpdated(e_u.getFirstRow(), e_u.getLastRow());
			} else {
				fireTableChanged(new TableModelEvent(MultiJTypeTableModel.this,
					e_u.getFirstRow(), e_u.getLastRow(),
					e_u.getColumn() + tabStart[tab]));
			}
		break;
		case TableModelEvent.INSERT :
			// Only pass up ONE insert event --- the LAST one
			if (tab == models.length-1)
				fireTableRowsInserted(e_u.getFirstRow(), e_u.getLastRow());
		case TableModelEvent.DELETE :
			if (tab == models.length-1)
				fireTableRowsDeleted(e_u.getFirstRow(), e_u.getLastRow());
		break;
		}
	}
}
// =============================================================
// Convenience Functions
/** Sets a general set of columns in a DataCols object.
 * @param model The visible model, for which this is modelU.
 * @param tab the sub-table whose columns need to be set.
 * @param dataCols Object to set values in (per column)
 * @param val The value to set.
 */
public void setCols(JTypeTableModel visibleModel, int tab, DataCols dataCols, Object val)
{
	int first = visibleModel.colU2col(getModelStartCol(tab));
	int next = visibleModel.colU2col(getModelNextCol(tab));
	for (int i=first; i<next; ++i) dataCols.setColumn(i, val);
}

/** Sets a general set of columns in a DataCols object.
 * @param model The visible model, for which this is modelU.
 * @param dataCols Object to set values in (per column)
 * @param val The value to set.
 */
public void setFormat(JTypeTableModel visibleModel, int tab, RenderEditCols dataCols, Object fmt)
{
	int first = visibleModel.colU2col(getModelStartCol(tab));
	int next = visibleModel.colU2col(getModelNextCol(tab)-1)+1;
	for (int i=first; i<next; ++i) dataCols.setFormat(i, fmt);
}



}

