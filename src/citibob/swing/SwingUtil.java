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

/** Miscellaneous static utility functions for dealing with Swing. */
public class SwingUtil
{

/** Sets the look of the mouse cursor for an entire window, given just the sub-element in that window. */
public static void setCursor(Component c, int type)
{
	// Get root parent (for AWT and Swing)
	Component root = c;
	if (root != null) {
		while (root.getParent() != null) root = root.getParent();
		root.setCursor(Cursor.getPredefinedCursor(type));
	}
}

/** Recursively sets or clears the enabled property on a JComponent and all its children. */
public static void setEnabledRecursive(JComponent c, boolean enabled)
{
        c.setEnabled(enabled);
        Component[] child = c.getComponents();
        for (int i = 0; i < child.length; ++i) {
                if (child[i] instanceof JComponent)
                        setEnabledRecursive((JComponent)child[i], enabled);
        }
}

/** Resize the panel correctly, based on size of things in it.
We're assuming that sqlEdit has been added to the panel as well. */
public static void resizeJPanel(JPanel p)
{
		Dimension d = p.getLayout().preferredLayoutSize(p);
		p.setPreferredSize(d);
}

/** Repositions a frame to be centered on the screen */
public static void center(Dialog frame)
{
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screen = tk.getScreenSize();
	Dimension win = frame.getSize();
    //frame.setSize(screenWidth / 2, screenHeight / 2);
    frame.setLocation(
		(screen.width - win.width) / 2,
		(screen.height - win.height) / 2);
}
/** Repositions a frame to be centered on the screen */
public static void center(Frame frame)
{
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screen = tk.getScreenSize();
	Dimension win = frame.getSize();
    //frame.setSize(screenWidth / 2, screenHeight / 2);
    frame.setLocation(
		(screen.width - win.width) / 2,
		(screen.height - win.height) / 2);
}

}
