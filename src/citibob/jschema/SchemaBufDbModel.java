/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) anydoin later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package citibob.jschema;

import java.sql.*;
import javax.swing.event.*;
import citibob.task.*;
import citibob.sql.*;
import java.util.*;
import citibob.jschema.log.*;
import citibob.util.IntVal;

public class SchemaBufDbModel extends BaseDbModel
implements TableDbModel, RowStatusConst
{
QueryLogger logger;

boolean updateBufOnUpdate = true;	// Should we update sequence columns on insert?
//SqlRunner str;

boolean insertBlankRow = false;
DbChangeModel dbChange;

protected SchemaBuf sbuf;		// The buffer -- has its own schema, to be used for SQL selects in subclass
String selectTable = null;		// Table to use for select queries

SqlSchemaInfo[] updateSchemas;
/** The listener used to pushBatch updates to the database instantly (a la Access) */
TableModelListener instantUpdateListener = null;
//InstantUpdateListener instantUpdateListener;

String whereClause;
String orderClause;

/** Convenience for subclasses */
//protected int intKey;
//protected String stringKey;
//protected Object[] keys;
protected int[] keyFields, invKeyFields;

	/** Should we add the key field to the SQL statement when we insert records?  Generally,
	this will be false for main tables (because they have auto-insert), and
	true for subsidiary tables. Defaults to true. */
	public boolean doInsertKeys = true;
	/** Should we select entire table if key value is null? */
	public boolean selectAllOnNull = false;

public void setDoInsertKeys(boolean doInsertKeys) { this.doInsertKeys = doInsertKeys; }
public void setSelectAllOnNull(boolean selectAllOnNull) { this.selectAllOnNull = selectAllOnNull; }
// -------------------------------------------------------------
protected void init(SchemaBuf sbuf, String selectTable, SqlSchemaInfo[] updateSchemas, DbChangeModel dbChange)
{
//if (sbuf == null) {
//	System.out.println("hoi");
//}
	this.sbuf = sbuf;
	this.selectTable = selectTable;
	this.dbChange = dbChange;
	this.updateSchemas = updateSchemas;

	// Set the select key fields
	Schema schema = sbuf.getSchema();
	this.keyFields = new int[0];
	invKeyFields = new int[schema.size()];
	for (int i=0; i<schema.size(); ++i) invKeyFields[i] = -1;
	
	if (updateSchemas != null)
	for (int i=0; i<updateSchemas.length; ++i) {
		updateSchemas[i].schemaMap = SchemaHelper.newSchemaMap(updateSchemas[i].schema, sbuf.getSchema());
	}
}
protected SchemaBufDbModel() {}

/** Uses the default table for the SqlSchema in buf. */
public SchemaBufDbModel(SchemaBuf sbuf, SqlSchemaInfo[] updateSchemas, DbChangeModel dbChange)
	{ init(sbuf, sbuf.getDefaultTable(), updateSchemas, dbChange); }
public SchemaBufDbModel(SchemaBuf sbuf, DbChangeModel dbChange)
	{ this(sbuf, sbuf.getDefaultTable(), dbChange); }
public SchemaBufDbModel(SqlSchema schema, DbChangeModel dbChange)
	{ this(new SchemaBuf(schema), dbChange); }
public SchemaBufDbModel(SchemaBuf sbuf)
	{ this(sbuf, sbuf.getDefaultTable(), null); }
public SchemaBufDbModel(SchemaBuf sbuf, String table, DbChangeModel dbChange)
{
	if (table == null) table = sbuf.getDefaultTable();
	init(sbuf, table,
		new SqlSchemaInfo[] {new SqlSchemaInfo((SqlSchema)sbuf.getSchema(), table)},
		dbChange);
}
public SchemaBufDbModel(SqlSchema schema, String table, DbChangeModel dbChange)
{
	if (table == null) table = schema.getDefaultTable();
	init(new SchemaBuf(schema), table,
		new SqlSchemaInfo[] {new SqlSchemaInfo(schema, table)},
		dbChange);
}

// -------------------------------------------------------------
public SchemaBuf getSchemaBuf() { return sbuf; }
public citibob.swing.table.JTypeTableModel getTableModel() { return getSchemaBuf(); }
public void setLogger(QueryLogger logger) { this.logger = logger; }

public void setUpdateBufOnUpdate(boolean b) { updateBufOnUpdate = b; }

// -----------------------------------------------------------
/** Sets up which fields will be used to key this SQL statement --- should
 generally be a subset of the key fields in the underlying schema.  Underlying
 key fields that are constant should be set up in setWhereClause().
 @param keyFieldNames  If null, just use key fields from schema.
 */
public void setSelectKeyFields(String... keyFieldNames)
{
	Schema schema = sbuf.getSchema();
	if (keyFieldNames == null) {
		// Use names from schema
		int nkey = 0;
		for (int i=0; i<schema.size(); ++i) {
			SqlCol col = (SqlCol)schema.getCol(i);
			if (col.isKey()) ++nkey;
		}
		keyFields = new int[nkey];
		int j=0;
		for (int i=0; i<schema.size(); ++i) {
			SqlCol col = (SqlCol)schema.getCol(i);
			if (col.isKey()) keyFields[j++] = i;
		}
	} else {
		keyFields = new int[keyFieldNames.length];
		for (int i=0; i<keyFieldNames.length; ++i) {
			keyFields[i] = schema.findCol(keyFieldNames[i]);
		}
	}
	// Get inverse of key fields map
//	invKeyFields = new int[schema.getColCount()];
	for (int i=0; i<schema.size(); ++i) invKeyFields[i] = -1;
	for (int i=0; i<keyFields.length; ++i) invKeyFields[keyFields[i]] = i;
		
//	keys = new Object[keyFields.length];
	setNumKeys(keyFields.length);
}

public void setNumKeys(int n)
{
	keys = new Object[n];
}

///** Sets all key fields at once */
//public void setKeys(Object... keys)
//	{ this.keys = keys; }
//
///** Sets just the first key field (most common case) */
//public void setKey(Object key)
//	{ keys[0] = key; }
//
//public void setKey(int ix, Object key)
//	{ keys[ix] = key; }

public void setKey(String name, Object key)
{
	int col = sbuf.getSchema().findCol(name);
	int keycol = invKeyFields[col];
	setKey(keycol, key);
}
public Object getKey(String name)
{
	int col = sbuf.getSchema().findCol(name);
	int keycol = invKeyFields[col];
	return getKey(keycol);
}
// -----------------------------------------------------------
//
//
//	if (key == null || key.length == 0) return;
//	
//	Schema schema = sbuf.getSchema();
//	StringBuffer sb = new StringBuffer("1=1");
//	int j = 0;
//	for (int i=0; i<schema.getColCount(); ++i) {
//		SqlCol c = (SqlCol)schema.getCol(i);
//		if (!c.isKey()) continue;
////System.out.println("SchemaBufDbModel.setKey found key col " + j + ": " + c.getName());
//		sb.append(" and " + selectTable + "." + c.getName() +
//			"=" + c.toSql(key[j]));
//		++j;
//	}
//	setWhereClause(sb.toString());
//}

///** Common convenience function, to override. */
//public void setKey(int key) {intKey = key;}
///** Common convenience function, to override. */
//public void setKey(String key) {stringKey = key;}

public void setWhereClause(String whereClause)
{
	this.whereClause = whereClause;
}
public void setOrderClause(String orderClause)
	{ this.orderClause = orderClause; }

// -----------------------------------------------------------
/** Adds extra fields to an insert query that must be provided
before a row can be inserted into the database.  Typically, this
will involve setting the key fields (same as setSelectWhere()),
which are usually the same for all the same for all records
in the SqlGenDbModel.  This method is called AFTER the rest of
the insert query has been constructed. */
public void setInsertKeys(int row, ConsSqlQuery q)
{
	if (doInsertKeys) {
		Schema schema = sbuf.getSchema();
		for (int i=0; i<keyFields.length; ++i) {
			SqlCol col = (SqlCol)schema.getCol(keyFields[i]);
			q.addColumn(col.getName(), col.toSql(getKey(i)));
		}
	}	
}

/** Set the where clause for the select statement, based on current key
 and current value of setWhere() and setOrder()... */
public void setSelectWhere(ConsSqlQuery q)
{
	Schema schema = sbuf.getSchema();
	StringBuffer sb = new StringBuffer("1=1");
	for (int i=0; i<keyFields.length; ++i) {
		Object val = getKey(i);
		SqlCol col = (SqlCol)schema.getCol(keyFields[i]);
		if (val == null) {
			if (!selectAllOnNull) sb.append(
				" and " + selectTable + "." + col.getName() + " is null");
		} else {
			sb.append(" and " + selectTable + "." + col.getName() +
				"=" + col.toSql(val));
		}
	}
	q.addWhereClause(sb.toString());
	q.addWhereClause(whereClause);
	q.addOrderClause(orderClause);
}

//
//public void setSelectWhere(ConsSqlQuery q)
//{
//	super.setSelectWhere(q);
//	if (idValue >= 0 || !prm.selectAllOnNull) q.addWhereClause(keyField + " = " + idValue);
//}
//public void setInsertKeys(int row, ConsSqlQuery q)
//{
//	super.setInsertKeys(row, q);
//	if (prm.doInsertKeys) q.addColumn(keyField, SqlInteger.sql(idValue));
////	q.addColumn("lastupdated", "now()");
//}
//












// ===========================================================

/** This should NOT be used by subclasses.  In general, instant update is a property
assigned by enclosing objects --- panels that USE this DbModel.
TODO: Make instant updates delete instantly when user hits "delete". */
public void setInstantUpdate(SqlRun runner, boolean instantUpdate)
{
	if (instantUpdate) {
		if (instantUpdateListener == null) {
			instantUpdateListener = new InstantUpdateListener(this, runner);
			this.getSchemaBuf().addTableModelListener(instantUpdateListener);
		}
	} else {
		if (instantUpdateListener != null) {
			this.getSchemaBuf().removeTableModelListener(instantUpdateListener);
			instantUpdateListener = null;
		}
	}
}
public boolean isInstantUpdate()
{
	return (instantUpdateListener != null);
}

// -----------------------------------------------------------
protected ConsSqlQuery doSimpleInsert(final int row, SqlRun str, SqlSchemaInfo qs)
{
	// Put together the insert for this row
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.INSERT);
	q.setMainTable(qs.table);
	sbuf.getInsertCols(row, q, false, qs);
	setInsertKeys(row, q);
	String sql = q.getSql();
		
	// Dispatch it
	str.execSql(sql, new UpdTasklet() {
	public void run() {
		sbuf.setStatus(row, 0);
	}});
	
	/** Figure out which sequence columns in qc.schema were not inserted, and find their keys */
	SqlSchema schema = qs.schema;
	
	TreeMap<String,ConsSqlQuery.NVPair> inserted = new TreeMap();
	for (ConsSqlQuery.NVPair nv : q.getColumns()) inserted.put(nv.name, nv);
	
	if (updateBufOnUpdate) {
		for (int i=0; i<schema.size(); ++i) {
			SqlCol col = (SqlCol)schema.getCol(i);
			if ((col.jType instanceof SqlSequence) && inserted.get(col.name)==null) {
				// Update this in the SchemaBuf if it wasn't inserted...
				final SqlSequence seq = (SqlSequence)col.jType;
//				int val = seq.getCurVal(st);
				final int qcol = qs.schemaMap[i];
				final IntVal ival = seq.getCurVal(str);
				str.execUpdate(new UpdTasklet2() {
				public void run(SqlRun str) throws Exception {
					sbuf.setValueAt(ival.val, row, qcol);
				}});
			}
		}
	}

	if (logger != null) logger.log(new QueryLogRec(q, qs, sbuf, row));
	return q;
}
// -----------------------------------------------------------
/**
 * 
 * @return True if an update was undertaken, false if nothing was done.
 */
