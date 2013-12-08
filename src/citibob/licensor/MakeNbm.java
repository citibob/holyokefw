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
package citibob.licensor;

import beans2nbm.gen.*;
import citibob.gui.TabbedCompSet;
import citibob.reflect.ClassPathUtils;
import java.io.File;
import org.xnap.commons.gui.CloseableTabbedPane;

public class MakeNbm {
    
    /** Creates a new instance of Main */
    public MakeNbm() {
    }
    

static String[] getBeanNames(Class[] klasses)
{
	String[] ret = new String[klasses.length];
	for (int i=0; i<ret.length; ++i) {
		ret[i] = klasses[i].getName();
	}
	return ret;
}
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
	{
		char sep = File.separatorChar;
		Beans2Nbm.Params prm = new Beans2Nbm.Params();
		
		prm.version = "1.1.1";		// Must be numeric for NetBeans
//		prm.projectHome = "../.."; // Works for NetBeans running Maven project
//		prm.projectHome = "c:\\fiscrob\\mvn\\holyokefw";
		prm.projectHome = ClassPathUtils.getMavenProjectRoot().getPath();
		prm.jarFolder = "target";		// For Maven
		prm.jarName = "holyokefw-1.1.1";
		
		prm.description = "Typed Widgets and other advanced Swing components" +
			" for database applications";
		prm.homepage = "http://citibob.net";
		prm.codeName = "org.holyokefw";
		prm.author = "Robert Fischer";
		prm.docsJar = null;
		prm.sourceJar = null;
		prm.displayName = "Holyoke Framework";
		prm.license =
			Licensor.readLicenseFile(MakeNbm.class, "license-long.txt");
		prm.minJDK = "1.5";
		prm.beanNames = getBeanNames(new Class[] {
			citibob.swing.typed.JBoolCheckbox.class,
			citibob.swing.typed.JKeyedComboBox.class,
			citibob.swing.typed.JTypedDateChooser.class,
			citibob.swing.typed.JTypedDayChooser.class,			
//			citibob.swing.typed.JTypedEditableLabel.class,
//			citibob.swing.typed.JTypedEditableLabelDB.class,
			citibob.swing.typed.JTypedFileName.class,
			citibob.swing.typed.JTypedLabel.class,
			citibob.swing.typed.JTypedPanel.class,
			citibob.swing.typed.JTypedScrollPane.class,
//			citibob.swing.typed.JTypedSelectTable.class,
			citibob.swing.StyledTable.class,
			citibob.swing.typed.SingleSelectStyledTable.class,
			citibob.swing.typed.JTypedTextField.class,
			citibob.swing.typed.KeyedButtonGroup.class,
			citibob.swing.typed.JKeyedMulti.class,
			citibob.swing.typed.JKeyedMultiPanel.class,
			citibob.swing.CitibobJTable.class,
//			citibob.swing.ColorsJTypeTable.class,
//			citibob.swing.ColPermuteTable.class,
//			citibob.swing.JConsole.class,
			citibob.swing.JDialogWiz.class,
			citibob.swing.JTextAreaWriter.class,
//			citibob.swing.JTypeColTable.class,
//			citibob.swing.StyledTable.class,
			citibob.swing.JTypeTable.class,
			citibob.swing.LogPanel.class,
			citibob.gui.ConsoleFrame.class,
//			citibob.gui.DBPrefsDialog.class,
			citibob.gui.ThreadConsoleGui.class,
			CloseableTabbedPane.class,
			TabbedCompSet.class
		});
		Beans2Nbm.makeNbm(prm);
	}
}
