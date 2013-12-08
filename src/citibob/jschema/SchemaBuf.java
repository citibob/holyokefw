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
package citibob.jschema;

import java.sql.*;

import citibob.swing.table.*;

import java.util.*;
import citibob.types.JType;
import static citibob.jschema.RowStatusConst.*;
import citibob.sql.ConsSqlQuery;
import citibob.sql.RsTasklet2;
import citibob.sql.SqlRun;
import citibob.sql.SqlSet;
import citibob.sql.SqlTypeSet;
import citibob.types.JavaJType;

public class SchemaBuf extends AbstractJTypeTableModel
implements OrigTableModel, DbBuf
{

// Extra columns at end (so col #'s match between SqlSchema and Model): __status__ and __rowno__
static final int C_STATUS = 0;
static final int C_ROWNO = 1;
static final int C_COUNT = 2;
static final String[] xtraColNames = {"__status__", "__rowno__"};

/** Data model that defines the main columns */
Schema schema;

// =====================================================
// Our data storage
ArrayList<SqlRow2> rows = new ArrayList();			// ArrayList<SqlRow2>
protected SqlRow2 newRow()
{
	int n = getSchemaColumnCount();
	SqlRow2 row = new SqlRow2(n);
	
	// Put in default values
	for (int i=0; i<n; ++i) {
		row.data[i] = ((SqlCol)schema.getCol(i)).getDefault();
	}
	
	return row;
}
// =====================================================
public SchemaBuf(SqlSchema schema)
{
	this.schema = schema;
}
protected SchemaBuf() {}
public SchemaBuf(ResultSet rs, SqlSchema[] typeSchemas, String[] keyFields, SqlTypeSet tset)
throws SQLException
	{ this(new RSSchema(rs, typeSchemas, keyFields, tset)); }
public SchemaBuf(SqlRun str, SqlSet protoSql, SqlSchema[] typeSchemas, String[] keyFields, SqlTypeSet tset)
	{ setCols(str, protoSql, typeSchemas, keyFields, tset); }
public SchemaBuf(SqlRun str, String protoSql, SqlSchema[] typeSchemas, String[] keyFields, SqlTypeSet tset)
	{ setCols(str, new SqlSet(protoSql), typeSchemas, keyFields, tset); }
// =====================================================
// Unique to SchemaBuf
/** The schema describing the columns */
public Schema getSchema()
	{ return schema; }
public void setSchema(Schema schema)
	{ this.schema = schema; }

/** Parses a series of (name,jtype) pairs */
public void setSchema(Object... objs)
{
	Schema schema = ConstSqlSchema.newSchema(objs);
	setSchema(schema);
}
public String getDefaultTable()
	{ return ((SqlSchema)schema).getDefaultTable(); }
// --------------------------------------------------
/** Tests whether a value in the table model has changed... */
boolean valueChanged(SqlRow2 r, int col)
{
	if (col >= getSchemaColumnCount()) return false;
	Object origData = r.origData[col];
	Object curData = r.data[col];
	return !(curData == null ? origData == null : curData.equals(origData));
}
public boolean valueChanged(int row, int col)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	return valueChanged(r, col);
}
/** Tests whether an entire row has changed */
public boolean valueChanged(int row, int[] cols)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	for (int i = 0; i < cols.length; ++i) {
		int col = cols[i];
		if (col < 0) continue;
		if (valueChanged(r, col)) return true;
	}
	return false;
}// --------------------------------------------------
public Object getOrigValueAt(int rowIndex, int colIndex)
{
	if (colIndex >= getSchemaColumnCount()) return null;
	SqlRow2 r = (SqlRow2)rows.get(rowIndex);
 	return r.origData[colIndex];
}
public void resetValueAt(int rowIndex, int colIndex)
{
	if (colIndex >= getSchemaColumnCount()) return;
	setValueAt(getOrigValueAt(rowIndex, colIndex), rowIndex, colIndex);
}
// --------------------------------------------------
public int getStatus(int row)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	return r.status;
}
// --------------------------------------------------
// ===============================================================
// Implementation of SqlGen: Read rows from the database
public int addRowNoFire(ResultSet rs, int rowIndex, int[] colmap) throws SQLException
{
	SqlRow2 row = newRow();
	for (int rscol = 0; rscol < colmap.length; ++rscol) {
		int scol = colmap[rscol];
		if (scol < 0) continue;
		SqlCol col = (SqlCol)schema.getCol(scol);
////System.out.println("scol = " + scol);
//System.out.println(col);
//System.out.println(col.getSqlType());
//if (col.getSqlType() == null) {
//	System.out.println("hoi");
//}
		row.data[scol] = col.getSqlType().get(rs, col.getName()); //xyzqqq
		row.origData[scol] = row.data[scol];
	}
	rows.add(rowIndex, row);
	return rowIndex;
}
// -------------------------------------------------------------
public void addNewRowsNoFire(int nrow)
{
	int ncol = getSchemaColumnCount();
	for (int i=0; i<nrow; ++i) {
		SqlRow2 row = new SqlRow2(ncol);
		rows.add(row);
	}	
}
/** Add a bunch of blank rows.  Does not set any defaults */
public void addNewRows(int nrow)
{
	int firstRow = rows.size();
	addNewRowsNoFire(nrow);
	int lastRow = rows.size()-1;
	if (lastRow >= firstRow) fireTableRowsInserted(firstRow, lastRow);

}
public void setRowCount(int nrow)
{
	if (nrow == 0) clear();
	addNewRows(nrow - getRowCount());
}
// -------------------------------------------------------------
/** Convenience function (sort of)... */
public void addAllRowsNoFire(ResultSet rs) throws SQLException
{
	int[] colmap = SchemaHelper.newSchemaMap(rs, schema);
	int n = 0;
	while (rs.next()) {
		addRowNoFire(rs, rows.size(), colmap);
		++n;
	}
	rs.close();
}
/** Convenience function (sort of)... */
public void addAllRows(ResultSet rs) throws SQLException
{
	int firstRow = rows.size();
	addAllRowsNoFire(rs);
	int lastRow = rows.size()-1;
	if (lastRow >= firstRow) fireTableRowsInserted(firstRow, lastRow);
}

