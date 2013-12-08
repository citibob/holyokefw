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
 * TypedWidgetSTFactory.java
 *
 * Created on March 18, 2006, 6:14 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swingers;

import citibob.sql.*;
import citibob.swing.typed.*;
import javax.swing.text.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class SqlNumericSwinger extends NumberSwinger
{

/** Creates a new instance of TypedWidgetSTFactory */
public SqlNumericSwinger(SqlNumeric sqlType) {
	super(sqlType, sqlType.newNumberFormat());
}

///** Create a widget suitable for editing this type of data. */
//protected citibob.swing.typed.TypedWidget createWidget()
//	{ return new JTypedTextField(); }

///** Creates an AbstractFormatterFactory for a JFormattedTextField.  If this
// SqlType is never to be edited with a JFormattedTextField, it can just
// return null.  NOTE: This should return a new instance of AbstractFormatterFactory
// because one instance is required per JFormattedTextField.  It's OK for the
// factory to just store instances of 4 AbstractFormatters and return them as needed. */
//public javax.swing.text.DefaultFormatterFactory newFormatterFactory()
//{
//	SqlNumeric tt = (SqlNumeric)jType;
//	NumberFormatter nff = new SqlNumericAbsFormatter(tt);
//	return newFormatterFactory(nff);
//}


// ================================================================
///**
// *
// * @author citibob
// */
//public static class SqlNumericAbsFormatter extends NumberFormatter
//implements SFormat
//{
//
//public SqlNumericAbsFormatter() { super(); }
//
//public SqlNumericAbsFormatter(NumberFormat nf)
//	{ super(nf); }
//
//public SqlNumericAbsFormatter(citibob.sql.SqlNumeric tt)
//	{ setFormat(tt.newNumberFormat()); }
//
///** Convert any valid numeric entry to the correct format. */
//public Object  stringToValue(String text)
//throws java.text.ParseException
//{
//	Object o;
//	try {
//		Double d = new Double(Double.parseDouble(text));
//		o = super.stringToValue(valueToString(d));
//	} catch(Exception e) {}
//	o = super.stringToValue(text);
//	return o;
//}
//
//}
// ==============================================================


}
