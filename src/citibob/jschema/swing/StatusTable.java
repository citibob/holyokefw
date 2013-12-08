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
 *
 * Created on March 19, 2005, 12:00 AM
 */

package citibob.jschema.swing;

import citibob.swing.typed.SwingerMap;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import citibob.jschema.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.swing.*;
import citibob.swing.JTypeColTable;


/**
 * High-level table: contains a status field and a bunch of other stuff.
 * @author citibob
 @deprecated
 */
public class StatusTable extends SchemaBufTable {

//ssbStatusSchemaBuf ssb;

public void setModelU(SchemaBuf schemaBuf,
String[] xColNames, String[] xSColMap,
boolean[] xEditable, SwingerMap swingers)
{
	// Prepend 1 column to column list
	boolean[] editable = (xEditable == null ? null : new boolean[xEditable.length + 1]);
	String[] colNames = new String[xColNames.length + 1];
	String[] sColMap = new String[xSColMap.length + 1];
	colNames[0] = "Status";
	sColMap[0] = "__status__";
	for (int i=0; i<xColNames.length; ++i) {
		if (editable != null) editable[i+1] = xEditable[i];
		colNames[i+1] = xColNames[i];
		sColMap[i+1] = xSColMap[i];
	}
	// Set it up
//	ssb = new StatusSchemaBuf(schemaBuf);
	super.setModelU(schemaBuf, colNames, sColMap, editable, swingers);

//	setRendererU("__status__", new StatusRenderer());
}

public void setModelU(SchemaBuf schemaBuf,
String[] xColNames, String[] xSColMap, String[] xTtColMap,
boolean[] xEditable, SwingerMap swingers)
{
	// Prepend 1 column to column list
	boolean[] editable = (xEditable == null ? null : new boolean[xEditable.length + 1]);
	String[] colNames = new String[xColNames.length + 1];
	String[] sColMap = new String[xSColMap.length + 1];
	String[] ttColMap = new String[xSColMap.length + 1];
	colNames[0] = "Status";
	sColMap[0] = "__status__";
//	ttColMap[0] = "__none__";		// Should not match...
	for (int i=0; i<xColNames.length; ++i) {
		if (editable != null) editable[i+1] = xEditable[i];
		colNames[i+1] = xColNames[i];
		sColMap[i+1] = xSColMap[i];
		ttColMap[i+1] = xTtColMap[i];
	}
	// Set it up
//	ssb = new StatusSchemaBuf(schemaBuf);
	super.setModelU(schemaBuf, colNames, sColMap, ttColMap, editable, swingers);

//	setRendererU("__status__", new StatusRenderer());
}	
// ==========================================================================
//static class StatusRenderer
//extends DefaultTableCellRenderer
//implements citibob.jschema.RowStatusConst {
//	public void setValue(Object o) {
//		if (o instanceof Integer) {
//			String s = "";
//			int status = ((Integer)o).intValue();
//			if ((status & INSERTED) != 0) s += "I";
//			if ((status & DELETED) != 0) s += "D";
//			if ((status & CHANGED) != 0) s += "*";
//			setText(s);
//		} else {
//			setText("<ERROR>");
//		}
//	}
//}
// =====================================================================

}
