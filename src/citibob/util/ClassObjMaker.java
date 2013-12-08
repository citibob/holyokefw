/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

/**
 *
 * @author fiscrob
 */
public class ClassObjMaker<TT> implements ObjMaker<TT>
{
	Class klass;
	public ClassObjMaker(Class klass)
	{
		this.klass = klass;
	}
	public TT newInstance() throws InstantiationException, IllegalAccessException {
		return (TT)klass.newInstance();
	}
}
