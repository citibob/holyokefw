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
import citibob.swing.table.StyledTM.ButtonListener;
import citibob.swing.typed.*;
import citibob.swing.typed.Swinger.RenderEdit;
import citibob.swingers.*;
import citibob.types.*;
//import de.chka.swing.components.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TableModelEvent;
import javax.swing.plaf.TableUI;


public class StyledTable extends JTable
implements MouseListener, MouseMotionListener
{
	
protected StyledTM styledModel = dummyStyledModel;
SortableTableModel sortModel;

private boolean highlightMouseover = false;		// SHould we highlight rows when mousing over?
//private MouseListener highlightMouseoverListener = null;
//private MouseMotionListener highlightMouseoverMotionListener = null;

// The MouseListeners created by default by the superclass and the UI.
MouseListener[] superListeners;



/** Should we fill the ScrollPane with our table, even if there aren't
enough data rows to warrant it?
See: http://nadeausoftware.com/articles/2008/01/java_tip_how_add_zebra_background_stripes_jtable
 */
private boolean fillViewport = true;			// 

/** Size of the default font --- used to calculate row heights */
FontMetrics tableFontMetrics;

int mouseRow = -1;		// Row the mouse is currently hovering over.

// Cell the mouse button is currently depressed on
int pressedRow = -1;
int pressedCol = -1;
boolean mousePressed = false;	// True if the mouse button is currently pressed in,
	// which would require a cell to be displayed in its pressed state.
/** Dummy for GUI builder */
static final JTypeTableModel nullTableModel = new DefaultJTypeTableModel();
static final StyledTM dummyStyledModel = new StyledTM(nullTableModel);

/** Set based on the last mousePresed event. */
//private boolean pressedPopupTrigger;
private ButtonListener pressedButtonListener;

// True if we should run the superclass listeners
// (which generally process list selection)
private boolean pressedRunSuper;

public StyledTable()
{
	// See: http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=4709394
	// Unfortunately, this "fix" breaks the JDateChooser date editor.
	// As soon as user selects a date, the focus is lost from the table,
	// BEFORE the JDateChooser has had a chance to update itself...
	// TODO: For now, I won't use it, but once JDateChooser is fixed, I'll turn
	// it back on.
	this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
}

@Override
public void setUI(TableUI ui) {
	
	super.setUI(ui);
	
	// Replace the mouse listeners
	superListeners = super.getMouseListeners();
	for (MouseListener l : superListeners) super.removeMouseListener(l);

	// Handle:
	//   a) Highlight on mouseover
	//   b) "Buttons" in the table
	//   c) Avoid selecting a cell on a dropdown-triggering mouse event
	super.addMouseListener(this);
	super.addMouseMotionListener(this);
}

// ===============================================================
// Fundamental stuff for drawing the JTable and interacting with the model
public void setStyledTM(StyledTM stm)
{
	this.styledModel = stm;
	
	// Set up table sorting stuff
	JTypeTableModel modelU = stm.getModelU();
	if (modelU instanceof SortableTableModel) {
		sortModel = (SortableTableModel)modelU;
		
//		// Set comparators in sortModel
//		if (ext.getCompModelU() != null)
//			sortModel.setComparators(ext.getCompModelU());

		
		// Sort when user clicks headers
		JTableHeader head = getTableHeader();
		head.addMouseListener(new SortableMouseHandler());
		head.setDefaultRenderer(
			new SortableHeaderRenderer(
			head.getDefaultRenderer()));
	}
	
	
	super.setModel(stm.getModel());
}

public StyledTM getStyledTM()
	{ return styledModel; }

// ===============================================================
// Old-style (simplified) model-setting

/** Sets up a <b>read-only</b> model based on a naked model.  Does not
 * reorder or rename columns.  One can call setTooltip
 * @param smap
 * @param modelU The model to use
 */
public void setModelU(SwingerMap smap, JTypeTableModel modelU)
{
	DelegateStyledTM stm = new DelegateStyledTM(modelU);
	stm.setDefaultModel(smap);
	stm.setEditableModel(new ConstDataGrid(Boolean.FALSE));
	setStyledTM(stm);
}
/** @param modelU Underling data buffer to use.  If it's an instance of
 * SortableTableModel, sortable features will be used.
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 * @param editable Whether each column is editable.  If null, use default.
 */
public void setModelU(SwingerMap smap, JTypeTableModel modelU,
		String[] colNames, String[] sColMap)
{
	DelegateStyledTM stm = new DelegateStyledTM(modelU);
	stm.setColumns(smap, colNames, sColMap);
	setStyledTM(stm);
}
// --------------------------------------------------------------
/** Once this has been set up with a <i>DelegateStyledTM</i>, sets tooltips on it,
 * based on the UNDERLYING columns in modelU.  For backwards compatibility only.
 * Otherwise, see DelegateStyledTM.setToolTips()
 NOTE: Requires our underlying StyledTM is DelegateStyledTM.
 * @param ttColMap Column in underlying table to display as tooltip for each column in displayed table.
 */
public void setTooltips(String... ttColMap)
{
	getDelegateStyledTM().setTooltips(ttColMap);
}

/** NOTE: Requires our underlying StyledTM is DelegateStyledTM. */
public void setEditable(boolean... editable)
	{ getDelegateStyledTM().setEditable(editable); }

/** Sets a render/edit on a colum, by UNDERLYING column name.
 NOTE: Requires our underlying StyledTM is DelegateStyledTM. */
public void setFormatU(String underlyingName, Object fmt)
{
	DelegateStyledTM stm = getDelegateStyledTM();
	RenderEditCols re = (RenderEditCols)stm.getRenderEditModel();
	re.setFormatU(underlyingName, fmt);
}
// --------------------------------------------------------------
public DelegateStyledTM getDelegateStyledTM()
	{ return (DelegateStyledTM)styledModel; }

/** Non-standard way to access any column of the selected row. */
public Object getValue(int colU)
{
	int selRow = this.getSelectedRow();
	if (selRow < 0) return null;
	return getModelU().getValueAt(selRow, colU);
}
public Object getValue(String colNameU)
{
	int colU = getModelU().findColumn(colNameU);
	return getValue(colU);
}

public JTypeTableModel getModelU() { return styledModel.getModelU(); }


// ===============================================================
public TableCellRenderer getCellRenderer(int row, int col)
{
	RenderEdit re = styledModel.getRenderEdit(row, col);
	if (re != null) return re.getRenderer(styledModel.isEditable(row, col));
	return super.getCellRenderer(row, col);

//	TableCellRenderer ret = ext.getRenderer(row, col);
//	if (ret != null) return ret;
//	return super.getCellRenderer(row, col);
}

public TableCellEditor getCellEditor(int row, int col)
{
	RenderEdit re = styledModel.getRenderEdit(row, col);
	if (re != null) return re.getEditor();
	return super.getCellEditor(row, col);
	
//	TableCellEditor ret = ext.getEditor(row, col);
//	if (ret != null) return ret;
//	return super.getCellEditor(row, col);
}

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
		String ttip = styledModel.getTooltip(row, col);
		jc.setToolTipText(ttip);
	}

	// Highlight row the mouse is over
	if (row == mouseRow || this.getSelectionModel().isSelectedIndex(row)) {
		c.setBackground(cTextHighlightBg);
		c.setForeground(cTextHighlightFg);
	} else {
		Color color;
		color = styledModel.getBgColor(row, col);
		c.setBackground(color == null ? cTextBg : color);
		color = styledModel.getFgColor(row, col);
		c.setForeground(color == null ? cTextFg : color);
	}
	
	// Set up the font
	Font font = styledModel.getFont(row, col);
	if (font != null) c.setFont(font);
	
	// Set up button depressed state for buttons we're rendering
