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
package citibob.swing.typed;

//import


/** A widget that edits a Java object.  Always implemented in conjunction
with subclassing java.awt.Component, i.e. (x istanceof TypedWidget) implies (x instanceof Component) */
public interface TypedWidget extends Cloneable
{

/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue();

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o);

/** From TableCellEditor (in case this is being used in a TableCellEditor):
 * Tells the editor to stop editing and accept any partially edited value
 * as the value of the editor. The editor returns false if editing was not
 * stopped; this is useful for editors that validate and can not accept
 * invalid entries. */
public boolean stopEditing();

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o);

///** Set up widget to edit a specific SqlType, with specific formatting.
// The type, along with type-specific formatting information, is included
// in the Swinger.  Note that this widget does not
// have to be able to edit ALL SqlTypes... it can throw a ClassCastException
// if asked to edit a JType it doesn't like. */
//public void setJType(citibob.swing.typed.Swinger f) throws ClassCastException;

/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName();
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col);


// =====================================================
// Methods implemented in java.awt.Component
/** Implemented in java.awt.Component */
public void setEnabled(boolean enabled);
/** Implemented in java.awt.Component --- property will be "value" */
public void addPropertyChangeListener(String property, java.beans.PropertyChangeListener listener);
/** Implemented in java.awt.Component --- property will be "value"  */
public void removePropertyChangeListener(String property, java.beans.PropertyChangeListener listener);
// =====================================================


//public Object clone() throws CloneNotSupportedException;

/** Sets value back to last legal value (i.e. setValue(getValue())) */
//void resetValue();

///** Sets the value to the last legal edited value.
//Allows focus to leave the widget*/
//void setLatestValue();
//
///** If false, then user is in middle of editing something
//and doesn't have a valid value yet. */
//boolean isValueValid();

/** Returns type of object this widget edits. */
//Class getObjClass();



/** Returns the underlying ObjModel; used to combine this widget's data model
 * into another model.  So far, Objmodel is not able to take listeners,
 * but that could change... */
//public ObjModel getObjModel();
//public void setObjModel(ObjModel m);

}
