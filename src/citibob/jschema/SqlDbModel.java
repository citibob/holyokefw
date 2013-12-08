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
 * SqlDbModel.java
 *
 * Created on February 12, 2007, 10:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;


import java.sql.*;
import citibob.sql.*;

/**
 *
 * @author citibob
 */
public class SqlDbModel extends ReadonlyDbModel implements TableDbModel
{
SqlTableModel model;

/** Creates a new instance of SqlDbModel */
public SqlDbModel(SqlTableModel model)
{
	this.model = model;
}


public SqlTableModel getTableModel() { return model; }
// ========================================================
// DbModel
/** Initialize component to receive data.  Might be needed if some kind of database lookup is needed. */
public void doInit(SqlRun str) {}

/** Get Sql query to re-select current record
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(SqlRun str)
{
//	ResultSet rs = st.executeQuery(sql);
	model.executeQuery(str);
}

/** Clear all buffered data from this component.  Then there
is no current record. */
public void doClear()
{
	model.clear();
//	model.setRowCount(0);
}
/** This is read-only, so key doesn't matter. */
public void setKey(Object[] key) {}

}
