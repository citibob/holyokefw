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
package citibob.task;

import citibob.app.App;
import java.util.*;

public class JobQueue extends JobQueueMVC
{

Thread qThread;
boolean singleTask = false;		// ONly do 1 task at a time
LinkedList tasks = new LinkedList();
Thread curTask;
ExpHandler ehandler;
public List getQueue() { return tasks; }
RawRun raw;

/** @param ehander Handles only minor TaskQueue errors.  Exceptions encountered
 *while running tasks are passed to the TaskQueue.Listener. */
public JobQueue(RawRun raw, ExpHandler ehandler)
{
	super();
	this.raw = raw;
	this.ehandler = ehandler;
}
public JobQueue(App app, ExpHandler ehandler)
{
	this(new DbRawRun(app), ehandler);
}

public void setSingleTask(boolean b)
{
	this.singleTask = b;
}
// -------------------------------------------------------
synchronized public boolean run(Job r)
{
//System.out.println("Adding task: " + r + "(" + r.getCBRunnable());
	if (super.checkPermissions(r.getPermissions())) {
		tasks.addLast(r);
		fireTaskAdded(r);
		this.notify();
		return true;
	} else return false;
}
/** Convenience function */
public void doRun(String name, CBTask r)
{
	Job task = new Job(r);
	run(task);
}
public void run(CBTask r)
{
	doRun(null, r);
}
// -------------------------------------------------------

///** Stop the currently running task. */
//public void stop()
//{
//	this.interrupt();
//}

public void start()
{
	qThread = new Thread(this);
	qThread.start();
}
public void clear()
{
	tasks.clear();
	qThread.interrupt();
	fireQueueCleared();
}

public void run() {
	for (;;) {
		Job r = null;

		// Remove element from the queue
		try {
			synchronized(this) {
				this.wait();
			}
		} catch(InterruptedException e) {
			System.out.println("EventQueue exiting...");
			return;		// Exit the thread if we're told to...
		}
		for (;;) {
			try {
				synchronized(this) {
					r = (Job)tasks.removeFirst();
				}
//System.out.println("Removed task: " + r);
				fireTaskRemoved(r);
			} catch(NoSuchElementException e) {
				// Queue is empty; thread can go back to sleep.
				break;
			}
			if (r == null) break;
			// Do whatever we're supposed to do...
			try {
				fireTaskStarting(r);
//System.out.println("Running task: " + r + "(" + r.getCBRunnable() + ")");
				Throwable e = raw.doRun(r.getCBRunnable());
//System.out.println("exp = " + e);
				if (e != null) ehandler.consume(e);
			} catch(Throwable e2) {
				// Shouldn't be much here, but just in case...
				ehandler.consume(e2);
			}

			// Make sure app is notified that task finished.
			try {
				fireTaskFinished(r, null);
			} catch(Throwable e2) {
				// Shouldn't be much here, but just in case...
				ehandler.consume(e2);					
			}
			if (singleTask) tasks.clear();		// Only 1 task at a time...
		}
	}
}
	
}
