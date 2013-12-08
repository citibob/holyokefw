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
// * TypedWidgetMap.java
// *
// * Created on October 8, 2006, 7:02 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// 
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package citibob.swing.typed;
//
//import java.util.HashMap;
//import javax.swing.*;
//
///**
// * For use with Cobra's SimpleHtmlPanel.
// * @author citibob
// */
//public class TypedWidgetMap extends HashMap
//{
//	
//public JTypedTextField addTextField(String name, Swinger swinger)
//{
//	JTypedTextField widget = new JTypedTextField(swinger);
//	put(name, widget);
//	
//	// By default, text fields are size 25
////int x = widget.getHeight();
////	widget.setSize(150,21);
//	java.awt.Dimension d = widget.getPreferredSize();
//	d.width = 150;
//	widget.setSize(d);
////	widget.setSize(150, d.height);
//	return widget;
//}
//
//public Object getValue(String name)
//{
//	Object o = get(name);
//	if (o == null) return null;
//	if (!(o instanceof TypedWidget)) return null;
//	TypedWidget w = (TypedWidget)o;
//	return w.getValue();
//}
//
//}