/* TODO: This doesn't work in the case that abstractButton.setSelected()
 * is used by a TypedWidget's setValue() method.  We can only mess
 * with setSelected() when we KNOW it doesn't conflict with the
 * value being displayed.  As of yet, we don't really know.
	if (c instanceof AbstractButton) {
		AbstractButton ab = (AbstractButton)c;
		boolean sel = pressedRow == row && pressedCol == col;
		ab.setSelected(sel);
//System.out.println("StyledTable pressed = (" + pressedRow + ", " + pressedCol + ")"
//	+ " cur = (" + row + ", " + col + ")");
	}
*/
	return c;
}
// ==========================================================
// Allow font changes (and keep track of font metrics)
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
// Highlight rows when the mouse is over them
public void setHighlightMouseover(boolean b)
{
	if (highlightMouseover == b) return;

	highlightMouseover = b;
//	if (b) {
//		this.addMouseListener(this); //new MyMouseAdapter());
//		this.addMouseMotionListener(this); //new MyMouseMotionAdapter());
//	} else {
//		this.removeMouseListener(this); //new MyMouseAdapter());
//		this.removeMouseMotionListener(this); //new MyMouseMotionAdapter());		
//	}
}
public boolean isHighlightMouseover() { return highlightMouseover; }
// --------------------------------------

