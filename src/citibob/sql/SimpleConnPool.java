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

import citibob.task.DbRawRun;
import citibob.task.DbTask;
import java.sql.*;
import java.util.Properties;

public abstract class SimpleConnPool implements ConnPool
{

protected ConnFactory connFactory;

//String url;
//Properties props;
//
public SimpleConnPool(ConnFactory connFactory)
{
	this.connFactory = connFactory;
}
//
//public SimpleConnPool(String url, Properties props)
//{
//	this.url = url;
//	this.props = props;
//}

//ExceptionHandler ehandler;
//
//public SimpleConnectionPool(ExceptionHandler eh)
//{
//	this.ehandler = eh;
//}


/** Get a connection from the pool. */
public Connection checkout() throws SQLException
{
	//return new WrapperConn(create(), this);	// Caused inifinte recursion on checkin
	return connFactory.create();
}

/** Return a connection */
public void checkin(Connection c) throws SQLException
{
	connFactory.close(c);
}
public void dispose() {}

public Exception exec(DbTask r)
	{ return DbRawRun.run(this, r); }

//public void doRun(StRunnable r)
//	{ DbRawRunner.run(r, this); }
//public void doRun(DbRunnable r)
//	{ DbRawRunner.run(r, this); }

}
