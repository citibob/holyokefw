/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger.RenderEdit;
import citibob.swing.typed.SwingerMap;
import citibob.text.SFormatMap;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author fiscrob
 */
public class DelegateStyledTM extends StyledTM
{

DataGrid<String> tooltipModel;
//DataGrid<SFormat> sFormatModel;
//DataGrid<Swinger> swingerModel;
DataGrid<RenderEdit> renderEditModel;
DataGrid<Color> bgColorModel;
DataGrid<Color> fgColorModel;
DataGrid<Font> fontModel;
DataGrid<Boolean> editableModel;
DataGrid<ButtonListener> buttonListenerModel;

//DataCols<Comparator> comparatorModel;

public DelegateStyledTM() {}

public DelegateStyledTM(JTypeTableModel modelU)
	{ super(modelU); }
//public DelegateStyledTM(JTypeTableModel modelU, JTypeTableModel model)
//	{ super(modelU, model); }
// ==========================================================
@Override
public Color getBgColor(int row, int col) {
	if (bgColorModel == null) return super.getBgColor(row, col);
	return bgColorModel.getValueAt(row, col);
}

@Override
public Color getFgColor(int row, int col) {
	if (fgColorModel == null) return super.getFgColor(row, col);
	return fgColorModel.getValueAt(row, col);
}

@Override
public Font getFont(int row, int col) {
	if (fontModel == null) return super.getFont(row, col);
	return fontModel.getValueAt(row, col);
}

@Override
public RenderEdit getRenderEdit(int row, int col) {
	if (renderEditModel == null) return super.getRenderEdit(row, col);
	return renderEditModel.getValueAt(row, col);
}

@Override
public String getTooltip(int row, int col) {
	if (tooltipModel == null) return super.getTooltip(row, col);
	return tooltipModel.getValueAt(row, col);
}

@Override
public boolean isEditable(int row, int col) {
	if (editableModel == null) return super.isEditable(row, col);
	return editableModel.getValueAt(row, col).booleanValue();
}
@Override
public ButtonListener getButtonListener(int row, int col) {
	if (buttonListenerModel == null) return super.getButtonListener(row, col);
	return buttonListenerModel.getValueAt(row, col);
}
// ==========================================================


	public void setBgColorModel(DataGrid<Color> bgColorModel) {
		this.bgColorModel = bgColorModel;
	}

	public void setEditableModel(DataGrid<Boolean> editableModel) {
		this.editableModel = editableModel;
	}

	public void setButtonListenerModel(DataGrid<ButtonListener> buttonListenerModel) {
		this.buttonListenerModel = buttonListenerModel;
	}

	public void setFgColorModel(DataGrid<Color> fgColorModel) {
		this.fgColorModel = fgColorModel;
	}

	public void setFontModel(DataGrid<Font> fontModel) {
		this.fontModel = fontModel;
	}

	public void setRenderEditModel(DataGrid<RenderEdit> renderEditModel) {
		this.renderEditModel = renderEditModel;
	}

	public void setTooltipModel(DataGrid<String> tooltipModel) {
		this.tooltipModel = tooltipModel;
	}

	public DataGrid<Color> getBgColorModel() {
		return bgColorModel;
	}

	public DataGrid<Boolean> getEditableModel() {
		return editableModel;
	}

	public DataGrid<Color> getFgColorModel() {
		return fgColorModel;
	}

	public DataGrid<Font> getFontModel() {
		return fontModel;
	}

	public DataGrid<RenderEdit> getRenderEditModel() {
		return renderEditModel;
	}

