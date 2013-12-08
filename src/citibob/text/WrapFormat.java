/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 *
 * @author fiscrob
 */
public class WrapFormat extends Format
{
	protected Format sub;

	public WrapFormat(Format sub)
		{ this.sub = sub; }
	
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
	{
		return sub.format(obj, toAppendTo, pos);
	}
	public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
		return sub.formatToCharacterIterator(obj);
	}
	public Object parseObject(String source, ParsePosition pos) {
		return sub.parseObject(source, pos);
	}
}
