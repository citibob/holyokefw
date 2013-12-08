/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;


public interface ObjMaker<TT> {

	public TT newInstance() throws InstantiationException, IllegalAccessException;
}
