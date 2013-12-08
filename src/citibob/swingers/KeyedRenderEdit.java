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
 * TypedWidgetRenderEdit.java
 *
 * Created on November 9, 2007, 1:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swingers;

import citibob.swing.typed.*;
import citibob.swing.typed.Swinger;
import citibob.types.JType;
import java.text.*;
import javax.swing.text.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import citibob.text.*;
import citibob.types.*;

/**
 *
 * @author citibob
 */
public class KeyedRenderEdit extends DefaultRenderEdit
{
	public KeyedRenderEdit(KeyedModel kmodel)
	{
		editor = new TypedWidgetEditor(new JKeyedComboBox(kmodel));
		rendererEditable = rendererNotEditable =
			new SFormatRenderer(new KeyedSFormat(kmodel));
	}
}
