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
package citibob.swing;

import java.util.*;

public abstract class AbstractRowModel implements RowModel
{

List<RowModel.ColListener>[] colListeners;	// Multiple listeners per column
public void addColListener(int colIndex, ColListener l)
{
	if (colListeners[colIndex] == null) colListeners[colIndex] = new LinkedList();
	colListeners[colIndex].add(l);
}

public void removeColListener(int colIndex, ColListener l)
{
	if (colListeners[colIndex] == null) return;
	colListeners[colIndex].remove(l);
//	colListeners[colIndex] = null;
}

void fireValueChanged(int colIndex)
{
//System.out.println("AbstractRowModel: value " + colIndex + " changed to " + this.get(colIndex));
	List<ColListener> ll = colListeners[colIndex];
	if (ll == null) return;
	for (ColListener l : ll) l.valueChanged(colIndex);
}
void fireAllValuesChanged()
{
//for (int i = 0; i < listeners.length; ++i) System.out.println("    fireAllValuesChanged: " + get(i));
//	for (int i = 0; i < colListeners.length; ++i) {
//		if (colListeners[i] != null) fireValueChanged(i);
//	}
	for (int i = 0; i < colListeners.length; ++i) fireValueChanged(i);
}
void fireCurRowChanged()
{
	for (int i = 0; i < colListeners.length; ++i) {
		List<ColListener> ll = colListeners[i];
		if (ll == null) continue;
		for (ColListener l : ll) l.curRowChanged(i);
//		if (colListeners[i] != null) colListeners[i].curRowChanged(i);
	}
}
void setColumnCount(int ncol) {
//	colListeners = new ColListener[tmodel.getColumnCount()];
	colListeners = new List[ncol];
}
}
