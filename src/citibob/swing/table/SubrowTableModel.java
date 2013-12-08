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
 * GrouperTableModel.java
 *
 * Created on August 11, 2007, 10:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

import java.sql.*;
import citibob.text.*;
import citibob.sql.*;
import java.io.*;
import citibob.types.JType;
//import com.Ostermiller.util.*;
import javax.swing.table.*;

/**
 * A TableModel that represents just a subrange of rows of a parent TableModel.
Used for grouping the parent TableModel using TableModelGrouper.
 * @author citibob
 */
public class SubrowTableModel extends AbstractJTypeTableModel
{

JTypeTableModel mod;
int firstrow;
int nextrow;

/** Creates a new instance of GrouperTableModel */
public SubrowTableModel(JTypeTableModel mod, int firstrow, int nextrow)
{
	this.mod = mod;
	this.firstrow = firstrow;
	this.nextrow = nextrow;
}

/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
public JType getJType(int row, int col) { return mod.getJType(row,col); }


public int getRowCount() { return nextrow - firstrow; }
public int getColumnCount() { return mod.getColumnCount(); }
public String 	getColumnName(int column) { return mod.getColumnName(column); }
public Object getValueAt(int row, int col) { return mod.getValueAt(row + firstrow,col); }
public Class 	getColumnClass(int columnIndex) { return mod.getColumnClass(columnIndex); }

public void setValueAt(Object obj, int row, int col) {mod.setValueAt(obj, row + firstrow, col); }


}