private static void updateCell(JTable aTable, int row, int col)
{
	JTypeTableModel model = (JTypeTableModel)aTable.getModel();
	TableModelEvent tce = new TableModelEvent(
		model, row, row,
		col, TableModelEvent.UPDATE);
	model.fireTableChanged(tce);
}

// MouseListener
protected void runSuperMousePressed(MouseEvent e)
	{ for (MouseListener l : superListeners) l.mousePressed(e); }

public void mousePressed(MouseEvent e)
{
	// =========== Figure out the parameters of this event
	// Initialize
	pressedRunSuper = true;
	pressedButtonListener = null;

	// See where the user clicked
	JTable aTable =  (JTable)e.getSource();
	Point point = e.getPoint();
	pressedRow = aTable.rowAtPoint(point);
	pressedCol = aTable.columnAtPoint(point);
	mousePressed = true;
	System.out.println("StyledTable.mousePressed: (" + pressedRow + ", " + pressedCol + ")");
	
	// Find a registered listener for that event
	pressedButtonListener = styledModel.getButtonListener(pressedRow, pressedCol);
	if (pressedButtonListener == null ||
		!pressedButtonListener.onPressed(pressedRow, pressedCol, e))
		runSuperMousePressed(e);
	
//	// Process events ourself
//
//	if (e.isPopupTrigger()) {
//		System.out.println("isPopupTrigger");
//		pressedRunSuper = false;
//	} else {
//		System.out.println("notPopupTrigger");
//		pressedButtonListener = styledModel.getButtonListener(pressedRow, pressedCol);
//		if (pressedButtonListener != null) pressedRunSuper = false;
//	}
//System.out.println("pressedRunSuper = " + pressedRunSuper);
//System.out.println("mousePressed: row/col=(" + pressedRow + ", " +
//		pressedCol + "), listener=" + pressedButtonListener);
//
//	// ======================== Run stuff
//	// Run super-listeners, if we should
//	if (pressedRunSuper) runSuperMousePressed(e);

	// Redraw the cell, pressed
	if (pressedRow >= 0 && pressedCol >= 0) {
		updateCell(aTable, pressedRow, pressedCol);
	}

//	// Run the button listener, if we should
//	if (pressedButtonListener != null) pressedButtonListener.onPressed(pressedRow, pressedCol, e);
//
//
//	// Run super-listeners first.  Prevent table from getting events
//	// that would cause a popup menu
//System.out.println("StyledTable.mousePressed: popupTrigger = " + e.isPopupTrigger());
//	pressedPopupTrigger = e.isPopupTrigger();
//	if (!pressedPopupTrigger)
//		for (MouseListener l : superListeners) l.mousePressed(e);
//
//	// Repaint the cell, so we can update any button renderers in it
//	// Figure out the row and column we clicked on
//
//	// Redraw the cell, pressed.
//System.out.println("    pressed row/col = (" + pressedRow + ", " + pressedCol + ")");
//
//	// Process user events
//	ButtonListener listener = styledModel.getButtonListener(pressedRow, pressedCol);
//System.out.println("    listener = " + listener);
//	if (listener != null) listener.onPressed(
//		pressedRow, pressedCol, e);

}

protected void runSuperMouseReleased(MouseEvent e)
	{ for (MouseListener l : superListeners) l.mouseReleased(e); }

