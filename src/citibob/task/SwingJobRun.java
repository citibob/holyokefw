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
 * ActionRunner.java
 *
 * Created on January 29, 2006, 7:49 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.task;

/**
 *
 * @author citibob
 */
public abstract class SwingJobRun extends JobRun
{

/** This call must be reentrant.  In other words, actionRunner.doRun() can be called recursively.  The recursive
 call to doRun() must execute and finish BEFORE the outer call.  It is incorrect for doRun() to simply put
 the runnable on a queue without checking first, as this would cause deadlock. */
public abstract void run(java.awt.Component component, CBTask r);

public void run(CBTask r) { run(null, r); }

public boolean run(java.awt.Component component, Job task)
{
	if (checkPermissions(task.getPermissions())) {
		run(component, task.getCBRunnable());
		return true;
	} else {
		return false;
	}
}
}
