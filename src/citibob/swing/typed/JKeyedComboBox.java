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
/* This works if there's a null in kmodel!! */

package citibob.swing.typed;
//import java.text.DateFormat;
//import java.util.Date;
import citibob.jschema.Column;
import citibob.jschema.Schema;
import citibob.jschema.SchemaSet;
import citibob.text.KeyedSFormat;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import citibob.types.KeyedModel;
import java.awt.*;
import java.awt.event.*;
import citibob.sql.*;
import citibob.types.JEnum;
import citibob.types.JEnum;

/**
 *
 * @author  citibob
Used to make a combo box that returns one of a fixed set of integer values.  The ComboBox displays a list of describtive strings, one per integer value to be returned.
 */
public class JKeyedComboBox extends JComboBox implements TypedWidget, KeyedModel.Listener {
KeyedModel kmodel;
KeyedSFormat kformatter;
Object value;
//JType jType;
Object segment = null;		// Only show items from the current segment

public static final Object NULL = new Object();
// ------------------------------------------------------
public JKeyedComboBox()
{
	setRenderer(new MyRenderer());
	
	// Called when user changes selection
	addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
		Object oldVal = getValue();
		Object o = getSelectedItem();
		setValue(o == NULL ? null : o);
//		// No need for setValue(), since value === getSelectedItem()
		
		// For some reason, this line was commented out.  Probably had to do
		// with debugging the School screen in OffstageArts.  But commenting
		// it out is clearly wrong.
		firePropertyChange("value", oldVal, getValue());
	}});
}
public JKeyedComboBox(KeyedModel kmodel)
{
	this();
	setKeyedModel(kmodel, null);
}
// --------------------------------------------------------------
/** Convenience method */
public void setKeyedModel(JEnum jenum)
	{ setKeyedModel(jenum.getKeyedModel(), jenum.getSegment()); }
/** Convenience method: sets dropdown equal to the type of the column;
 column must be of type JEnum. */
public void setKeyedModel(Column col)
	{ setKeyedModel((JEnum)col.getType()); }
/** Convenience method: sets dropdown equal to the type of the column.
 Column must be of type JEnum*/
public void setKeyedModel(Schema schema, String colName)
	{ setKeyedModel(schema.getCol(colName)); }
public void setKeyedModel(SchemaSet sset, String schemaName, String colName)
	{ setKeyedModel(sset.get(schemaName), colName); }


public void setKeyedModel(KeyedModel kmodel)
	{ setKeyedModel(kmodel, null); }

public void setKeyedModel(KeyedModel kmodel, Object segment)
{
	if (this.kmodel != null) {
		this.kmodel.removeListener(this);
	}
	this.kmodel = kmodel;
	this.segment = segment;
	kmodel.addListener(this);
	refreshKeyedModel();
}
public void setSegment(Object segment)
{
	this.segment = segment;
	refreshKeyedModel();
}
public void refreshKeyedModel()
{
	Object val = getValue();
	kformatter = new KeyedSFormat(kmodel);
	Vector keyList = kmodel.newKeyList(segment, NULL);
//System.out.println("keyList.size() = " + keyList.size());
	DefaultComboBoxModel cmodel = new DefaultComboBoxModel(keyList);
	super.setModel(cmodel);
	if (keyList.size() > 0) {
		if (val == null) this.setSelectedIndex(0);	// Make sure getValue() returns something
		else setSelectedItem(val);
	}
}
public KeyedModel getKeyedModel()
{ return kmodel; }
//public void setJType(Swinger f)
//{
//	JType jType = f.getJType();
//	if (!(jType instanceof JEnum)) 
//		throw new ClassCastException("Expected Enum type, got " + jType);
//	JEnum etype = (JEnum)jType;
//	setKeyedModel(etype.getKeyedModel());
//}
// --------------------------------------------------------------
public boolean isInstance(Object o)
{
	return kmodel.getItemMap().containsKey(o);
}
// --------------------------------------------------------------
String colName;
/** Row (if any) in a RowModel we will bind this to at runtime. */
public String getColName() { return colName; }
/** Row (if any) in a RowModel we will bind this to at runtime. */
public void setColName(String col) { colName = col; }
public boolean stopEditing() { return true; }
public Object clone() throws CloneNotSupportedException { return super.clone(); }

// ---------------------------------------------------


//// -------------------------------------------------------
//public void setSelectedItem(Object o)
//{
//	System.out.println("setSelectedItem("+ o + ")");
//	super.setSelectedItem(o);
////	System.out.println("getSelectedItem = " + getSelectedItem());
//}
//public Object getSelectedItem()
//{
//	Object ret = super.getSelectedItem();
////	System.out.println("getSelectedItem = " + ret);
//	return ret;
//}
// ============================================================
// TypedWidget stuff
public void setValue(Object d)
{
//System.out.println("JKeyedComboBox.setValue: " + d);
//	// HACK: Handle it if 
//	if (d instanceof Short) d = new Integer(((Short)d).intValue());
	Object oldVal = value;
	
//	// No event if we haven't changed... (stop infinite recursion too)
//	if (oldVal == d) return;
//	if (oldVal != null && d != null && d.equals(oldVal)) return;
	value = d;
	
	setSelectedItem(d == null ? NULL : d);
	this.firePropertyChange("value", oldVal, d);
}
//public void setValue(int i)
//{
//	Integer ii = new Integer(i);
//	setValue(ii);
//}
	
public Object getValue()
{
	return value;
//	Object o = getSelectedItem();
//	return o;
}

// ==============================================================
// KeyedModelListener
public void keyedModelChanged() {
	refreshKeyedModel();
}
// ==============================================================
class MyRenderer 
extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(
	JList list, Object value, int index,
	boolean isSelected, boolean cellHasFocus) {
//if (value == null || value == NULL) {
//	System.out.println("hoi");
//}
		if (kformatter != null) value = kformatter.valueToString(
			value == NULL ? null : value);
		return super.getListCellRendererComponent(
			list, value == NULL ? null : value, index,isSelected,cellHasFocus);
    }
}
}
