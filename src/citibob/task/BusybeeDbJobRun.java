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
import citibob.swing.*;
import citibob.sql.*;
import java.awt.*;

/**
 * Just run the CBRunnables in the current thread.  Route exceptions to the ExpHandler.
 * @author citibob
 */
public class BusybeeDbJobRun extends SwingJobRun
{

DbRawRun raw;
ExpHandler eh;
int recursionDepth;

//public ConnPool getPool() { return raw.getPool(); }

public BusybeeDbJobRun(DbRawRun raw, ExpHandler eh)
{
	this.raw = raw;
	this.eh = eh;
}
public BusybeeDbJobRun(App app, ExpHandler eh)
{
	this(new DbRawRun(app), eh);
}
public BusybeeDbJobRun(App app)
{
	this(new DbRawRun(app), new SimpleExpHandler());
}

public void run(Component component, CBTask rr)
{
	++recursionDepth;
	if (recursionDepth == 1) SwingUtil.setCursor(component, Cursor.WAIT_CURSOR);
	Throwable e = raw.doRun(rr);
	SwingUtil.setCursor(component, Cursor.DEFAULT_CURSOR);
	if (e != null && eh != null) eh.consume(e);
	--recursionDepth;
}

}
