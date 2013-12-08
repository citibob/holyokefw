package citibob.task;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;

/** Queue that drops duplicate entries as they're being executed */
public abstract class DropDupQueue extends Thread
{
	
ExpHandler expHandler;
HashSet<Runnable> set = new HashSet();
LinkedList<Runnable> list = new LinkedList();

public DropDupQueue(ExpHandler expHandler)
{
	this.expHandler = expHandler;
}

protected abstract void invokeAndWait(Runnable task)
throws InterruptedException, InvocationTargetException;

public synchronized void offer(Runnable task)
{
	if (!set.contains(task)) {
//System.out.println("Adding GUI task: " + task);
		set.add(task);
		list.add(task);
		notify();
	} else {
//System.out.println("Dropping duplicate GUI task: " + task);
	}
}

public void run()
{
	try {
		for (;;) {
			Runnable task = null;
			
			// Get a task to run
			synchronized(this) {
				if (list.size() == 0) wait();

				// Remove next task from queue
				task = list.removeFirst();
				set.remove(task);
			}
			if (isInterrupted()) return;
			
			// Run the task!
			try {
				invokeAndWait(task);
			} catch(InvocationTargetException e) {
				expHandler.consume(e);
			}
			if (isInterrupted()) return;
		}
	} catch(InterruptedException e) {}
}
	
}
