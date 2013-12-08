package citibob.task;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/** Queue that drops duplicate entries as they're being executed */
public class SwingDropDupQueue extends DropDupQueue
{
public SwingDropDupQueue(ExpHandler expHandler)
{
	super(expHandler);
}

protected void invokeAndWait(Runnable task)
throws InterruptedException, InvocationTargetException
{
System.out.println("======= invokeAndWait: " + task);
	SwingUtilities.invokeAndWait(task);
System.out.println("======= done with invokeAndWait: " + task);
}
	
}
