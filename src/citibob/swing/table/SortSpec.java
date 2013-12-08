/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author fiscrob
 */
public class SortSpec {
	public static class SortCol {
		public int col;
		public int dir;		// Should be 1 (forward) or -1 (backward).  Cannot be 0
	}
	
	// Indexed by number of columns in the overall table.
	int[] dirs;
	int[] sortIndex;		// The order of sort in the sortCols array
	
	public SortSpec(int ncol)
	{
		dirs = new int[ncol];
		sortIndex = new int[ncol];
	}
	
//	protected SortCol[] sortColsByCol;					// Which way is this column sorted?
	protected List<SortCol> sortCols = new ArrayList();

	public List<SortCol> getSortCols() { return sortCols; }

	public void clear() {
		for (int i=0; i<dirs.length; ++i) {
			dirs[i] = 0;
			sortIndex[i] = -1;
		}
		sortCols.clear();
	}

	public int getSortDir(int col)
	{ return dirs[col]; }

	public int getSortIndex(int col)
	{ return sortIndex[col]; }
	
	public void setSortDir(int col, int dir)
	{
		dirs[col] = dir;
		if (dir == 0) {
			// Remove the SortCol from our list
			for (Iterator<SortCol> ii = sortCols.iterator(); ii.hasNext();) {
				SortCol sc = ii.next();
				if (sc.col == col) {
					ii.remove();
					sortIndex[col] = -1;
					return;
				}
			}
		} else {
			// Modify existing SortCol
			for (SortCol sc : sortCols) {
				if (sc.col == col) {
					sc.dir = dir;
					return;
				}
			}
		
			// Not found; add it
			SortCol sc = new SortCol();
				sc.col = col;
				sc.dir = dir;
			sortIndex[col] = sortCols.size();
			sortCols.add(sc);
		}
	}
	
	
	/** Gets the value of the spec, as a String */
	public String getStringVal()
	{
		if (sortCols.size() == 0) return "";
		StringBuffer sb = new StringBuffer();
		
		for (SortCol sc : sortCols) {
			sb.append(sc.dir > 0 ? '+' : '-');
			sb.append(sc.col);
			sb.append('|');
		}
		
		return sb.toString();
	}
	
	/** Sets the value of the spec, as a String */
	public void setStringVal(String sspec)
	{
		clear();
		if (sspec == null) return;
		
		String[] sspecs = sspec.split("\\|");
		int n = sspecs.length;
		
		for (int i=0; i<n; ++i) {
			SortCol sc = new SortCol();
			
			String cspec = sspecs[i];
			if (cspec.length() == 0) continue;
			
			char cdir = cspec.charAt(0);
			switch(cdir) {
				case '+' : sc.dir = 1; break;
				case '-' : sc.dir = -1; break;
			}
			sc.col = Integer.parseInt(cspec.substring(1));
			
			// Add to the data structure
			sortCols.add(sc);
			dirs[sc.col] = sc.dir;
			sortIndex[sc.col] = i;
		}
	}
}
