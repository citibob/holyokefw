/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.swing.table.JTypeTableModel;
import citibob.swing.table.PivotTableModel;
import citibob.text.SFormat;
import java.util.List;

/**
 *
 * @author citibob
 */
public class PivotSchemaBuf extends PivotTableModel
{
public PivotSchemaBuf(JTypeTableModel mainU, SchemaBuf dataU,
String[] sKeyCols,
String sPivotKeyColD, String sPivotValColD,
List pivotVals, SFormat pivotValsFmt)
{
	super(mainU, dataU, sKeyCols, sPivotKeyColD, sPivotValColD, pivotVals, pivotValsFmt);
}
	@Override
	protected int newCell(int rowM, int colM) {
		SchemaBuf sbDataU = (SchemaBuf)dataU;
		int rowD = sbDataU.insertRow(-1);
		
		for (int i=0; i<keyColsM.length; ++i) {
			// Set the keys
			sbDataU.setValueAt(
				mainU.getValueAt(rowM, keyColsM[i]),
				rowD, keyColsD[i]);
			
			// Set the pivot column
			sbDataU.setValueAt(pivotKeyVals[colM],
				rowD, pivotKeyColD);
		}
		return rowD;
	}

}
