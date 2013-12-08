/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing;

import citibob.swing.table.JTypeTableModel;
import citibob.swing.table.LiveSetTableModel;
import citibob.swing.typed.SingleSelectStyledTable;
import javax.swing.event.TableModelEvent;

/**
 *
 * @author fiscrob
 */
public class LiveSetTable extends SingleSelectStyledTable
{

	public LiveSetTable()
	{
		// We'll clear value ourselves, by listening directly to the Tracker
		setClearValueOnClearTable(false);
	}

public void tableChanged(TableModelEvent xe)
{
	if (styledModel == null) return;
	
	JTypeTableModel modelU = styledModel.getModelU();
//	Object src = xe.getSource();
	if (modelU instanceof LiveSetTableModel) {
		boolean trackerRowsChanged = ((LiveSetTableModel)modelU).getSetChanged();
		if (trackerRowsChanged) setValue(null);
	}
//	if (xe instanceof TrackerTableModelEvent) {
//		TrackerTableModel.Event e = (TrackerTableModel.Event)xe;
//		if (e.trackerRowsChanged) setValue(null);
//	}
	super.tableChanged(xe);
}
///** @param jtModel Underling data buffer to use
// * @param typeCol Name of type column in the schema
// * @param xColNames Columns (other than type and status) from schema to display
// */
//public void setModelU(TrackerTableModel modelU,
//		String[] colNames, String[] sColMap, boolean[] editable,
//		boolean sortable, citibob.swing.typed.SwingerMap smap)
//{
//	
//}
///** @param jtModel Underling data buffer to use
// * @param typeCol Name of type column in the schema
// * @param xColNames Columns (other than type and status) from schema to display
// * @param ttColMap Column in underlying table to display as tooltip for each column in displayed table.
// */
//public void setModelU(TrackerTableModel jtModel,
//		String[] colNames, String[] sColMap, String[] ttColMap, boolean[] editable,
//		boolean sortable, citibob.swing.typed.SwingerMap smap)
//{
//	
//}
}
