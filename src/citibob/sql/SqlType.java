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
 * SqlType.java
 *
 * Created on April 1, 2006, 12:39 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql;

import java.sql.*;

/**
 *
 * @author citibob
 */
public interface SqlType extends citibob.types.JType
{
	/** Convert an element of this type to an Sql string for use in a query */
	String toSql(Object o);

	/** Returns the SQL string that encodes this data type. */
	String sqlType();

	/** Read element of this type out of the result set (& convert appropriately to Java type). */
	public Object get(java.sql.ResultSet rs, int col) throws SQLException;
	public Object get(java.sql.ResultSet rs, String col) throws SQLException;

}
