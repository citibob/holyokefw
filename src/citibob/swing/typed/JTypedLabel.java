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

import citibob.text.KeyedSFormat;
import citibob.text.StringSFormat;
import citibob.types.JType;
import java.text.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import citibob.exception.*;
import citibob.sql.*;
import citibob.swing.typed.SwingerMap;
import java.beans.*;
//import citibob.sql.JType;
import citibob.text.*;
import citibob.types.*;

/**
 *
 * @author  citibob
 */
public class JTypedLabel
extends JLabel
implements TextTypedWidget {

/** Our best guess of the class this takes. */
//Class objClass = null;
JType jType;	
SFormat sformat;
protected Object val;
boolean useToolTips = true;		// Should we set the tooltip to the same as the label text?

public JTypedLabel()
{
	super();
}
public JTypedLabel(CharSequence initialVal)
{
	super();
	setJType(initialVal.getClass(), new StringSFormat());
	setValue(initialVal);
}
public JTypedLabel(Object initialVal, String fmtString)
{
	super();
	Class klass = initialVal.getClass();
	setJType(klass, fmtString);
	setValue(initialVal);
}
public JTypedLabel(Class klass, String fmtString)
{
	super();
	setJType(klass, fmtString);
}
// --------------------------------------------------------------
//public void setJType(Swinger f)
//{
////System.out.println("JTypedTextField.setJType: " + f + ", " + f.getJType());
//	jType = f.getJType();
//	formatter = f.newFormatterFactory().getDefaultFormatter();
//}
public void setJType(JType jt)
	{ this.jType = jt; }
public void setJType(JType jt, SFormat sformat)
{
	this.jType = jt;
	this.sformat = sformat;
	this.setHorizontalAlignment(sformat.getHorizontalAlignment());
}
public void setJType(JType jt, Format fmt)
	{ setJType(jt, new FormatSFormat(fmt)); }
public void setJType(JType jt, String fmtString)
	{ setJType(jt, citibob.text.FormatUtils.newFormat(jt.getClass(), fmtString)); }


public void setJType(Class klass, SFormat sformat)
	{ setJType(new JavaJType(klass), sformat); }
public void setJType(Class klass, Format fmt)
	{ setJType(klass, new FormatSFormat(fmt)); }
public void setJType(Class klass, String fmtString)
	{ setJType(klass, citibob.text.FormatUtils.newFormat(klass, fmtString)); }
public void setJTypeString()
	{ setJType(String.class, new StringSFormat()); }


/** Convenience function.
 @param nullText String to use for null value, or else <null> if this is not nullable. */
public void setJType(citibob.types.KeyedModel kmodel, String nullText)
{
	JEnum tt = new JEnum(kmodel);
	setJType(tt, new KeyedSFormat(kmodel, nullText));
}


// --------------------------------------------------------------

public boolean isInstance(Object o)
{
	if (jType == null) return true;
	return jType.isInstance(o);
}
public boolean stopEditing()
{  return true; }
// --------------------------------------------------------------
public void setValue(Object xval)
{
	if (val == xval && val != null) return;		// This was called multiple times; ignore
	Object oldVal = val;
	val = xval;
	try {
		String text = sformat.valueToString(val);
		setDisplayValue(xval, text);
	} catch(java.text.ParseException e) {
		setText("<parseException>");
	}
	this.firePropertyChange("value", oldVal, val);
}
public void setDisplayValue(Object xval, String text)
{

	this.val = val;
	setText(text);
	if (useToolTips) setToolTipText(text);
}
public Object getValue()
{
	return val;
}
// --------------------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }
// ---------------------------------------------------
}
