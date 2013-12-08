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
package citibob.util;

import java.util.*;

/**
 * A set in which each item is indexed with an integer 0..size()-1.  The
 * index changes when the set changes.
 */
public abstract class IndexSet<TT> implements java.io.Serializable
{

TT[] sym;
Map<TT,Integer> tsym;

abstract protected Map<TT,Integer> newMap();

// -----------------------------------------------
protected IndexSet() { }
public IndexSet(TT... sym)
{
	init(sym);
}
public IndexSet(Collection<TT> asym)
{
	TT[] sym = (TT[])new Object[asym.size()];
	asym.toArray(sym);
	init(sym);
}
// -----------------------------------------------

protected void init(TT[] sym)
{
	this.sym = sym;
	tsym = newMap();
	for (int i=0; i<sym.length; ++i)
		tsym.put(sym[i], new Integer(i));
}

// -----------------------------------------------

public String toString() {
	StringBuffer buf = new StringBuffer();
	buf.append("IndexSet(");
	for (int i=0; i<sym.length; ++i) {
		buf.append(sym[i]);
		buf.append(' ');
	}
	buf.append(')');
	return buf.toString();
}


public TT get(int ix)
{
	if (ix<0 || ix>=sym.length) return null;
	return sym[ix];
}

	public TT[] getSym() {
		return sym;
	}

public TT[] get(int... ixx)
{
	TT[] out = (TT[])new Object[ixx.length];
	for (int i=0; i<out.length; ++i) out[i] = get(ixx[i]);
	return out;
}

public TT[] getSymbols() { return sym; }

public int toIx(TT s)
{
	Integer ii = tsym.get(s);
	return (ii == null ? -1 : ii.intValue());
}

/** Makes an index array of a sub-universe. */
public int[] toIx(TT... sym)
{
	int[] ret = new int[sym.length];
	for (int i=0; i<sym.length; ++i)
	{
		ret[i] = toIx(sym[i]);
	}
	return ret;
}
	
public int size()
{
	return sym.length;
}

// =========================================================
public static void main(String[] args)
{
	IndexSet<String> set = new IndexHashSet("A", "B", "C");
	System.out.println(set.toIx("B"));
	System.out.println(set.get(2));

}
}
