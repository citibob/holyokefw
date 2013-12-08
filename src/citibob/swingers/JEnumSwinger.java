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
import citibob.text.KeyedSFormat;
import citibob.types.JEnum;
import javax.swing.text.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class JEnumSwinger extends AbstractSwinger
{

/** Creates a new instance of TypedWidgetSTFactory */
public JEnumSwinger(JEnum sqlType) {
	super(sqlType, new KeyedSFormat(sqlType.getKeyedModel()), false);
}

/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget createWidget()
{
	return new JKeyedComboBox();
}

public void configureWidget(TypedWidget tw)
{
	JEnum tt = (JEnum)jType;
	JKeyedComboBox w = (JKeyedComboBox)tw;
	w.setKeyedModel(tt);
}

}
