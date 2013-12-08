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
import citibob.sql.ConsSqlQuery;
import static citibob.jschema.RowStatusConst.*;

/** NOTE: Implementations of this interface do not NECESSARILY have to be Schema-based; however, it is expected that most will be. */
public interface SqlGen // extends RowStatusConst //, citibob.swing.table.JTypeTableModel
{
int getRowCount();

/** Has the given row changed value since it was loaded with data? */
boolean valueChanged(int row);

/** Was the given row inserted or deleted since being read from database? */
public int getStatus(int row);

/** After we've updated the DB, use this to clear status bits. */
public void setStatus(int row, int status);

// ==============================================================
// Interface as a Table or portion of a SQL query

public void setColPrefix(String colPrefix);

public String getDefaultTable();

// ===============================================================
// Read rows from the database

/** Adds a row, but doesn't fire any events; it's the caller's responsibility to
 fire events when all rows are added. */
public int addRowNoFire(ResultSet rs, int rowIndex) throws SQLException;

/** Fire event after addRowNoFire() calls.  Generally will be implemented by AbstractTableModel */
public void fireTableRowsInserted(int firstRow, int lastRow);


/** Add a new row from the current place in a result set */
void addRow(ResultSet rs) throws SQLException;

/** Add all the rows from a result set, starting with rs.next(), then close the result set. */
void addAllRows(ResultSet rs) throws SQLException;

/** Removes a row from the buffer... */
void removeRow(int row);

/** Undoes any edits... */
//public void resetRow(int row);

///** Convenience functions for single-row SchemaBufs */
//void setOneRow(ResultSet rs);

// ===============================================================
// Write rows to the database

/** Makes update query update column(s) represented by this object. */
void getUpdateCols(int row, ConsSqlQuery q, boolean updateUnchanged);
void getInsertCols(int row, ConsSqlQuery q, boolean insertUnchanged);

/** Adds the where clauses corresponding to the currently stored keyfield values. */
void getWhereKey(int row, ConsSqlQuery q, String table);
//void getInsertKey(int row, SqlQuery q);

/** If schema-based, just a passthrough to the underlying Schema. */
void getSelectCols(ConsSqlQuery q, String table);

}