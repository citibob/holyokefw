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
import java.io.*;
import java.util.Map;

/**
 *
 * @author citibob
 */
public class JFileChooserPrefSetter extends BasePrefSetter {
public JFileChooserPrefSetter(Map<String,String> baseVals)
	{ super(baseVals); }

/** Use prefix.xxx as name for our preferences. */
public void setPrefs(Component comp, final Preferences prefs, boolean reset)
{
	final JFileChooser chooser = (JFileChooser)comp;
//	String curDir = prefs.get("currentDirectory", null);
	String curDir = getString(prefs, "currentDirectory", null);
	if (curDir != null) chooser.setCurrentDirectory(new File(curDir));

	if (reset) return;

	// Hack: save the preferences whenever mouse enters this component.	
	// There is no easy "column width changed" listener.
	chooser.addMouseListener(new MouseAdapter() {
	public void mouseExited(MouseEvent e) {
		File fCurDir = chooser.getCurrentDirectory();
		putString(prefs, "currentDirectory", fCurDir.toString());
	}});
}

}