	public DataGrid<String> getTooltipModel() {
		return tooltipModel;
	}

// ==========================================================

/** Only works if this StyledTM happens to have been set up with
 * a RenderEditCols as the RenderEdit Model
 * @return
 */
public RenderEditCols getRenderEditCols()
	{ return (RenderEditCols)getRenderEditModel(); }
	
public RenderEditCols setRenderEditCols(SwingerMap smap)
{
	RenderEditCols re = new RenderEditCols(this, smap);
	this.setRenderEditModel(re);
	return re;
}

/** Sets up editable DataCols, with each column set to its default editability. */
public DataCols<Boolean> setEditableCols()
{
	// Do the editable stuff
	int n = model.getColumnCount();
	DataCols<Boolean> editable = new DataCols(Boolean.class, n);
	for (int i=0; i<n; ++i) {
		editable.data[i] = (model.isCellEditable(0, i) ? Boolean.TRUE : Boolean.FALSE);
	}
	this.setEditableModel(editable);
	return editable;
}
	
// ==========================================================
//public void setCompModelU(SwingerMap smap)
//{
//	// Only useful for sortable tables
//	if (! (modelU instanceof SortableTableModel)) return;
//	
//	// Set up comparators based on the type of each column; can change later.
//	compModelU = ComparatorCols.newComparatorCols((SortableTableModel)modelU, smap);
//}
// ==========================================================

public void setDefaultSwingers(SwingerMap smap)
{
	// Set of swingers / types / etc.
	RenderEditCols re = new RenderEditCols(this, smap);
	this.setRenderEditModel(re);
}

/** Makes up a default model based on modelU */
public void setDefaultModel(SwingerMap smap)
{
	// Set up the ColPermuteTableModel
	setModel(new ColPermuteTableModel(modelU));
	setDefaultSwingers(smap);
}

public ColPermuteTableModel setColumns(SwingerMap smap,
String[] colNames, String[] sColMap)
{
	ColPermuteTableModel ret =
		new ColPermuteTableModel(modelU, colNames, sColMap);

	setModel(ret);

	// Set of swingers / types / etc.
	setDefaultSwingers(smap);

	return ret;
}

public void setEditable(boolean... xeditable)
{
	// Do the editable stuff
	if (xeditable == null) return;		// Use default...
	int n = model.getColumnCount();
	DataCols<Boolean> editable = new DataCols(Boolean.class, n);
	for (int i=0; i<n; ++i) {
		editable.data[i] = (xeditable[i] ? Boolean.TRUE : Boolean.FALSE);
	}
	this.setEditableModel(editable);
	
}


/** @param smap may be null.
 * @param fmtSpecs.  Array of blocks of four:
<nl>
<li>Underlying Column Name (String)</li>
<li>Visible Column Name (String).  If null: use Underlying name.</li>
<li>Editable (Boolean).  If null: use editable from modelU </li>
<li>Format Spec: Swinger, SFormat, JType, Class, etc.  If null: get from SwingerMap.</li>
 </nl>
 * 
 */
public RenderEditCols setColumns(SwingerMap smap, Object... fmtSpecs)
{
	int n = fmtSpecs.length / 4;
	
	// Set up the ColPermuteTableModel
	String[] colMap = new String[n];
	String[] colNames = new String[n];
	for (int i=0; i<n; ++i) {
		String nameU = (String)fmtSpecs[i*4];
		String name = (String)fmtSpecs[i*4+1];
		if (name == null) name = nameU;
		colMap[i] = nameU;
		colNames[i] = name;
	}
	setModel(new ColPermuteTableModel(modelU, colNames, colMap));
	
	// Set up editable
	DataCols<Boolean> editable = new DataCols(Boolean.class, n);
	for (int i=0; i<n; ++i) {
		Boolean ed = (Boolean)fmtSpecs[i*4+2];
		// Override editable only if we've given a non-null
		if (ed == null) ed = super.isEditable(0, i);
		editable.data[i] = ed;
	}
	this.setEditableModel(editable);
	
	// Set of swingers / types / etc.
	RenderEditCols re = new RenderEditCols(this, smap);
	for (int i=0; i<n; ++i) {
		Object spec = fmtSpecs[i*4+3];
		// Override result from constructor only if we've specified something.
		if (spec != null) re.setFormat(i, spec);
	}
	this.setRenderEditModel(re);
	return re;
}

/** @param fmtSpecs.  Array of blocks of two:
<nl>
<li>Underlying column in modelU that provides tooltip (String)</li>
<li>Format Spec: Swinger, SFormat, JType, Class, etc.  If null: get from SwingerMap.</li>
 </nl>
 * fmtSpecs must specifiy the same number of columns as is in model.
 * 
 */
public void setTooltips(SFormatMap smap, Object... fmtSpecs)
{
	int n = fmtSpecs.length / 2;
	String[] colMap = new String[n];
	for (int i=0; i<n; ++i) {
		colMap[i] = (String)fmtSpecs[i*2];
	}
	StringTableModel tt = new StringTableModel(
		modelU, colMap, null, false, smap);
	
	// Set up non-default formatting
	for (int i=0; i<n; ++i) {
		Object spec = fmtSpecs[i*2+1];
		// Override result from constructor only if we've specified something.
		if (spec != null) tt.setFormat(i, spec);
	}
	this.setTooltipModel(tt);
}

/** Classic tooltip setter.  For backwards compatibility
 * @param ttColMap Column in underlying table to display as tooltip for each column in displayed table.
*/
public void setTooltips(String... ttColMap)
{
	setTooltipModel(new ColPermuteTableModel(getModelU(), null, ttColMap));
}

}
