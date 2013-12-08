/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class IndexTreeSet<TT> extends IndexSet<TT>
{

protected Map<TT,Integer> newMap() { return new TreeMap(); }

public IndexTreeSet(TT... sym)
{
	super(sym);
}
public IndexTreeSet(Collection<TT> asym)
{
	super(asym);
}
}
