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
 * JTrePrefSetter.java
 *
 * Created on July 17, 2005, 8:41 PM
 */

package citibob.swing.prefs;

import java.awt.*;
import java.util.prefs.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Map;

/**
 *
 * @author citibob
 */
public class JFramePrefSetter extends BasePrefSetter {
public JFramePrefSetter(Map<String,String> baseVals)
	{ super(baseVals); }
/** Use prefix.xxx as name for our preferences. */
public void setPrefs(Component c, final Preferences prefs, boolean reset)
{
	final JFrame cc = (JFrame)c;

	// Set our own parameters from the preferences
	Dimension sz = cc.getSize();
	sz.width = getInt(prefs, "size.width", sz.width);
	sz.height = getInt(prefs, "size.height", sz.height);
	cc.setSize(sz);
	
	// Center the window by default
	Point loc = new Point();
	Dimension scSize = Toolkit.getDefaultToolkit().getScreenSize();
	loc.x = (scSize.width - sz.width) / 2;
	loc.y = (scSize.height - sz.height) / 2;

	loc.x = getInt(prefs, "_location.x", loc.x);
	loc.y = getInt(prefs, "_location.y", loc.y);
	cc.setLocation(loc);
	
//System.out.println("JFrame: got size = " + sz);
   	if (reset) return;

    // Set up listener(s) to save preferences as our geometry changes.
	cc.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			Dimension sz = cc.getSize();
			putInt(prefs, "size.width", sz.width);
			putInt(prefs, "size.height", sz.height);
	//System.out.println("Setting size: " + prefix + " = " + sz);
		}
		public void componentMoved(ComponentEvent e) {
			Point point = cc.getLocation();
			putInt(prefs, "_location.x", point.x);
			putInt(prefs, "_location.y", point.y);
		}
	});
}

}

