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
 * MainSqlTableModel.java
 *
 * Created on February 14, 2007, 12:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.*;
import citibob.swing.typed.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class MainSqlTableModel extends SqlTableModel
{

//String joinCol;
//HashMap<Object,List<Integer>> joinMap;	// Map from key value to the rows it's found in

/** @param joinCol Name of column (in TableModel) used to join on. */
//public MainSqlTableModel(SqlTypeSet tset, String joinCol, String sql)
public MainSqlTableModel(SqlTypeSet tset, String sql)
{
	super(tset, sql);
//	this.joinCol = joinCol;
//	joinMap = new HashMap();
}

//public Map<Object,List<Integer>> getJoinMap() { return joinMap; }

public Map<Object,List<Integer>> makeJoinMap(String joinCol)
{
	HashMap<Object,List<Integer>> joinMap = new HashMap();
	// Re-set the joinMap
	int iJoinCol = findColumn(joinCol);
	joinMap.clear();
	for (int i=0; i<this.getRowCount(); ++i) {
		Object val = getValueAt(i, iJoinCol);
		List<Integer> l = joinMap.get(val);
		if (l == null) {
			l = new LinkedList();
			l.add(i);
			joinMap.put(val, l);
		} else {
			l.add(i);
		}
	}
	return joinMap;
}

public void executeQuery(SqlRun str, String sql)
{
System.out.println("MainSqlTableModel.executeQuery: " + sql);
	super.executeQuery(str, sql);
}

}
