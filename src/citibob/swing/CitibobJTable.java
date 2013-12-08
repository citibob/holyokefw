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
package citibob.swing;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import citibob.text.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.swingers.*;
import citibob.types.*;
//import de.chka.swing.components.*;
import citibob.util.ObjectUtil;

/** @deprecated */
public class CitibobJTable extends JTable
implements MouseListener, MouseMotionListener
{

JTypeTableModel ttModel;		// Tooltips
	
private boolean highlightMouseover = false;		// SHould we highlight rows when mousing over?

/** Should we fill the ScrollPane with our table, even if there aren't
enough data rows to warrant it?
See: http://nadeausoftware.com/articles/2008/01/java_tip_how_add_zebra_background_stripes_jtable
 */
private boolean fillViewport = true;			// 
	
//private boolean isHighlightSelected = true;		// Should we highlight selected rows?
//public void setHighlightSelected(boolean b) { this.isHighlightSelected = b; }
//public boolean getHighlightSelected() { return this.isHighlightSelected; }

public void setHighlightMouseover(boolean b)
{
	if (highlightMouseover == b) return;

	highlightMouseover = b;
	if (b) {
		this.addMouseListener(this); //new MyMouseAdapter());
		this.addMouseMotionListener(this); //new MyMouseMotionAdapter());
	} else {
		this.removeMouseListener(this); //new MyMouseAdapter());
		this.removeMouseMotionListener(this); //new MyMouseMotionAdapter());		
	}
}
public boolean isHighlightMouseover() { return highlightMouseover; }

//RowHeightUpdater rhu;

public CitibobJTable()
{
	// See: http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=4709394
	// Unfortunately, this "fix" breaks the JDateChooser date editor.
	// As soon as user selects a date, the focus is lost from the table,
	// BEFORE the JDateChooser has had a chance to update itself...
	// TODO: For now, I won't use it, but once JDateChooser is fixed, I'll turn
	// it back on.
	this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//	super.setAutoResizeMode(AUTO_RESIZE_OFF);
	setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//javax.swing.plaf.basic.BasicComboBoxUI
}

//public void setModel(TableModel model)
//{
//	super.setModel(model);
//	if (model instanceof CitibobTableModel) {
//		CitibobTableModel ctm = (CitibobTableModel)model;
////		rhu = new RowHeightUpdater(this, ctm.getPrototypes());
////		rhu.setEnabled(true);
//	}
//}

public JTypeTableModel getCBModel()
{
	TableModel m = super.getModel();
	return (JTypeTableModel)m;
}

/** Returns the row a value is found on (or -1 if no such row) */
public int rowOfValue(Object val, int col)
	{ return rowOfValue(val, col, getModel()); }

/** Returns the row a value is found on (or -1 if no such row) */
protected int rowOfValue(Object val, int col, TableModel model)
{
	for (int i=0; i<model.getRowCount(); ++i) {
		Object rval = model.getValueAt(i, col);
		boolean eq = (rval == null ? val == null : val.equals(rval));
		if (eq) return i;
	}
	return -1;
}

protected void setSelectedRow(Object val, int col)
	{ setSelectedRow(val,col, getModel()); }

/** Sets ONE selected row, based on the value of a column.  Clears
 selection if val == null. */
protected void setSelectedRow(Object val, int col, TableModel model)
{
	
	if (val == null) {
		getSelectionModel().clearSelection();
		return;
	}
	
	// Test if we're already set correctly.
	int row = getSelectedRow();
	if (row >= 0 && ObjectUtil.eq(model.getValueAt(row, col), val)) return;
	
	// Find the row and set it.
	row = rowOfValue(val, col, model);
	setSelectedRow(row);
//	if (row >= 0) {
//		this.getSelectionModel().setSelectionInterval(row,row);
//		return;
//	} else {
//		getSelectionModel().clearSelection();
//	}
}

public void setSelectedRow(int row)
{
	if (row >= 0 && row < getRowCount()) {
		this.getSelectionModel().setSelectionInterval(row,row);		
	} else {
		getSelectionModel().clearSelection();		
	}
}

//public void setRowHeightUpdaterEnabled(boolean b)
//{
//	rhu.setEnabled(b);
//}
//
//public boolean isRowHeightUpdaterEnabled()
//	{ return rhu.isEnabled(); }

public void setRenderEdit(int colNo, Swinger swinger)
{
	if (swinger == null) return;
	Swinger.RenderEdit re = swinger.newRenderEdit();
	setRenderEdit(colNo, re);
}

public void setRenderEdit(int colNo, KeyedModel kmodel)
{
	setRenderEdit(colNo,
		new KeyedRenderEdit(kmodel));
}

protected boolean isEditable(int row, int col)
{
	return getModel().isCellEditable(row, col);
}

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderEdit(int colNo, Swinger.RenderEdit re)
{
	if (re == null) return;		// Don't change, if we don't know what to set it TO.
	
	TableColumn col = getColumnModel().getColumn(colNo);
	TableCellRenderer rr = re.getRenderer(isEditable(0, colNo));
		if (rr != null) col.setCellRenderer(rr);
	TableCellEditor ee = re.getEditor();
		if (ee != null) col.setCellEditor(ee);
}

//public void setRenderEdit(int colNo, TypedWidget tw)
//{
//	TableColumn col = getColumnModel().getColumn(colNo);
//	col.setCellRenderer(new SFormatRenderer(sformat));
//	JTypedTextField tw = new JTypedTextField();
//	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
//	col.setCellEditor(new TypedWidgetEditor(tw));
//}

/** Sets a text-based renderer and editor pair at once, for a column,
without going through Swingers or anything.  Works for simpler text-based
renderers and editors ONLY. */
public void setFormat(int colNo, SFormat sformat)
{
	TableColumn col = getColumnModel().getColumn(colNo);
	col.setCellRenderer(new SFormatRenderer(sformat));
	JTypedTextField tw = new JTypedTextField();
	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
	col.setCellEditor(new TypedWidgetEditor(tw));
}

public void setFormat(int colNo, java.text.Format fmt)
{
	SFormat sfmt = (fmt instanceof NumberFormat ?
		new FormatSFormat(fmt, "", SFormat.RIGHT) :
		new FormatSFormat(fmt, "", SFormat.LEFT));
	setFormat(colNo, sfmt);
}

/** Sets up a renderer and editor based on a format string.  Works for a small
number of well-known types, this is NOT general. */
public void setFormat(int colNo, String fmtString)
{
	Class klass = getModel().getColumnClass(colNo);
	Format fmt = FormatUtils.newFormat(klass, fmtString);
//if (fmt == null) {
//	Class klass3 = getModel().getColumnClass(colNo);	
//}
	setFormat(colNo, fmt);
}

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderer(int colNo, TableCellRenderer re)
{
	TableColumn col = getColumnModel().getColumn(colNo);
	col.setCellRenderer(re);
}


// ==========================================================
// Toltips
/** Set the tooltips to be used */
public void setTTModel(JTypeTableModel ttModel)
{
	this.ttModel = ttModel;
}
/** Override this to do tooltips in custom manner.  For now, we return the "tooltip column" */
public String getTooltip(int row, int col)
{
	if (ttModel == null) return null;
	return (String)ttModel.getValueAt(row, col);
}
// ==========================================================

// ==========================================================
// Mess with changing the font and adjusting table cell heights
// accordingly.
FontMetrics tableFontMetrics;
public void setFont(Font tableFont)
{
//	this.tableFont = tableFont;
	super.setFont(tableFont);
	tableFontMetrics = getFontMetrics(tableFont);
}
/** @Override
 * Adjust the height of the rows based on our chosen font.
 */
public int getRowHeight()
{
	if (tableFontMetrics == null) return super.getRowHeight();
	return tableFontMetrics.getHeight();
}
// ==========================================================
/** Force the table to fill the viewport's height. */
public boolean getScrollableTracksViewportHeight( )
{
	if (!fillViewport) return false;
	
	final java.awt.Component p = getParent( );
	if ( !(p instanceof javax.swing.JViewport) )
		return false;
	return ((javax.swing.JViewport)p).getHeight() > getPreferredSize().height;
}


// ================================================================
// Stuff to highlight on mouseover
// See: http://forum.java.sun.com/thread.jspa?threadID=280692&messageID=1091824
// TODO: Actually, we need to use different colors (and fonts) if this is being
// used in a popup.  We should subclass for that...  But it's OK for now.
Color cTextHighlightBg = UIManager.getDefaults().getColor("List.selectionBackground");
Color cTextBg = UIManager.getDefaults().getColor("List.background");
Color cTextHighlightFg = UIManager.getDefaults().getColor("List.selectionForeground");
Color cTextFg = UIManager.getDefaults().getColor("List.foreground");
public Component prepareRenderer(TableCellRenderer renderer, int row, int col)
{
	
	Component c = super.prepareRenderer(renderer, row, col);

	// Tooltips
	if (c instanceof JComponent) {
		JComponent jc = (JComponent)c;
		String ttip = getTooltip(row, col);
//	System.out.println(ttip);
		jc.setToolTipText(ttip);
//		jc.setToolTipText("<html>This is the first line<br>This is the second line</html>");
	}

	// Highlight row the mouse is over
	if (row == mouseRow || this.getSelectionModel().isSelectedIndex(row)) {
		c.setBackground(cTextHighlightBg);
		c.setForeground(cTextHighlightFg);
	} else {
		c.setBackground(getBackground(row,col));
		c.setForeground(getForeground(row,col));
	}
	
	// Set up the font
	c.setFont(getFont(row,col));
	
	return c;
}
int mouseRow = -1;		// Row the mouse is currently hovering over.

/** Override this to change the foreground color of a cell. */
public Color getForeground(int row, int col)
	{ return cTextFg; }
/** Override this to change the foreground color of a cell. */
public Color getBackground(int row, int col)
	{ return cTextBg; }
/** Override this to change the font of a cell.  NOTE: The font
 returned MUST have the same height as the Font set up using setFont(Font). */
public Font getFont(int row, int col)
	{ return getFont(); }
// =====================================================================
// MouseMotionListener
/**
 * Invoked when a mouse button is pressed on a component and then 
 * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be 
 * delivered to the component where the drag originated until the 
 * mouse button is released (regardless of whether the mouse position 
 * is within the bounds of the component).
 * <p> 
 * Due to platform-dependent Drag&Drop implementations, 
 * <code>MOUSE_DRAGGED</code> events may not be delivered during a native 
 * Drag&Drop operation.  
 */
public void mouseDragged(MouseEvent e) {}

/**
 * Invoked when the mouse cursor has been moved onto a component
 * but no buttons have been pushed.
 */
public void mouseMoved(MouseEvent e) {
	if (!highlightMouseover) return;
	
	JTable aTable =  (JTable)e.getSource();
	int oldRow = mouseRow;
	mouseRow = aTable.rowAtPoint(e.getPoint());
//	itsColumn = aTable.columnAtPoint(e.getPoint());
	if (oldRow != mouseRow) aTable.repaint();
}

// =====================================================================
// MouseListener
/**
 * Invoked when the mouse button has been clicked (pressed
 * and released) on a component.
 */
public void mouseClicked(MouseEvent e) {}

/**
 * Invoked when a mouse button has been pressed on a component.
 */
public void mousePressed(MouseEvent e) {}

/**
 * Invoked when a mouse button has been released on a component.
 */
public void mouseReleased(MouseEvent e) {}

/**
 * Invoked when the mouse enters a component.
 */
public void mouseEntered(MouseEvent e) {}

/**
 * Invoked when the mouse exits a component.
 */
public void mouseExited(MouseEvent e) {
	if (!highlightMouseover) return;

	JTable aTable =  (JTable)e.getSource();
	mouseRow = -1;
	aTable.repaint();
}
// ================================================================
}
