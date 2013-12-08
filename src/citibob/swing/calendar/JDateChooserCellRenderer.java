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
//import java.util.Date;
//
//import java.awt.Component;
//import javax.swing.JTable;
//import javax.swing.table.TableCellRenderer;
//
//import citibob.swing.calendar.JDateChooser;
//
//
//public class JDateChooserCellRenderer 
//		implements TableCellRenderer {
//
//	JDateChooser chooser;
//	private static final long serialVersionUID = 5326414052628982795L;
//
//	public JDateChooserCellRenderer(JDateChooser chooser)
//	{ this.chooser = chooser; }
//	
//	public Component getTableCellRendererComponent(
//            JTable table, Object value ,
//            boolean isSelected, boolean hasFocus,
//            int row, int column) {
//		
//		Date date ;
//		if (value instanceof Date) 
//			date = (Date) value; // nb: date can be null
//		else 
//			date = null;
//		
//		chooser.getModel().setTime((Date)date);
//		return chooser;
//	}
//
//}
