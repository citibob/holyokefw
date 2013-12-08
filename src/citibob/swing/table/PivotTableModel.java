/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.text.SFormat;
import citibob.types.JType;
import citibob.util.ObjectUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * NOTE: dataU should always be updated first, before mainU.
 * @author citibob
 */
public abstract class PivotTableModel extends AbstractJTypeTableModel implements TableModelListener
{

protected JTypeTableModel mainU;		// The main table (provides the rows)
/** The unpivoted data.  Assumes we're the only ones editing
 * this table, thus no need to listen to it for events.*/
protected JTypeTableModel dataU;		
protected int[] keyColsM;			// The key columns in the main table; used to populate the rows
protected int[] keyColsD;			// Corresponding key columns in the data table
protected int pivotKeyColD;			// The pivot column in the data table; used to populate the columns
protected int pivotValColD;			// The pivot column in the data table; used to populate the columns
protected Object[] pivotKeyVals;			// Value of each pivot column
HashMap<Object, Integer> iPivotVals;	// Inverse of pivotVals
SFormat pivotValsFmt;			// Converts pivot values to something more presentable (could be KeyedSFormat)

// The rows and columns of our pivot table
ArrayList<int[]> dataRows;		// Row in the dataU that each element corresponds to


public PivotTableModel(JTypeTableModel mainU, JTypeTableModel dataU,
String[] sKeyCols,
String sPivotKeyColD, String sPivotValColD,
List xpivotVals, SFormat pivotValsFmt)
{
	this.mainU = mainU;
	this.dataU = dataU;
	keyColsM = new int[sKeyCols.length];
	keyColsD = new int[sKeyCols.length];
	for (int i=0; i < sKeyCols.length; ++i) {
		keyColsM[i] = mainU.findColumn(sKeyCols[i]);
		keyColsD[i] = dataU.findColumn(sKeyCols[i]);
	}
	pivotKeyColD = dataU.findColumn(sPivotKeyColD);
	pivotValColD = dataU.findColumn(sPivotValColD);
	
	// Set up our columns
	pivotKeyVals = new Object[xpivotVals.size()];
	xpivotVals.toArray(pivotKeyVals);
	iPivotVals = new HashMap();
	for (int i=0; i<pivotKeyVals.length; ++i) iPivotVals.put(pivotKeyVals[i], i);
	
	this.pivotValsFmt = pivotValsFmt;
	refresh();
	
	mainU.addTableModelListener(this);
}


private int[] newDataRow()
{
	int[] rows = new int[pivotKeyVals.length];
	for (int j=0; j<rows.length; ++j) rows[j] = -1;
	return rows;
}

/** Inserts a new row into the dataU table; corresponds to setting up a new
 * cell in the pivot table.
 @param row Row in the main table (and this) of the new cell.
 @param col Column (in this) that we're adding a new cell
 @returns Row in dataU of the new cell we just inserted.
 */
protected abstract int newCell(int row, int col);

protected int getCreateCell(int row, int col)
{
	int row_d = dataRows.get(row)[col];
	if (row_d < 0) {
		row_d = newCell(row, col);
		dataRows.get(row)[col] = row_d;
	}
	return row_d;
}


static class HKey {
	public Object[] val;
	public HKey(int size) {
		val = new Object[size];
	}
	public int hashCode() {
		int ret = 17;
		for (int i=0; i<val.length; ++i)
			ret = ret * 7 + val[i].hashCode();
		return ret;
	}
	public boolean equals(Object o) {
		HKey b = (HKey)o;
		for (int i=0; i<val.length; ++i)
			if (!ObjectUtil.eq(val[i], b.val[i])) return false;
		return true;
	}
	public String toString()
	{
		StringBuffer ret = new StringBuffer("HKey(");
		for (int i=0; i<val.length; ++i) ret.append(val[i].toString() + " ");
		ret.append(")");
		return ret.toString();
	}
}

protected void refresh()
{
	// New dataRows
	dataRows = new ArrayList(mainU.getRowCount());
	for (int i=0; i<mainU.getRowCount(); ++i) dataRows.add(newDataRow());
	
	// Map the rows in mainU
	HashMap<HKey, int[]> mainMap = new HashMap();;
	for (int row=0; row<mainU.getRowCount(); ++row) {
		HKey hkey = new HKey(keyColsM.length);
		for (int j=0; j<keyColsM.length; ++j)
			hkey.val[j] = mainU.getValueAt(row, keyColsM[j]);
		mainMap.put(hkey, dataRows.get(row));
	}
	
	
	// Match up each row in dataU to a row/col in mainU
	HKey hkey = new HKey(keyColsD.length);
	for (int i=0; i<dataU.getRowCount(); ++i) {
		// Match to a row in dataRow
		for (int j=0; j<keyColsD.length; ++j)
			hkey.val[j] = dataU.getValueAt(i, keyColsD[j]);
		int[] row = mainMap.get(hkey);
		if (row == null) continue;		// Row does not exist in mainU
		
		// Figure out which column
		Integer Col = iPivotVals.get(dataU.getValueAt(i, pivotKeyColD));
		if (Col == null) continue;
		
		// set...
		row[Col] = i;
	}
	
	fireTableDataChanged();
}


//public PivotTableModel(JTypeTableModel mainU, JTypeTableModel dataU,
//String[] 
//		
//		String[] sKeyColsU, )



//public JTypeTableModel getModelU() { return sub; }

///** Insert a row and initialize it with data.  colNames[] and
//vals[] must have the same length.
// @param vals initial values for the columns
// @param colNames columns for which we want initial values */
//public int insertRowDataU(int rowIndex, String[] colNames, Object[] vals)
//throws KeyViolationException
//{
//
//	
//	public abstract void insertRow
//
//	
	
	
public int getColumnCount() {
	return pivotKeyVals.length;
}

public int getRowCount() {
	return mainU.getRowCount();
}

public Object getValueAt(int rowIndex, int columnIndex)
{
	int row_d = dataRows.get(rowIndex)[columnIndex];
	if (row_d < 0) return null;
	return dataU.getValueAt(row_d, pivotValColD);
}

public JType getJType(int row, int col) {
	return dataU.getJType(0, pivotValColD);
}

@Override
public int findColumn(String columnName)
{
	try {
		for (int i=0; i<pivotKeyVals.length; ++i) {
			String name = pivotValsFmt.valueToString(pivotKeyVals[i]);
			if (name.equals(columnName)) return i;
		}
		return -1;
	} catch(ParseException e) {
		return -1;
	}
}

@Override
public Class<?> getColumnClass(int columnIndex) {
	return dataU.getColumnClass(pivotValColD);
}

@Override
public String getColumnName(int column) {
	try {
		return pivotValsFmt.valueToString(pivotKeyVals[column]);
	} catch(ParseException e) {
		return null;
	}
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex) {
	return dataU.isCellEditable(0, pivotValColD);
}

@Override
public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	int row_d = getCreateCell(rowIndex, columnIndex);
	dataU.setValueAt(aValue, row_d, pivotValColD);
	fireTableCellUpdated(rowIndex, columnIndex);
}

