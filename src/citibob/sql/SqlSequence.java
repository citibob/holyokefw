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
 * SqlSequence.java
 *
 * Created on September 24, 2006, 11:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.sql;


import citibob.util.IntVal;
import java.sql.*;

/**
 * Abstracted idea of an integer sequence in a SQL database.
 * @author citibob
 */
public interface SqlSequence {

	/** Return current value of the sequence (after an INSERT has been called that incremented it.) */
	public IntVal getCurVal(SqlRun str);
	
	/** Increment the sequence and return its value. */
	public IntVal getNextVal(SqlRun str);
	
//	/** Retrieves a value previously stored in an SqlRunner */
//	public int retrieve(SqlRun str);
	
}
