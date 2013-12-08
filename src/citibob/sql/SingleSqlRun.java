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
 * For JDBC drivers that do not support multiple result sets per query.
 */

package citibob.sql;

import citibob.task.ExpHandler;
import java.sql.*;
import java.util.*;

/**
 * A way to run SQL queries --- either in batch, or one at a time.
 * @author citibob
 */
public class SingleSqlRun implements SqlRun
{

protected ConnPool pool;
protected ExpHandler expHandler;

		
public String getTableName(String base)
{
	return base;
}

public void execSql(String sql, SqlTasklet handler)
{	
	execSql(new SqlSet(sql), handler);
}
public void execSql(SqlSet ssql, SqlTasklet handler)
{	
	Throwable ret = null;
	Statement st = null;
	Connection dbb = null;
	ResultSet rs = null;
	try {
		if (ssql != null) {
			String sql = ssql.getPreSql() + ";\n" + ssql.getSql() + ";\n" + ssql.getPostSql();
System.out.println("SingleSqlRun: " + sql);
			dbb = pool.checkout();
			st = dbb.createStatement();
			if (handler == null) st.execute(sql);
			else rs = st.executeQuery(sql);
//			boolean hasRS = st.execute(sql);
//			if (hasRS) rs = st.getResultSet();
		}

		// Now run the handler
		if (handler != null) {
			if (handler instanceof RssTasklet) {
				throw new UnsupportedOperationException();
				// ((RssTasklet)handler).run(rss);
			} else if (handler instanceof RsTasklet) {		
				((RsTasklet)handler).run(rs);
			} else if (handler instanceof UpdTasklet) {
				((UpdTasklet)handler).run();
			}
			if (handler instanceof RssTasklet2) {
				throw new UnsupportedOperationException();
//				((RssTasklet2)handler).run(str, rss);
			} else if (handler instanceof RsTasklet2) {		
				((RsTasklet2)handler).run(this, rs);
			} else if (handler instanceof UpdTasklet2) {
				((UpdTasklet2)handler).run(this);
			}
		}
	} catch(Exception e) {
		expHandler.consume(e);
	} finally {
		try {
			if (st != null) st.close();
		} catch(SQLException se) {}
		try {
			if (dbb != null) pool.checkin(dbb);
		} catch(SQLException se) {}
	}
}


//ExpHandler expHandler;
public SingleSqlRun(ConnPool pool, ExpHandler expHandler) {
	this.pool = pool;
	this.expHandler = expHandler;
}
	
/** Adds Sql to next batch to run, without any processing code. */
public void execSql(String sql)
{
	execSql(sql, null);
}
/** Adds Sql to next batch to run, without any processing code. */
public void execSql(SqlSet ssql)
{
	execSql(ssql, null);
}

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet r)
{
	execSql((SqlSet)null, r);
}

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet2 r)
{
	execSql((SqlSet)null, r);
}

public void flush()
{
}
public String clear() { return ""; }
// TODO: Review all these methods below, see if they can be simplified.
public void pushFlush() {}
public void popFlush() {}
public int getRecursionDepth() { return 0; }
public void pushBatch() {}
public void popBatch() {} // throws Exception {}

}

