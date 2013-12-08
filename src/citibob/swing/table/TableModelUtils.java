/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

/**
 *
 * @author fiscrob
 */
public class TableModelUtils {
	
/** Creates a colMap if the user didn't specify one.  Only includes the visible columns. */
public static int[] defaultColMap(JTypeTableModel model_u)
{
	// Count number of "real" columns
	int ncol = 0;
	for (int i=0; i < model_u.getColumnCount(); ++i) {
		if (!model_u.getColumnName(i).startsWith("__")) ++ncol;
	}
	int[] colMap = new int[ncol];
	int j=0;
	for (int i=0; i < model_u.getColumnCount(); ++i) {
		if (model_u.isVisible(i)) colMap[j++] = i;
//		if (!model_u.getColumnName(i).startsWith("__")) colMap[j++] = i;
	}
	
	return colMap;
}
}
