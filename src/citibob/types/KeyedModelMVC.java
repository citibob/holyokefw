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
package citibob.types;

public abstract class KeyedModelMVC
{
public static interface Listener {
    public void keyedModelChanged();
}
// ======================================================
public static class Adapter implements KeyedModelMVC.Listener {
    public void keyedModelChanged() {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(KeyedModelMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(KeyedModelMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireKeyedModelChanged()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		KeyedModelMVC.Listener l = (KeyedModelMVC.Listener)ii.next();
		l.keyedModelChanged();
	}
}
}
