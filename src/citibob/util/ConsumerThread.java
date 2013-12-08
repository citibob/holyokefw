package citibob.util;

import citibob.task.ExpHandler;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author fiscrob
 */
public abstract class ConsumerThread<TT> extends Thread
{

protected ExpHandler expHandler;
BlockingQueue<TT> queue = new LinkedBlockingQueue();

public ConsumerThread(ExpHandler expHandler)
{
	this.expHandler = expHandler;
}

public void offer(TT obj)
	{ queue.offer(obj); }

public BlockingQueue<TT> getQueue()
{ return queue; }

public void run()
{
	try {
		for (;;) {
			processQueue();
			if (interrupted()) {
				if (!queue.isEmpty()) processQueue();
				break;
			}
		}
	} catch(InterruptedException e) {}
}

public abstract void processItems(LinkedList<TT> items) throws Exception;

void processQueue() throws InterruptedException
{
	TT item;
	final LinkedList<TT> cur = new LinkedList();
	
	// Get list of all available items we can process now
	item = queue.take();	// blocks, can throw InterruptedException
	cur.add(item);
	while ((item = queue.poll()) != null) cur.add(item);
//	queue.drainTo(cur);
	
	try {
		processItems(cur);
	} catch(Exception e) {
		expHandler.consume(e);
	}
}
}
