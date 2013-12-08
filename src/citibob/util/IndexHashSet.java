/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IndexHashSet<TT> extends IndexSet<TT>
{

protected Map<TT,Integer> newMap() { return new HashMap(); }

public IndexHashSet(TT... sym)
{
	super(sym);
}
public IndexHashSet(Collection<TT> asym)
{
	super(asym);
}
}
