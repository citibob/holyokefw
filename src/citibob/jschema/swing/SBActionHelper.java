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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema.swing;

import citibob.app.App;
import citibob.jschema.SchemaBufDbModel;
import citibob.task.ETask;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author citibob
 */
public class SBActionHelper {

App fapp;
SchemaBufDbModel dm;
Component component;		// Enclosing AWT/Swing Component (for mouse changing)
JTable table;				// Table being used to show the model

/** Adds a blank row by default.  Override to get more complex behavior. */
public void addRow()
{
	// Default: blank row.  
	dm.getSchemaBuf().insertRow(-1);
}
// =============================================================
public void addAction()
{
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		addRow();
	}});
}

public void delAction()
{
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().deleteRow(table.getSelectedRow());
		table.requestFocus();
	}});
}                                          
public void undelAction()
{                                                 
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().undeleteRow(table.getSelectedRow());
		table.requestFocus();		// Enable easy cursor key movement
	}});
}                                            

public void delAllAction()
{                                                  
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().deleteAllRows();
	}});
}                                             

public void undelAllAction()
{                                                     
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().undeleteAllRows();
	}});
}                                                




	
}
