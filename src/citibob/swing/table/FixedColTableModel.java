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

public abstract class FixedColTableModel extends AbstractJTypeTableModel
{

JType[] types;
String[] colNames;
boolean[] editable;

public int getColumnCount() { return types.length; }

/** @param editable may be null */
public FixedColTableModel(String[] colNames, JType[] types, boolean[] editable)
{
	this.types = types;
	this.colNames = colNames;
	this.editable = editable;
}
public boolean isCellEditable(int row, int col)
	{ return (editable == null ? false : editable[col]); }
public String getColumnName(int col)
	{ return colNames[col]; }
public JType getJType(int row, int col)
	{ return types[col]; }
public Class getColumnClass(int col)
	{ return types[col].getObjClass(); }


}
