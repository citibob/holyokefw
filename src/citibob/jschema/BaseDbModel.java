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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

/**
 *
 * @author citibob
 */
public abstract class BaseDbModel implements DbModel
{
	
protected Object[] keys;
	
/** Sets all key fields at once */
public void setKeys(Object... xkeys)
{
	for (int i=0; i<keys.length; ++i) setKey(i, xkeys[i]);
}

/** Sets just the first key field (most common case) */
public void setKey(Object key)
	{ setKey(0,key); }

public void setKey(int ix, Object key)
	{ if (keys != null) keys[ix] = key; }

//public Object[] getKeys() { return keys; }
public Object getKey(int ix) { return keys[ix]; }
public Object getKey() { return getKey(0); }

}