public void setRows(SqlRun str, String sql)
{
	setRows(str, new SqlSet(sql));
}
public void setRows(SqlRun str, SqlSet ssql)
{
	str.execSql(ssql,new RsTasklet2() {
	public void run(SqlRun str, ResultSet rs) throws SQLException {
		clearNoFire();
		addAllRowsNoFire(rs);
		fireTableDataChanged();
	}});
}
public void setCols(SqlRun str, SqlSet ssql,
final SqlSchema[] typeSchemas, final String[] keyFields, final SqlTypeSet tset)
{
	str.execSql(ssql,new RsTasklet2() {
	public void run(SqlRun str, ResultSet rs) throws SQLException {
		clearNoFire();
		schema = new RSSchema(rs, typeSchemas, keyFields, tset);
		fireTableStructureChanged();
	}});
}
public void setRowsAndCols(SqlRun str, SqlSet ssql,
final SqlSchema[] typeSchemas, final String[] keyFields, final SqlTypeSet tset)
{
	str.execSql(ssql,new RsTasklet2() {
	public void run(SqlRun str, ResultSet rs) throws SQLException {
		setRowsAndCols(rs, typeSchemas, keyFields, tset);
	}});
}
public void setRowsAndCols(ResultSet rs,
final SqlSchema[] typeSchemas, final String[] keyFields, final SqlTypeSet tset)
throws SQLException
{
	clearNoFire();
	schema = new RSSchema(rs, typeSchemas, keyFields, tset);
	addAllRowsNoFire(rs);
	fireTableStructureChanged();
}
// ===============================================================
// Implementation of SqlGen: Help with writing rows to the database
// --------------------------------------------------
/** Makes update query update column(s) represented by this object.
 @param updateUnchanged If true, update even columns that haven't been edited.
 @param qschema SqlSchema to use for generating the query. */
