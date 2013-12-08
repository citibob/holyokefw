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
 * JTypedScrollPane.java
 *
 * Created on June 8, 2007, 10:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.typed;

/**
 * 
 * @author citibob
 */
public class JTypedScrollPane
extends javax.swing.JScrollPane implements TypedWidget
{

TypedWidget subWidget() { return (TypedWidget)getComponent(0); }
	
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue() { return subWidget().getValue(); }

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o) { subWidget().setValue(o); }

/** From TableCellEditor (in case this is being used in a TableCellEditor):
 * Tells the editor to stop editing and accept any partially edited value
 * as the value of the editor. The editor returns false if editing was not
 * stopped; this is useful for editors that validate and can not accept
 * invalid entries. */
public boolean stopEditing() { return subWidget().stopEditing(); }

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o) {return subWidget().isInstance(o); }

///** Set up widget to edit a specific SqlType.  Note that this widget does not
// have to be able to edit ALL SqlTypes... it can throw a ClassCastException
// if asked to edit a SqlType it doesn't like. */
//public void setJType(citibob.swing.typed.Swinger f) throws ClassCastException
//{ subWidget().setJType(f); }

/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return subWidget().getColName(); }

/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { subWidget().setColName(col); }
	
}
