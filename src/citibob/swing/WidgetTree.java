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
package citibob.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import citibob.exception.*;
import java.util.prefs.*;
import citibob.swing.prefs.*;
import citibob.task.ExpHandler;

/** Static utilities for dealing with an entire tree of widgets (widget tree / widget hierarchy). */
public class WidgetTree
{

public static Component getRoot(Component c)
{
	if (c == null) return null;
	
    // Get root parent
    Component root = c;
    while (root.getParent() != null)
		root = root.getParent();
	return root;
}

/** Assuming c is in a JFrame, gets it. */
public static JFrame getJFrame(Component c)
{
	Component root = getRoot(c);
	if (root instanceof JFrame) return (JFrame)root;
	return null;
//	return (JFrame)getRoot(c);
}
///** Sets the look of the mouse cursor for an entire window, given just the sub-element in that window. */
//public static void setCursor(Component c, int type)
//{
//    getRoot().setCursor(Cursor.getPredefinedCursor(type));
//}

public static void setEnabled(Component c, boolean enabled)
{
	// Take care of yourself
	c.setEnabled(enabled);
	setChildrenEnabled(c, enabled);
}

/** Recursively sets or clears the enabled property on a JComponent and all its children. */
public static void setChildrenEnabled(Component c, boolean enabled)
{
	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			setEnabled(child[i], enabled);
		}
	}
}

/** Sets the handler for all components implementing ThrowsException in a widget tree. */
public static void setExceptionHandler(Component c, ExpHandler handler)
{
	// Take care of yourself
	if (c instanceof ThrowsException)
		((ThrowsException)c).setExpHandler(handler);

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			setExceptionHandler(child[i], handler);
		}
	}
}

///** Recursively calls setPrefs() */
//public static void setPrefs(Component c, Preferences prefs)
//{
//	// Take care of yourself
//	if (c instanceof PrefWidget)
//		((PrefWidget)c).setPrefs(prefs);
//
//	// Take care of your children
//	if (c instanceof Container) {
//	    Component[] child = ((Container)c).getComponents();
//	    for (int i = 0; i < child.length; ++i) {
//			setPrefs(child[i], prefs);
//		}
//	}
//}

}
