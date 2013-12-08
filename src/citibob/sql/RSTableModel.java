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
package citibob.sql;

import citibob.jschema.SchemaBuf;
import citibob.sql.SqlTypeSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RSTableModel extends SchemaBuf
{
	SqlTypeSet tset;
	boolean autoSchema;		// Should we automatically make the schema from the ResultSet?
	
	/** Create schema from result set at query time */
	public RSTableModel(SqlTypeSet tset)
	{
		super();
		this.tset = tset;
		autoSchema = true;
	}
	/** Schema must be set manually before query.
	 @see SchemaBuf.setSchema() */
	public RSTableModel()
	{
		autoSchema = false;
	}
	
	/** All-in-one: execute a query, set up row headers, and add all rows to the table model. */
	public void executeQuery(SqlRun str, SqlSet ssql)
	{
		if (autoSchema) {
			super.setRowsAndCols(str, ssql, null, null, tset);
		} else {
			super.setRows(str, ssql);
		}
	}
	/** All-in-one: execute a query, set up row headers, and add all rows to the table model. */
	public void executeQuery(SqlRun str, String sql)
	{
		executeQuery(str, new SqlSet(sql));
	}
	public void executeQuery(ResultSet rs) throws SQLException
	{
		if (autoSchema) {
			super.setRowsAndCols(rs, null, null, tset);
		} else {
			super.clear();
			super.addAllRows(rs);
		}
	}
	/** Override to avoid asking for default value. */
	protected SqlRow2 newRow()
	{
		int n = getSchemaColumnCount();
		return new SqlRow2(n);
	}
}


