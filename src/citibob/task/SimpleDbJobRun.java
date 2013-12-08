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

import citibob.app.App;
import citibob.sql.*;

/**
 * Just run the CBRunnables in the current thread.  Route exceptions to the ExpHandler.
 * @author citibob
 */
public class SimpleDbJobRun extends JobRun
{

DbRawRun raw;
ExpHandler eh;

public ConnPool getPool() { return raw.getPool(); }

public SimpleDbJobRun(DbRawRun raw, ExpHandler eh)
{
	this.raw = raw;
	this.eh = eh;
}
public SimpleDbJobRun(App app, ExpHandler eh)
{
	this(new DbRawRun(app), eh);
}
public SimpleDbJobRun(App app)
{
	this(new DbRawRun(app), new SimpleExpHandler());
}

public void run(CBTask rr)
{
	Throwable e = raw.doRun(rr);
	if (e != null && eh != null) eh.consume(e);
}

}
