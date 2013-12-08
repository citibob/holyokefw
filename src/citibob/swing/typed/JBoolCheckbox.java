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
 * JDate.java
 *
 * Created on May 14, 2003, 8:52 PM
 */

package citibob.swing.typed;

import citibob.types.JType;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;
import java.beans.*;

/**
 *
 * @author  citibob
 * Doesn't hold NULL values.
 */
public class JBoolCheckbox extends JCheckBox implements SimpleTypedWidget {

//PropertyChangeSupport support = new PropertyChangeSupport(this);
Boolean val;


/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
	return val;
}

public boolean getBoolValue()
{
	return val.booleanValue();
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object d)
{
	if (d != null && d.getClass() != Boolean.class)
		throw new ClassCastException("Expected Boolean");
	if (d == null) d = Boolean.FALSE;
	Object oldVal = val;
	val = (Boolean)d;
	setSelected(val.booleanValue());
//System.out.println("JBoolCheckbox.firePropertyChange(" + oldVal + ", " + val + ")");
//if (val == true) {
//	System.out.println("hoi");
//}
	firePropertyChange("value", oldVal, val);
}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See JType.. */
public boolean isInstance(Object o)
{
	return (o instanceof Boolean);
}

/** Set up widget to edit a specific JType.  Note that this widget does not
 have to be able to edit ALL JTypes... it can throw a ClassCastException
 if asked to edit a JType it doesn't like. */
public void setJType(JType jType) throws ClassCastException
{
	Class klass = jType.getObjClass();
	if (!(Boolean.class.isAssignableFrom(klass)))
		throw new ClassCastException("Expected Boolean type, got " + klass);
}

// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public boolean stopEditing() { return true; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }

// ---------------------------------------------------

public JBoolCheckbox()
{
	super();
	val = this.isSelected() ? Boolean.TRUE : Boolean.FALSE;
	addActionListener(new java.awt.event.ActionListener() {
	public void actionPerformed(java.awt.event.ActionEvent evt) {
		setValue(isSelected());
	}});
}
public void setValue(boolean b)
	{ setValue(b ? Boolean.TRUE : Boolean.FALSE); }

}
