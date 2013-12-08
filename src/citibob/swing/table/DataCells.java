/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import java.lang.reflect.Array;

/**
 * A data grid that stores a separate value for each cell it represents.
 * The value depends on the row AND column.
 * @author fiscrob
 */
public class DataCells<TT> implements DataGrid<TT>
{

protected int nrow;
protected int ncol;		// # of columns...
TT[] data;		// The data (in row-major format)

public TT getValueAt(int row, int col)
	{ return data[row * ncol + col]; }

public void setValueAt(TT val, int row, int col)
	{ data[row * ncol + col] = val; }

public int getRowCount() { return nrow; }
public int getColumnCount() { return ncol; }

public DataCells(Class klass, int nrow, int ncol)
{
	data = (TT[])Array.newInstance(klass, nrow * ncol);
}

}
