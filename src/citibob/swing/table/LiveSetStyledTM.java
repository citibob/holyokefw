/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.app.App;
import citibob.swing.typed.SwingerMap;
import citibob.task.ExpHandler;
import citibob.types.JType;
import citibob.util.LiveItem;
import citibob.util.LiveSet;
import java.util.Comparator;

public abstract class LiveSetStyledTM<RowType extends LiveItem>
extends DelegateStyledTM
implements LiveSet.Listener
{
//private LiveSetTableModel liveTM;
public LiveSetTableModel<RowType> getLiveTM() { return ((LiveSetTableModel)modelU); }
public LiveSet<RowType> getLiveSet() { return getLiveTM().liveSet; }

public RowType getRow(int irow) { return getLiveTM().getItem(irow); }

protected LiveSetStyledTM() {}
public LiveSetStyledTM(ExpHandler expHandler, SwingerMap smap, Object... colSpecs)
	{ init(expHandler, smap, colSpecs); }

protected void init(App app, Object... colSpecs)
{
	init(app.expHandler(), app.swingerMap(), colSpecs);
}
protected void init(ExpHandler expHandler, SwingerMap smap, Object... colSpecs)
{
	// Set up basic modelU and model
	int n = colSpecs.length / 3;
	String[] colNames = new String[n];
	JType[] jTypes = new JType[n];
	for (int i=0; i<n; ++i) {
		colNames[i] = (String)colSpecs[i*3];
		jTypes[i] = (JType)colSpecs[i*3+1];
	}
	LiveSetTableModel liveTM = new LiveSetTableModel<RowType>(expHandler, colNames, jTypes) {
		public final Object getValueAt(RowType row, int col) {
			// This is a transient condition, it seems to happen
			// during backtest around day changes.  I'm not quite
			// sure why (fiscrob).  In any case, ignore it...
//			if (row == null) return null;
			return LiveSetStyledTM.this.getValueAt(row, col);
		}
		public Comparator getComparator(int col) {
			return LiveSetStyledTM.this.compModelU.getValueAt(0, col);
		}
	};
	modelU = liveTM;
	compModelU = new ComparatorCols(liveTM, smap);
	model = new ColPermuteTableModel(modelU);
	
	// Set up swingers, including custom formats
	RenderEditCols re = new RenderEditCols(this, smap);
	for (int col=0; col<model.getColumnCount(); ++col) {
		int colU = model.getColU(col);
//	for (int i=0; i<n; ++i) {

		Object fmtSpec = colSpecs[colU*3+2];
		String colName = (String)colSpecs[colU*3];
		JType jType = (JType)colSpecs[colU*3+1];
//System.out.println("col = " + col + ", colU = " + colU);
//System.out.println(
//	"\tcolName = " + colName + "\n"+
//	"\tjType = " + jType + "\n" +
//	"\tfmtSpec = " + fmtSpec + "\n");
		try {
			if (fmtSpec != null) re.setFormat(col, fmtSpec);
		} catch(Exception e) {
			e.printStackTrace();
			break;
		}
	}
	setRenderEditModel(re);
}
public abstract Object getValueAt(RowType row, int col);

public void setJTypeU(String colName, JType jType)
{
	int colU = super.getModel().findColumnU(colName);
	((LiveSetTableModel)modelU).jTypes[colU] = jType;
}
public void setFormatU(String colName, Object fmtSpec)
{
	RenderEditCols re = (RenderEditCols)getRenderEditModel();
	re.setFormatU(colName, fmtSpec);
}


//public void setLiveSet(LiveSet liveSet)
//	{ liveTM.setLiveSet(liveSet); }


}
