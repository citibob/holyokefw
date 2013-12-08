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
 * JTypeTable.java
 *
 * Created on April 1, 2006, 10:11 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

import citibob.swing.typed.Swinger;
import citibob.swing.typed.SwingerMap;
import citibob.types.JType;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import citibob.sql.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;

/**
 * Allows a table with an arbitrary JType in each cell; sets up renderers
 * and editors properly, according to a SwingerMap.  Should be paired with
 * an JTypeTableModel.
 * NOTE: This class caches RenderEdit objects it makes.  They could grow
 * over time, if it keeps being used to edit different JTypes.
 * 
 * <p>This needs to be replaced by an implementation of DataGrid<RenderEdit>
 * and a subclass of StyledTM.  But since it works for now, I'll let it be...
 * @deprecated
 * @author citibob
 */
public class JTypeTable extends CitibobJTable
{
	SwingerMap smap;
	Map<JType,Swinger.RenderEdit> reMap;
	
	public void setSwingerMap(SwingerMap smap)
		{ this.smap = smap; }

	/** Creates a new instance of JTypeTable */
	public JTypeTable() {
		reMap = new WeakHashMap();
	}

	public TableCellEditor getCellEditor(int row, int col)
	{
		Swinger.RenderEdit re = getRenderEdit(row, col);
		if (re != null) {
			TableCellEditor ee = re.getEditor();
			if (ee != null) return ee;
		}
		return super.getCellEditor(row, col);
	}
	public TableCellRenderer getCellRenderer(int row, int col)
	{
		Swinger.RenderEdit re = getRenderEdit(row, col);
		if (re != null) {
			TableCellRenderer rr = re.getRenderer(isCellEditable(row,col));
			if (rr != null) return rr;
		}
		return super.getCellRenderer(row, col);
	}
	public Swinger.RenderEdit getRenderEdit(int row, int col)
	{
		if (smap == null) return null;
		
		JTypeTableModel mod = (JTypeTableModel)getModel();
		JType jType = mod.getJType(row, col);
		if (jType == null) return null;
		
		// See if we have cached a RenderEdit for this type
		boolean editable = mod.isCellEditable(row, col);
		Swinger.RenderEdit re = (Swinger.RenderEdit)reMap.get(jType);
		if (re != null || reMap.containsKey(jType)) return re;
		
		// Get the swinger to make us a new RenderEdit
		Swinger swinger = smap.newSwinger(jType);
		if (swinger == null) {
			reMap.put(jType, re);		// avoid requrerying later
			return null;
		} else {
			re = swinger.newRenderEdit();
			reMap.put(jType, re);
System.out.println("New RenderEdit: " + jType + " --> " + re);
			return re;
		}
	}
}
