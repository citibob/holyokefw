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

import citibob.swing.StyledTable;
import citibob.swing.table.SortedTableModel;
import citibob.swing.table.StyledTM;
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
public class StyledTablePrefSetter extends JTablePrefSetter {
public StyledTablePrefSetter(Map<String,String> baseVals)
	{ super(baseVals); }
/** Use prefix.xxx as name for our preferences. */
public void setPrefs(Component comp, final Preferences prefs, boolean reset)
{
	super.setPrefs(comp, prefs, reset);
	
	final StyledTable table = (StyledTable)comp;
	table.setSortString(getString(prefs, "sortSpec", null));
	
	table.addPropertyChangeListener("sortSpec", new PropertyChangeListener() {
	public void propertyChange(PropertyChangeEvent evt) {
		putString(prefs, "sortSpec", (String)evt.getNewValue());
	}});
}

}

