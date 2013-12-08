/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

import java.text.ParseException;

/**
 *
 * @author fiscrob
 */
public class NullSFormat extends BaseSFormat
{
	public String valueToString(Object value) throws ParseException {
		return null;
	}

public static final NullSFormat instance = new NullSFormat();
	
}
