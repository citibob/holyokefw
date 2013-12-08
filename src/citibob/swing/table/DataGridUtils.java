/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.util.ObjectUtil;

/**
 *
 * @author fiscrob
 */
public class DataGridUtils {
/** Returns the row a value is found on (or -1 if no such row) */
public static int getRowOfValue(Object val, int col, DataGrid model)
{
	for (int i=0; i<model.getRowCount(); ++i) {
		Object rval = model.getValueAt(i, col);
		boolean eq = ObjectUtil.eq(rval, val);
		if (eq) return i;
	}
	return -1;
}

}
