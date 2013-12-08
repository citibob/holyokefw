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

import java.util.*;
import java.io.*;

public abstract class JobQueueMVC extends JobRun implements Runnable
{
public static interface Listener {
    /**  Task added to queue. */
    public void taskAdded(Job t);


    /**  Task removed from the queue */
    public void taskRemoved(Job t);


    /**  Task being executed */
    public void taskStarting(Job t);


    /**  Task finished running --- exception (if any) is passed along */
    public void taskFinished(Job t, Throwable e);


    /**  Queue cleared (usually, the running task will be finished here as well.) */
    public void queueCleared();
}
// ======================================================
public static class Adapter implements JobQueueMVC.Listener {
    /**  Task added to queue. */
    public void taskAdded(Job t) {}


    /**  Task removed from the queue */
    public void taskRemoved(Job t) {}


    /**  Task being executed */
    public void taskStarting(Job t) {}


    /**  Task finished running --- exception (if any) is passed along */
    public void taskFinished(Job t, Throwable e) {}


    /**  Queue cleared (usually, the running task will be finished here as well.) */
    public void queueCleared() {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(JobQueueMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(JobQueueMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireTaskAdded(Job t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		JobQueueMVC.Listener l = (JobQueueMVC.Listener)ii.next();
		l.taskAdded(t);
	}
}
public void fireTaskRemoved(Job t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		JobQueueMVC.Listener l = (JobQueueMVC.Listener)ii.next();
		l.taskRemoved(t);
	}
}
public void fireTaskStarting(Job t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		JobQueueMVC.Listener l = (JobQueueMVC.Listener)ii.next();
		l.taskStarting(t);
	}
}
public void fireTaskFinished(Job t, Throwable e)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		JobQueueMVC.Listener l = (JobQueueMVC.Listener)ii.next();
		l.taskFinished(t, e);
	}
}
public void fireQueueCleared()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		JobQueueMVC.Listener l = (JobQueueMVC.Listener)ii.next();
		l.queueCleared();
	}
}
}
