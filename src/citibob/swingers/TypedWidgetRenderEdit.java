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
// * TypedWidgetRenderEdit.java
// *
// * Created on November 9, 2007, 1:01 AM
// *
// * To change this template, choose Tools | Template Manager
// * and open the template in the editor.
// */
//
//package citibob.swingers;
//
//import citibob.swing.typed.*;
//import citibob.swing.typed.Swinger;
//import citibob.types.JType;
//import java.text.*;
//import javax.swing.text.*;
//import javax.swing.*;
//import java.util.*;
//import javax.swing.table.*;
//import citibob.text.*;
//
///**
// *
// * @author citibob
// */
//public class TypedWidgetRenderEdit implements Swinger.RenderEdit
//{
//
//	TableCellEditor editor;
//	TableCellRenderer rendererEditable;
//	TableCellRenderer rendererNotEditable;
//	public TypedWidgetRenderEdit(TypedWidget tw)
//	{
//		rendererEditable = new TypedWidgetRenderer(tw);
//		rendererNotEditable = rendererEditable;
//		editor = new TypedWidgetEditor(tw);
//	}
//	public TypedWidgetRenderEdit(TypedWidget tw, SFormat sformat, boolean renderWithWidget)
//	{
//		rendererEditable = new TypedWidgetRenderer(tw);
//		rendererNotEditable = (renderWithWidget ? rendererEditable :
//			new SFormatRenderer(sformat));
//		editor = new TypedWidgetEditor(tw);
//	}
//	public TableCellEditor getEditor()
//		{return editor; }
//	public TableCellRenderer getRenderer(boolean editable)
//	{
//		return editable ? rendererEditable : rendererNotEditable;
//	}
//}
