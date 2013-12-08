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

import java.io.Writer;
import java.sql.*;
import java.util.*;

/**
 * A way to run SQL queries --- either in batch, or one at a time.
 * @author citibob
 */
public interface SqlRun {

/** Adds SQL to the next batch to run.
 Multiple ResultSets returned, and it can create
 additional SQL as needed.
 @param rr one of RssRunnable, RsRunnable, UpdRunnable */
public void execSql(String sql, SqlTasklet rr);

/** Adds Sql to next batch to run, without any processing code. */
public void execSql(String sql);

/** Adds SQL to the next batch to run.
 Multiple ResultSets returned, and it can create
 additional SQL as needed.
 @param rr one of RssRunnable, RsRunnable, UpdRunnable */
public void execSql(SqlSet ssql, SqlTasklet rr);

/** Adds Sql to next batch to run, without any processing code. */
public void execSql(SqlSet ssql);

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet r);

/** Adds processing code to run without any SQL. */
public void execUpdate(UpdTasklet2 r);

/** Executes all (potentially) buffered SQL up to now.  */
public void flush(); // throws Exception;

/** Clears all (potentiallY) buffered SQL; returns result of buffer (sort of) */
public String clear();

/** Increases the "recursion depth." */
public void pushFlush();

/** Reduces the "recursion depth" by one, and flushes if it's gotten to zero. */
public void popFlush();

/** Same as popFlush(), but throws away any pending SQL */
//public void popNoFlush();

///** Conditional flush --- only flush if recursion depth is 0. */

public void pushBatch();
public void popBatch(); // throws Exception;

/** Gets a temporary table name that will be unique in this batch */
public String getTableName(String base);


///** @deprecated
// Gets the SqlRunner for the next batch --- used inside SqlRunnable
// to run things in sequence. */
//public SqlRunner next();

///** While SqlRunnables are running --- store a value for retrieval by later SqlRunnable. */
//public void put(Object key, Object val);
//
///** While SqlRunnables are running --- retrieve a previously stored value. */
//public Object get(Object key);

// =================================================================
// Something that keeps track of the "current available" SqlRunner

}