public void mouseReleased(MouseEvent e) {
//	// Run super-listeners first.  Prevent table from getting events
//	// that would cause a popup menu
//	if (pressedRunSuper && !e.isPopupTrigger())
//		for (MouseListener l : superListeners) runSuperMouseReleased(e);
	mousePressed = false;

	System.out.println("StyledTable.mouseReleased: (" + pressedRow + ", " + pressedCol + ")");

	JTable aTable =  (JTable)e.getSource();
	if (pressedRow >= 0 && pressedRow < aTable.getRowCount() &&
		pressedCol >= 0 && pressedCol < aTable.getColumnCount())
	{
		// Repaint the cell, so we can update any button renderers in it

		if (pressedButtonListener == null ||
			!pressedButtonListener.onReleased(pressedRow, pressedCol, e))
			runSuperMouseReleased(e);

		// Redraw the cell, unpressed
		updateCell(aTable, pressedRow, pressedCol);
	}
}

protected void runSuperMouseClicked(MouseEvent e)
	{ for (MouseListener l : superListeners) l.mouseClicked(e); }

public void mouseClicked(MouseEvent e)
{
	mousePressed = false;

	System.out.println("StyledTable.mouseClicked: (" + pressedRow + ", " + pressedCol + ")");
	// Figure out row and column of the mouse event
	JTable aTable =  (JTable)e.getSource();
	if (pressedRow >= 0 && pressedRow < aTable.getRowCount() &&
		pressedCol >= 0 && pressedCol < aTable.getColumnCount())
	{
		if (pressedButtonListener == null ||
			!pressedButtonListener.onClicked(pressedRow, pressedCol, e))
			runSuperMouseClicked(e);
	}
}

public void mouseEntered(MouseEvent e) {
System.out.println("StyledTable.mouseEntered()");
	// Pass through to super-listeners first
	for (MouseListener l : superListeners) l.mousePressed(e);
	
	// Nothing else to do
}
public void mouseExited(MouseEvent e) {
System.out.println("StyledTable.mouseExited()");
	// Pass through to super-listeners first
	for (MouseListener l : superListeners) l.mousePressed(e);
	
	// Unpaint row if we're highlighting on mouseover
	if (highlightMouseover) {
		JTable aTable =  (JTable)e.getSource();
		mouseRow = -1;
		aTable.repaint();
	}
}
// --------------------------------------
// MouseMotionListener
public void mouseDragged(MouseEvent e) {}
public void mouseMoved(MouseEvent e) {
	if (!highlightMouseover) return;
	
	JTable aTable = (JTable)e.getSource();
	int oldRow = mouseRow;
	mouseRow = aTable.rowAtPoint(e.getPoint());
//	itsColumn = aTable.columnAtPoint(e.getPoint());
	if (oldRow != mouseRow) aTable.repaint();
}

// ==========================================================
// Handle the fillViewport flag
public boolean isFillViewport()
	{ return fillViewport; }

public void setFillViewport(boolean fillViewport)
	{ this.fillViewport = fillViewport; }

/** Force the table to fill the viewport's height, if fillViewport. */
public boolean getScrollableTracksViewportHeight( )
{
	if (!fillViewport) return false;
	
	final java.awt.Component p = getParent( );
	if ( !(p instanceof javax.swing.JViewport) )
		return false;
	return ((javax.swing.JViewport)p).getHeight() > getPreferredSize().height;
}
// ==========================================================
//// Handle the selected row (and data lookup in it)
///** Returns the row a value is found on (or -1 if no such row) */
//public int rowOfValue(Object val, int col)
//	{ return rowOfValue(val, col, getModel()); }
//
///** Returns the row a value is found on (or -1 if no such row) */
//protected int rowOfValue(Object val, int col, TableModel model)
//{
//	for (int i=0; i<model.getRowCount(); ++i) {
//		Object rval = model.getValueAt(i, col);
//		boolean eq = (rval == null ? val == null : val.equals(rval));
//		if (eq) return i;
//	}
//	return -1;
//}

// =======================================================================





//private boolean isHighlightSelected = true;		// Should we highlight selected rows?
//public void setHighlightSelected(boolean b) { this.isHighlightSelected = b; }
//public boolean getHighlightSelected() { return this.isHighlightSelected; }


//RowHeightUpdater rhu;


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

