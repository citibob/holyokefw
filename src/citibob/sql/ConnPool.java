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

import citibob.task.DbTask;
import java.sql.*;

public interface ConnPool {

/** Get a connection from the pool.  This is thread-safe.  */
Connection checkout() throws SQLException;

/** Return a connection.  This is thread-safe. */
void checkin(Connection c) throws SQLException;

/** Returns a connection, destroying any resources it used */
void closeAll() throws SQLException;

/** Free up resources when you'return done with this conn pool. */
public void dispose();

//public void doRun(StRunnable r);
////	{ DefaultRawRunner.run(r, this); }
public Exception exec(DbTask r);

public void close(Connection dbb)
throws SQLException;

}
