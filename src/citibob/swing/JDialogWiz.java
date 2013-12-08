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
 * JDialogWiz.java
 *
 * Created on January 27, 2007, 6:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing;

import citibob.wizard.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public abstract class JDialogWiz extends JDialog implements SwingWiz
{
	
public JDialogWiz(Frame owner, String title, boolean modal)
{
	super(owner, title, modal);
}

/** Should this Wiz screen be cached when "Back" is pressed? */
public boolean getCacheWiz() { return true; }

}
