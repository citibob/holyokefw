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
 * DefaultJTypeTableModel.java
 *
 * Created on October 18, 2007, 9:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.*;
import citibob.types.JType;

/**
 *
 * @author citibob
 */
public class DefaultJTypeTableModel
extends javax.swing.table.DefaultTableModel
implements JTypeTableModel
{

protected citibob.types.JType[] jTypes;		// JType of each column

public JTypeTableModel getModelU() { return this; }
public int getColU(int col) { return col; }
/** Given a column in the underlying table, returns the corresponding
 * column in the visible table. */
public int colU2col(int colU) { return colU; }
public int findColumnU(String colU) { return findColumn(colU); }

// -----------------------------------------------------------
public DefaultJTypeTableModel(Object[][] data, Object[] columnNames, JType[] jTypes)
{
	super(data, columnNames);
	this.jTypes = jTypes;
}
public DefaultJTypeTableModel(Object[] columnNames, JType[] jTypes, int rowCount)
{
	super(columnNames, rowCount);
	this.jTypes = jTypes;
}
public JType getJType(int row, int col)
{
	return jTypes[col];
}
/** Overrides built-in JTable method. */
public Class getColumnClass(int col)
{
	return getJType(0, col).getObjClass();
}
public boolean isVisible(int col)
{ return true; }
// -----------------------------------------------------------
// ================= Basic convenience functions implemented
public DefaultJTypeTableModel() { super(); }
/** Use this if you're going to subclass getJType(). */
public DefaultJTypeTableModel(String[] colNames, int nrow)
	{ super(colNames, nrow); }
public Object getValueAt(int row, String col)
	{ return getValueAt(row, findColumn(col)); }
public void setValueAt(Object val, int row, String col)
	{ setValueAt(val, row, findColumn(col)); }
public JType getJType(int row, String col)
	{ return getJType(row, findColumn(col)); }

// -----------------------------------------------------------
///** Default implementation; can override. */
//public Object getSortValueAt(int row, int col)
//	{ return getValueAt(row, col); }


}
