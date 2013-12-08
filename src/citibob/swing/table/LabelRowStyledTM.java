/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger;
import citibob.swing.typed.SwingerMap;
import citibob.types.JType;
import citibob.util.LiveItem;

public class LabelRowStyledTM
extends DelegateStyledTM
{

SwingerMap smap;
protected Object[] fmtSpecs;

	
/** Auto-set column types, RenderEdits, etc.
 @param fmtSpecs
 Array of blocks of two (only for overriding defautls):
<nl>
<li>Column Name (String)</li>
<li>Format Spec: Swinger, SFormat, JType, Class, etc.  If null: get from SwingerMap.</li>
 </nl>
 */
public LabelRowStyledTM(SwingerMap smap, Object... fmtSpecs)
{
	init(smap, fmtSpecs);
}
protected LabelRowStyledTM() {}
protected void init(SwingerMap smap, Object[] fmtSpecs)
{
	this.smap = smap;
	this.fmtSpecs = fmtSpecs;
}
public void SetModelU(LabelRowTM modelU)
{
	super.setModels(model, model);
	this.modelU = modelU;
//	LabelRowTM modelU = (LabelRowTM)getModelU();
	
	// Don't permute the columns
	setModel(new ColPermuteTableModel(modelU));
	
	refreshModel();
}

/** Re-read type information */
public void refreshModel()
{
	int nrow = modelU.getRowCount();
	int noverride = (fmtSpecs == null ? 0 : fmtSpecs.length / 2);
	
	// Set of swingers / types / etc.
	RenderEditLabelRows re = new RenderEditLabelRows(this, smap);
	for (int row=0; row<nrow; ++row) {
		JType jType = modelU.getJType(row, 1);
		Swinger swinger = smap.newSwinger(jType);
System.out.println("jType = " + jType + " -> " + swinger);
		if (swinger != null) re.setFormat(row, swinger);
	}
	
	// Override...
	for (int i=0; i<noverride; ++i) {
		String rowName = (String)fmtSpecs[i*2];
		Object spec = fmtSpecs[i*2+1];
		re.setFormat(rowName, spec);
	}
	
	this.setRenderEditModel(re);
	((LabelRowTM)getModelU()).fireTableDataChanged();
}


}
