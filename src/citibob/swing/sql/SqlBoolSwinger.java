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

package citibob.swing.sql;

import citibob.swingers.AbstractSwinger;
import citibob.swing.typed.*;
import citibob.sql.*;
import citibob.text.BoolSFormat;
import java.util.Arrays;
import javax.swing.text.*;
import java.text.*;
import citibob.sql.ansi.SqlBool;
import java.util.Comparator;

/**
 *
 * @author citibob
 */
public class SqlBoolSwinger extends AbstractSwinger
{

/** Creates a new instance of TypedWidgetSTFactory */
public SqlBoolSwinger(SqlBool sqlType) {
	super(sqlType, new BoolSFormat(), !sqlType.isInstance(null));
}

/** Create a widget suitable for editing this type of data. */
public citibob.swing.typed.TypedWidget createWidget()
{
	if (jType.isInstance(null)) return new JBoolButton();
	else return new JBoolCheckbox();
}

public void configureWidget(TypedWidget w)
{
	if (w instanceof SimpleTypedWidget) {
		((SimpleTypedWidget)w).setJType(this.getJType());
	} else if (w instanceof TextTypedWidget) {
		((TextTypedWidget)w).setJType(this.getJType(), getSFormat());
	}
}

}