public void getUpdateCols(int row, ConsSqlQuery q, boolean updateUnchanged, SqlSchemaInfo qs)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	for (int qcol = 0; qcol < qs.schema.size(); ++qcol) {
		int col = qs.schemaMap[qcol];
		if (col < 0) continue;		// Query wants to write a column not in our data set
		SqlCol qc = (SqlCol)qs.schema.getCol(qcol);

		Object origData = r.origData[col];
		Object curData = r.data[col];
		boolean unchanged = (curData == null ? origData == null : curData.equals(origData));
		if (updateUnchanged || !unchanged) {
			q.addColumn(qc.getName(), qc.getSqlType().toSql(curData));
		}
	}
}
// --------------------------------------------------
public void getInsertCols(int row, ConsSqlQuery q, boolean insertUnchanged, SqlSchemaInfo qs)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	for (int qcol = 0; qcol < qs.schema.size(); ++qcol) {
		int col = qs.schemaMap[qcol];
		if (col < 0) continue;		// Query wants to write a column not in our data set
		SqlCol qc = (SqlCol)qs.schema.getCol(qcol);

		Object origData = r.origData[col];
		Object curData = r.data[col];
		boolean unchanged = (curData == null ? origData == null : curData.equals(origData));
System.out.println("   getInsertCols(" + row + ", " + col + "): " + !unchanged + ", " + origData + " -> " + curData);
		if (insertUnchanged || !unchanged) {
			q.addColumn(qc.getName(), qc.getSqlType().toSql(curData));
		}
	}

}
// --------------------------------------------------
/** Adds key fields to where clause; for delete and select queries.
 * For columns, this will just check the isKey() field and add itself
 * or not.  For records, will call getWhereKey on children
 * (unless the record has special knowledge about itself.) */
public void getWhereKey(int row, ConsSqlQuery q, SqlSchemaInfo qs)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	for (int qcol = 0; qcol < qs.schema.size(); ++qcol) {
		int col = qs.schemaMap[qcol];
		if (col < 0) continue;		// Query wants to write a column not in our data set
		SqlCol qc = (SqlCol)qs.schema.getCol(qcol);

		if (qc.isKey()) {
			q.addWhereClause(qs.table + "." + qc.getName() + " = " +
				 qc.getSqlType().toSql(r.data[col]));
		}
	}
}
// --------------------------------------------------
//public void getSelectCols(ConsSqlQuery q, String asName, SchemaInfo qs)
//{
//	for (int qcol = 0; qcol < qs.schema.getColCount(); ++qcol) {
//		int col = qs.schemaMap[qcol];
//		if (col < 0) continue;		// Query wants to write a column not in our data set
//		Column qc = qs.schema.getCol(qcol);
//
//		q.addColumn(qs.table + "." + qc.getName() + " as " + qc.getName());
//	}
//}
/** For auto-generating SELECT queries, we use our own schema. */
public void getSelectCols(ConsSqlQuery q, String asName)
{
	for (int qcol = 0; qcol < schema.size(); ++qcol) {
		int col = qcol;
		if (col < 0) continue;		// Query wants to write a column not in our data set
		SqlCol qc = (SqlCol)schema.getCol(qcol);

		q.addColumn(asName + "." + qc.getName() + " as " + qc.getName());
	}
}
// --------------------------------------------------
protected void clearNoFire()
{
	rows.clear();	
}
/** Clear all rows from RecSet, sets nRows() == 0 */
public void clear()
{
	int oldSize = rows.size();
	clearNoFire();
	if (oldSize > 0) fireTableRowsDeleted(0, oldSize - 1);
}
// --------------------------------------------------
public void setStatus(int row, int status)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	r.status = status;
	fireStatusChanged(row);
}
public void fireStatusChanged(int row)
{
	super.fireTableCellUpdated(row, schema.size()+C_STATUS);
}
// ---------------------------------------------------
/** Mark a row for deletion. */
public void deleteRow(int rowIndex)
{
	if (rowIndex < 0) return;
	SqlRow2 r = (SqlRow2)rows.get(rowIndex);
	if ((r.status & DELETED) != 0) return;		// Already deleted
	if ((r.status & INSERTED) != 0) {
		removeRow(rowIndex);
	} else {
		// Mark row as deleted, so we can remove it from DB
		r.status |= DELETED;
		fireStatusChanged(rowIndex);
	}
}
/** Mark all rows for deletion. */
public void deleteAllRows()
{
	for (int i=0; i<getRowCount(); ++i) deleteRow(i);
}
public void undeleteRow(int rowIndex)
{
	if (rowIndex < 0) return;
	SqlRow2 r = (SqlRow2)rows.get(rowIndex);
	if ((r.status & DELETED) == 0) return;		// Already not deleted
	
	// Mark row as not deleted, so we can remove it from DB
	r.status &= ~DELETED;
	fireStatusChanged(rowIndex);
}
/** Mark all rows for deletion. */
public void undeleteAllRows()
{
	for (int i=0; i<getRowCount(); ++i) undeleteRow(i);
}
// ---------------------------------------------------
/** Physically remove a row from this buffer. */
public void removeRow(int row)
{
	if (row < 0) return;
	rows.remove(row);
	fireTableRowsDeleted(row, row);
}
// ---------------------------------------------------
/** For making space for a new row of data to be later inserted into DB.
If rowIndex == -1, it gets appended on end. */
public int insertRow(int rowIndex)
{
	if (rowIndex == -1) rowIndex = rows.size();
	SqlRow2 row = newRow();
	row.status = INSERTED;
	rows.add(rowIndex, row);
	fireTableRowsInserted(rowIndex, rowIndex);
	return rowIndex;
}
// --------------------------------------------------
/** Insert a row and initialize it with data.  colNames[] and
vals[] must have the same length.
 @param vals initial values for the columns
 @param colNames columns for which we want initial values */
