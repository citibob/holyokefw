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

package citibob.text;

import citibob.types.KeyedModel;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class BoolSFormat
extends BaseSFormat
{

int limit;
String trueText = "true";
String falseText = "false";
//String nullText;

static TreeMap<String, Boolean> vals;
static {
	vals = new TreeMap();
	vals.put("false", Boolean.FALSE);
	vals.put("no", Boolean.FALSE);
	vals.put("f", Boolean.FALSE);
	vals.put("n", Boolean.FALSE);
	vals.put("true", Boolean.TRUE);
	vals.put("yes", Boolean.TRUE);
	vals.put("t", Boolean.TRUE);
	vals.put("y", Boolean.TRUE);
}

public BoolSFormat(String trueText, String falseText, String nullText)
{
	super(nullText);
	this.trueText = trueText;
	this.falseText = falseText;
}
public BoolSFormat(String nullText) { super(nullText); }
//public String getNullText() { return nullText; }
public BoolSFormat() { this(""); }

/** Not to be used */
public Object  stringToValue(String text)
{
	if (text == null || text.equals(nullText)) return null;
	return vals.get(text.toLowerCase());
}
public String  valueToString(Object value)
{
	if (value == null) return nullText;
	boolean b = ((Boolean)value).booleanValue();
	return (b ? trueText : falseText);
}
}
