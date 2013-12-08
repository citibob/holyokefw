package citibob.swing.table;

import citibob.types.JType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class SortedTableModel
extends WrapJTypeTableModel
implements TableModelListener, SortableTableModel
{

	// Per-row information --- cache a sorted version of the data
	protected int[] viewToModel = null;
	protected int[] modelToView = null;

	
	public int viewToModel(int row) { return viewToModel[row]; }
	public int modelToView(int row) { return modelToView[row]; }
	
	// Per-column information
//	protected Comparator[] comparators;		// How to sort on each column
	protected DataGrid<Comparator> comparators;
	protected SortSpec spec;
	
	LinkedList<TableModelListener> listeners = new LinkedList();

	/** @param sub The underlying TableModel to be sorted.  NOTE: sub MUST
	 * have just one type per column!!!
	 */
	public SortedTableModel(JTypeTableModel sub)
	{
		super(sub);
		int ncol = sub.getColumnCount();
		comparators = ComparatorCols.newComparatorCols(ncol);		// Set up default
//		comparators = new Comparator[ncol];
//		this.comparators = new Comparator[sub.getColumnCount()];
		sub.addTableModelListener(this);
		setSortSpec(new SortSpec(ncol));
	}
	/** Must be called as part of initialization!!! */
	public void setComparators(DataGrid<Comparator> comparators)
	{
		this.comparators = comparators;
	}
	public void setSortSpec(SortSpec spec)
	{
		this.spec = spec;
		resort();
	}
//	public void setSortDir(int col, int dir)
//	{
//		spec.setSortDir(col, dir);
//		refresh();
//	}
//	public void setSortDir(String scol, int dir)
//	{
//		int col = findColumn(scol);
//		setSortDir(col, dir);
//	}
//	public void clearSort() {
//		spec.clear();
//		resort();
//	}
	
//	public void setComparator(int col, Comparator comp)
//	{
//		comparators[col] = comp;
//	}
//	public void setComparator(String scol, Comparator comp)
//	{
//		setComparator(findColumn(scol), comp);
//	}
	public SortSpec getSortSpec() { return spec; }
	
	public void resort()
	{
		setSorted();
		fireTableChanged(new TableModelEvent(this)); //, 0, getRowCount()-1));
	}

	
// -----------------------------------------------------------------
static class Row {
	int rowU;			// Row # in the underlying table
	public Row(int rowU) { this.rowU = rowU; }
}
class RowComparator implements Comparator<Row> {
public int compare(Row a, Row b) {
	// Compare by each sort column in turn
	for (SortSpec.SortCol sc : spec.getSortCols()) {
		int col = sc.col;
		Object valA = sub.getValueAt(a.rowU, col);
		Object valB = sub.getValueAt(b.rowU, col);

		// Handle nulls
		if (valA == null) {
			if (valB == null) continue;		// They're equal
			return -1;				// A < B (null is less than anything)
		} else {
			if (valB == null) return 1;		// A > B
		}
		
		// Neither is null, use the regular comparator for this column
//System.out.println("COmparator: " + col + " " + comparators[col]);
//		int cmp = comparators[col].compare(valA, valB);
//		if (comparators == null) {
//			return ((Comparable) valA).compareTo(valB);
//		} else {
			Comparator comp = comparators.getValueAt(0, col);
			int cmp = comp.compare(valA, valB);
			if (cmp != 0) return cmp * sc.dir;
//		}
	}
	
	// The two rows compare exactly on all their sort columns...
	// Use original order!
	return a.rowU - b.rowU;
}}
public void setSorted()
{
	RowComparator comp = new RowComparator();
	
	// Make the array
	int nrow = sub.getRowCount();
//System.out.println("setSorted: nrow = " + nrow);
//if (nrow == 0) {
//	System.out.println("hoi");
//}
	Row[] rows = new Row[nrow];
	for (int i=0; i<nrow; ++i) rows[i] = new Row(i);
	Arrays.sort(rows, comp);

	// Copy it back
	viewToModel = new int[nrow];
	modelToView = new int[nrow];
	for (int i=0; i<nrow; ++i) {
		viewToModel[i] = rows[i].rowU;
		modelToView[rows[i].rowU] = i;
	}		
}
boolean isSorted() { return viewToModel != null; }
void clearSorted() { viewToModel = null; }
// -----------------------------------------------------------------


// =========================== TableModel
public boolean isCellEditable(int rowIndex, int columnIndex)
{ return sub.isCellEditable(viewToModel[rowIndex], columnIndex); }

public Object getValueAt(int rowIndex, int columnIndex)
{
	if (viewToModel.length <= rowIndex) {
		System.out.println("hoi");
	}
	return sub.getValueAt(viewToModel[rowIndex], columnIndex);
}

public void setValueAt(Object aValue, int rowIndex, int columnIndex)
{ sub.setValueAt(aValue, viewToModel[rowIndex], columnIndex); }

// ----------- Event Stuff
public void addTableModelListener(TableModelListener l)
{ listeners.add(l); }

public void removeTableModelListener(TableModelListener l)
{ listeners.remove(l); }

public void fireTableChanged(TableModelEvent e)
{ for (TableModelListener l : listeners) l.tableChanged(e); }

// =========================== CitibobTableModel

/** Gets the value at a specific column and row, with column referenced by name */
public Object getValueAt(int row, String col)
{ return sub.getValueAt(viewToModel[row], col); }

public void setValueAt(Object val, int row, String col)
{ sub.setValueAt(val, viewToModel[row], col); }

// ========================== JTypeTableModel
/** Return SqlType for a cell.  If type depends only on col, ignores the row argument. */
public JType getJType(int row, int col)
{ return sub.getJType(0,col); }
//{ return sub.getJType(viewToModel(row),col); }

/** Convenience function */
public JType getJType(int row, String col)
{ return sub.getJType(0, col); }
//{ return sub.getJType(viewToModel(row), col); }

// ================================================================
// TableModelListener
public void tableChanged(TableModelEvent e)
{
	boolean dirty = !isSorted();		// Dirty bit to see if we should resort

	if (!dirty) {
		if (e.getType() == TableModelEvent.UPDATE) {
			if (e.getFirstRow() == TableModelEvent.HEADER_ROW) {
				// Table structure changed. Not supported for now!
				throw new UnsupportedOperationException(
					"SortedJTypeTableModel cannot handle table structure changes!!!");
			}

			int col = e.getColumn();
			if (col == TableModelEvent.ALL_COLUMNS) {
				// One of our columns is sorted
				dirty = true;
			} else if (e.getFirstRow() != e.getLastRow()) {
				// See if there's more than one row.
				// We could fire individual events for each row changed, but
				// we'll just be lazy and mark the dirty bit.
				dirty = true;
			} else {
				// See if we're changing any sorted columns
				if (spec.getSortDir(col) != 0) dirty = true;
//				for (SortCol sc : spec.getSortCols()) {
//					if (sc.col == col) {
//						dirty = true;
//						break;
//					}
//				}
			}
		} else {
			// It's INSERT or DELETE --- set dirty bit!
			dirty = true;
		}
	}

	if (dirty) {
		setSorted();
		fireTableChanged(new TableModelEvent(this));
	} else {
		// Re-work old event
		fireTableChanged(new TableModelEvent(this,
			modelToView[e.getFirstRow()],
			modelToView[e.getLastRow()],
			e.getColumn(), e.getType()));
	}
}
}
