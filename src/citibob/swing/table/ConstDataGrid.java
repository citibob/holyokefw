/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

/**
 *
 * @author fiscrob
 */
public class ConstDataGrid<TT>
implements DataGrid<TT>
{
	
TT val;

public ConstDataGrid(TT val)
{ this.val = val; }

	public int getColumnCount() {
		return 0;
	}

	public int getRowCount() {
		return 0;
	}

	public TT getValueAt(int row, int col) {
		return val;
	}


	
	
}
