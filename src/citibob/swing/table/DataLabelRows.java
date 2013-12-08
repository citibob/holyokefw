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
public class DataLabelRows<TT> implements DataGrid<TT>
{

TT[] data;
TT col0Val;

public TT getValueAt(int row, int col) {
	switch(col) {
		case 0 : return col0Val;
		case 1 : return data[row];
	}
	return null;
}

public void setCol0(TT val) { col0Val = val; }
public void setRow(int row, TT val) { data[row] = val; }

public int getRowCount() { return data.length; }
public int getColumnCount() { return 2; }

public DataLabelRows(Class klass, int nrow)
{
	data = (TT[])Array.newInstance(klass, nrow);
}
	
}
