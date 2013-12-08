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

/**
 *
 * @author citibob
 */
public class JDoubleSwinger extends NumberSwinger
{

public JDoubleSwinger(boolean nullable, NumberFormat nf)
	{super(new JavaJType(Double.class, nullable), nf); }
public JDoubleSwinger(boolean nullable, String fmtString)
	{ this(nullable, new DecimalFormat(fmtString)); }
public JDoubleSwinger()
{
	this(true, "#.0000");
}

protected NumberFormatter newNumberFormatter(NumberFormat fmt) {
	NumberFormatter nff = super.newNumberFormatter(fmt);
	nff.setValueClass(Double.class);
	return nff;
}
//return new NumberFormatter(fmt) {
//public Object stringToValue(String text) throws java.text.ParseException {
//	Number n = (Number)super.stringToValue(text);
//	return new Double(n.doubleValue());
//}};}

}