protected boolean doSimpleUpdate(int row, SqlRun str, SqlSchemaInfo qs)
{
	SchemaBuf sb = (SchemaBuf)sbuf;
	SqlSchema schema = qs.schema;
	
	if (sbuf.valueChanged(row, qs.schemaMap)) {
		ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.UPDATE);
		sbuf.getUpdateCols(row, q, false, qs);
		q.setMainTable(qs.table);

		// Add the where clause, and error-check.
		int beforeWhere = q.numWhereClauses();
		sbuf.getWhereKey(row, q, qs);
		int afterWhere = q.numWhereClauses();
		System.out.println(q.getSql());
		if (beforeWhere == afterWhere) {
			throw new IllegalArgumentException("Update statement missing key fields in WHERE clause\n"
				+ q.getSql());
		}
	String sql = q.getSql();
//System.out.println("doSimpleUpdate: " + sql);
		str.execSql(sql);
			sbuf.setStatus(row, 0);
		if (logger != null) logger.log(new QueryLogRec(q, qs, sb, row));
		return true;
	} else {
		sbuf.setStatus(row, 0);
		return false;
	}
}
/** Get Sql query to delete current record. */
protected ConsSqlQuery doSimpleDeleteNoRemoveRow(int row, SqlRun str, SqlSchemaInfo qs)
{
	SchemaBuf sb = (SchemaBuf)sbuf;
	Schema schema = sb.getSchema();

	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.DELETE);
	q.setMainTable(qs.table);
	sbuf.getWhereKey(row, q, qs);
	if (q.numWhereClauses() == 0) {
		throw new IllegalArgumentException("Delete statement missing WHERE clause\n" +
			q.getSql());
	}
	String sql = q.getSql();
	str.execSql(sql);
	if (logger != null) logger.log(new QueryLogRec(q, qs, sb, row));
