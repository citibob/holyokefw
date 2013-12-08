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
 * JPanelWiz.java
 *
 * Created on January 27, 2007, 9:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing;

/**
 *
 * @author citibob
 */
public abstract class JPanelWiz extends javax.swing.JPanel implements citibob.wizard.Wiz
{
	protected JPanelWizWrapper wrapper;
	protected String title;
	protected boolean cacheWiz = true;
	protected boolean cacheWizFwd;
	
	public String getTitle() { return title; }
	
	public JPanelWiz(String title) { this.title = title; }

	/** Called by the wrapper; not for users. */
	void setWrapper(JPanelWizWrapper wrapper) { this.wrapper = wrapper; }
	
	/** Override to take action when the "<< Back" button is pressed. */
	public void backPressed() {}
	public void nextPressed() {}
	public void cancelPressed() {}
	///** After the Wiz is done running, report its output into a Map. */
	//public void getAllValues(java.util.Map map);

	// ===========================================
	// Wiz
	public boolean getCacheWiz() { return cacheWiz; }
	public boolean getCacheWizFwd() { return cacheWizFwd; }
	public void setTitle(String title) { this.title = title; }
	
}
