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
 * SigFormat.java
 *
 */

package citibob.text;

import ucar.unidata.util.*;
import java.text.*;

/**
 * Used for formatting doubles to a specified number of significant digits.
 * Only use the format() method.  Most other methods of NumberFormat() don't work.
 */
public class SigFormat
{

int min_sigfig;
int width;
	
/** Creates a new instance of SigFormat */
public SigFormat(int min_sigfig, int width)
{
	this.min_sigfig = min_sigfig;
	this.width = width;
}

public SigFormat(int min_sigfig) { this(min_sigfig, -1); }

public String format(double x)
{
	return ucar.unidata.util.Format.d(x, min_sigfig, width);
}

}
