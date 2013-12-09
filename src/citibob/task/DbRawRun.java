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
package citibob.task;

import java.sql.*;
import citibob.sql.*;
import citibob.app.*;

/**
 * Just run the CBRunnables in the current thread.  Also used as a basis
 * for running the polymorphic runnable types.
 * @author citibob
 */
public class DbRawRun implements RawRun
{

App app;
//ConnPool pool;
//SqlBatchSet batchSet;
//int recursionDepth;

public ConnPool getPool() { return app.pool(); }
public DbRawRun(App app)
{
	this.app = app;
//	this.pool = app.pool();
}

///** Will not work for SqlTask */
//public DbRawRun(ConnPool pool)
//{
//	this.app = null;
//	this.pool = pool;
//}

public static Exception run(ETask r)
{
	try {
		r.run();		
	} catch(Exception e) {
		return e;
//		eh.consume(e);
	}
	return null;
}

public static Exception run( ConnPool pool,StTask r)
{
	Exception ret = null;
	Statement st = null;
	Connection dbb = null;
	try {
		dbb = pool.checkout();
		st = dbb.createStatement();
		r.run(st);
	} catch(Exception e) {
		ret = e;
//		eh.consume(e);
	} finally {
		try {
			if (st != null) st.close();
		} catch(SQLException se) {}
		try {
			pool.checkin(dbb);
		} catch(SQLException se) {}
	}
	return ret;
}
	
//public static Exception run(BatchRunnable r, ConnPool pool)
//{
//	SqlBatchSet batch = new SqlBatchSet();
//	try {
//		r.run(batch);
//		batch.runBatches(pool);
//	} catch(Exception e) {
//		return e;
//	}
//	return null;
//}
public Exception run(SqlTask r)
{
//	SqlBatchSet batch = new SqlBatchSet();
	SqlRun batchSet = app.sqlRun();
	batchSet.pushFlush();
	try {
		r.run(batchSet);
//		if (batchSet.getRecursionDepth() == 1) batchSet.flush();
	} catch(Exception e) {
		return e;
	} finally {
		batchSet.popFlush();
	}
	return null;
}

//public static Exception run(BatchRunnable r, Con.nPool pool)
//{
//	Exception ret = null;
//	Statement st = null;
//	Connection dbb = null;
//	try {
//		SqlBatch batch = new SqlBatch();
//		r.run(batch);
//		dbb = pool.checkout();
//		st = dbb.createStatement();
//		batch.exec(st);
//	} catch(Exception e) {
//		ret = e;
////		eh.consume(e);
//	} finally {
//		try {
//			if (st != null) st.close();
//		} catch(SQLException se) {}
//		try {
//			pool.checkin(dbb);
//		} catch(SQLException se) {}
//	}
//	return ret;
//}

public static Exception run(ConnPool pool, DbTask r)
{
	Connection dbb = null;
	Exception ret = null;
	try {
		dbb = pool.checkout();
		r.run(dbb);
	} catch(Exception e) {
		ret = e;
	} finally {
		try {
			if (pool != null) pool.checkin(dbb);
		} catch(SQLException se) {}
	}
	return ret;
}

public Exception doRun(CBTask rr)
{
	Exception ret;
//	SqlRun batchSet = app.sqlRun();
	if (rr instanceof SqlTask) {
		SqlTask r = (SqlTask)rr;
		ret = run(r);
	} else if (rr instanceof ETask) {
		ETask r = (ETask)rr;
		ret = run(r);
	} else if (rr instanceof StTask) {
		StTask r = (StTask)rr;
		ret = run(app.pool(),r);
	} else if (rr instanceof DbTask) {
		DbTask r = (DbTask)rr;
		ret = run(app.pool(),r);
	} else {
		ret = new ClassCastException("CBRunnable of class " + rr.getClass() + " is not one of ERunnable, StRunnable or DbRunnable");
	}
	return ret;
}

}
