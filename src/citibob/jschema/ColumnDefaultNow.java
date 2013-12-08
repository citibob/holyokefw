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
 * SqlDateColumn.java
 *
 * Created on March 13, 2006, 11:23 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.jschema;

import citibob.sql.*;
import java.util.*;
import citibob.types.JDateType;

/**
 *
 * @author citibob
 */
public class ColumnDefaultNow extends citibob.jschema.SqlCol
{

// ===================================================================
/** @param dateType must be an SqlDate from some package... */
public ColumnDefaultNow(SqlDateType dateType, String name, boolean key)
{
	super(dateType, name, key);
}
// ===================================================================

/** Default value for column when inserting new row in buffers. 
 This method will be overridden. */
public Object getDefault()
{
	JDateType jtype = (JDateType)super.getType();
	java.util.Date dt = jtype.truncate(new Date());
//System.out.println("DefaultNow = " + dt + "(jtype = " + jtype.getClass());
	return dt;
}
	
}
