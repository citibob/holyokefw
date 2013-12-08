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
 * TypedHashMap.java
 *
 * Created on October 8, 2006, 10:38 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.wizard;

import java.util.*;

/**
 *
 * @author citibob
 */
public class TypedHashMap extends HashMap
{

public long getLong(Object key) { return ((Long)get(key)).longValue(); }
public int getInt(Object key) { return ((Integer)get(key)).intValue(); }
public Integer getInteger(Object key) { return (Integer)get(key); }
public String getString(Object key) { return ((String)get(key)); }
public boolean getBool(Object key) { return ((Boolean)get(key)).booleanValue(); }
/** Sees if there's a key associated with a null value... */
public boolean containsNull()
{
	for (Iterator ii=entrySet().iterator(); ii.hasNext(); ) {
		Map.Entry e = (Map.Entry)ii.next();
		if (e.getValue() == null) return true;
	}
	return false;
}

}
