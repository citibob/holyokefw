/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

import java.text.NumberFormat;

/**
 *
 * @author citibob
 */
public class PercentSFormat extends FlexiSFormat
{

public PercentSFormat()
{
	super(new SFormat[] {
		new FormatSFormat(NumberFormat.getPercentInstance(), "", SFormat.RIGHT),
		new DivDoubleSFormat("#", .01)
//		new DivDoubleSFormat("#.#", 1)
	});
}
	
}
