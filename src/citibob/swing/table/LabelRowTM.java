/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.reflect.ReflectUtils;
import citibob.types.JType;
import citibob.types.JavaJType;
import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * @author fiscrob
 */
public abstract class LabelRowTM
extends AbstractJTypeTableModel
{

protected JType[] jTypes;			// Types by row
protected String[] colNames = {"Param", "Val"};


public abstract String getLabel(int row);
public abstract Object getVal(int row);
public abstract void setValueAt(Object val, int row);

// ------------------------------------------------------------
public void setValueAt(Object val, int row, int col) {
	if (col == 1) setValueAt(val, row);
}

public String getColumnName(int column)
{
	return colNames[column];
}

@Override
public boolean isCellEditable(int rowIndex, int columnIndex) {
	switch(columnIndex) {
		case 0 : return false;
		case 1 : return true;
	}
	return false;
}

public int getColumnCount() {
	return 2;
}


public Object getValueAt(int rowIndex, int columnIndex) {
	switch(columnIndex) {
		case 0 : return getLabel(rowIndex);
		case 1 : return getVal(rowIndex);
	}
	return null;
}

public JType getJType(int row, int col) {
	switch(col) {
		case 0 : return JavaJType.jtString;
		case 1 : return jTypes[row];
	}
	return null;
}
	

}

