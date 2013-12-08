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
 * JNullChooser.java
 *
 * Created on March 19, 2006, 6:22 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.calendar;

import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public class JNullChooser extends JCheckBox
implements CalModel.Listener, ItemListener
{

/** Creates a new instance of JNullChooser */
public JNullChooser() {
	this.addItemListener(this);
	this.setText("null");
}

/** Make it work correctly inside a dropdown and a JTable. */
public boolean isFocusTraversable() { return false; }
// =====================================================
// Standard for all the JxxxChooser calendar sub-components
CalModel model;
public void setCalModel(CalModel m) {
	if (model != null) model.removeListener(this);
	model = m;
	model.addListener(this);
	nullChanged();
	setEnabled(model.isNullable());
}
public CalModel getCalModel() { return model; }
// =====================================================
// ===================================================================
// CalModel.Listener
/**  Value has changed. */
public void nullChanged()
{
	boolean nll = model.isNull();
	if (nll != this.isSelected()) this.setSelected(nll);
}
public void calChanged() {}
/**  The "final" value has been changed. */
public void dayButtonSelected() {}

// ===================================================================
public void itemStateChanged(ItemEvent e) {
	boolean nll = this.isSelected();
	if (nll != model.isNull()) model.setNull(nll);
	if (nll) model.fireDayButtonSelected();
}


}
