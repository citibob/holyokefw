/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.jschema.DbBuf;

/**
 * Glues together a main table and a pivot table
 * @author citibob
 */
public class ExtPivotTableModel
extends MultiJTypeTableModel implements DbBuf
{

public static final int BASE = 0;
public static final int PIVOT = 1;
	
public ExtPivotTableModel(DbBuf base, PivotTableModel pivot)
{
	super(base, pivot);
}

public DbBuf getBase() { return (DbBuf)getModel(BASE); }
public JTypeTableModel getPivot() { return (JTypeTableModel)getModel(PIVOT); }


/** Mark a row for deletion. */
public void deleteRow(int rowIndex)
	{ getBase().deleteRow(rowIndex); }

/** Mark all rows for deletion. */
public void deleteAllRows()
	{ getBase().deleteAllRows(); }

public void undeleteRow(int rowIndex)
	{ getBase().undeleteRow(rowIndex); }

/** Mark all rows for deletion. */
public void undeleteAllRows()
	{ getBase().undeleteAllRows(); }


/** Create an appropriate ColPermuteTableModel */
public ColPermuteTableModel project(
String[] xcolNames, String[] xsColMap)
{
	JTypeTableModel pivotU = getPivot();
	int nmcol = xcolNames.length;
	int npcol = pivotU.getColumnCount();

	String[] colNames = new String[nmcol + npcol];
	String[] sColMap = new String[nmcol + npcol];
	int i;
	for (i=0; i<nmcol; ++i) {
		colNames[i] = xcolNames[i];
		sColMap[i] = xsColMap[i];
	}
	for (int j=0; j<npcol; ++j) {
		colNames[i+j] = pivotU.getColumnName(j);
		sColMap[i+j] = pivotU.getColumnName(j);
	}

	return new ColPermuteTableModel(this, colNames, sColMap);
}





//public void setPivotValFormat(StyledTableModel stm, SFormat sfmt)
//{
//	DataCols cols;
//	cols.set
//	// Find first pivoted column
////	ColPermuteTableModel pm = table.getPermuteModel();
//	JTypeTableModel pm = stm.getModel();
//	int firstColU = mainU.getColumnCount();
//	int firstCol = pm.getColUInv(firstColU);		// First pivot column in main table
//	
//	for (int i=firstCol; i<table.getColumnCount(); ++i)
//		table.setFormat(i, sfmt);
//}

// ==============================================================================

///** Sets up model and modelU for use in StyledTableModel.
// * @param base is the projected version of baseU, for consumer display.
// * @returns return[0] is modelU, return[1] is model */
//public static DbBuf setModels(StyledTableModel stm, DbBuf baseU, JTypeTableModel base, PivotTableModel pivot)
//{
//	DbBuf modelU = new ExtPivotTableModel(baseU, pivot);
//	JTypeTableModel model = new MultiJTypeTableModel(base, pivot);
//	stm.setModels(modelU, model);
//	return modelU;
//}



}

//	
//	
//	
//	
//DbBuf baseU;
//PivotTableModel pivot;
//				// Also: setdisplay status to edited when either part of the table is edited.
//
//
//public ExtPivotTableModel(DbBuf baseU, PivotTableModel pivot)
//{
//	this.baseU = baseU;
//	this.pivot = pivot;
//}
//
//public JTypeTableModel newModel()
//
//
//
//
//public ExtPivotTableModel(DbBuf mainU, PivotTableModel pivotU)
//{
//	super(mainU, pivotU);
//	this.mainU = mainU;
//	this.pivotU = pivotU;
//}
//
//public void setPivotValFormat(StyledTableModel stm, SFormat sfmt)
//{
//	// Find first pivoted column
////	ColPermuteTableModel pm = table.getPermuteModel();
//	JTypeTableModel pm = stm.getModel();
//	int firstColU = mainU.getColumnCount();
//	int firstCol = pm.getColUInv(firstColU);		// First pivot column in main table
//	
//	for (int i=firstCol; i<table.getColumnCount(); ++i)
//		table.setFormat(i, sfmt);
//}
//
//public void setModelU(JTypeColTable table,
//String[] xcolNames, String[] xsColMap,
//String[] xttColMap,
//boolean[] xeditable, boolean pEditable,
//SwingerMap smap)
//{
//	int nmcol = xcolNames.length;
//	int npcol = pivotU.getColumnCount();
//	
//	if (xttColMap == null) xttColMap = new String[nmcol];
//	if (xeditable == null) xeditable = new boolean[nmcol];
//	
//	boolean[] editable = new boolean[nmcol + npcol];
//	String[] colNames = new String[nmcol + npcol];
//	String[] sColMap = new String[nmcol + npcol];
//	String[] ttColMap = new String[nmcol + npcol];
//	int i;
//	for (i=0; i<nmcol; ++i) {
//		colNames[i] = xcolNames[i];
//		sColMap[i] = xsColMap[i];
//		ttColMap[i] = xttColMap[i];
//		editable[i] = xeditable[i];
//	}
//	for (int j=0; j<npcol; ++j) {
//		colNames[i+j] = pivotU.getColumnName(j);
//		sColMap[i+j] = pivotU.getColumnName(j);
//		ttColMap[i+j] = null;
//		editable[i+j] = pEditable;
//	}
//	
//	table.setModelU(this, colNames, sColMap, ttColMap, editable, smap);
//}
//
//
//
//	/** Mark a row for deletion. */
//	public void deleteRow(int rowIndex)
//		{ mainU.deleteRow(rowIndex); }
//
//	/** Mark all rows for deletion. */
//	public void deleteAllRows()
//		{ mainU.deleteAllRows(); }
//
//	public void undeleteRow(int rowIndex)
//		{ mainU.undeleteRow(rowIndex); }
//
//	/** Mark all rows for deletion. */
//	public void undeleteAllRows()
//		{ mainU.undeleteAllRows(); }
//
//
//}
