/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

/**
 *
 * @author citibob
 */
public class ObjectUtil {
public static boolean eq(Object a, Object b)
{
	if (a == b) return true;
	if (a == null || b == null) return false;
	return a.equals(b);
}

}
