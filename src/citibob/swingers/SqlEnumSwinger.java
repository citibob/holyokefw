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

package citibob.swingers;

import citibob.sql.*;
import citibob.swing.typed.*;
import citibob.text.KeyedSFormat;
import citibob.types.JEnum;
import javax.swing.text.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class SqlEnumSwinger extends AbstractSwinger
{

/** Creates a new instance of TypedWidgetSTFactory */
public SqlEnumSwinger(SqlEnum sqlType) {
	super(sqlType, new KeyedSFormat(sqlType.getKeyedModel()), false);
}

/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget createWidget()
	{ return new JKeyedComboBox(); }
public void configureWidget(TypedWidget w)
{
	JEnum etype = (JEnum)jType;
	if (w instanceof JKeyedComboBox) {
		JKeyedComboBox ww = (JKeyedComboBox)w;
		ww.setKeyedModel(etype);
	} else if (w instanceof JTypedLabel) {
		JTypedLabel ww = (JTypedLabel)w;
		ww.setJType(etype, new KeyedSFormat(etype.getKeyedModel()));
	}
}

}
