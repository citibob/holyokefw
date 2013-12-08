/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger;
import citibob.swing.typed.Swinger.RenderEdit;
import citibob.swing.typed.SwingerMap;
import citibob.swing.typed.SwingerUtils;
import citibob.swingers.KeyedRenderEdit;
import citibob.text.FormatUtils;
import citibob.text.SFormat;
import citibob.types.JType;
import citibob.types.JavaJType;
import citibob.types.KeyedModel;
import java.text.Format;

/**
 * Assigns Renderers and Editors by assigning a single data
 * type to each row --- to applied in column 1.  Column 0 always
 * has type String.
 * @author fiscrob
 */
public class RenderEditLabelRows extends DataLabelRows<RenderEdit>
{

StyledTM styledModel;

public RenderEditLabelRows(StyledTM styledModel)
{
	super(RenderEdit.class,
		styledModel.getModel().getRowCount());
	this.styledModel = styledModel;
}

public RenderEditLabelRows(StyledTM styledModel, SwingerMap smap)
{
	this(styledModel);
	if (smap == null) return;

	setCol0(smap.newSwinger(JavaJType.jtString).newRenderEdit());
	
	// Set up the RenderEdits...
	JTypeTableModel model = styledModel.getModel();
	for (int row=0; row<data.length; ++row) {
		String rowName = (String)model.getValueAt(row, 0);
		JType jType = model.getJType(row, 1);
		if (jType == null) continue;
		Swinger swinger = smap.newSwinger(jType, rowName);
		if (swinger == null) return;
		setFormat(row, swinger);
	}
}


// -----------------------------------------------------------
// Re-useable in other classes
// -----------------------------------------------------------
public void setFormat(int row, Object obj)
{
	if (obj instanceof RenderEdit)
		setFormat(row, (RenderEdit)obj);
	else if (obj instanceof Swinger)
		setFormat(row, (Swinger)obj);
	else if (obj instanceof KeyedModel)
		setFormat(row, (KeyedModel)obj);
	else if (obj instanceof SFormat)
		setFormat(row, (SFormat)obj);
	else if (obj instanceof Format)
		setFormat(row, (Format)obj);
	else if (obj instanceof String)
		setFormat(row, (String)obj);
	else throw new IllegalArgumentException("Illegal type for format specification: " + obj.getClass());
}

public void setFormat(int row, RenderEdit re)
	{ data[row] = re; }
public void setFormat(int row, Swinger swinger)
	{ data[row] = swinger.newRenderEdit(); }
public void setFormat(int row, KeyedModel kmodel)
	{ setFormat(row, new KeyedRenderEdit(kmodel)); }
/** Sets a text-based renderer and editor pair at once, for a rowumn,
without going through Swingers or anything.  Works for simpler text-based
renderers and editors ONLY. */
public void setFormat(int row, SFormat sformat)
{
	setFormat(row, SwingerUtils.newRenderEdit(sformat));
}
public void setFormat(int row, java.text.Format fmt)
{
	setFormat(row, fmt, SFormat.LEFT);
}
public void setFormat(int row, java.text.Format fmt, int horizAlign)
{
	setFormat(row, SwingerUtils.newRenderEdit(fmt, horizAlign));
}

/** Sets up a renderer and editor based on a format string.  Works for a small
number of well-known types, this is NOT general. */
public void setFormat(int row, String fmtString)
{
//System.out.println("styledModel.getModel(" + rowNo + ") = " + styledModel.getModel());
	Class klass = styledModel.getModel().getJType(row, 1).getObjClass();
	setFormat(row, SwingerUtils.newRenderEdit(klass, fmtString));
}

// -----------------------------------------------------------
public void setFormat(String rowName, Object obj)
{
	// Find the row...
	LabelRowTM tm = (LabelRowTM)styledModel.getModelU();
	for (int row=0; row<tm.getRowCount(); ++row) {
		if (rowName.equals(tm.getValueAt(row, 0))) {
			setFormat(row, obj);
		}
	}
}

}
