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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

import citibob.sql.RsTasklet;
import citibob.sql.SqlRun;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author fiscrob
 */
public class IntVal {
	public int val;

	public void setVal(SqlRun str, String sql) {
		str.execSql(sql, new RsTasklet() {
		public void run(ResultSet rs)  throws SQLException {
			rs.next();
			val = rs.getInt(1);
		}});
	}
	
	public static IntVal getVal(SqlRun str, String sql) {
		IntVal val = new IntVal();
		val.setVal(str, sql);
		return val;
	}
}
