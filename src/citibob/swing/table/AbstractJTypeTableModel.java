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
public abstract class AbstractJTypeTableModel<TT>
extends javax.swing.table.AbstractTableModel
implements JTypeTableModel<TT>
{


public Object getValueAt(int row, String col)
	{ return getValueAt(row, findColumn(col)); }
public void setValueAt(Object val, int row, String col)
	{ setValueAt(val, row, findColumn(col)); }
public JType getJType(int row, String col)
	{ return getJType(row, findColumn(col)); }
/** By default, do not display columns starting with "__" in name. */
public boolean isVisible(int col)
	{ return !getColumnName(col).startsWith("__"); }


public JTypeTableModel getModelU() { return this; }

/** If this wraps another TableModel:
Finds a column by name in the UNDERLYING table model, then
reports its location in THIS table model.  Otherwise, same
as findColumn(). */
public int findColumnU(String colU)
	{ return findColumn(colU); }
/** Given a column in THIS table, reports the column in the UNDERLYING table.
 * Returns -1 if none.
 */
public int getColU(int col) { return col; }
/** Given a column in the underlying table, returns the corresponding
 * column in the visible table. */
public int colU2col(int colU) { return colU; }

}
