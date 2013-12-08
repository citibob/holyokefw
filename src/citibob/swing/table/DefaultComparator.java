/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import java.util.Comparator;


public class DefaultComparator implements Comparator
{
	public static final Comparator instance = new DefaultComparator();
	public int compare(Object o1, Object o2) {
		return ((Comparable) o1).compareTo(o2);
	}	
}
