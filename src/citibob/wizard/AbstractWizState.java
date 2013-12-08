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
 * AbstractWizState.java
 *
 * Created on December 1, 2007, 11:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard;

/**
 *
 * @author citibob
 */
public abstract class AbstractWizState implements WizState
{
	public String name;		// Name of this Wiz screen.
	public String back;		// Wiz normally traversed to on back button
	public String next;
	
	public AbstractWizState(String name, String back, String next) {
		this.name = name;
		this.back = back;
		this.next = next;
	}
	public String getName() { return name; }
	public String getBack() { return back; }
	public String getNext() { return next; }
	
	public AbstractWizState(String name) {
		this(name, null, null);
	}
	/** Runs before the Wiz, even if cached Wiz is being re-used. */
	public void pre(Wizard.Context con) throws Exception {}

}
