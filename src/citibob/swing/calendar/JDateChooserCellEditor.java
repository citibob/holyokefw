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
//
//package citibob.swing.calendar;
//
//import java.awt.Component;
//
//import javax.swing.JTable;
//import javax.swing.event.*;
//import javax.swing.table.*;
//import javax.swing.*;
//import java.util.*;
//
//import citibob.swing.calendar.JDateChooser;
//
//
//public class JDateChooserCellEditor 
//		extends AbstractCellEditor 
//		implements TableCellEditor {
//	
//
//	private static final long serialVersionUID = 2500889390699008056L;
//
//	public JDateChooserCellEditor(JDateChooser xcomponent)
//	{
//		this.component = xcomponent;
//		this.addCellEditorListener(new CellEditorListener() {
//			public void  editingCanceled(ChangeEvent e) {
//				System.out.println("Cell Edint cancelled!");}
//		    public void  editingStopped(ChangeEvent e) {
//				System.out.println("Cell Editing stopped!");
//				((JDateChooser)component).getModel().useTmpDay();
//			}
//		});
//	}
//	
//	// This is the component that will handle the editing of the cell value
//    JComponent component; // = new JDateChooser();
//
//    // This method is called when a cell value is edited by the user.
//    public Component getTableCellEditorComponent(JTable table, Object value,
//            boolean isSelected, int rowIndex, int vColIndex) {
//System.out.println("getTableCellEditorComponent: " + value);
////        ((JDateChooser)component).setTable(table);
//
//    	// 'value' is value contained in the cell located at (rowIndex, vColIndex)
//    	Date date ;
//		if (value instanceof Date) 
//			date = (Date) value; // nb: date can be null
//		else 
//			date = null;
//		
////		if( date != null )
//			((JDateChooser)component).getModel().setTime((Date)date);
////		else 
////			((JDateChooser)component).startEmpty = true;
//		
//        // Return the configured component
//        return component;
//    }
//    
//    // This method is called when editing is completed.
//    // It must return the new value to be stored in the cell.
//    public Object getCellEditorValue() {
//		JDateChooser jdc = (JDateChooser)component;
////		jdc.setPopupVisible(false);		// TODO: Get around popup JComboBox bug in Java 1.4; see JDateChooser file.
//        Object ret = jdc.getModel().getTime();
//System.out.println("getCellEditorValue: " + ret);
//		return ret;
//    }
//}