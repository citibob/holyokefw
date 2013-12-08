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

import citibob.task.BaseExpHandler;
import citibob.task.ExpHandler;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class BatchSqlRun implements SqlRun {

protected ConnPool pool;
protected ExpHandler expHandler;

static class Rec {
	SqlBatch batch;			// The batch we're constructing (but not yet running)
	int recursionDepth = 0;		// How many times we've entered a "batch" zone in external code
	public Rec() { this.batch = new SqlBatch(); }
}
Stack<Rec> stack;
Rec top() { return stack.peek(); }
// ========================================================================
// Setting up the batch

int nextID = 0;
public String getTableName(String base)
{
	return base + (nextID++);
}


/** Not really public */
public void pushFlush() {
	++top().recursionDepth;
}
/** Not really public */
public void popFlush() {
	if (0 >= --top().recursionDepth) {
		flush();
		top().recursionDepth = 0;
	}
}

/** Cancels pending transactions */
//public void popNoFlush()
//{
//	if (0 >= --top().recursionDepth) {
//		top().recursionDepth = 0;
//	}
//}
/** Not really public */
public int getRecursionDepth() { return top().recursionDepth; }

/** @param pool Connection to use to run batches here; can be null; */
public BatchSqlRun(ConnPool xpool, ExpHandler expHandler)
{
	this.pool = xpool;
	this.expHandler = expHandler;
	stack = new Stack();
	pushBatch();
}

public String clear() {
	String sql = top().batch.getAllSql();
	stack.clear();
	pushBatch();
	return sql;
}


public void pushBatch()
{
	stack.push(new Rec());
}
public void popBatch()
{
//	try {
//		runBatches();
//	} catch(Exception e) {
//		expHandler.consume(e);
//	}
	flush();
	stack.pop();
}




//public SqlRunner next() { return this; }

public void execSql(String sql)
	{ execSql(sql, null); }
public void execSql(SqlSet ssql)
	{ execSql(ssql, null); }

/** Adds SQL to the batch --- multiple ResultSets returned, and it can create
 additional SQL as needed. */
public void execSql(String sql, SqlTasklet rr)
	{ top().batch.execSql(sql, rr); }
public void execSql(SqlSet ssql, SqlTasklet rr)
	{ top().batch.execSql(ssql, rr); }

public void execUpdate(UpdTasklet r)
	{ top().batch.execSql("", r); }
public void execUpdate(UpdTasklet2 r)
	{ top().batch.execSql("", r); }

/** Executes all (potentially) buffered SQL up to now. */
public void flush() {
	try {
		runBatches();
	} catch(Exception e) {
		expHandler.consume(e);
	}
}
// ========================================================================
// ---------------------------------------

void runBatches() throws Exception
{
	Rec rec = top();
	if (rec.batch.size() == 0) return;
	Throwable ret = null;
	Statement st = null;
	Connection dbb = null;
	try {
		dbb = pool.checkout();
		st = dbb.createStatement();
		try {
			runBatches(st);
		} catch(Exception e) {
			// Try to detect if the SSL tunnel (or other connectivity)
			// If so, try to reset ourselves by closing all connections in the pool
			Throwable ee = expHandler.findCauseByClass(e, IOException.class);
			if (ee != null) {
				if (dbb != null) pool.checkin(dbb);
				pool.closeAll();
				dbb = null;
			}
			throw e;
		}
	} finally {
		nextID = 0;
		try {
			if (st != null) st.close();
		} catch(SQLException se) {}
		try {
			if (dbb != null) pool.checkin(dbb);
		} catch(SQLException se) {}
	}
}

/** Recursively executes this batch and all batches its execution creates. */
public void runBatches(Statement st) throws Exception
{
	Rec rec = top();
	int nbatch = 0;
	if (rec.batch.size() == 0) return;

	for (;;) {
		// Update to next batch...
		SqlBatch curBatch = rec.batch;
		rec.batch = new SqlBatch();

		curBatch.execOneBatch(st, this);
		++nbatch;
		if (rec.batch.size() == 0) break;
	}
	System.out.println("+++ Done running " + nbatch + " batches of SQL");
}

}
