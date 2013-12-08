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
 * AdhocOJRSTableModel.java
 *
 * Created on February 13, 2007, 11:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import citibob.jschema.Column;
import citibob.jschema.ConstSchema;
import citibob.swing.table.*;
import java.sql.*;
import javax.swing.table.*;
import javax.swing.event.*;
import citibob.swing.typed.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public abstract class AdhocOJSqlTableModel extends OuterJoinSqlTableModel
{

public AdhocOJSqlTableModel(MainSqlTableModel main, String mainJoinCol, String joinCol,
Column[] tableCols, SqlTypeSet tset, String sql)
{
	super(main, mainJoinCol, joinCol, tset, sql);
	super.setSchema(new ConstSchema(tableCols));
//	setColHeaders(tableCols);
//	setColumnCount(tableCols.length);
//System.out.println("Column Count set to " + getColumnCount());
}

public void executeQuery(Statement st, String sql) throws SQLException
{
//	setNumRows(0);
//	setRowCount(0);
//	for (int i=0; i<main.getRowCount(); ++i) {
//		addRow(new Vector(getColumnCount()));
//	}
//	addNewRows(main.getRowCount() - getRowCount());
	clear();
	setRowCount(main.getRowCount());
	ResultSet rs = null;
	try {
		rs = st.executeQuery(sql);
		addAllRows(rs);
	} finally {
		try { rs.close(); } catch(Exception e) {}
	}
}

/** Debugging */
public Object getValueAt(int row, int col)
{
	Object o = super.getValueAt(row, col);
//System.out.println("AdhocOJSqlTableModel.getValueAt(" + row + ", " + col + ") = " + o + "(row/col count = " + getRowCount() + " / " + getColumnCount());
	return o;
}
public void setValueAt(Object o, int row, int col)
{
//System.out.println("AdhocOJSqlTableModel.setValueAt(" + row + ", " + col + ") = " + o);
	super.setValueAt(o, row, col);
//System.out.print("      "); getValueAt(row,col);
}
///** Once we've figured out which row in our table a particular ResultSet
//record goes in, integrate it into our data table.  This will often be overridden. */
//public void setRow(int row, ResultSet rs) throws SQLException

}
