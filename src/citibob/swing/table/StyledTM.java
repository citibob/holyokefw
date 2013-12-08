/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger.RenderEdit;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.Comparator;

/**
 *
 * @author fiscrob
 */
public class StyledTM {

	
public static interface ButtonListener {
	/**
	 * Called when the table detects a button-cell was clicked.
	 * @param row Row in table of clicked button
	 * @param col Column in table of clicked button
	 * @param button The mouse button that was clicked
	 * @param modifiers Ctrl/Shift keys held down when button was pressed
	 * @return true if event was consumed; false if it should be passed on to
	 * another default listener.
	 */
	public boolean onClicked(int row, int col, MouseEvent e);
	
	public boolean onPressed(int row, int col, MouseEvent e);
	
	public boolean onReleased(int row, int col, MouseEvent e);
}

public static class ButtonAdapter implements ButtonListener {
	public boolean onClicked(int row, int col, MouseEvent e) { return true; }
	
	public boolean onPressed(int row, int col, MouseEvent e) { return true; }
	
	public boolean onReleased(int row, int col, MouseEvent e) { return true; }

}

	
protected JTypeTableModel modelU;		// Some underlying table model
protected DataCols<Comparator> compModelU;		// Way to sort each column

/** The model to display; may be a projection of modelU, may be the same. */
protected JTypeTableModel model;

protected StyledTM() {}

/** Does not set model, just modelU */
public StyledTM(JTypeTableModel modelU)
{
	this.modelU = modelU;
}

public void setModel(JTypeTableModel model)
{
	this.model = model;
}
public void setModels(JTypeTableModel modelU, JTypeTableModel model)
{
	this.modelU = modelU;
	this.model = model;
}

public void setCompModelU(DataCols<Comparator> compModelU)
	{ this.compModelU = compModelU; }
public DataCols<Comparator> getCompModelU()
	{ return compModelU; }
//public StyledTableModel(JTypeTableModel modelU, JTypeTableModel model)
//{
//	this.modelU = modelU;
//	this.model = model;
//}
/** The main table model */
public JTypeTableModel getModel() { return model; }
/** The main table model */
public JTypeTableModel getModelU() { return modelU; }

///** Formatting extensions */
//public SFormat getSFormat(int row, int col)
//{
//	Swinger swinger = getSwinger(row, col);
//	if (swinger != null) return swinger.getSFormat();
//	return null;
//}
//public Swinger getSwinger(int row, int col) { return null; }
//public abstract TableCellRenderer getRenderer(int row, int col);
//public abstract TableCellEditor getEditor(int row, int col);
public RenderEdit getRenderEdit(int row, int col) { return null; }

public String getTooltip(int row, int col) { return null; }
public Color getBgColor(int row, int col) { return getExtBgColor(row, col); }
public Color getFgColor(int row, int col) { return null; }
/** Background color for rows BEYOND the end of the TableModel; used to
 * paint zebra stripes below the table data. */
public Color getExtBgColor(int row, int col) { return null; }
/** Font must be the same size as the font previously set via StyledTable.setFont() */
public Font getFont(int row, int col) { return null; }
public boolean isEditable(int row, int col)
	{ return model.isCellEditable(row, col); }



/** If non-null, then text in this cell should be rendered as a button. */
public ButtonListener getButtonListener(int row, int col)
	{ return null; }

//public int getRowOfValue(Object val, int col)
//	{ return getRowOfValue(val, col, getModel()); }
//public int getRowOfValueU(Object val, int colU)
//	{ return getRowOfValue(val, colU, getModelU()); }
// =================================================================

///** @param fmtSpecs: optional (name, format) pairs */
//public static DelegateStyledTM newColModel0(JTypeTableModel modelU, SwingerMap smap)
//{
//	DelegateStyledTM smodel = new DelegateStyledTM(modelU);
//	RenderEditDataCols reModel = new RenderEditDataCols(smodel, smap);
//	smodel.setRenderEditModel(reModel);
//}
//
///** @param fmtSpecs: optional (name, format) pairs */
//public static DelegateStyledTM newColModel2(JTypeTableModel modelU, SwingerMap smap,
//Object... fmtSpecs)
//{
//	DelegateStyledTM smodel = newColModel0(modelU, smap);
//	RenderEditDataCols reModel = (RenderEditDataCols)smodel.renderEditModel;
//	
//	// Add any column type specifications
//	for (int i=0; i<fmtSpecs.length; i += 2) {
//		String name = (String)fmtSpecs[i];
//		Object spec = fmtSpecs[i+1];
//		reModel.setFormatU(name, spec);
//	}
//	
//	return smodel;
//}
//
//public static DelegateStyledTM newColModel3(JTypeTableModel modelU, SwingerMap smap,
//Object... fmtSpecs)
//{
//	DelegateStyledTM smodel = new DelegateStyledTM(modelU);
//	RenderEditDataCols reModel = new RenderEditDataCols(smodel, smap);
//	smodel.setRenderEditModel(reModel);
//	
//	// Add any column type specifications
//	for (int i=0; i<fmtSpecs.length; i += 2) {
//		String name = (String)fmtSpecs[i];
//		Object spec = fmtSpecs[i+1];
//		reModel.setFormatU(name, spec);
//	}
//	
//	return smodel;
//	
//}


// Use this to replace JTypeColTable, etc...
//
///** @param modelU Underling data buffer to use.  If it's an instance of
// * SortableTableModel, sortable features will be used.
// * @param typeCol Name of type column in the schema
// * @param xColNames Columns (other than type and status) from schema to display
// */
//public void setModelU(JTypeTableModel modelU,
//		String[] colNames, String[] sColMap, boolean[] editable,
//		citibob.swing.typed.SwingerMap smap)
//{
////	modelU = wrapModel(modelU, sColMap);
//	
//	// Set underlying model
//	this.modelU = modelU;
//	
//////	// Set up column selection on top of modelU
//////	permuteModel = new ColPermuteTableModel(
//////		modelU, colNames, sColMap);
//////	if (editable != null) permuteModel.setEditable(editable);
//
//	
//	// Set up table sorting stuff
//	if (modelU instanceof SortableTableModel) {
//		sortModel = (SortableTableModel)modelU;
//		
//		JTableHeader head = getTableHeader();
//		head.addMouseListener(new MouseHandler());
//		head.setDefaultRenderer(
//			new SortableHeaderRenderer(
//			head.getDefaultRenderer()));
//	}
//
//	// Now go!
//	super.setModel(permuteModel);
//	
//	// Set the RenderEdit for each column, according to that column's SqlType.
////	for (int c=0; c<sColMap.length; ++c) {
//	for (int col=0; col<getColumnCount(); ++col) {
//		int col_u = permuteModel.getColU(col);
//		if (col_u < 0) {
//			System.out.println("ERROR: Column " + sColMap[col] + " is undefined!!!");
//		}
//		JType sqlType = modelU.getJType(0,col_u);
//		if (sqlType == null) continue;
//		String colName = modelU.getColumnName(col_u);
//		if (smap != null) {
//			Swinger swing = smap.newSwinger(sqlType, colName);
//			if (swing == null) continue;
//			setRenderEdit(col, swing);
//			Comparator comp = swing.getComparator();
//if (comp == null) {
//	System.out.println("hoi");
//}
//			if (sortModel != null) sortModel.setComparator(col_u, comp);
//		}
//	}
//
//}
//
///** @param modelU Underling data buffer to use
// * @param typeCol Name of type column in the schema
// * @param xColNames Columns (other than type and status) from schema to display
// * @param ttColMap Column in underlying table to display as tooltip for each column in displayed table.
// */
//public void setModelU(JTypeTableModel modelU,
//		String[] colNames, String[] sColMap, String[] ttColMap, boolean[] editable,
//		citibob.swing.typed.SwingerMap smap)
//{
//	this.setModelU(modelU, colNames, sColMap, editable, smap);
//	
//	// Come up with model for all the tooltips
//	ttModel = new StringTableModel(modelU, ttColMap, null, false, smap);
//////	ttModel = new ColPermuteTableModel(modelU, colNames, ttColMap, editable);
////	ttFmt = new SFormat[ttModel.getColumnCount()];
////	for (int i=0; i<ttModel.getColumnCount(); ++i) {
////		int colU = ttModel.getColU(i);
////		if (colU < 0) continue;
////		JType jt = modelU.getJType(0, colU);
////		String colName = modelU.getColumnName(colU);
////		if (jt == null) continue;
////		ttFmt[i] = smap.newSwinger(jt, colName).getSFormat();
////	}
////new StringTableModel
//}





}
