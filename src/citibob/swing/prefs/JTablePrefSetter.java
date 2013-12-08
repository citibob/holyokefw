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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.prefs.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author citibob
 */
public class JTablePrefSetter extends BasePrefSetter {
public JTablePrefSetter(Map<String,String> baseVals)
	{ super(baseVals); }
/** Use prefix.xxx as name for our preferences. */
public void setPrefs(Component comp, final Preferences prefs, boolean reset)
{
	final JTable table = (JTable)comp;

	TableColumnModel cols = table.getColumnModel();
	for (int i = 0; i < cols.getColumnCount(); ++i) {
		TableColumn c = cols.getColumn(i);
		final String propName = "column[" + i + "]";
		int w = getInt(prefs, propName + ".width", c.getWidth());
		c.setPreferredWidth(w);
//		c.setWidth(w);

		if (reset) continue;

		c.addPropertyChangeListener(new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (!evt.getPropertyName().equals("width")) return;
			putInt(prefs, propName + ".width", (Integer)evt.getNewValue());
//			System.out.println(evt.getPropertyName() + "   " + evt.getNewValue());
		}});

	}


//	// Hack: save the preferences whenever mouse enters this component.	
//	// There is no easy "column width changed" listener.
//	final MouseListener myMouseListener = new MouseAdapter() {
//	public void mouseExited(MouseEvent e) {
//		TableColumnModel cols = table.getColumnModel();
//		for (int i = 0; i < cols.getColumnCount(); ++i) {
//			TableColumn c = cols.getColumn(i);
//			String propName = "column[" + i + "]";
//			putInt(prefs, propName + ".width", c.getWidth());
//System.out.println("Changed pref: " + prefs.name() + " % " + propName + " to " + c.getWidth());
//		}
//	}};
//JScrollPane sc;
//sc.add
//table.add
//	table.add
//	table.addMouseListener(



}


}

