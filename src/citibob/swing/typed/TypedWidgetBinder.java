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

import citibob.swing.typed.Swinger;
import citibob.swing.typed.SwingerMap;
import citibob.types.JType;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.*;

import citibob.swing.typed.*;
import citibob.sql.*;
import citibob.exception.*;
import citibob.jschema.*;
import citibob.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Binds a TypedWidget to a RowModel, passing events back and forth.
 * @author  citibob
 */
public class TypedWidgetBinder
implements RowModel.ColListener, java.beans.PropertyChangeListener
{

/** Only move data from SchemaBuf to TypedWidget */
public static final int BT_READ = 1;
/** Only move data from TypedWidget to RowModel */
public static final int BT_WRITE = 2;
/** Move data both ways between RowModel and TypedWidget */
public static final int BT_READWRITE = BT_READ | BT_WRITE;
	
boolean inValueChanged = false;
boolean inCurRowChanged = false;
boolean inPropertyChange = false;

TableRowModel bufRow;
int colNo;
TypedWidget tw;

// --------------------------------------------------------------------
///** Convenience function --- look up column name for us. */
//public void bind(TypedWidget tw, SchemaRowModel bufRow, SwingerMap map)
//	{ bind(tw, bufRow, tw.getColName(), map); }
///** Don't set the JType of the widget */
//public void bind(TypedWidget tw, TableRowModel bufRow)
//	{ bind(tw, bufRow, null); }
//
//// -------------------------------------------------------------------------------
//public void bind(TypedWidget tw, SchemaRowModel bufRow, String colName, SwingerMap map)
//{
//	bind(tw, (TableRowModel)bufRow, colName);
//}
//
//public void bindReadOnly(TypedWidget tw, SchemaRowModel bufRow, String colName)
//{
//	readOnly = true;
//	bind(tw, bufRow, colName);
//}
///** Just bind widget, don't mess with its type. */

// -------------------------------------------------------------------------------
// Convenience functions
/** Binds tw to bufRow as BT_READWRITE using default column name, DOES set types. */
public void bind(TypedWidget tw, SchemaRowModel bufRow,
SwingerMap smap)
	{ bind(tw, bufRow, null, BT_READWRITE, smap); }
	
/** Binds tw to bufRow as BT_READWRITE using default column name, DOES NOT set any types. */
public void bind(TypedWidget tw, SchemaRowModel bufRow)
	{ bind(tw, (TableRowModel)bufRow, tw.getColName(), BT_READWRITE); }
// -------------------------------------------------------------------------------
/** Bind widget and DO set its type.

 @param colName Name in RowModel of column to bind widget to.  If null, bind to
 tw.getColName().
 @param tw Widget to bind to buffer
 @param bufRow Buffer to bind to widget.
 NOTE: (x instanceof SchemaRowModel) ==> (x instanceof TableRowModel)
 @param BT_READ, BT_WRITE, or BT_READWRITE
 @smap Used to auto-configure type of widget. */
public void bind(TypedWidget tw, SchemaRowModel bufRow,
String colName, int bindType, SwingerMap smap)
{
	if (colName == null) colName = tw.getColName();
	setJType(tw, bufRow, colName, smap);
	bind(tw, (TableRowModel)bufRow, colName, bindType);
}
// ------------------------------------
///** Bind widget and DO NOT set its type.
// @param colName Name in RowModel of column to bind widget to.  If null, bind to
// tw.getColName().
// @param tw Widget to bind to buffer
// @param bufRow Buffer to bind to widget
// NOTE: (x instanceof SchemaRowModel) ==> (x instanceof TableRowModel)
// @param BT_READ, BT_WRITE, or BT_READWRITE */ 
//public void bind(TypedWidget tw, SchemaRowModel bufRow,
//String colName, int bindType)
//	{ bind(tw, (TableRowModel)bufRow, colName, bindType); }
/** Bind widget and DO NOT set its type.
 @param colName Name in RowModel of column to bind widget to.  If null, bind to
 tw.getColName().
 @param tw Widget to bind to buffer
 @param bufRow Buffer to bind to widget
 NOTE: (x instanceof SchemaRowModel) ==> (x instanceof TableRowModel)
 @param BT_READ, BT_WRITE, or BT_READWRITE */
public void bind(TypedWidget tw, TableRowModel bufRow,
String colName, int bindType)
{
	if (colName == null) colName = tw.getColName();
	colNo = bufRow.findColumn(colName);
	if (colNo < 0) return;		// This column is not for us

	this.bufRow = bufRow;
	this.tw = tw;
	if ((bindType & BT_WRITE) != 0) {
		// Bind as listener to the TypedWidget
		tw.addPropertyChangeListener("value", this);
	}
	
	/** Listen to the RowModel */
	if ((bindType & BT_READ) != 0) {
		// Bind as a listener to the RowModel (which fronts a SchemaBuf)...
		bufRow.addColListener(colNo, this);

		/* Now, set the initial value. */
		valueChanged(colNo);
	}
}

// --------------------------------------------------------------------
/** Set the type of a widget */
public static void setJType(TypedWidget tw, SchemaRowModel bufRow, String colName, SwingerMap map)
{
	// Set the type
	if (map != null) {
		Schema schema = bufRow.getSchema();
		int col = schema.findCol(colName);
		if (col < 0) return;		// This widget was not meant for us
//		if (col < 0) System.out.println("TypedWidgetBinder: Cannot find column nanmed " + colName);
		JType sqlType = schema.getCol(col).getType();
		Swinger f = map.newSwinger(sqlType);		// Default ways to render & edit
//System.out.println("TypedWidgetBinder.setJType() colName = " + colName);
		f.configureWidget(tw);
		//tw.setJType(f);
	}
}
// -------------------------------------------------------------------------------
/** Listen to the widget. */
void bindWidget(TypedWidget tw)
{
	this.tw = tw;
}
// --------------------------------------------------------------------
public void unBind()
{
	//Component c = (Component)tw;
	tw.removePropertyChangeListener("value", this);
	bufRow.removeColListener(colNo, this);
	
	// Allow for garbage collection
	tw = null;
	bufRow = null;
}
// --------------------------------------------------------------------
boolean valsEqual(Object a, Object b)
{
	if (a == b) return true;
	if (a != null && a.equals(b)) return true;
	return false;
}

// ===============================================================
// Implementation of RowModel.Listener
/** Propagate change in underlying RowModel to widget value. */
public void valueChanged(int col)
{
//if ("mailprefid".equals(tw.getColName())) {
//	System.out.println("hoi");
//	System.out.println("valueChanged(" + col + ") = " + bufRow.get(col));
//	System.out.println("tw = " + tw + ", bufRow = " + bufRow);
//}
	if (inValueChanged) return;
	inValueChanged = true;

	Object val = bufRow.get(col);
	tw.setValue(val);

	inValueChanged = false;
}
public void curRowChanged(int col)
{
//if ("mailprefid".equals(tw.getColName())) {
//	System.out.println("hoi");
//}
	if (inCurRowChanged) return;
	inCurRowChanged = true;

	int row = bufRow.getCurRow();
	//Component c = (Component)tw;
	tw.setEnabled(row != MultiRowModel.NOROW);
	tw.setValue(row == MultiRowModel.NOROW ? null : bufRow.get(col));

	inCurRowChanged = false;
}
// ===============================================================
// Implementation of PropertyChangeListener
public void propertyChange(java.beans.PropertyChangeEvent evt)
{
//if ("mailprefid".equals(tw.getColName())) {
//	System.out.println("hoi");
//}
	if (inPropertyChange) return;
	inPropertyChange = true;

//System.out.println("Property change received from widget: " + evt.getSource());
	//if (!"value".equals(evt.getPropertyName())) return;
	
//	// Stop infinite loop of property change events between the two objects.
//	if (!valsEqual(evt.getNewProperty(), evt.getOldProperty())) return;
//	// OK, propagate it down to the row.
	bufRow.set(colNo, evt.getNewValue());

	inPropertyChange = false;
}
// ===============================================================
/** Convenience method: make a SchemaRowModel, then bind... */
public static void bindRecursive(Component c, SchemaBuf sb, SwingerMap map)
{
	SchemaBufRowModel rmodel = new SchemaBufRowModel(sb);
	bindRecursive(c, rmodel, map);
}

/** Binds all components in a widget tree to a (SqlSchema, RowModel), if they implement SchemaRowBinder. */
public static void bindRecursive(Component c, SchemaRowModel bufRow, SwingerMap map)
{
	// Take care of yourself
	if (c instanceof TypedWidget) {
		TypedWidget tw = (TypedWidget)c;
//System.out.println("Binding TypedWidget: " + tw.getColName());
		String colName = tw.getColName();
		if (colName != null && bufRow.getSchema().findCol(colName) >= 0) {
			new TypedWidgetBinder().bind(tw, bufRow, tw.getColName(), BT_READWRITE, map);
		}
	}

	// Take care of your children
	if (c instanceof Container) {
//System.out.println("Binding TypedWidget Container: " + c.getClass());
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			bindRecursive(child[i], bufRow, map);
		}
	}
	
	// Take care of explicit invisible children
	if (c instanceof BindContainer) {
//System.out.println("Binding TypedWidget Container: " + c.getClass());
	    Component[] child = ((BindContainer)c).getBindComponents();
	    for (int i = 0; i < child.length; ++i) {
			bindRecursive(child[i], bufRow, map);
		}
	}
}

