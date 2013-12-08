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
public class KeyedSFormat
extends BaseSFormat
{

KeyedModel kmodel;
//HashMap<String,Object> imap;		// Inverse map from what kmodel has
//String nullText;

void setKmodel(KeyedModel kmodel)
{
	this.kmodel = kmodel;
////	Set setx;// = kmodel.getItemMap().entrySet();
//	for (Iterator ii = kmodel.getItemMap().entrySet().iterator(); ii.hasNext(); ) {
//		Map.Entry e = (Map.Entry)ii.next();
//		imap.put((String)e.getValue(), e.getKey());
//		System.out.println("hoi");
//	}
}

/** Creates a new instance of KeyedFormatter */
public KeyedSFormat(KeyedModel kmodel, String nullText) {
	super(nullText);
	setKmodel(kmodel);
}
public KeyedSFormat(KeyedModel kmodel) { this(kmodel, ""); }

//public String getNullText() { return nullText; }

/** Not to be used */
public Object  stringToValue(String text)
{
	if (text == null) return null;
	if (nullText.equals(text)) return null;
	Object val = kmodel.getInv(text).key;
	return val;
}
public String  valueToString(Object value)
{
	String s = kmodel.toString(value);
	if (s == null) {	// Not found in kmodel
		if (value == null) return nullText;
		return "x" + value.toString();
	}
	return s;
//	
//	if (value == null) {
//		
//	if (s == null && value == null) return nullText;
//		
//		String ret = kmodel.toString(null);
//		if (ret != null) return ret;
//		return nullText;
//	}
//	if (s != null) return s;
//	return "x" + value.toString();
	
}
}
