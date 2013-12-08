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
/*
 * SqlTableModel.java
 *
 * Created on February 13, 2007, 11:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import citibob.swing.table.*;
import java.sql.*;
import citibob.swing.typed.*;

/**
 * An RSTableModel that carries around its own SQL query, so it can always execute its own query.
 * @author citibob
 */
public class SqlTableModel extends RSTableModel
{
protected String sql;

public void setSql(String sql) {this.sql = sql; }
public String getSql() { return sql; }

public SqlTableModel()
{}

public SqlTableModel(SqlTypeSet tset)
{
	super(tset);
}

public SqlTableModel(SqlTypeSet tset, String sql)
{
	super(tset);
	this.sql = sql;
}

public void executeQuery(SqlRun str)
{
	executeQuery(str, getSql());
}

}
