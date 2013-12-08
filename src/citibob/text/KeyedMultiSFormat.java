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
import java.util.*;
import javax.swing.*;

/**
 *
 * @author citibob
 */
public class KeyedMultiSFormat
extends BaseSFormat
{

KeyedModel kmodel;
//String nullText;

void setKmodel(KeyedModel kmodel)
{
	this.kmodel = kmodel;
}

/** Creates a new instance of KeyedFormatter */
public KeyedMultiSFormat(KeyedModel kmodel, String nullText) {
	super(nullText);
	setKmodel(kmodel);
}
public KeyedMultiSFormat(KeyedModel kmodel) { this(kmodel, ""); }

//public String getNullText() { return nullText; }

final String separator = ",";
final String subNullText = "null";

public Object stringToValue(String text)
{
	if (text == null) return null;
	if (nullText.equals(text)) return null;

	String[] ll = text.split(separator);
	List val = new LinkedList();
	for (String sitem : ll) {
		Object ival;
		if (subNullText.equals(sitem)) ival = null;
		else ival = kmodel.getInv(sitem).key;
		val.add(ival);
	}
	return val;
}

private String ivalToString(Object ival)
{
	String s = kmodel.toString(ival);
	if (s != null) {		// Found in kmodel
		return s;
	} else {	// Not found in kmodel
		if (ival == null) return(subNullText);
		return("x" + ival.toString());
	}
	
}

public String  valueToString(Object value)
{
	if (value == null) return nullText;
	
	if (value instanceof List) {
		List val = (List)value;
		if (val.size() == 0) return nullText;

		StringBuffer sb = new StringBuffer();
		Iterator ii = val.iterator();
		for (;;) {
			sb.append(ivalToString(ii.next()));
//			Object ival = ii.next();
//			String s = kmodel.toString(ival);
//			if (s != null) {		// Found in kmodel
//				sb.append(s);
//			} else {	// Not found in kmodel
//				if (ival == null) sb.append(subNullText);
//				sb.append("x" + value.toString());
//			}
			if (!ii.hasNext()) break;
			sb.append(separator);

		}
		return sb.toString();
	} else {
		return ivalToString(value);
	}
	
}
}
