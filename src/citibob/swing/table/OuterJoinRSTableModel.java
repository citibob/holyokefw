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
///*
// * OuterJoinRSTableModel.java
// *
// * Created on February 12, 2007, 8:50 PM
// *
// * To change this template, choose Tools | Template Manager
// * and open the template in the editor.
// */
//
//package citibob.swing.table;
//
//import java.sql.*;
//import javax.swing.table.*;
//import javax.swing.event.*;
//import citibob.swing.typed.*;
//import java.util.*;
//import citibob.sql.*;
//
///**
// *
// * @author citibob
// */
//public class OuterJoinRSTableModel extends RSTableModel
//{
//
////public OuterJoinRSTableModel()
////{
////	super();
////}
//	
//JTypeTableModel main;	// The main table
//Map<Object,Integer> joinMap;
//String keyCol;
//
///** @param main main table we're doing outer join to
// @param main main table we're joining to
// @param joinMap indicates the row that each key value appears on
// @param keyCol name of column in this table to join to
// */
//public OuterJoinRSTableModel(JTypeTableModel main, Map<Object,Integer> joinMap, String keyCol,
//SqlTypeSet tset)
//{
//	super(tset);
//	this.main = main;
//	this.joinMap = joinMap;
//	this.keyCol = keyCol;
//}
//
//public void executeQuery(Statement st, String sql) throws SQLException
//{
//	setRowCount(main.getRowCount());
//	super.executeQuery(st, sql);
//}
//
//public void addAllRows(ResultSet rs)
//throws SQLException
//{
//	ResultSetMetaData meta = rs.getMetaData();
//	int ncol = meta.getColumnCount();
//	int iKeyCol = this.findColumn(keyCol);
//	while (rs.next()) {
//		int row = joinMap.get(rs.getObject(iKeyCol));
//		for (int i=0; i<ncol; ++i) {
//			this.setValueAt(rs.getObject(i), row, i);
//		}
//	}
//}
//}
