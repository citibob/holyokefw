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
import citibob.types.JType;
import citibob.types.JavaJType;
import javax.swing.text.*;
import java.text.*;
import citibob.swing.typed.*;
import citibob.sql.pgsql.*;
import static citibob.swing.typed.JTypedTextField.*;
import citibob.text.*;
import java.util.Comparator;

/**
 *
 * @author citibob
 */
public class BoolSwinger extends AbstractSwinger
//implements Comparator<Boolean>
{
static JType boolJType = new JavaJType(Boolean.class);

public BoolSwinger()
{
	super(boolJType, new BoolSFormat(), true);
}


/** Just create the widget, do not configure it. */
protected citibob.swing.typed.TypedWidget createWidget()
{
	return new JBoolCheckbox();
}

/** Override this.  Most swingers don't have to configure their widgets,
but Swingers for complex widget types do. */
public void configureWidget(TypedWidget tw) {}

//public Comparator getComparator() { return this; }

public int compare(Object xo1, Object xo2)
{
	Boolean o1 = (Boolean)xo1;
	Boolean o2 = (Boolean)xo2;
	if (o1.booleanValue()) {
		return o2.booleanValue() ? 0 : 1;
	} else {
		return o2.booleanValue() ? -1 : 0;
	}
}

}
