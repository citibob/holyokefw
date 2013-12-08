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
 * MultiTableCellEditor.java
 *
 * Created on June 30, 2005, 11:24 PM
 */

package citibob.swing;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;


/**
 * Allows a cell editor that is a synthesis (union) of other cell editors.
 * This allows different cell editors to be used in the same column of a table.
 * @author citibob
 */
public class MultiCellEditor
implements CellEditor, CellEditorListener
{

CellEditor cur = null;
LinkedList listeners = new LinkedList();

public void setCur(CellEditor ncur)
{
	if (ncur == cur) return;
	if (cur != null) cur.removeCellEditorListener(this);
	cur = ncur;
	if (cur != null) cur.addCellEditorListener(this);
}
public CellEditor getCur()
	{ return cur; }
public void addCellEditorListener(CellEditorListener l)
{
//System.out.println("START addCellEditorListener");
	listeners.add(l);
//System.out.println("FINSH addCellEditorListener");
}
public void removeCellEditorListener(CellEditorListener l)
{
//System.out.println("START removeCellEditorListener");
	listeners.remove(l);
//System.out.println("FINSH removeCellEditorListener");
}
public void cancelCellEditing()
	{ cur.cancelCellEditing(); }
public Object getCellEditorValue()
	{ return cur.getCellEditorValue(); }
public boolean isCellEditable(EventObject anEvent)
	{ return true; } // cur is still null!!  cur.isCellEditable(anEvent); }
public boolean shouldSelectCell(EventObject anEvent)
	{ return cur.shouldSelectCell(anEvent); }
public boolean stopCellEditing()
{
System.out.println("MC: stopCellEditing");
	boolean ret = cur.stopCellEditing(); 
System.out.println("ret = " + ret);
	return ret;
}

// =====================================================
// CellEditorListener
// Forward events from cur onto the objects listening to us.
public void editingCanceled(ChangeEvent e)
{
	Object[] aListeners = listeners.toArray();
	for (int i = 0; i < aListeners.length; ++i) {
		CellEditorListener l = (CellEditorListener)(aListeners[i]);
		l.editingCanceled(e);
	}
}
public void editingStopped(ChangeEvent e)
{
	// Clone the list to prevent concurrentmodificationexception
	// (l.editingStopped will result in changing the list.
	Object[] aListeners = listeners.toArray();
	for (int i = 0; i < aListeners.length; ++i) {
		CellEditorListener l = (CellEditorListener)(aListeners[i]);
		l.editingStopped(e);
	}
}

// =====================================================
}
