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
package citibob.swing.calendar;

import java.util.*;

public abstract class CalModelMVC
{
public static interface Listener {
    /**  Value has changed. */
    public void calChanged();


    /**  Nullness has changed. */
    public void nullChanged();


    /**  The "final" value has been changed. */
/*    finalCalChanged();*/

    /**  User clicked on a day selection button --- will cause the popup to disappear. */
    public void dayButtonSelected();
}
// ======================================================
public static class Adapter implements CalModelMVC.Listener {
    /**  Value has changed. */
    public void calChanged() {}


    /**  Nullness has changed. */
    public void nullChanged() {}


    /**  The "final" value has been changed. */
/*    finalCalChanged();*/

    /**  User clicked on a day selection button --- will cause the popup to disappear. */
    public void dayButtonSelected() {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(CalModelMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(CalModelMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireCalChanged()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		CalModelMVC.Listener l = (CalModelMVC.Listener)ii.next();
		l.calChanged();
	}
}
public void fireNullChanged()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		CalModelMVC.Listener l = (CalModelMVC.Listener)ii.next();
		l.nullChanged();
	}
}
public void fireDayButtonSelected()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		CalModelMVC.Listener l = (CalModelMVC.Listener)ii.next();
		l.dayButtonSelected();
	}
}
}