// --------------------------------------------------------------
/** Set the type of a widget
 @param colName column in ResultSet to bind --- if null, use from tw. */
static void setValue(TypedWidget tw, java.sql.ResultSet rs,
SwingerMap map, citibob.sql.SqlTypeSet typeset) throws java.sql.SQLException
{
	String colName = tw.getColName();
	int col = 0;
	try {
		col = rs.findColumn(colName);
	} catch(java.sql.SQLException e) {
		return;		// Col not found
	}
	if (col <= 0) return;
	SqlType sqlType = typeset.getSqlType(rs, col);
	Swinger swinger = map.newSwinger(sqlType);
System.out.println("Binder.setValue: tw = " + tw.getClass() + "\n     swinger = " + swinger.getClass());
	//tw.setJType(swinger);
	swinger.configureWidget(tw);
	tw.setValue(sqlType.get(rs, col));
}


/** Sets the JType and value of all widgets in a tree, but does not bind them. */
public static void setValueRecursive(Component c, java.sql.ResultSet rs, SwingerMap map, SqlTypeSet tset)
throws SQLException
{
	// Take care of yourself
	if (c instanceof TypedWidget) {
		TypedWidget tw = (TypedWidget)c;
		if (tw.getColName() != null) {
			setValue(tw, rs, map, tset);
		}
	}

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			setValueRecursive(child[i], rs, map, tset);
		}
	}
	
	// Take care of explicit invisible children
	if (c instanceof BindContainer) {
	    Component[] child = ((BindContainer)c).getBindComponents();
	    for (int i = 0; i < child.length; ++i) {
			setValueRecursive(child[i], rs, map, tset);
		}
	}
}

}
