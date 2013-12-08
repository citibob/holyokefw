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
 * FixedNavigator.java
 *
 * Created on December 2, 2007, 12:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard;

import java.util.*;

/**
 * Implements a simple line of states, as in a classic wizard.
 * @author citibob
 */
public class HashNavigator implements Wizard.Navigator
{
	Map<String,String> next;
	protected HashNavigator()
	{
		this.next = new HashMap();		
	}
	public HashNavigator(String[] nav)
	{
		this();
		for (int i=0; i<nav.length; i += 2) {
			next.put(nav[i], nav[i+1]);
		}
	}
	
	
	public String getNext(WizState stateRec) {
		String stateName = next.get(stateRec.getName());
		
		// If we have no opinion, ask the Wiz itself...
		if (stateName == null) stateName = stateRec.getNext();
		if ("<end>".equals(stateName)) stateName = null;
		return stateName;
	}
	
	/** Always backtrack to prevState */
	public String getBack(WizState stateRec) { return null; }
	
}
