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
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.swing.typed;

import citibob.sql.*;
import citibob.swing.typed.*;
import citibob.text.*;
import citibob.util.ObjectUtil;

public class JTypedLabelDB extends JTypedLabel
{

DBFormat dbformat;
SqlRun str;

// ---------------------------------------------------------------
// Must override stuff in TextTypedWidget
public void setJType(SqlRun str, DBFormat dbformat)
{
//	super.setJType(jt);
	this.str = str;
	this.dbformat = dbformat;
}

/** @Overrides */
public void setValue(Object xval)
{
	if (ObjectUtil.eq(val, xval)) return;		// This was called multiple times; ignore
//	if (val == xval && val != null) return;		// This was called multiple times; ignore
	Object oldVal = val;
	val = xval;
	dbformat.setDisplayValue(str, xval);		// Display will catch up later
	this.firePropertyChange("value", oldVal, val);	// But property changed NOW.
}

}

