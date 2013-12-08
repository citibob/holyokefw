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
 * ArrayListUtil.java
 *
 * Created on September 14, 2006, 10:36 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.util;

import java.util.*;

/**
 *
 * @author citibob
 */
public class ArrayListUtil {

public static void shift(ArrayList al, int ix, int n, int shift)
{
//	int oldsize = al.size();
//	int lastix = ix + n + shift;	// last index (+1) of last item being shifted
	if (shift > 0) {
//		while (al.size() < lastix) al.add(null);	// potentially add...
		for (int i=n-1; i>=0; --i) al.set(ix+i+shift, al.get(ix+i));
	} else {
		for (int i=0; i<n; ++i) al.set(ix+i+shift, al.get(ix+i));
	}
}

public static void shift(ArrayList al, int ix, int shift)
{
	int n = al.size() - ix;
	if (shift > 0) n -= shift;
	shift(al, ix, n, shift);
}

/** Adds or deletes items to attain a particular size. */
public static void setSize(ArrayList al, int n)
{
	while (al.size() < n) al.add(null);
	while (al.size() > n) al.remove(al.size() - 1);
}



//public static void insertBefore(ArrayList al, int ix, Object o)
//{
//	int size = al.size();
//	if (ix >= size) {
//		al.add(o);	
//	} else {
//		al.add(al.get(size - 1));
//		for (int i=size-1; i>ix; ++i) al.set(i-1, al.get(i));
//		al.set(ix, o);
//	}
//}
//
//public static void delete(ArrayList al, int ix)
//{
//	int size = al.size();
//	if (ix >= size-1) {
//		al.
//}
//	int 
//	for (int i=ix+1)
//}

}
