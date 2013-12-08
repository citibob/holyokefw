/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

import java.util.Collection;


public class LiveSetEvent<ItemType extends LiveItem> {
	
//public static final int CLEARED = 0;
public static final int ROWUPDATED = 1;
public static final int ROWADDED = 2;
public static final int ROWREMOVED = 3;
public static final int RESORTED = 4;		// User re-sorted the tracker table
public static final int ROWSCHANGED = 5;
int type;
ItemType row;	// Null iff type==CLEARED
LiveSet tracker;	// LiveSet the event happened on (or null if RESORTED)
Collection<ItemType> items;	// Not null if we want to initialize with a bunch of items on CLEARED

	public static LiveSetEvent newItemsChanged(LiveSet tracker)
	{
		LiveSetEvent ret = new LiveSetEvent();
		Collection items = tracker.copyAll();
			ret.type = ROWSCHANGED;
			ret.tracker = tracker;
			ret.items = items;
		return ret;
	}

	protected LiveSetEvent() {}
	
	public LiveSetEvent(int type, LiveSet tracker, ItemType row) {
		this.tracker = tracker;
		this.type = type;
		this.row = row;
	}

	public ItemType getItem() {
		return row;
	}
public Collection<ItemType> getItems() {
	return items;
}
	public int getType() {
		return type;
	}

	
}
