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
 * NewRecordWizard.java
 *
 * Created on October 8, 2006, 10:41 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing;

//import citibob.swing.html.*;
import citibob.wizard.*;
import javax.swing.*;
import java.util.*;
import java.awt.Component;
import citibob.sql.*;
import java.util.prefs.*;
import citibob.app.*;
import citibob.jschema.SqlCol;
import citibob.swing.prefs.*;

/**
 * Assumes Wiz components of class SwingWiz (and JDialog)
 * @author citibob
 */
public class SwingWizard extends Wizard {


protected java.awt.Frame frame;
//protected App app;
java.util.prefs.Preferences wizardPref;		// Root node for this wizard
SwingPrefs swingPrefs; // = new SwingPrefs();	// Could change this if we like



/*   --------- Sample Wizard creation code
new State("", "", "") {
	public HtmlWiz newWiz()
		{ return new }
	public void process()
	{
		
	}
}
*/

public SwingWizard(String wizardName, App app, Component component)
{
	this(wizardName, app, (javax.swing.JFrame)WidgetTree.getRoot(component));
}

/** @param frame Parent frame for the modal dialogs created by the Wizard. */
public SwingWizard(String wizardName, App app, java.awt.Frame frame)
//	String wizardName, java.awt.Frame frame, String startState)
{
	super(wizardName, app);
//	this.wizardName = wizardName;
	this.frame = frame;
//	this.startState = startState;
//	states = new HashMap();
	wizardPref = app.guiRoot().node("wizard").node(wizardName);
	swingPrefs = app.swingPrefs();
//	this.app = app;
}

public Wiz createWiz(WizState state, Wizard.Context con) throws Exception
{
	// Overridden to post-process wiz after it's created
	final Wiz wiz = super.createWiz(state, con);
	final Preferences wizPref = wizardPref.node(state.getName());
	
	// Run later to make sure Swing stuff is initialized before we save prefs.
	
	con.str.execUpdate(new UpdTasklet() {
	public void run() {
		swingPrefs.setPrefs((Component)wiz, wizPref);
	}});
	return wiz;
}

protected boolean checkFieldsFilledIn()
{
	if (super.checkFieldsFilledIn()) return true;
	
	JOptionPane.showMessageDialog((JDialog)wiz,
		"You must fill in all the fields.");
	return false;
}


protected void runWiz(Wiz wiz) throws Exception
{
	java.awt.Component pane = (Component)wiz;
	
	wiz.setTitle(wizardName);
	pane.setVisible(true);
}

protected boolean reallyCancel() throws Exception
{
//	int ret = JOptionPane.showConfirmDialog(frame,
//		"Are you sure you wish to cancel the Wizard?",
//		"Really Cancel?", JOptionPane.YES_NO_OPTION);
//	return (ret == JOptionPane.YES_OPTION);
	return true;
}

protected String vsql(Object val, String col, citibob.jschema.SqlSchema schema)
{
	return ((SqlCol)schema.getCol(col)).toSql(val);
}
protected String vsql(String col, citibob.jschema.SqlSchema schema)
{
	return vsql(v.get(col), col, schema);
}
public boolean runWizard(String startState) throws Exception
{
	try {
		app.sqlRun().pushBatch();
		return super.runWizard(startState);
	} finally {
		app.sqlRun().popBatch();
	}
}
}
