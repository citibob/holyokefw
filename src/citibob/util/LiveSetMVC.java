package citibob.util;

public abstract class LiveSetMVC<TT extends LiveItem>
//implements LiveSetI<TT>
{
public static interface Listener<TT extends LiveItem> {
	public void itemAdded(TT item) ;

	public void itemRemoved(TT item) ;

	public void itemUpdated(TT item) ;
	public void beforeItemUpdated(TT item) ;

	/**  The set of items has changed; listener should re-read them all */
	public void itemsChanged() ;
}
// ======================================================
public static class Adapter<TT extends LiveItem>
implements LiveSetMVC.Listener<TT> {
	public void itemAdded(TT item) {}

	public void itemRemoved(TT item) {}

	public void itemUpdated(TT item) {}
	public void beforeItemUpdated(TT item) {}

	/**  The set of items has changed; listener should re-read them all */
	public void itemsChanged() {}
}
// ======================================================
protected java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(LiveSetMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(LiveSetMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireItemAdded(TT item) 
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		LiveSetMVC.Listener l = (LiveSetMVC.Listener)ii.next();
		l.itemAdded(item);
	}
}
public void fireItemRemoved(TT item) 
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		LiveSetMVC.Listener l = (LiveSetMVC.Listener)ii.next();
		l.itemRemoved(item);
	}
}
public void fireItemUpdated(TT item) 
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		LiveSetMVC.Listener l = (LiveSetMVC.Listener)ii.next();
		l.itemUpdated(item);
	}
}
public void fireBeforeItemUpdated(TT item) 
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		LiveSetMVC.Listener l = (LiveSetMVC.Listener)ii.next();
		l.beforeItemUpdated(item);
	}
}
public void fireItemsChanged() 
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		LiveSetMVC.Listener l = (LiveSetMVC.Listener)ii.next();
		l.itemsChanged();
	}
}
}
