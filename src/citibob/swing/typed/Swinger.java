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
 * SqlSwinger.java
 *
 * Created on March 18, 2006, 6:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.typed;

import citibob.types.*;
import java.util.Comparator;
import javax.swing.table.*;

/**
 * Class that generates various Swing-related renderers, formatters and editors
 * based on an SqlType.
 * @author citibob
 */
public interface Swinger {
	
	/** Set up which type this Swinger is making stuff for. */
	//public void setSqlType(citibob.sql.SqlType sqlType);

//	/** Re-configure the way data are displayed and formatted --- for SOME types */
//	public void setFormatString(String sfmt);
	
	/** The type this Swinger is associated with. */
	public JType getJType();

	/** Tells us how to sort this widget */
	public Comparator getComparator();
	
	/** Returns an SFormat associated with this Swinger. */
	public citibob.text.SFormat getSFormat();
	
	/** Renderer and editor for a CitibobJTable.  If JTable's default
	 renderer and editor is desired, just return null.  Normally, this will
	 just return new TypedWidgetRenderEdit(newTypedWidget())
	 @param editable Is the item we want to render & edit editable?
	 Used to determine which form of renderer is used*/
	public RenderEdit newRenderEdit();
	
//	/** Produces just a Renderer. */
//	public TableCellRenderer newRenderer();


	/** Create and configure a widget suitable for editing this type of data. */
	public citibob.swing.typed.TypedWidget newWidget();

	/** Given a pre-existing widget, configure it suitably for types and
	formatting of this Swinger. */
	public void configureWidget(TypedWidget tw);	

//	/** Should we use newWidget() to render in a JTable, or should we use just plain Strings?
//	TODO: Change this to newTableWidget() instead... */
//	public boolean renderWithWidget();

//	/** Used to sort on items of this column/type/etc. */
//	public java.util.Comparator getComparator();
// ====================================================
public interface RenderEdit {
	public TableCellRenderer getRenderer(boolean editable);
	public TableCellEditor getEditor();
}

}
