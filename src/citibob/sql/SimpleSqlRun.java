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

import citibob.task.ExpHandler;
import java.sql.*;
import java.util.*;

/**
 * A way to run SQL queries --- either in batch, or one at a time.
 * @author citibob
 */
public class SimpleSqlRun extends BatchSqlRun
{

//ExpHandler expHandler;
public SimpleSqlRun(ConnPool pool, ExpHandler expHandler) {
	super(pool, expHandler);
//	this.expHandler = expHandler;
}
	
/** Adds SQL to the next batch to run.
 Multiple ResultSets returned, and it can create
 additional SQL as needed.
 @param rr one of RssRunnable, RsRunnable, UpdRunnable */
public void execSql(String sql, SqlTasklet rr)
{
	super.execSql(sql, rr);
	flush();
}

/** Adds Sql to next batch to run, without any processing code. */
public void execSql(String sql)
{
	super.execSql(sql);
	flush();
}

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet r)
{
	super.execUpdate(r);
	flush();
}

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet2 r)
{
	super.execUpdate(r);
	flush();
}

public void flush()
{
	if (top().batch.size() > 0) super.flush();
}
//public void myFlush()
//{
//	try {
//		super.flush();
//	} catch(Exception e) {
//		expHandler.consume(e);
//	}
//}


}

