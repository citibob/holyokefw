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
 * KeyedFormatter.java
 *
 * Created on March 18, 2006, 4:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.text;

import citibob.types.KeyedModel;
import javax.swing.*;
import java.util.*;
import citibob.text.*;

/**
 * AbstractFormatter based on any kind of java.text.Format object
 * Mostly used for dates.
 * @author citibob
 */
public class SFormatAbsFormatter extends JFormattedTextField.AbstractFormatter
{

SFormat format;

// ---------------------------------------------
public SFormatAbsFormatter(SFormat format)
{
	this.format = format;
}
// ---------------------------------------------
public Object stringToValue(String text) throws java.text.ParseException
{
	return format.stringToValue(text);
}
public String  valueToString(Object value) throws java.text.ParseException
{
	return format.valueToString(value);
}
}