///*
//JSchema: library for GUI-based database applications
//This file Copyright (c) 2006-2007 by Robert Fischer
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/
//package citibob.sql;
//
//import citibob.swing.table.*;
//import citibob.types.JType;
//import java.sql.*;
//import javax.swing.table.*;
//import javax.swing.event.*;
//import citibob.swing.typed.*;
//import java.util.*;
//
///** Reads in a record set and makes the data available as a (read-only) table model. */
//public class RSTableModel
//extends DefaultJTypeTableModel
////implements JTypeTableModel
//{
//
//	// Extra columns at end (so col #'s match between SqlSchema and Model): __status__ and __rowno__
////static final int C_STATUS = 0;
//static final int C_ROWNO = 0;
//static final int C_COUNT = 1;
//static final String[] xtraColNames = {"__rowno__"};
//
////List proto;		// Prototypes for CitibobTableModel
//
//// =====================================================
//// DefaultTableModel handles data storage
//public int getColumnCount()
//	{ return getSchemaColumnCount() + C_COUNT; }
//public int getSchemaColumnCount()
//	{ return super.getColumnCount(); }
//
//// ===============================================================
//// Implementation of SqlGen: Read rows from the database
//
///** To be used by subclasses */
//public void setColHeaders(Col[] cols)
//{
//	jTypes = new SqlType[cols.length];
//	Object[] ids = new Object[cols.length];
//	for (int i=0; i<cols.length; ++i) {
//		ids[i] = cols[i].name;
//		jTypes[i] = cols[i].type;
//	}
//	setColumnIdentifiers(ids);
//	setColumnCount(jTypes.length);
//}
//
//// --------------------------------------------------
//public void setColHeaders(java.sql.ResultSet rs) throws SQLException
//{
//	ResultSetMetaData md = rs.getMetaData();
//	int ncol = md.getColumnCount();
//	Object[] ids = new Object[ncol];
//	jTypes = new SqlType[md.getColumnCount()];
//	for (int i=0; i<ncol; ++i) {
//		jTypes[i] = tset.getSqlType(md, i+1);
//System.out.println("jTypes[" + i + "] = " + jTypes[i]);
////if (jTypes[i] == null) {
////	System.out.println("hoi");
////}
//		ids[i] = md.getColumnLabel(i+1);
//	}
//	setColumnIdentifiers(ids);
//	setColumnCount(md.getColumnCount());
//}
/////** Used to make reports involving client-side joins --- some of the columns will
//// come from a ResultSet (or even a table), and some from ad-hoc computation.
//// @params types Describes types of the columns.  Each element can be of type
//// ResultSet or JType[] */
////public void setColTypes(Object[] types, SqlTypeSet tset) throws SQLException
////{
////	// Count total columns
////	int ncol = 0;
////	for (Object o : types) {
////		if (o instanceof ResultSet) {
////			ResultSetMetaData md = ((ResultSet)o).getMetaData();
////			ncol += md.getColumnCount();
////		} else {
////			// Will throw ClassCastException if arg of wrong type.
////			ncol += ((JType[])o).length;
////		}
////	}
////	
////	// Set it up
////	jTypes = new JType[ncol];
////	int j=0;
////	for (Object o : types) {
////		if (o instanceof ResultSet) {
////			ResultSetMetaData md = ((ResultSet)o).getMetaData();
////			for (int i=0; i<md.getColumnCount(); ++i) {
////				jTypes[j++] = tset.getSqlType(md, i+1);
////			}
////		} else {
////			for (JType t : (JType[])o) jTypes[j++] = t;
////		}
////	}
////	setColumnCount(ncol);
////}
//// --------------------------------------------------
///** Appends a row in the data */
//public void addRow(ResultSet rs) throws java.sql.SQLException
//{
//	ResultSetMetaData meta = rs.getMetaData();
//	int ncol = meta.getColumnCount();
//	Object[] data = new Object[ncol];
//	for (int i = 0; i < ncol; ++i) {
////if (i == )
//		data[i] = ((SqlType)jTypes[i]).get(rs, i+1);
////			rs.getObject(i+1);
////		data[i] = rs.getObject(i+1);
//	}
//	addRow(data);
//}
//// --------------------------------------------------
/////** Add data from a result set; and set up the columns too!
//// @deprecated */
////public void addAllRows(ResultSet rs) throws java.sql.SQLException
////{
////	// Set number of columns
////	ResultSetMetaData meta = rs.getMetaData();
////	int ncol = meta.getColumnCount();
////	setColumnCount(ncol);
////
////	// Set column headers
////	Object[] ids = new Object[ncol];
////	for (int i = 0; i < ncol; ++i) {
////		ids[i] = meta.getColumnLabel(i+1);
////System.out.println("addAllRows: ids = " + ids[i]);
////	}
////	setColumnIdentifiers(ids);
////	
////	// Set data
////	while (rs.next()) addRow(rs);
////}
////public void addAllRows(ResultSet rs) throws java.sql.SQLException
////{ addAllRows(rs, 0); 
//// ===============================================================
//SqlTypeSet tset;
//public RSTableModel(SqlTypeSet tset)
//{
//	super();
//	this.tset = tset;
//}
///** Use this constructor if you'll be setting your own column types. */
//public RSTableModel()
//{
//	super();
//	tset = null;
//}
//// ===============================================================
//
///** All-in-one: execute a query, set up row headers, and add all rows to the table model. */
//public void executeQuery(SqlRunner str, String sql)
//{
//	setNumRows(0);
//	str.execSql(sql, new RsRunnable() {
//	public void run(SqlRunner str, ResultSet rs) throws SQLException {
//		if (tset != null) setColHeaders(rs);
//		addAllRows(rs);
//	}});
//}
//public void addAllRows(ResultSet rs) throws SQLException
//{
//	while (rs.next()) addRow(rs);
//}
//// ===============================================================
//// Implementation of TableModel
//
//// --------------------------------------------------
//// --------------------------------------------------------
//public int findColumn(String colName)
//{
////	for (int i=0; i<xtraColNames.length; ++i)
////		if (xtraColNames[i].equals(colName)) return getSchemaColumnCount()+i;
//
//	for (int i = 0; i < getColumnCount(); ++i) {
//		if (colName.equals(getColumnName(i))) return i;
//	}
//	return -1;
//}
//// --------------------------------------------------
///** Allow editing of all non-key fields. */
//public boolean isCellEditable(int rowIndex, int columnIndex)
//	{ return false; }
//// --------------------------------------------------
///** This is a NOP: Values are immutable once inserted. Noo... report generator
// might wish to change values at times...*/
//public void setValueAt(Object val, int rowIndex, int colIndex)
//{
//	super.setValueAt(val, rowIndex, colIndex);
//}
//// --------------------------------------------------
//
//// ===============================================================
///** Return SqlType for a cell.  Only needed if this is a model for JTypeTable or subclass */
//public JType getJType(int row, int col)
//	{ return jTypes[col]; }
//
/////** Return SqlType for an entire column --- or null, if this column does not have a single SqlType. */
////public JType getColumnJType(int col)
////	{ return jTypes[col]; }
////
/////** Return SqlType for a cell */
////public JType getJType(int row, int col)
////	{ return jTypes[col]; }
//
//
////// ===============================================================
////// Implementation of CitibobTableModel (prototype stuff)
////public List getPrototypes()
////	{ return proto; }
////public void setPrototypes(List proto)
////	{ this.proto = proto; }
////public void setPrototypes(Object[] pr)
////{
////	proto = new ArrayList(pr.length);
////	for (int i = 0; i < pr.length; ++i) {
////		proto.add(pr[i]);
////	}
////}
//
//// ==========================================================
///** This is a lot like citibob.jschema.Column */
//public static class Col
//{
//	public String name;
//	public SqlType type;
//	public Col(String name, SqlType type) {
//		this.name = name;
//		this.type = type;
//	}
//}
//
//}