//	sbuf.removeRow(row);
	return q;
}

// -----------------------------------------------------------
public void setInsertBlankRow(boolean b) { insertBlankRow = b; }
/** If a row is inserted to the buffer but not edited, should it be inserted to the DB? */
public boolean getInsertBlankRow() { return insertBlankRow; }

// -----------------------------------------------------------
public String getSelectSql()
{
	ConsSqlQuery q = new ConsSqlQuery(ConsSqlQuery.SELECT);
	sbuf.getSelectCols(q, selectTable);
	q.addTable(selectTable);
	setSelectWhere(q);
	return q.getSql();
}
/** Get Sql query to re-select current records
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(SqlRun str)
{
	sbuf.setRows(str, getSelectSql());
}
// -----------------------------------------------------------
/** Get Sql query to insert record into database,
* assuming it isn't already there. */
public void doInsert(SqlRun str)
{
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		for (SqlSchemaInfo qs : updateSchemas) doSimpleInsert(row, str, qs);
	}
}
// -----------------------------------------------------------
/** Have any of the values here changed and not stored in the DB? */
public boolean valueChanged()
{
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		if (sbuf.getStatus(row) != 0) return true;
//		if (sbuf.valueChanged(row)) return true;
	}
	return false;
}
// -----------------------------------------------------------
/** Get Sql query to flush updates to database.
* Only updates records that have changed.
 @returns status of what was done to the row (DELETED, INSERTED, CHANGED, 0) */
