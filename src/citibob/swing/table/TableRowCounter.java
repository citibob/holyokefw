/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.*;
import citibob.text.IntegerSFormat;
import citibob.types.JavaJType;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author citibob
 */
public class TableRowCounter
extends JTypedLabel implements TableModelListener
{
	
TableModel model;

public TableRowCounter() {
	super.setJType(JavaJType.jtInteger, new IntegerSFormat());
}

public void setModel(TableModel model)
{
	this.model = model;
	model.addTableModelListener(this);
}

public void tableChanged(TableModelEvent e) {
	refresh();
	
//	switch(e.getType()) {
//		case TableModelEvent.UPDATE :
////			if (e.getFirstRow() != TableModelEvent.HEADER_ROW) break;
//		case TableModelEvent.INSERT :
//		case TableModelEvent.DELETE :
//			refresh();
//		break;
//	}
}

public void refresh()
{
	super.setValue(new Integer(model.getRowCount()));
}

}