public boolean isCellEditable(int row, int col)
{
	return getStyledTM().isEditable(row, col);
}

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderEdit(int colNo, Swinger.RenderEdit re)
{
	if (re == null) return;		// Don't change, if we don't know what to set it TO.
	
	TableColumn col = getColumnModel().getColumn(colNo);
	TableCellRenderer rr = re.getRenderer(isCellEditable(0, colNo));
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

// ======================================================================
/** NOTE: Third Party Code.
 * Mouse Handler is Copyright (c) 1995 - 2008 by Sun Microsystems.
 * See ArrowIcon.java for full copyright notice. */
private class SortableMouseHandler extends MouseAdapter {
public void mouseClicked(MouseEvent e) {
	JTableHeader h = (JTableHeader) e.getSource();
	TableColumnModel columnModel = h.getColumnModel();
	int viewColumn = columnModel.getColumnIndexAtX(e.getX());
	int col_h = columnModel.getColumn(viewColumn).getModelIndex();
	if (col_h == -1) return;
	
	// Find this column in the main model (or sort) table
//	int col_u = permuteModel.getColU(col_h);
	int col_u = styledModel.getModel().getColU(col_h);

	// Obtain current sorting for possible change
	SortSpec spec = sortModel.getSortSpec();
	int dir = spec.getSortDir(col_u);
	if (!e.isControlDown()) {
		spec.clear();
	}

	// Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or 
	// {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed. 
	dir = dir + (e.isShiftDown() ? -1 : 1);
	dir = (dir + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
	spec.setSortDir(col_u, dir);
System.out.println("Sort by column " + col_u + " direction " + dir);
	
	// Do the refresh
	sortModel.resort();
	getTableHeader().repaint();
	fireSortChanged(spec);
}}
void fireSortChanged(SortSpec spec)
{
	firePropertyChange("sortSpec", null, spec.getStringVal());
}
public void setSortString(String sspec)
{
	if (!(getStyledTM().getModelU() instanceof SortableTableModel)) return;
	SortableTableModel modelU = (SortableTableModel)getStyledTM().getModelU();
	modelU.getSortSpec().setStringVal(sspec);
}
public String getSortString()
{
	SortSpec spec = getSortSpec();
	if (spec == null) return null;
	return getSortSpec().getStringVal();	
}
public SortSpec getSortSpec()
{
	if (!(getStyledTM().getModelU() instanceof SortedTableModel))
		return null;
	SortedTableModel modelU = (SortedTableModel)getStyledTM().getModelU();
	return modelU.getSortSpec();
}

// ======================================================================
/** NOTE: Third Party Code.
 * SortableHeaderRenderer is Copyright (c) 1995 - 2008 by Sun Microsystems.
 * See ArrowIcon.java for full copyright notice. */
private class SortableHeaderRenderer implements TableCellRenderer {
private TableCellRenderer sub;

public SortableHeaderRenderer(TableCellRenderer sub) {
	this.sub = sub;
}

public Component getTableCellRendererComponent(
JTable table,  Object value, boolean isSelected, 
boolean hasFocus, int row, int column)
{		
	SortSpec spec = sortModel.getSortSpec();
	Component c = sub.getTableCellRendererComponent(table, 
			value, isSelected, hasFocus, row, column);
	if (c instanceof JLabel) {
		JLabel l = (JLabel) c;
		l.setHorizontalTextPosition(JLabel.LEFT);

		// User might have rearranged columns in the JTable
		int col_h = table.convertColumnIndexToModel(column);

		// Find this column in the main model (or sort) table
		int col_u = styledModel.getModel().getColU(col_h);
//		int col_u = permuteModel.getColU(col_h);
		
		// Select the icon to display
		Icon icon = null;
		int dir = spec.getSortDir(col_u);
		if (dir != 0) icon = new ArrowIcon(
			dir < 0, l.getFont().getSize(),
			spec.getSortIndex(col_u));
		l.setIcon(icon);
	}
	return c;
}
}
//// ==========================================================
//// Toltips
///** Set the tooltips to be used */
//public void setTTModel(JTypeTableModel ttModel)
//{
//	this.ttModel = ttModel;
//}
///** Override this to do tooltips in custom manner.  For now, we return the "tooltip column" */
//public String getTooltip(int row, int col)
//{
//	if (ttModel == null) return null;
//	return (String)ttModel.getValueAt(row, col);
//}
// ==========================================================

// ==========================================================


// ================================================================


// =====================================================================
// ================================================================
}
