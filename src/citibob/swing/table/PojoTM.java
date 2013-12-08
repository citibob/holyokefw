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
public class PojoTM
extends LabelRowTM
{

public static class DummyPojo {}
Object pojo;
Field[] fields;			
//JType[] jTypes;			// Types by column


public PojoTM() {
	this(new DummyPojo());
}
public PojoTM(Object pojo)
{
	setPojo(pojo);
}

public String getLabel(int row) { return fields[row].getName(); }
public Object getVal(int row) {
	try {
		return fields[row].get(pojo);
	} catch(IllegalAccessException e) { }
	return null;
}

public void setPojo(Object pojo)
{
	if (pojo == null) pojo = new DummyPojo();
	this.pojo = pojo;
	
	// Get list of fields
	List<Field> ff = ReflectUtils.getAllFieldsList(pojo.getClass());
	fields = new Field[ff.size()];
	jTypes = new JType[ff.size()];
	ff.toArray(fields);
	
	// Get JType for each one
	for (int i=0; i<fields.length; ++i) {
		Field f = fields[i];
		f.setAccessible(true);
		jTypes[i] = new JavaJType(fields[i].getType());
	}
	super.fireTableDataChanged();
}


public void setValueAt(Object val, int row) {
	try {
		fields[row].set(pojo, val);
	} catch(IllegalAccessException e) { }
}

public int getRowCount() {
	return fields.length;
}
	

}

