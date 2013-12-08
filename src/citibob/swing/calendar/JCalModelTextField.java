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
 * JCalModelTextFied.java
 *
 * Created on August 21, 2007, 2:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.calendar;

import citibob.types.JDate;
import citibob.swing.typed.*;
import citibob.types.JDateType;
import citibob.types.JType;
import java.util.*;
import javax.swing.*;
import citibob.text.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public class JCalModelTextField
extends JTypedTextField
implements CalModel.Listener, java.beans.PropertyChangeListener, JCalendar, java.awt.event.FocusListener
{

/** Creates a new instance of JCalModelTextFied */
public JCalModelTextField() {
}

protected CalModel model;

public void setModel(CalModel model) {
	this.model = model;
	model.addListener(this);	// Changes in model --> changes in text field
	this.addPropertyChangeListener("value", this);	// Changes in text field --> changes in model
//	this.addFocusListener(this);
}
public void setJType(JType jt, SFormat sformat, CalModel cmod)
{
	JDateType jdt = (JDateType)jt;
	this.setJType(jdt, newFormatterFactory(sformat), sformat.getHorizontalAlignment());
	setModel(cmod);
}
public void setJType(JType jt, String[] sfmts, TimeZone displayTZ, String nullText, CalModel cmod)
{
	this.setJType(jt,
		new FormatSFormat(new DateFlexiFormat(sfmts, displayTZ),
			nullText, SFormat.LEFT),
		cmod);
}
public void setValue(Object o)
{
	Date dt = (Date)o;
	super.setValue(((JDateType)jType).truncate(dt));
}

// =========================================================
// PropertyChangeListener
boolean inPropertyChange = false;
public void propertyChange(java.beans.PropertyChangeEvent evt)
{
//System.out.println("Property Changed: " + evt.getNewValue());
	if (inPropertyChange) return;
	inPropertyChange = true;
	model.setTime((Date)evt.getNewValue());
	inPropertyChange = false;
}
// =========================================================
// FocusListener
public void focusGained(FocusEvent e) {}
public void focusLost(FocusEvent e) {
	super.stopEditing();
	model.setTime((Date)super.getValue());
}
// =========================================================
// CalModel.Listener
/**  Value has changed. */
public void calChanged()
{
	if (inPropertyChange) return;
//System.out.println("Cal Changed: " + model.getTime());
	this.setValue(model.getTime());
}


/**  Nullness has changed. */
public void nullChanged()
{
	if (inPropertyChange) return;
	this.setValue(model.getTime());
}


/**  The "final" value has been changed. */
/*    finalCalChanged();*/

/**  User clicked on a day selection button --- will cause the popup to disappear. */
public void dayButtonSelected() {}

// =========================================================
static JCalModelTextField newField(JDateType jdt, CalModel cal)
{
//	JDateSwinger swinger = new JDateSwinger(new JDate(),
//		TimeZone.getDefault(), "yyyy-MM-dd");
//	CalModel cmod = swinger.newCalModel();

	JCalModelTextField tf;
	tf = new JCalModelTextField();
//	tf.setText("");
	TimeZone displayTZ = jdt.getTimeZone();
	tf.setJType(jdt, new String[] {null, "yyyy-MM-dd", "MM/dd/yy", "MM/dd/yyyy"}, displayTZ, "", cal);
//	tf.setModel(cal);
	tf.setPreferredSize(new java.awt.Dimension(200,19));

	return tf;
}


public static void main(String[] args)
throws Exception
{
	JFrame f = new JFrame();

	JDateType jdt = new JDate();
	CalModel model = new CalModel(jdt);

	java.awt.Container c = f.getContentPane();
	c.setLayout(new java.awt.FlowLayout());

	JCalModelTextField tf;
	tf = newField(jdt, model);
	tf.setValue(null);
	c.add(tf);

	tf = newField(jdt, model);
	tf.setValue(new Date());
	c.add(tf);

	c.add(new JButton("Hello"));
//		f.getContentPane().add(new JLabel("    "));

	f.setSize(200,200);
	f.pack();
	f.setVisible(true);
}


}