// =======================================================================
/** Listens to mainU.  Assumes that key columns in mainU will not change. */
public void tableChanged(TableModelEvent e_u) 
{
	TableModelEvent e_t;

	super.fireTableDataChanged();
	
	int type = e_u.getType();
	int col_u = e_u.getColumn();
	switch(type) {
		case TableModelEvent.UPDATE :
			// Assumes that dataU has ALREADY been updated.  dataU should always
			// be updated first.
			if (col_u == TableModelEvent.ALL_COLUMNS && e_u.getLastRow() == Integer.MAX_VALUE) {
				refresh();
			}
		break;
		case TableModelEvent.INSERT : {
			int nrow = e_u.getLastRow() - e_u.getFirstRow() + 1;
			for (int i=0; i<nrow; ++i) dataRows.add(null);
			int i;
			for (i=dataRows.size()-1; i > e_u.getLastRow(); --i)
				dataRows.set(i, dataRows.get(i-nrow));
			for (; i >= e_u.getFirstRow(); --i) {
				dataRows.set(i, newDataRow());
			}

			this.fireTableRowsInserted(e_u.getFirstRow(), e_u.getLastRow());
		} break;
		case TableModelEvent.DELETE : {
			int nrow = e_u.getLastRow() - e_u.getFirstRow() + 1;
			int i;
			for (i=e_u.getFirstRow(); i < dataRows.size() - nrow; ++i)
				dataRows.set(i, dataRows.get(i + nrow));
			for (i=0; i<nrow; ++i) dataRows.remove(dataRows.size()-1);
		} break;
	}
}




}
