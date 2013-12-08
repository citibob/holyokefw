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
 * JTypedSelectTable.java
 *
 * Created on June 8, 2007, 8:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.typed;

import citibob.swing.*;
import citibob.swing.table.*;
import citibob.types.JType;
import citibob.util.ObjectUtil;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * A table that allows single-row selections.  Implements TypedWidget. */
public class SingleSelectStyledTable extends StyledTable
implements TypedWidget, ListSelectionListener
{

protected boolean clearValueOnClearTable = true;	// Flag: Should we clear the valuewhen the table is cleared?


int valueColU = 0;		// This column in the selected row will be returned as the value
protected Object val = null;
//Object lastVal = null;		// The last non-null value we had.

// State flags
protected boolean inSelect;		// Are we in the middle of having the user change the value?
protected boolean disableUpdateOnSelect;	// Are we in the middle of a program-initiated setValue()?


/** Are we in the middle of having the user change the value? */
public boolean isInSelect() { return inSelect; }

public SingleSelectStyledTable()
{
	super();
	super.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	setHighlightMouseover(true);
}


public boolean isClearValueOnClearTable() {
	return clearValueOnClearTable;
}

public void setClearValueOnClearTable(boolean clearValueOnClearTable) {
	this.clearValueOnClearTable = clearValueOnClearTable;
}
// --------------------------------------------------------
public void setSelectedRow(int row)
{
	if (row >= 0 && row < getRowCount()) {
		this.getSelectionModel().setSelectionInterval(row,row);		
	} else {
		getSelectionModel().clearSelection();		
	}
}

/** Sets ONE selected row, based on the value of a column.  Clears
 selection if val == null.  This update the display to match an
 underlying model. */
protected void setSelectedRow(Object val, int col, DataGrid model)
{
	
	if (val == null) {
		getSelectionModel().clearSelection();
		return;
	}
	
	// Test if we're already set correctly.
	int row = getSelectedRow();
	if (row >= 0 && ObjectUtil.eq(model.getValueAt(row, col), val)) return;
	
	// Find the row and set it.
	row = DataGridUtils.getRowOfValue(val, col, model);
	setSelectedRow(row);
//	if (row >= 0) {
//		this.getSelectionModel().setSelectionInterval(row,row);
//		return;
//	} else {
//		getSelectionModel().clearSelection();
//	}
}

public void setSelectedRow(Object val, int col)
	{ setSelectedRow(val, col, styledModel.getModel()); }
public void setSelectedRowU(Object val, int colU)
	{ setSelectedRow(val, colU, styledModel.getModelU()); }

public void setSelectedRowU(Object val, String colU)
{
	JTypeTableModel model = styledModel.getModelU();
	setSelectedRow(val, model.findColumn(colU), model);
}
// ---------------------------------------------------------------------
/** Controls which column in selected row will be returned as the value */
public void setValueColU(String name)
	{ valueColU = styledModel.getModelU().findColumn(name); }
	
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{ return val; }

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
 public void setValue(Object newVal)
{
	// Disable event processing on display update
	disableUpdateOnSelect = true;

	// Set the new value
	Object oldVal = val;
	val = newVal;
	
	// Update the graphic display
	setSelectedRowU(val, valueColU);
	
	// Fire property change if needed
	if (!ObjectUtil.eq(oldVal, val))
		firePropertyChange("value", oldVal, val);
	
	disableUpdateOnSelect = false;
}

/** From TableCellEditor (in case this is being used in a TableCellEditor):
 * Tells the editor to stop editing and accept any partially edited value
 * as the value of the editor. The editor returns false if editing was not
 * stopped; this is useful for editors that validate and can not accept
 * invalid entries. */
public boolean stopEditing() {return true;}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o)
{
	JType type = (styledModel.getModelU()).getJType(0,valueColU);
	return type.isInstance(o);
}

/** Set up widget to edit a specific SqlType.  Note that this widget does not
 have to be able to edit ALL SqlTypes... it can throw a ClassCastException
 if asked to edit a SqlType it doesn't like. */
public void setJType(citibob.swing.typed.Swinger f) throws ClassCastException {}

// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
//public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------


// ================================================================
// ListSelectionListener
/** JTable implements ListSelectionListener.  This method overrides that implementation. */
public void valueChanged(ListSelectionEvent e) {
	if (disableUpdateOnSelect) return;

	super.valueChanged(e);
	inSelect = true;
	Object oldval = val;
	
	int selRow = this.getSelectedRow();
	if (selRow < 0) val = null;
	else val = styledModel.getModelU().getValueAt(selRow, valueColU);
	firePropertyChange("value", oldval, val);
	inSelect = false;
}
///** JTable implements ListSelectionListener.  This method overrides that implementation. */
//public void valueChanged(ListSelectionEvent e) {
//	if (disableUpdateOnSelect) return;
//	
//	super.valueChanged(e);
//	inSelect = true;
//	Object oldval = val;
//	if (oldval != null) lastVal = oldval;
//
//	int selRow = this.getSelectedRow();
////	if (selRow < 0) val = null;
////	else 
//	/** Do not set to null on lack of selection... */
//	if (selRow >= 0) {
//		Object newVal = getModelU().getValueAt(selRow, valueColU);
//if (newVal == null) {
//	System.out.println("newVal is null, selRow = " + selRow + ", valueColU = " + valueColU + ", val = " + val);
//}
//		val = newVal;
//	}
//	
//	if (!ObjectUtil.eq(val, oldval)) {
//		firePropertyChange("value", oldval, val);
//	}
//	inSelect = false;
//}

// ===================================================================
// TableModelListener
/** JTable implements TableModelListener.  This method overrides that implementation. */
public void tableChanged(TableModelEvent e)
{
	Object curVal = val;
//System.out.println("tableChanged: val = " + val);

	disableUpdateOnSelect = true;
	super.tableChanged(e);
	if (e.getType() != e.UPDATE || e.getLastRow() != e.getFirstRow()) {
		// We've done a major change -- could involve display refresh
		// or change in value
		
		// Set value to null if we've cleared the table.  This is in
		// preparation (in HolyokeFW) for loading in a new ResultSet
		// in which it's generally appropriate to clear things.
		if (clearValueOnClearTable && getModel().getRowCount() == 0) {
			if (val != null) {
				Object oldVal = val;
				val = null;
				firePropertyChange("value", oldVal, val);
			}
		}
		
		// Refresh the display
		setSelectedRowU(val, valueColU);
	}
	disableUpdateOnSelect = false;

}
}

