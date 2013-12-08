/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger;
import citibob.swing.typed.SwingerMap;
import citibob.types.JType;
import java.util.Comparator;

/**
 *
 * @author fiscrob
 */
public class ComparatorCols extends DataCols<Comparator>
{

	
///** @param smap can be null */
//public static DataCols<Comparator> newComparatorCols(
//SortableTableModel model, SwingerMap smap)
//{
//	return new ComparatorCols(model, smap);
//}
public ComparatorCols(SortableTableModel model, SwingerMap smap)
{
	super(Comparator.class, model.getColumnCount());
	DataCols<Comparator> cmodel = this;
	
	for (int i=0; i<model.getColumnCount(); ++i) {
		cmodel.data[i] = DefaultComparator.instance;
		
		JType jType = model.getJType(0, i);
		if (jType == null) continue;
		
		Swinger swinger = smap.newSwinger(jType);
		if (swinger == null) continue;
		
		cmodel.data[i] = swinger.getComparator();
	}
//	return cmodel;
}

/** Sets up a default ComModel, used initially in SortedTableModel */
public static DataCols<Comparator> newComparatorCols(int ncol)
{
	DataCols<Comparator> cmodel = new DataCols(Comparator.class, ncol);
	
	for (int i=0; i<ncol; ++i) {
		cmodel.data[i] = DefaultComparator.instance;
	}
	return cmodel;
}


	
}
