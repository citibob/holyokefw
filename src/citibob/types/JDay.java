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
package citibob.types;

import java.text.*;
import java.util.*;
import java.sql.*;
import citibob.sql.pgsql.*;
import citibob.util.Day;


public class JDay implements JDateType
{

protected boolean nullable = true;

	protected JDay(boolean nullable)
	{
		this.nullable = nullable;
	}
	public JDay() { this(true); }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Day.class; }
	
	public boolean isInstance(Object o)
		{ return (o instanceof Day || (nullable && o == null)); }
		
	// ===========================================================
	// JDateType
	/** Time Zone the data are stored in. */
	public TimeZone getTimeZone() {
		return null;
	}

	/** Returns a truncated version of the input; i.e. if this is a SqlDate,
	 *then truncates off hour, minute, second. */
	public java.util.Date truncate(java.util.Date dt) { return dt; }


}

	

