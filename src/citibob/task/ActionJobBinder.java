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

package citibob.task;

import citibob.task.*;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;

/**
 *
 * @author citibob
 */
public class ActionJobBinder
{
JobMap map;
SwingJobRun runner;
Component component;			// Parent component


public ActionJobBinder(Component component, SwingJobRun runner, JobMap map)
{
	this.component = component;
	this.runner = runner;
	this.map = map;
}

/** Binds a Swing component to a previously registered runnable. */
public ActionListener newListener(final Job task)
{
//	final Task task = map.get(key);
	ActionListener listener = new ActionListener() {
	public void actionPerformed(java.awt.event.ActionEvent evt) {
		runner.run(component, task);
	}};
	return listener;
}

/** Convenience Function */
public void bind(AbstractButton button, String key)
{
	button.addActionListener(newListener(map.get(key)));
}

//public void bind(AbstractButton button, Task task)
//{
//	button.addActionListener(newListener(task));
//}


}
