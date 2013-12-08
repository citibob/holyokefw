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

import citibob.swing.text.NullNumberFormatter;
import citibob.types.JType;
import javax.swing.text.*;
import java.text.*;
import static citibob.swing.typed.JTypedTextField.*;
import citibob.text.*;

/**
 *
 * @author citibob
 */
public class NumberSwinger extends TypedTextSwinger
{

public NumberSwinger(JType jType, NumberFormat nf)
	{ super(jType, new FormatSFormat(nf, "", SFormat.RIGHT)); }
public NumberSwinger(JType jType, SFormat fmt)
	{ super(jType, fmt); }


public javax.swing.JFormattedTextField.AbstractFormatter newAbsFormatter()
{
	FormatSFormat sfmt = (FormatSFormat)getSFormat();
	NumberFormat nf = (NumberFormat)sfmt.getFormat();
	NumberFormatter nff = newNumberFormatter(nf);
	return nff;
}

/** Override as needed */
protected NumberFormatter newNumberFormatter(NumberFormat fmt)
{
	if (jType.isInstance(null)) {
		return new NullNumberFormatter(fmt, "");		
	} else {
		return new NumberFormatter(fmt);
	}
}


}
