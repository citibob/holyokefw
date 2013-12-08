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
 * GuiUtil.java
 *
 * Created on June 11, 2007, 11:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.gui;

import javax.swing.*;
import java.awt.*;
import citibob.app.*;
import citibob.swing.*;
import java.io.File;
import java.util.prefs.Preferences;

/**
 *
 * @author citibob
 */
public class GuiUtil
{

/** @param dialogName Name to use in Swing Preferences.  If null, take name from jPanel.getName() */
public static void showJPanel(Component c, JPanel panel, App app, String title, String dialogName, boolean modal)
{
	if (dialogName == null) dialogName = panel.getName();
//	JFrame frame = new JFrame();
	JFrame root = (javax.swing.JFrame)WidgetTree.getRoot(c);
	JDialog frame = new JDialog(root, modal);
	frame.setTitle(title);
	frame.setSize(500,300);
	frame.getContentPane().add(panel);
	if (dialogName != null) app.setUserPrefs(frame, dialogName);
	frame.setVisible(true);
}

/** @param suffix with the dot. */
public static JFileChooser newChooser(final String suffix)
{
	JFileChooser chooser = new JFileChooser();
	chooser.addChoosableFileFilter(
		new javax.swing.filechooser.FileFilter() {
		public boolean accept(File file) {
			if (file.isDirectory()) return true;
			String filename = file.getName();
			return filename.endsWith(suffix);
		}
		public String getDescription() {
			return "*" + suffix;
		}
	});
	return chooser;
}


/** @param prefs Preferences node in which current directory is stored. */
public static File chooseSaveFileCheckOverwrite(Component parent,
JFileChooser chooser, final String suffix,
Preferences prefs, String prefsKey)
{
	String path = null;
	String fname = null;
	if (prefs != null) {
		String sdir = prefs.get(prefsKey, null);
		if (sdir != null) chooser.setCurrentDirectory(new File(sdir));
	}
	for (;;) {
		chooser.showSaveDialog(parent);

		path = chooser.getCurrentDirectory().getAbsolutePath();
		prefs.put(prefsKey, path);
		if (chooser.getSelectedFile() == null) return null;
		fname = chooser.getSelectedFile().getPath();
		if (!fname.endsWith(suffix)) fname = fname + suffix;
		File f = new File(fname);
		if (!f.exists()) return f;
		if (JOptionPane.showConfirmDialog(parent,
			"The file " + f.getName() + " already exists.\nWould you like to ovewrite it?",
			"Overwrite File?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) break;
	}
	return null;
//	return chooser.getSelectedFile();
}



}
