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
import java.util.Map;
import java.util.prefs.*;

/**
 * Sets the preferences for an entire class of widgets: JTable, JSplitPane, etc.
@see SwingPrefs
 * @author citibob
 */
public abstract class BasePrefSetter implements SwingPrefSetter {

	Map<String,String> baseVals;		// Defaults from jar file
	
	public BasePrefSetter(Map<String,String> baseVals)
	{
		this.baseVals = baseVals;
	}
	
	protected int getInt(Preferences prefs, String key, int deflt)
	{
		String fullKey = prefs.absolutePath() + '/' + key;
		String sbase = baseVals.get(fullKey);
		if (sbase != null) deflt = Integer.parseInt(sbase);
		return prefs.getInt(key, deflt);
	}
	protected void putInt(Preferences prefs, String key, int val)
	{
		String fullKey = prefs.absolutePath() + '/' + key;
		String sbase = baseVals.get(fullKey);
		if (sbase != null) {
			String sval = ""+val;
			if (sval.equals(sbase)) {
				prefs.remove(key);
				return;
			}
		}
		prefs.putInt(key, val);
	}
	protected String getString(Preferences prefs, String key, String deflt)
	{
		String sbase = baseVals.get(prefs.absolutePath() + '/' + key);
		if (sbase != null) deflt = sbase;
		return prefs.get(key, deflt);
	}
	protected void putString(Preferences prefs, String key, String val)
	{
		String fullKey = prefs.absolutePath() + '/' + key;
		String sbase = baseVals.get(fullKey);
		if (sbase != null) {
			if (val.equals(sbase)) {
				prefs.remove(key);
				return;
			}
		}
		prefs.put(key, val);
	}
	
}