public int insertRow(int rowIndex, String[] colNames, Object[] vals)
throws KeyViolationException
{

for (int i = 0; i < colNames.length; ++i) System.out.println("    insertRow " + i + ": " + colNames[i] + " " + vals[i]);
	int rowInserted = 0;		// Row # of the row we inserted (or found)
	int[] coli = new int[colNames.length];
	int numKey = 0;
	for (int i=0; i<colNames.length; ++i) {
		coli[i] = findColumn(colNames[i]);
		if (coli[i] < 0) throw new KeyViolationException("Column named " + colNames[i] + " not found!");
		if (isKey(coli[i])) ++numKey;
	}

	// Count # of keys in SqlSchema.  If we haven't inserted into all of them
	// assume we're using an auto-increment or default key field, and thus
	// no key violation.
	int fullKeyCount = 0;
	for (int i=0; i<this.getSchemaColumnCount(); ++i) if (isKey(i)) ++fullKeyCount;

	if (numKey == 0) {
		// No keys: just insert the row and be done with it.
		rowInserted = insertRow(rowIndex);
	} else {
		// Check the key fields against other items in the table
		for (int r=0; ; ++r) {
			if (r == getRowCount()) {
				// Didn't find a match: insert a new row.
				rowInserted = insertRow(rowIndex);
				break;
			}

			// See if this row matches
			boolean beq = true;
			for (int i=0; i<colNames.length; ++i) {
//System.out.println("r = " + r + " col " + i + " coli = " + coli[i] + " valueAt = " + getValueAt(r,coli[i]) + " val = " + vals[i]);
				if (isKey(coli[i]) && !getValueAt(r,coli[i]).equals(vals[i])) {
					beq = false;
					break;
				}
			}

			// It does match!  Let's use it!
			if (beq) {
				// We equal a pre-existing key...
				if ((getStatus(r) & DELETED) != 0) {
					// If it's been marked deleted, just un-mark it.
//System.out.println("r = " + r);
					setStatus(r, getStatus(r) & ~DELETED);
					rowInserted = r;
					break;
				} else if (numKey == fullKeyCount) {
					// An actual duplicate; throw exception
					throw new KeyViolationException("Key violation");	// Keep msg simple for now
				}
			}
		}
	}
	// NOW: the correct row has been inserted.

	// Set any values necessary
	for (int i=0; i<colNames.length; ++i) {
		setValueAt(vals[i], rowInserted, findColumn(colNames[i]));
	}
	return rowInserted;
}
public int insertRow(int rowIndex, String colName, Object val)
throws KeyViolationException
{
	return insertRow(rowIndex, new String[] {colName}, new Object[] {val});
}
// ===============================================================
// Implementation of TableModel

