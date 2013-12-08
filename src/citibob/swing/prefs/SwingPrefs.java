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
 * SwingPrefs.java
 *
 * Created on July 17, 2005, 8:35 PM
 */

package citibob.swing.prefs;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import citibob.exception.*;
import citibob.swing.StyledTable;
import java.util.prefs.*;
import java.util.*;

/**
 *
 * @author citibob
 */
public class SwingPrefs implements SwingPrefSetter {
	
HashMap settersType;		// type --> SwingPrefSetter
//HashMap objs;		// Individual objects --> SwingPrefSetter; however, we might need to use a HashMap that compares actual pointers.
HashMap nullCount;	// component type --> # we've seen; to generate names	

public SwingPrefs(Map<String,String> baseVals)
{
	if (baseVals == null) baseVals = new TreeMap();
	
	settersType = new HashMap();
	settersType.put(JFrame.class, new JFramePrefSetter(baseVals));
	settersType.put(JDialog.class, new JDialogPrefSetter(baseVals));
	settersType.put(StyledTable.class, new StyledTablePrefSetter(baseVals));
	settersType.put(JTable.class, new JTablePrefSetter(baseVals));
	settersType.put(JSplitPane.class, new JSplitPanePrefSetter(baseVals));
	settersType.put(JFileChooser.class, new JFileChooserPrefSetter(baseVals));
	settersType.put(JTabbedPane.class, new JTabbedPanePrefSetter(baseVals));
}

public void setPrefs(Component c, Preferences prefs)
	{ setPrefs(c, prefs, false); }
public void setPrefs(Component c, Preferences prefs, boolean reset)
{
	nullCount = new HashMap();
	setPrefsRecurse(c, prefs, reset);
}

public SwingPrefSetter getSetter(Class c)
{
	while (c != null) {
		SwingPrefSetter setter = (SwingPrefSetter)settersType.get(c);
		if (setter != null) return setter;
		c = c.getSuperclass();
	}
	return null;
}

/** Loads preferences for an entire widget tree.  Also sets listeners
 * so they will be saved as they change. */
private void setPrefsRecurse(Component c, Preferences prefs, boolean reset)
{
	SwingPrefSetter setter = getSetter(c.getClass());
	if (setter != null || c instanceof PrefWidget) {
		// Make up name for component
		Class klass = c.getClass();
//		String name = c.getName();		// Bad default names like frame1, frame2, etc. changed every time we created container
		String name = null;
		if (name == null || "".equals(name) || "null".equals(name)) {
			Integer Count = (Integer)nullCount.get(klass);
			int nCount;
			if (Count == null) nCount = 1; //Count = new Integer(1);
			else nCount = Count.intValue() + 1;
			nullCount.put(klass, new Integer(nCount));

			// Get class name
			String pkgName = klass.getPackage().getName();
			String className = klass.getName();
			//System.out.println(className.length());
			String leafName = className.substring(pkgName.length()+1);
			name = leafName + nCount;
		}

		prefs = prefs.node(name);

		// Take care of yourself
//System.out.println("Setting Pref (node = " + prefs.absolutePath() + ") for " + c);
		if (setter != null) setter.setPrefs(c, prefs, reset);
		else ((PrefWidget)c).setPrefs(prefs, reset);
	}

	// Take care of your children
	if (c instanceof Container) {
	    Component[] child = ((Container)c).getComponents();
	    for (int i = 0; i < child.length; ++i) {
			setPrefsRecurse(child[i], prefs, reset);
		}
	}
}
	
}
