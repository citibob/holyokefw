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
 * KeyedButtonGroup.java
 *
 * Created on May 9, 2005, 10:46 PM
 */

package citibob.swing.typed;

import citibob.jschema.*;
import citibob.types.JType;
import java.util.*;
import javax.swing.*;
import java.beans.*;
import java.awt.event.*;
import java.beans.*;

/**
 *
 * @author citibob
 */
public class KeyedButtonGroup
extends ButtonGroup implements TypedWidget, ActionListener {

PropertyChangeSupport support = new PropertyChangeSupport(this);

protected Map map;		// key -> AbstractButton
Map imap;		// ButtonModel -> key
//Class objClass = null;
Object val = null;
JType jType;

/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{ return val; }

///** Sets the correct button for a value. */
//private void setValueButton(Object o)
//{
//	
//}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o)
{
	Object oldVal = val;
	val = o;
System.out.println("o = " + o);
	AbstractButton b = (AbstractButton)map.get(o);
	setSelected(b.getModel(), true);
	support.firePropertyChange("value", oldVal, val);
}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See JType.. */
public boolean isInstance(Object o)
{
	return map.containsKey(o);
}

/** Set up widget to edit a specific JType.  Note that this widget does not
 have to be able to edit ALL JTypes... it can throw a ClassCastException
 if asked to edit a JType it doesn't like. */
public void setJType(citibob.swing.typed.Swinger f) throws ClassCastException
{
	jType = f.getJType();
	// Could be anything...
//	JType jType = f.getJType();
//	if (!(jType instanceof SqlEnum)) 
//		throw new ClassCastException("Expected Enum type, got " + jType);
//	SqlEnum etype = (SqlEnum)jType;
//	setKeyedModel(etype.getKeyedModel());
}

// ---------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public boolean stopEditing() { return true; }

// ---------------------------------------------------
/** Creates a new instance of KeyedButtonGroup */
public KeyedButtonGroup()
{
	map = new HashMap();
	imap = new HashMap();
}
// -------------------------------------------------------------
public void add(Object key, AbstractButton b)
{
	super.add(b);
	ButtonModel bm = b.getModel();
	map.put(key, b);
	imap.put(bm, key);
	b.addActionListener(this);

	// Check the type of the item we just added.
	if (jType != null) {
		if (!jType.isInstance(key)) {
			throw new ClassCastException(
				"KeyedButtonGroup.add() received object " + key +
				", not compatible with " + jType);
		}
	}
}

public AbstractButton remove(Object key)
{
	Object o = map.remove(key);
	if (o == null) return null;
	AbstractButton b = (AbstractButton)o;
	if (b != null) {
		b.removeActionListener(this);
		imap.remove(b.getModel());
		super.remove(b);
	}
	return b;
}
// -------------------------------------------------------------

// =====================================================
// TypedWidget

protected Object getValue(AbstractButton b)
{
	ButtonModel m = b.getModel();
	Object ret = imap.get(m);
//System.out.println("getValue: " + b + " returns\n '" + ret + "'");
	return ret;
}

protected void setButton(AbstractButton b)
{
	setSelected(b.getModel(), true);
}
// =====================================================
// Methods normally implemented in java.awt.Component
public void setEnabled(boolean enabled)
{
	for (Enumeration ii = super.getElements(); ii.hasMoreElements();) {
		AbstractButton b = (AbstractButton)ii.nextElement();
		b.setEnabled(enabled);
	}
}
/** Implemented in java.awt.Component --- property will be "value" */
public void addPropertyChangeListener(String property, java.beans.PropertyChangeListener listener)
{
	support.addPropertyChangeListener(listener);
}
/** Implemented in java.awt.Component --- property will be "value"  */
public void removePropertyChangeListener(String property, java.beans.PropertyChangeListener listener)
{
	support.removePropertyChangeListener(listener);
}
// ===============================================================
// Implementation of ActionListener

/** Propagate data from widget to underlying model. */
public void actionPerformed(ActionEvent e)
{
	Object oldVal = val;
	AbstractButton b = (AbstractButton)(e.getSource());
	val = getValue(b);
	support.firePropertyChange("value", oldVal, val);
}


}

