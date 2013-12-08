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
///*
// * StatusTableCellRenderer.java
// *
// * Created on May 8, 2005, 7:42 PM
// */
//
//package citibob.swing.swingers;
//
//import javax.swing.table.*;
//import citibob.swing.typed.*;
//import java.sql.*;
//import citibob.types.KeyedModel;
//
///**
// *
// * @author citibob
// */
//public class StatusRenderEdit extends RenderEdit
//{
//
//public StatusRenderEdit()
//{
//	renderer = new StatusTableCellRenderer();
//	editor = null;
//}
//// =====================================================================
//public static class StatusTableCellRenderer
//extends DefaultTableCellRenderer
//implements citibob.jschema.RowStatusConst {
//
//
//public void setValue(Object o) {
//	if (o instanceof Integer) {
//		String s = "";
//		int status = ((Integer)o).intValue();
//		if ((status & INSERTED) != 0) s += "I";
//		if ((status & DELETED) != 0) s += "D";
//		if ((status & CHANGED) != 0) s += "*";
//		setText(s);
//	} else {
//		setText("<ERROR>");
//	}
//}
//
//}
//}
//
//
