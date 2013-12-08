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
 * SqlSerial.java
 *
 * Created on September 24, 2006, 10:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql.pgsql;

import java.sql.*;
import citibob.sql.*;
import citibob.util.IntVal;

/**
 *
 * @author citibob
 */
public class SqlSerial extends SqlInteger implements SqlSequence
{
	String seq;		// Name of the postgreSQL sequence for this serial column
	
	public SqlSerial(String seq, boolean nullable) {
		super(nullable);
		this.seq = seq;
	}
	public SqlSerial(String seq) { this(seq, false); }

	static String curValSql(String seq) { return "select currval(" + SqlString.sql(seq) + ")"; }
	static String nextValSql(String seq) { return "select nextval(" + SqlString.sql(seq) + ")"; }

	public int getCurVal(Statement st) throws SQLException
	{
		ResultSet rs = st.executeQuery(curValSql(seq));
		rs.next();
		int ret = rs.getInt(1);
		rs.close();
		return ret;
	}
	public int getNextVal(Statement st) throws SQLException
	{
		ResultSet rs = st.executeQuery(nextValSql(seq));
		rs.next();
		int ret = rs.getInt(1);
		rs.close();
		return ret;		
	}
	
	public IntVal getCurVal(SqlRun str)
		{ return getCurVal(str, seq); }
	public IntVal getNextVal(SqlRun str)
		{ return getNextVal(str, seq); }
	
	/** Return current value of the sequence (after an INSERT has been called that incremented it.)  Stores
	 value in Str under name seq. */
	public static IntVal getCurVal(SqlRun str, final String seq)
	{
		final IntVal ival = new IntVal();
		String sql = curValSql(seq);
		str.execSql(sql, new RsTasklet() {
		public void run(ResultSet rs) throws Exception {
			rs.next();
			ival.val = rs.getInt(1);
		}});
		return ival;
	}
	
	/** Return current value of the sequence (after an INSERT has been called that incremented it.) */
	public static IntVal getNextVal(SqlRun str, final String seq)
	{
		final IntVal ival = new IntVal();
		String sql = nextValSql(seq);
		str.execSql(sql, new RsTasklet() {
		public void run(ResultSet rs) throws Exception {
			rs.next();
			ival.val = rs.getInt(1);
		}});
		return ival;
	}
}
