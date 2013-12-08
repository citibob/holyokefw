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
 * SqlBatch.java
 *
 * Created on August 28, 2007, 9:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.*;
import java.util.*;

/**
 *
 * @author citibob
 */
class SqlBatch { //implements SqlRun {

// Used while building query
StringBuffer preSqlBuf = new StringBuffer();
StringBuffer sqlBuf = new StringBuffer();
StringBuffer postSqlBuf = new StringBuffer();
List<SqlTasklet> handlers = new ArrayList();

// Used while interpreting results
//boolean inExec = false;
//HashMap map;				// Map of values to pass from from one SqlRunnable to the next
//BatchSqlRun nextBatch;			// The batch we're constructing for the next set of stuff
int nextCurrent = Statement.CLOSE_ALL_RESULTS;
List<ResultSet> xrss = new ArrayList();

/** Creates a new instance of SqlBatch */
public SqlBatch() {
}

public int size() { return handlers.size(); }


// ========================================================================
// Setting up the batch

public void execSql(String sql)
	{ execSql(sql, null); }

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed. */
public void execSql(String sql, SqlTasklet rr)
{
	sqlBuf.append(sql);
	sqlBuf.append(";\n select 'hello' as __divider__\n;");
	handlers.add(rr);
}

public void execSql(SqlSet ssql)
	{ execSql(ssql, null); }

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed. */
public void execSql(SqlSet ssql, SqlTasklet rr)
{
	preSqlBuf.append(ssql.getPreSql() + ";\n");
	sqlBuf.append(ssql.getSql());
	sqlBuf.append(";\n select 'hello' as __divider__\n;");
	postSqlBuf.append(ssql.getPostSql() + ";\n");
	handlers.add(rr);
}


public void execUpdate(UpdTasklet2 r)
{
	execSql("", r);
}
public void execUpdate(UpdTasklet r)
{
	execSql("", r);
}

//// ========================================================================
//// Running the batch
//public BatchSqlRun next() { return nextBatch; }
///** While SqlRunnables are running --- store a value for retrieval by later SqlRunnable. */
//public void put(Object key, Object val)
//{ map.put(key, val); }
//
///** While SqlRunnables are running --- retrieve a previously stored value. */
//public Object get(Object key)
//{ return map.get(key); }

// ---------------------------------------
String getAllSql()
{ return preSqlBuf.toString() + sqlBuf.toString() + postSqlBuf.toString(); }

/** Execute the SQL batch; puts any new queries in "nextBatch" */
void execOneBatch(Statement st, SqlRun str) throws Exception
{
	String sql = getAllSql();
System.out.println(
"=================================================\n" +
	"Executing batch with " + size() + " segments: \n" + sql +
"=================================================");
System.out.flush();
	st.execute(sql);

	ResultSet rs;
	Iterator<SqlTasklet> curHandler = handlers.iterator();
	for (int n=0;; ++n,st.getMoreResults(nextCurrent)) {
		nextCurrent = Statement.KEEP_CURRENT_RESULT;
		rs = st.getResultSet();
		if (rs == null) {
			// Not an update or a select --- we're done with all result sets
			if (st.getUpdateCount() == -1) break;
			// It was an update --- go on to next result set
			continue;
		}

		// See if this is a divider
		ResultSetMetaData meta = rs.getMetaData();
		if (meta.getColumnCount() > 0 && "__divider__".equals(meta.getColumnLabel(1))) {
			rs.close();

			// It was --- process all buffered result sets
//			System.out.println("DIVIDER");
			ResultSet[] rss = new ResultSet[xrss.size()];
			xrss.toArray(rss);

			// Process this batch of ResultSets
			SqlTasklet handler = curHandler.next();
			if (handler != null) {
				if (handler instanceof RssTasklet) {
					((RssTasklet)handler).run(rss);
				} else if (handler instanceof RsTasklet) {		
					((RsTasklet)handler).run(rss[0]);
				} else if (handler instanceof UpdTasklet) {
					((UpdTasklet)handler).run();
				}
				if (handler instanceof RssTasklet2) {
					((RssTasklet2)handler).run(str, rss);
				} else if (handler instanceof RsTasklet2) {		
					((RsTasklet2)handler).run(str, rss[0]);
				} else if (handler instanceof UpdTasklet2) {
					((UpdTasklet2)handler).run(str);
				}
			}

			// Get ready for new batch
			xrss.clear();
			nextCurrent = Statement.CLOSE_ALL_RESULTS;
		} else {
			// Just a regular ResultSet --- buffer it
			xrss.add(rs);
		}

	}
//	return (nextBatch.size() == 0 ? null : nextBatch);
}
}

