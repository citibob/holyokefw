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
 * CSVReportOutput.java
 *
 * Created on February 14, 2007, 11:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.table.*;
import java.sql.*;
import citibob.text.*;
import citibob.sql.*;
import citibob.types.KeyedModel;
import java.io.*;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.table.*;

/**
 * Wraps a TableModel, converting everything to String.
 WARNING: Does not bother to pass up events from the TableModel it wraps.
 * This could easily be added.
 * @author citibob
 */
public class StringTableModel extends ColPermuteTableModel<String> {

String nullValue = null;			// Convert null values to this!
SFormat[] formatters;		// Formatter for each column
//JTypeTableModel mod;
//int[] colMap;					// The columns we want to do

/** This will create a "default" colMap, including only the VISIBLE
 * columns in the underlying TableModel. The array of formatters
 * must be of the same length.
 * @param mod
 * @param formatters
 */
public StringTableModel(JTypeTableModel mod, SFormatMap smap)
{
	init(mod, null, null, false, smap);
}
public StringTableModel(JTypeTableModel mod, String[] sColMap,
boolean[] editable, boolean forwardEvents, SFormatMap smap)
{
	init(mod, sColMap, editable, forwardEvents, smap);
}

public StringTableModel(JTypeTableModel mod, SFormatMap smap, String[] scol, SFormat[] sfmt)
{
	init(mod, scol, null, false, smap);
	
	if (sfmt == null) return;
	for (int col=0; col<formatters.length; ++col) {
		if (sfmt[col] != null) formatters[col] = sfmt[col];
	}
}
public StringTableModel(JTypeTableModel mod, SFormat[] sfmt)
{
	super.init(mod, null, null, false);
	this.formatters = sfmt;
}

public void setNullValue(String nullValue)
{ this.nullValue = nullValue; }

protected void init(JTypeTableModel mod, String[] sColMap, boolean[] editable,
boolean forwardEvents, SFormatMap smap)
{
	super.init(mod, null, sColMap, forwardEvents);
	
	if (smap == null) return;
	formatters = new SFormat[getColumnCount()];
	for (int col=0; col<formatters.length; ++col) {
		int colU = getColU(col);
		SFormat fmt = null;
		if (colU >= 0) fmt = smap.newSFormat(
			mod.getJType(0,colU), mod.getColumnName(colU));
		if (fmt == null) fmt = NullSFormat.instance;
		formatters[col] = fmt;
	}
}

// -----------------------------------------------------------------------
public void setFormat(int col, Object obj)
{
	if (obj instanceof KeyedModel)
		setFormat(col, (KeyedModel)obj);
	else if (obj instanceof SFormat)
		setFormat(col, (SFormat)obj);
	else if (obj instanceof Format)
		setFormat(col, (Format)obj);
	else if (obj instanceof String)
		setFormat(col, (String)obj);
	else throw new IllegalArgumentException("Illegal type for format specification: " + obj.getClass());
}
public void setFormat(int colNo, SFormat sformat)
{
	formatters[colNo] = sformat;
}
public void setFormat(int col, KeyedModel kmodel)
{
	if (col < 0) return;
	setFormat(col, FormatUtils.toSFormat(kmodel));
}

/** Sets a text-based renderer and editor pair at once, for a column,
without going through Swingers or anything.  Works for simpler text-based
renderers and editors ONLY. */
public void setFormat(int colNo, java.text.Format fmt, int horizAlign)
{

	setFormat(colNo, FormatUtils.toSFormat(fmt, horizAlign));
}
public void setFormat(int colNo, java.text.Format fmt)
{
	setFormat(colNo, FormatUtils.toSFormat(fmt));
}

/** Sets up a renderer and editor based on a format string.  Works for a small
number of well-known types, this is NOT general. */
public void setFormat(int colNo, String fmtString)
{
	Class klass = modelU.getColumnClass(colMap[colNo]);
	setFormat(colNo, FormatUtils.toSFormat(klass, fmtString));
}
// -----------------------------------------------------------------------
// -----------------------------------------------------------
public void setFormatU(String colNameU, Object obj)
{
	if (obj instanceof KeyedModel)
		setFormatU(colNameU, (KeyedModel)obj);
	else if (obj instanceof SFormat)
		setFormatU(colNameU, (SFormat)obj);
	else if (obj instanceof Format)
		setFormatU(colNameU, (Format)obj);
	else if (obj instanceof String)
		setFormatU(colNameU, (String)obj);
	else throw new IllegalArgumentException("Illegal type for format specification: " + obj.getClass());
}

// Allow setting of the RenderEdit by column name in the underlying table
public void setFormatU(String underlyingName, KeyedModel kmodel)
	{ setFormat(findColumnU(underlyingName), kmodel); }

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setFormatU(String underlyingName, SFormat sfmt)
	{ setFormat(findColumnU(underlyingName), sfmt); }

public void setFormatU(String underlyingName, java.text.Format fmt, int horizAlign)
	{ setFormat(findColumnU(underlyingName), fmt, horizAlign); }

public void setFormatU(String underlyingName, java.text.Format fmt)
	{ setFormat(findColumnU(underlyingName), fmt); }

public void setFormatU(String underlyingName, String fmtString)
	{ setFormat(findColumnU(underlyingName), fmtString); }
// -----------------------------------------------------------------------

//public int getRowCount() { return modelU.getRowCount(); }
//public int getColumnCount() { return colMap.length; }
//public String getColumnName(int column) { return modelU.getColumnName(colMap[column]); }
public String getValueAt(int row, int col) {
	try {
//		int colU = colMap[col];
//		Object val = modelU.getValueAt(row,colU);
		Object val = super.getValueAt(row, col);
		SFormat fmt = formatters[col];
		String ret = fmt.valueToString(val);
		if (ret == null) ret = nullValue;
		return ret;
	} catch(Exception e) {
		e.printStackTrace();
		return e.toString();
	}
}

/** Replaces the usual setValueAt(). */
public void setStringValueAt(String stringVal, int row, int col)
throws ParseException
{
	Object val;

	int mcol = colMap[col];
	val = formatters[mcol].stringToValue(stringVal);
	modelU.setValueAt(val, row, mcol);
}
public Class getColumnClass(int columnIndex) { return String.class; }
}
