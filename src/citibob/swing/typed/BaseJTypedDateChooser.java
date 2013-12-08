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
// * JDate.java
// *
// * Created on May 14, 2003, 8:52 PM
// */
//
//package citibob.swing.typed;
//
//import java.text.DateFormat;
//import java.util.Date;
//import javax.swing.*;
//import citibob.swing.calendar.*;
//import citibob.sql.*;
//import java.beans.*;
//import java.util.*;
//
///**
// * @author  citibob
// */
//public abstract class BaseJTypedDateChooser extends JDateChooser implements TypedWidget {
//
//JDateType jType;
////PropertyChangeSupport support = new PropertyChangeSupport(this);
//
//public BaseJTypedDateChooser(JCalendar jcalendar)
//{
//	super(null, jcalendar);
////	super(null, new CalModel(Calendar.getInstance(), true));	// Dummy values for CalModel
////	super(dateFormatString, new CalModel(Calendar.getInstance(tz), nullable));
//}
///** Returns last legal value of the widget.  Same as method in JFormattedTextField */
//public Object getValue()
//{
////System.out.println("JTDC.getValue = " + getModel().getTime());
//	return getModel().getTime();
//}
//
///** Sets the value.  Same as method in JFormattedTextField.  Fires a
// * propertyChangeEvent("value") when calling setValue() changes the value. */
//public void setValue(Object d)
//{
//// TODO: Temporarily allow null in ALL fields --- to make it work
//// in the query editor for dates...
//if (d != null) {
//	if (!isInstance(d)) throw new ClassCastException("Bad type " + d.getClass() + " " + d);
//}
//	java.util.Date dt = (d == null ? null :  jType.truncate((java.util.Date)d));
//	
//System.out.println("JTDC: Setting date to " + dt );
//	java.util.Date oldDt = getModel().getTime();
//	getModel().setTime(dt);
//	//support.firePropertyChange("value", oldDt, dt);
//	// calChanged() will be called below...
//}
//
///** Overrides from JDateChooser to fire propertychangedevent... */
//public void calChanged()
//{
////System.out.println("calChanged to: " + getValue());
//	super.calChanged();
//	if (!getModel().isNull()) {
////System.out.println("Widget firing propertyChange: " + this);
//		firePropertyChange("value", null, getValue());
//	}
//}
//
////static java.util.Date oldDt = new java.util.Date();
//
///** Overrides from JDateChooser to fire propertychangedevent... */
//public void nullChanged() {
//	super.nullChanged();
//	firePropertyChange("value", null, getValue());
//}
//
///** Is this object an instance of the class available for this widget?
// * If so, then setValue() will work.  See JType.. */
//public boolean isInstance(Object o)
//{
//	return jType.isInstance(o);
//}
//
///** Set up widget to edit a specific JType.  Note that this widget does not
// have to be able to edit ALL JTypes... it can throw a ClassCastException
// if asked to edit a JType it doesn't like.
// @param cal Gives the calendar (and TimeZone) used to display and edit this date/time.  This
// could be either the TimeZone in the JDateSwinger (f; used to display dates to user) or
// the TimeZone used to read and write from the database. */
//public void setJType(citibob.swing.typed.Swinger f, Calendar cal) throws ClassCastException
//{
////	jType = (SqlDateType)f.getJType();
//	jType = (JDateType)f.getJType();
//	
//	// Set up the type properly
//	Class klass = jType.getObjClass();
//	if (!(java.util.Date.class.isAssignableFrom(klass)))
//		throw new ClassCastException("Expected Date type, got " + klass);
////	CalModel mcal = new CalModel(jType.getCalendar(), jType.isInstance(null));
////	CalModel mcal = new CalModel((Calendar)jType.getCalendar().clone(), jType.isInstance(null));
//	CalModel mcal = new CalModel(cal, jType.isInstance(null));
//	mcal.setCalTime(jType.truncate(mcal.getCalTime()));	// Make sure Calendar always stores a valid date/time value
//	super.setModel(mcal);
//	//super.setNullable(jType.isInstance(null));
//}
//
//public boolean stopEditing()
//{
//System.out.println("stopEditing: value = " + getValue());
////	getModel().useTmpDay();
//	return true;
//}
//// ---------------------------------------------------
//String colName;
///** Row (if any) in a RowModel we will bind this to at runtime. */
//public String getColName() { return colName; }
///** Row (if any) in a RowModel we will bind this to at runtime. */
//public void setColName(String col) { colName = col; }
//public Object clone() throws CloneNotSupportedException { return super.clone(); }
//// ---------------------------------------------------
/////** Implemented in java.awt.Component --- property will be "value" */
////public void addPropertyChangeListener(String property, java.beans.PropertyChangeListener listener)
////{
////	support.addPropertyChangeListener(listener);
////}
/////** Implemented in java.awt.Component --- property will be "value"  */
////public void removePropertyChangeListener(String property, java.beans.PropertyChangeListener listener)
////{
////	support.removePropertyChangeListener(listener);
////}
//
//}
