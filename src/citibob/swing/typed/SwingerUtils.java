/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.typed;

import citibob.swing.typed.Swinger.RenderEdit;
import citibob.swingers.DefaultRenderEdit;
import citibob.swingers.KeyedRenderEdit;
import citibob.swingers.SFormatRenderer;
import citibob.swingers.TypedWidgetEditor;
import citibob.text.FormatUtils;
import citibob.text.KeyedSFormat;
import citibob.text.SFormat;
import citibob.types.JType;
import citibob.types.KeyedModel;
import java.text.Format;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author fiscrob
 */
public class SwingerUtils {


public static RenderEdit newRenderEdit(Swinger swinger)
	{ return swinger.newRenderEdit(); }
public static RenderEdit newRenderEdit(KeyedModel kmodel)
	{ return new KeyedRenderEdit(kmodel); }

public static RenderEdit newRenderEdit(SFormat sformat)
{
	TableCellRenderer rr = new SFormatRenderer(sformat);
	JTypedTextField tw = new JTypedTextField();
	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
	TableCellEditor ee = new TypedWidgetEditor(tw);
	RenderEdit re = new DefaultRenderEdit(rr, ee);
	return re;
}

public static RenderEdit newRenderEdit(Class klass, String fmtString)
{
	SFormat sfmt = FormatUtils.toSFormat(klass, fmtString);
	return newRenderEdit(sfmt);
}
public static RenderEdit newRenderEdit(java.text.Format fmt, int horizAlign)
{
	SFormat sfmt = FormatUtils.toSFormat(fmt, horizAlign);
	return newRenderEdit(sfmt);
}
// ===================================================================
public static SFormat newSFormat(KeyedModel kmodel)
	{ return new KeyedSFormat(kmodel); }
public static SFormat newSFormat(Class klass, String fmtString)
	{ return FormatUtils.toSFormat(klass, fmtString); }
// ===================================================================





//	if (obj instanceof RenderEdit)
//		newRenderEditU(colNameU, (RenderEdit)obj);
//	else if (obj instanceof Swinger)
//		newRenderEditU(colNameU, (Swinger)obj);
//	else if (obj instanceof KeyedModel)
//		newRenderEditU(colNameU, (KeyedModel)obj);
//	else if (obj instanceof SFormat)
//		newRenderEditU(colNameU, (SFormat)obj);
//	else if (obj instanceof Format)
//		newRenderEditU(colNameU, (Format)obj);
//	else if (obj instanceof String)
//		newRenderEditU(colNameU, (String)obj);
//	else throw new IllegalArgumentException("Illegal type for format specification: " + obj.getClass());

	
}
