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
 * SwingPrefSetter.java
 *
 * Created on July 17, 2005, 8:37 PM
 */

package citibob.swing.prefs;

import java.awt.*;
import java.util.prefs.*;

/**
 * Sets the preferences for an entire class of widgets: JTable, JSplitPane, etc.
@see SwingPrefs
 * @author citibob
 */
public interface SwingPrefSetter {
	/** Use prefix.xxx as name for our preferences.
	 @param reset true if we're re-setting the prefs here, in which we
	 will just re-read, but not set any listeners (since they're already
	 set for us). */
	void setPrefs(Component c, Preferences prefs, boolean reset);
}
