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
package citibob.swing.table;

import citibob.types.JType;
import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;

public interface JTypeTableModel<TT> extends TableModel, DataGrid<TT>
{
	/** Model provies "prototype" information, i.e. a sample row of data. */
//	List getPrototypes();

	/** These, you will get for free if you subclass AbstractTableModel. */
	void fireTableChanged(TableModelEvent e);

	/** Finds a column's index by name --- also implemented in AbstractTableModel.
	 Returns -1 if column names doesn't exist or is null. */
	int findColumn(String name);

	/** Should this column be displayed by default? */
	public boolean isVisible(int col);
	
	/** Gets the value at a specific column and row, with column referenced by name */
	public Object getValueAt(int row, String col);
	public void setValueAt(Object val, int row, String col);

	/** If this wraps another JTypeTableModel, return the model being wrapped. */
	public JTypeTableModel getModelU();

/** Finds a column by name in the UNDERLYING table model, then
reports its location in THIS table model.  This allows one to refer
to columns by their UNDERLYING name, not their display name. */
public int findColumnU(String name);

/** Given a column in the visible table, returns the corresponding
 * column in the underlying table. */
public int getColU(int col);

/** Given a column in the underlying table, returns the corresponding
 * column in the visible table. */
public int colU2col(int colU);

//	/** Return SqlType for an entire column --- or null, if this column does not have a single SqlType. */
//	public JType getColumnJType(int col);

	/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
	public JType getJType(int row, int col);

	/** Convenience function */
	public JType getJType(int row, String col);

	/** @returns SFormat to use for this type.  Normally
	 * returns null, which will allow the system to use the
	 * type coming from the SwingerMap.
	 * If not null, this will override formats
	 * supplied by the Swinger/SFormat Map.  This allows one
	 * to create table models that include formatting info
	 * along with type info.
	 */
//	public SFormat getSFormat();
	
//	/** Return a value we can sort on --- usually the same as getValueAt().  But, eg,
//	if a column is an integer key with a KeyedModel, then it would be sorted according
//	to the KeyedModel sort order. */
//	public Object getSortValueAt(int row, int col);




}