// --------------------------------------------------
public int findColumn(String colName)
{
//System.out.println("this = " + this);
	for (int i=0; i<xtraColNames.length; ++i)
		if (xtraColNames[i].equals(colName)) return getSchemaColumnCount()+i;
	return schema.findCol(colName);
}
public Class getColumnClass(int col)
{
	int ncol = getSchemaColumnCount();
	if (col >= ncol) switch(col - ncol) {
		case C_STATUS : return Integer.class;
		case C_ROWNO : return Integer.class;
	}
	return schema.getCol(col).getType().getObjClass();
}
public JType getJType(int row, int col)
{
//if (col < 0) {
//	System.out.println("hoi");
//}
	int ncol = getSchemaColumnCount();
	if (col >= ncol) switch(col - ncol) {
		case C_STATUS : return JavaJType.jtInteger;
		case C_ROWNO : return JavaJType.jtInteger;
	}
	return schema.getCol(col).getType();
}
public String getColumnName(int col)
{
	int ncol = getSchemaColumnCount();
	if (col >= ncol) return xtraColNames[col - ncol];
	
	return schema.getCol(col).getName();
}
// --------------------------------------------------
public boolean isKey(int col)
{
	int ncol = getSchemaColumnCount();
	if (col >= ncol) return false;
	return ((SqlCol)schema.getCol(col)).isKey();
}
/** Allow editing of all non-key fields. */
public boolean isCellEditable(int row, int col)
{
	int ncol = getSchemaColumnCount();
	if (col >= ncol) return false;
	return !isKey(col);
}
// --------------------------------------------------
public void setValueAt(Object val, int row, int col)
{
	int ncol = getSchemaColumnCount();
	if (col >= ncol) return;
	
	// Figure out whether our new value is same or different from original
	SqlRow2 srow = (SqlRow2)rows.get(row);

	boolean ochanged = valueChanged(srow, col);
	srow.data[col] = val;		// Make the change

	// Figure out status, etc...
	boolean nchanged = valueChanged(srow, col);
	if (ochanged == nchanged) return;
	int status = getStatus(row);
	int nstatus = (nchanged ? status | CHANGED : status & ~CHANGED);
	if (status != nstatus) this.setStatus(row, nstatus);

	// Update listeners
	fireTableCellUpdated(row, col);
}
// --------------------------------------------------
/** # columns in the table, including extra columns */
public int getColumnCount()
	{ return schema.size() + C_COUNT; }
public int getSchemaColumnCount()
	{ return schema.size(); }

public int getRowCount()
	{ return rows.size(); }
public Object getValueAt(int row, int col)
{
	SqlRow2 r = (SqlRow2)rows.get(row);
	int ncol = getSchemaColumnCount();
	if (col >= ncol) switch(col - ncol) {
		case C_STATUS : return r.getStatus();
		case C_ROWNO : return new Integer(row+1);
	}
	return r.data[col];
}
public boolean isVisible(int col)
{
	return (col < getSchemaColumnCount());
}
// ===================================================================
protected static class SqlRow2
{
	/** Status bits; @see DbModel. */
	int status = 0;

	Object[] data;
	/** The "original" version of this data.  Used to see if values have changed. */
	Object[] origData;
	public SqlRow2(int ncols)
	{
		data = new Object[ncols];
		origData = new Object[ncols];
	}

	public int getStatus()
		{ return status; }
	public void setStatus(int status)
		{ this.status = status; }
	public Object get(int col)
		{ return data[col]; }
}
// ====================================================================

}
