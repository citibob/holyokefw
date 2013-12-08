package citibob.swing.table;

import citibob.util.*;
import citibob.swing.JTypeColTable;
import citibob.task.ExpHandler;
import citibob.types.JType;
import citibob.util.ConsumerThread;
import citibob.util.LiveSet;
import citibob.util.LiveSetEvent;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/** A TableModel based on a single LiveSet */
public abstract class LiveSetTableModel<ItemType extends LiveItem>
extends AbstractJTypeTableModel
implements SortableTableModel, LiveSet.Listener, Comparator<ItemType>
{

/** Used as an add-on to the standard TableModelEvent.  This variable
 * is set when an event is fired.  Since events are only fired from
 * the Swing thread, its value won't change by accident.
 */
protected boolean evtSetChanged;
public boolean getSetChanged() { return evtSetChanged; }

protected LiveSet<ItemType> liveSet;		// The liveSet we're listening to
ExpHandler expHandler;
//ConsumerThread<LiveSetEvent> queue;
MyConsumerThread queue;
Timer timer;
long minRefreshMS = 0;		// Minimum time between row refreshes

static final int RS_INITIAL = 0;
static final int RS_TIMERSET = 1;
static final int RS_TIMERSETANDTICK = 2;
static final int RS_RESETTING = 3;
static class ItemState<ItemType extends LiveItem> {
	ItemType item;
	int state;
	public ItemState(ItemType item) {
		this.item = item;
	}
}
//TrackerCols<ItemType> cols;
ArrayList<ItemType> items = new ArrayList();	// The data, sorted...
ArrayList<ItemState<ItemType>> itemStates = new ArrayList();
TreeMap<ItemType,Integer> iitems = new TreeMap();		// Gives the item index of each item
boolean sorted;				// True if the table data is CURRENTLY sorted...

// Per-column information
//protected Comparator[] comparators;		// How to sort on each column
//protected DataGrid<Comparator> comparators;
protected SortSpec spec;

LinkedList<TableModelListener> listeners = new LinkedList();

public void setMinRefreshMS(long minRefreshMS) {
	this.minRefreshMS = minRefreshMS;
}


public LiveSet getLiveSet() { return liveSet; }

public Comparator getComparator(int col) { return DefaultComparator.instance; }

/** @param colSpecs: (name, jType) ordered pairs */
public LiveSetTableModel(ExpHandler expHandler,
String[] colNames, JType[] jTypes)
{
	this.expHandler = expHandler;
	this.colNames = colNames;
	this.jTypes = jTypes;
	this.spec = new SortSpec(getColumnCount());
}
/** Sets the livSet this TableModel listens to, and also
 * adds itself as a listener.  Do NOT use this with Plapyen!
 * @param xliveSet
 */
public void setLiveSet(LiveSet xliveSet)
{
	if (liveSet != null) {
		liveSet.removeListener(this);
	}
	setLiveSetNoListen(xliveSet);
//	this.liveSet = xliveSet;
	liveSet.addListener(this);
}
/** User should not need this. */
public void setLiveSetNoListen(LiveSet xliveSet)
{
	this.liveSet = xliveSet;
}
// ===========================================================
// TrackerCols<ItemType>
String[] colNames;
JType[] jTypes;

public void setColumnName(int i, String name)
	{ colNames[i] = name; }
public String getColumnName(int i) { return colNames[i]; }
//public JType getJType(int i) { return jTypes[i]; }
public int getColumnCount() { return colNames.length; }
public abstract Object getValueAt(ItemType item, int col);
// -----------------------------------------------------------

//protected LiveSet getLiveSet() { return liveSet; }

ConsumerThread<LiveSetEvent> getQueue() { return queue; }
// ============================================================
// Start and stop our internal queue + thread.
public void start()
{
	queue = new MyConsumerThread(expHandler);
	timer = new Timer();
	queue.start();
}
public void stop() throws Exception
{
	if (queue != null) queue.interrupt();
	if (timer != null) timer.cancel();
}
// ============================================================
// ================================================================
// Comparable<ItemType>
public int compare(ItemType a, ItemType b) {
	// Compare by each sort column in turn
	for (SortSpec.SortCol sc : spec.getSortCols()) {
		int col = sc.col;
		Object valA = getValueAt(a, col);
		Object valB = getValueAt(b, col);
			
		// Handle nulls
		if (valA == null) {
			if (valB == null) continue;		// They're equal
			return -1;				// A < B (null is less than anything)
		} else {
			if (valB == null) return 1;		// A > B
		}

		// Neither is null, use the regular comparator for this column
		Comparator comp = getComparator(col); //comparators.getValueAt(0, col);
		int cmp = comp.compare(valA, valB);
		if (cmp != 0) return cmp * sc.dir;
	}
	
	// The two items compare exactly on all their sort columns...
	// Use original order!
	return a.compareTo(b);
}
// -----------------------------------------------------------------


public ItemType getItem(int itemIx)
{
	return items.get(itemIx);
}

//public int getColumnCount() { return cols.getColumnCount(); }

public Object getValueAt(int itemIndex, int columnIndex) {
	return getValueAt(getItem(itemIndex), columnIndex);
}

public JType getJType(int item, int col) {
	return jTypes[col];
}

@Override
public Class<?> getColumnClass(int columnIndex) {
	return jTypes[columnIndex].getObjClass();
}

@Override
public boolean isCellEditable(int itemIndex, int columnIndex) {
	return false;
}

public int getRowCount() { return items.size(); }

// ================================================================
// SortableTableModel
/** @returns Current SortSpec.  The caller may change this as
 * it wishes, followed by a resort() call. */
public SortSpec getSortSpec() { return spec; }

/** Re-sort according to the latest SortSpec.  Must be called
 from the Queue thread, since it changes the TableModel data structures.
 @param itemsChanged true if the set of items in the underlying LiveSet have either
 been cleared or completely changed (just an insert or delete doesn't count)*/
void resortFromQueueThread(final boolean setChanged)
{
	// Re-do the sorting
//	ItemComparator comp = new ItemComparator();
	ArrayList<ItemType> newItems = new ArrayList(items);
	TreeMap<ItemType,Integer> newIitems = new TreeMap();	
	Collections.sort(newItems, this);
	for (int i=0; i<newItems.size(); ++i) {
		newIitems.put(newItems.get(i), i);
	}
	items = newItems;
	iitems = newIitems;

	try {
		EventQueue.invokeAndWait(new Runnable() {
		public void run() {
//			TableModelEvent evt = new Event(
//				TrackerTableModel.this, LiveItemsChanged);
//			fireTableChanged(evt);
			evtSetChanged = setChanged;
			fireTableChanged(new TableModelEvent(LiveSetTableModel.this)); //, 0, getItemCount()-1));
			evtSetChanged = false;
			
		}	});
	} catch(Exception e) {}
}

/** This is called from the Swing or Pocono thread.  The resorting must take place
 * in the table's thread, hence dump on an event. */
public void resort()
{
	if (queue == null) return;
	queue.offer(new LiveSetEvent(LiveSetEvent.RESORTED, null, null));
}

/** Set a new SortSpec (and resort).  Normally runs from Swing Thread*/
public void setSortSpec(SortSpec spec)
{
	this.spec = spec;
	resort();
}

//public void setComparators(DataGrid<Comparator> comparators)
//{
//	this.comparators = comparators;
//}
// ==================================================
// LiveSet.Listener --- runs in the Playpen thread

public void itemsChanged() {
	queue.offer(LiveSetEvent.newItemsChanged(liveSet));
}

public void itemAdded(LiveItem item) {
	queue.offer(new LiveSetEvent(LiveSetEvent.ROWADDED, liveSet, item));
}

public void itemRemoved(LiveItem item) {
	queue.offer(new LiveSetEvent(LiveSetEvent.ROWREMOVED, liveSet, item));
}

/** Ignore this; we don't need to know what the value WAS when we update. */
public void beforeItemUpdated(LiveItem item) {
}


public void itemUpdated(LiveItem item) {
	queue.offer(new LiveSetEvent(LiveSetEvent.ROWUPDATED, liveSet, item));
}
// ================================================================
class MyConsumerThread extends ConsumerThread<LiveSetEvent>
{
public MyConsumerThread(ExpHandler expHandler) 
	{ super(expHandler); }

public void processItems(LinkedList<LiveSetEvent> xevents) throws Exception {
//System.out.println("***** LiveSet Events processing " + xevents.size() + " events");
	boolean LiveItemsChanged = false;
	boolean needsRefresh = false;		// True if we must punt and refresh the whole thing
	int origSize = items.size();
//	boolean[] todel = new boolean[items.size()];	// Items we will delete
//	boolean willdel = false;
	TreeSet<ItemType> todel = new TreeSet();
	LiveSetEvent[] events;
	
	// Ignore all events before the first CLEAR event.
	int n=0;
	ListIterator<LiveSetEvent> ii;
	for (ii = xevents.listIterator(xevents.size()); ; ) {
		if (!ii.hasPrevious()) break;
		LiveSetEvent evt = ii.previous(); ++n;
//		if (evt.type == LiveSetEvent.CLEARED || evt.type == LiveSetEvent.ROWSCHANGED) break;
		if (evt.getType() == LiveSetEvent.ROWSCHANGED) break;
	}
	events = new LiveSetEvent[n];
	for (int i=0; ii.hasNext(); ) events[i++] = ii.next();

	// ==================================================
	// Apply the events to our internal table state
	for (LiveSetEvent<ItemType> e : events) {
		switch(e.getType()) {
			case LiveSetEvent.RESORTED :
				needsRefresh = true;
			break;
//			case LiveSetEvent.CLEARED :
			case LiveSetEvent.ROWSCHANGED :
				items.clear();
				// Copy over items stored in the event
				if (e.getItems() != null) {
					for (ItemType item : e.getItems()) {
//if (item == null) {
//	System.out.println("LiveSetTableModel A: item == null");
//}
						items.add(item);
						itemStates.add(new ItemState(item));
					}
				}
				needsRefresh = true;
				LiveItemsChanged = true;
			break;
			case LiveSetEvent.ROWREMOVED : {
//				Integer item = iitems.get(e.item);
				todel.add(e.getItem());
				needsRefresh = true;
			}
			case LiveSetEvent.ROWADDED : {
				ItemType item = e.getItem();
//if (item == null) {
//	System.out.println("LiveSetTableModel B: item == null");
//}
				items.add(item);
				itemStates.add(new ItemState(item));
				iitems.put(item, items.size()-1);
				needsRefresh = true;
			} break;
		}
	}

	// Apply deletions
	if (todel.size() > 0) {
		ArrayList<ItemType> oldItems = items;
		iitems.clear();
		items =  new ArrayList(oldItems.size() - todel.size());
		itemStates = new ArrayList(items.size());
		int i=0;
		for (ItemType item : oldItems) if (!todel.contains(item)) {
			items.add(item);
			itemStates.add(new ItemState(item));
			iitems.put(item, i++);
		}
	}
	// ==================================================
	// Fire events required to re-display the screen
	if (needsRefresh) {
		// Refresh if we already know that's what we must do
		resortFromQueueThread(LiveItemsChanged);
		return;
	} else {
		final TreeSet<Integer> updateItems = new TreeSet();
		// No full refresh, pop out the actual events
		for (LiveSetEvent e : events) {
			if (e.getType() != LiveSetEvent.ROWUPDATED) continue;

			// assert: e.type == LiveSetEvent.UPDATED
			Integer item = iitems.get(e.getItem());
			if (item == null) continue;
			updateItems.add(item);
		}
	
		try {
			EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				for (Integer item : updateItems) {
					if (minRefreshMS == 0L) {
						fireTableRowsUpdated(item, item);
					} else {
						ItemState state = itemStates.get(item);
						switch(state.state) {
							case RS_INITIAL : {
								fireTableRowsUpdated(item, item);
								//create a timer to notify us later
								timer.schedule(new RefreshTask(state),
									minRefreshMS);
								state.state = RS_TIMERSET;
							} break;
							case RS_TIMERSET : {
								state.state = RS_TIMERSETANDTICK;
							} break;
							case RS_RESETTING : {
								fireTableRowsUpdated(item, item);					
								state.state = RS_INITIAL;
							}
						}
					}
				}
			}});
		} catch(Exception e) {}

	}
}
}
// ================================================================
// ================================================================
// Utility functions
/** Works only for tables bound to a TrackerTableModel. */
public static LiveItem getSelectedItem(JTypeColTable table)
{
	int iitem = table.getSelectedRow();
	if (iitem < 0) return null;
	LiveSetTableModel model = (LiveSetTableModel)table.getModelU();
	return model.getItem(iitem);
}

//class Event extends TableModelEvent
//{
//	boolean LiveItemsChanged = false;
//	public Event(TableModel source, boolean LiveItemsChanged)
//	{
//		super(source);
//		this.LiveItemsChanged = LiveItemsChanged;
//	}
//    public Event(TableModel source, int firstItem, int lastItem, int column, int type)
//	{
//		super(source, firstItem, lastItem, column, type);
//	}
//}
// ================================================================
// Should really run in the Playpen (or other source) thread
class RefreshTask extends TimerTask
{
	ItemState state;
	public RefreshTask(ItemState state) {
		this.state = state;
	}
	public void run() {
		int oldState = state.state;
		state.state = RS_RESETTING;
//		if (oldState == RS_TIMERSETANDTICK) {
			queue.offer(new LiveSetEvent(
				LiveSetEvent.ROWUPDATED, liveSet, state.item));
//		}
	}
}
}
