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
 * JDateType.java
 *
 * Created on March 15, 2006, 10:35 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.types;

import java.util.*;
import citibob.jschema.*;

/**
 * An SqlType for representing dates...
 * @author citibob
 */
public interface JDateType extends JType
{

/** Time Zone the data are stored in. */
public TimeZone getTimeZone();

/** Returns a truncated version of the input; i.e. if this is a SqlDate,
 *then truncates off hour, minute, second. */
public java.util.Date truncate(java.util.Date dt);


}
