/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import java.lang.reflect.Array;

/**
 * A data grid that stores a separate value for each column.  The value
 * depends only on the column, not the row.
 * @author fiscrob
 */
public class DataCols<TT> implements DataGrid<TT>
{

TT[] data;

public TT getValueAt(int row, int col) { return data[col]; }

public void setColumn(int col, TT val) { data[col] = val; }

public int getRowCount() { return 0; }
public int getColumnCount() { return data.length; }

public DataCols(Class klass, int ncol)
{
	data = (TT[])Array.newInstance(klass, ncol);
//	data = (TT[])new TT[ncol];
//	data = new TT[ncol];
}

//public DataCols(Class klass, StyledTableModel model)
//{
//	this(klass, model.getModel().getColumnCount());
//}

	
	
}