public int doUpdate(SqlRun str, int row, SqlSchemaInfo qs)
{
	boolean deleted = false;
//System.out.println("doUpdate.status(" + row + ") = " + sbuf.getStatus(row));
	int status = sbuf.getStatus(row); // & ~CHANGED;
	switch(status) {
		// case DELETED || INSERTED :
			// Do nothing; we inserted then deleted record.
		// break;
		case DELETED :
		case DELETED | CHANGED :
			doSimpleDeleteNoRemoveRow(row, str, qs);
			return DELETED;
		case INSERTED :
			if (insertBlankRow) {
				doSimpleInsert(row, str, qs);
				return INSERTED;
			} else {
				sbuf.removeRow(row);
				return 0;
			}
		case INSERTED | CHANGED :
			doSimpleInsert(row, str, qs);
			return INSERTED;
		case 0 :
		case CHANGED :	// No status bits, just a normal record
			boolean q = doSimpleUpdate(row, str, qs);
			return (q ? CHANGED : 0);
	}
	return 0;
}
// -----------------------------------------------------------
void fireTablesWillChange(SqlRun str)
{
	if (updateSchemas != null) for (SqlSchemaInfo qs : updateSchemas) if (dbChange != null) {
		dbChange.fireTableWillChange(str, qs.table);
	}
}
/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
public void doUpdate(SqlRun str)
{
	boolean changed = false;
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		int status = doUpdateNoFireTableWillChange(str, row);
		if (status != 0) changed = true;
		if ((status & DELETED) != 0) --row;
	}
	if (changed) fireTablesWillChange(str);
}
/** Returns status of what was done */
int doUpdateNoFireTableWillChange(SqlRun str, int row)
{
	int status = 0;
	if (updateSchemas == null) {
		status = status | doUpdate(str, row, null);
	} else {
		for (SqlSchemaInfo qs : updateSchemas) {
			status = status | doUpdate(str, row, qs);
		}
	}
	if ((status & DELETED) != 0) sbuf.removeRow(row);
	return status;
}
// -----------------------------------------------------------
/** Get Sql query to delete current record. */
public void doDelete(SqlRun str)
{
	for (int row = 0; row < sbuf.getRowCount(); ++row) {
		for (SqlSchemaInfo qs : updateSchemas) {
			// Only delete if this is a real record in the DB.
			if ((sbuf.getStatus(row) & INSERTED) == 0) {
				doSimpleDeleteNoRemoveRow(row, str, qs);
			}
			sbuf.removeRow(row);
			-- row;
		}
	}
	fireTablesWillChange(str);
}
public void doClear()
	{ sbuf.clear(); }
// -----------------------------------------------------------
// ==============================================
private static class InstantUpdateListener implements TableModelListener {
//	SqlRunner str;
//	TaskRunner runner;
	SqlRun runner;
	SchemaBufDbModel dbModel;
	public InstantUpdateListener(SchemaBufDbModel dbModel, SqlRun runner)
	{
		this.runner = runner;
		this.dbModel = dbModel;
	}
	public void tableChanged(final TableModelEvent e) {
System.out.println("InstantUpdateListener.tableChanged()");
//		runner.run(new BatchRunnable() {
//		public void run(SqlRunner str) throws SQLException {
			switch(e.getType()) {
				// TODO: Update only rows that have changed, don't waste
				// your time on all the other rows!
				case TableModelEvent.UPDATE :
					for (int r=e.getFirstRow(); r <= e.getLastRow(); ++r) {
System.out.println("InstantUpdateListener.doUpdate row = " + r);
						dbModel.doUpdateNoFireTableWillChange(runner, r);
						dbModel.fireTablesWillChange(runner);
					}
				break;
			}
//		}});
 	}
}
// =============================================================
// -----------------------------------------------------------

}
