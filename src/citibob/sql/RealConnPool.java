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

import java.sql.*;
import java.util.*;

public class RealConnPool extends SimpleConnPool
{

static class DbbDate
{
	Connection dbb;
	long lastUsedMS;
	boolean closed;		// Set to true if this is a closed (stale) connection
	
	public DbbDate(Connection dbb) {
		this.dbb = dbb;
		this.lastUsedMS = System.currentTimeMillis();
	}
}
	
LinkedList<DbbDate> reserves = new LinkedList();	// Our reserve connections, not being used for now

public RealConnPool(ConnFactory connFactory)
{ super(connFactory); }

//public RealConnPool(String url, Properties props)
//{ super(url, props); }

//ExceptionHandler ehandler;
//
//public SimpleConnectionPool(ExceptionHandler eh)
//{
//	this.ehandler = eh;
//}

/** Get a connection from the pool. */
public synchronized Connection checkout() throws SQLException
{
//return create();
	long ms = System.currentTimeMillis();
System.out.println("checkout: reserves.size = " + reserves.size() + " " + reserves);
	while (reserves.size() > 0) {
		DbbDate dd = reserves.removeFirst();
		if (dd.closed) continue;
		if (ms - dd.lastUsedMS < 60 * 1000L) {
			// Good connection, less than 1 minute stale
//System.out.println("Re-using good connection: " + dd.dbb);
			return dd.dbb;
		}
		// Throw out connections > 1 minute stale
//System.out.println("Throwing out connection: " + dd.dbb);
		try {
			connFactory.close(dd.dbb);
		} catch(SQLException e) {
		} catch(NullPointerException ne) {
			// This should never happen, because null is being checked on checkin()
			System.err.println("ConnPool has a null reserve connection!");
			ne.printStackTrace();
		}
		
	}
	
	// No reserves left; create a new connection
System.out.println("RealConnPool: Creating new database connection");
	return connFactory.create();
//TODO: Keep track of lastused date --- throw out connections after 10 minutes
//Also.... in Exception handler, an SQLException should cause that connection to be closed and NOT returned.
}

/** Return a connection */
public synchronized void checkin(Connection dbb) throws SQLException
{
	if (dbb == null) {
		throw new IllegalArgumentException("Cannot checkin a null reference!");
	}
//	c.close();
	if (reserves.size() >= 5) connFactory.close(dbb);
	else reserves.addLast(new DbbDate(dbb));
System.out.println("checkin: reserves.size = " + reserves.size() + " " + reserves);
}

//public synchronized void close(Connection dbb) throws SQLException
//{
//	if (dbb == null) {
//		throw new IllegalArgumentException("Cannot checkin a null reference!");
//	}
//	connFactory.close(dbb);
//}

public synchronized void closeAll() throws SQLException
{
	for (DbbDate dbbd : reserves) {
		dbbd.closed = true;
		connFactory.close(dbbd.dbb);
	}
//	reserves.clear();
System.out.println("Cleared: reserves.size = " + reserves.size() + " " + reserves);
}

public void close(Connection dbb)
throws SQLException
{
	connFactory.close(dbb);
}

public void dispose() {}

//public void doRun(StRunnable r)
//	{ DbRawRunner.run(r, this); }
//public void doRun(DbRunnable r)
//	{ DbRawRunner.run(r, this); }

}
