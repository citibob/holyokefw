///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package citibob.swing.table;
//
//import citibob.reflect.ReflectUtils;
//import citibob.types.JType;
//import citibob.types.JavaJType;
//import java.lang.reflect.Field;
//import java.util.List;
//
///**
// *
// * @author fiscrob
// */
//public class PojoTMCols 
//extends AbstractJTypeTableModel
//{
//
//Object pojo;
//Field[] fields;
//JType[] jTypes;
//
//public void setPojo(Object pojo)
//{
//	this.pojo = pojo;
//	
//	// Get list of fields
//	List<Field> ff = ReflectUtils.getAllFieldsList(pojo.getClass());
//	fields = new Field[ff.size()];
//	jTypes = new JType[ff.size()];
//	ff.toArray(fields);
//	
//	// Get JType for each one
//	for (int i=0; i<fields.length; ++i) {
//		Field f = fields[i];
//		f.setAccessible(true);
//		jTypes[i] = new JavaJType(fields[i].getDeclaringClass());
//	}
//}
//
//public PojoTMCols() {}
//public PojoTMCols(Object pojo)
//{
//	setPojo(pojo);
//	super.fireTableDataChanged();
//}
//
//	
//@Override
//public void setValueAt(Object val, int row, int col) {
//	fields[col].set(pojo, val);
//}
//
//@Override
//public String getColumnName(int column) {
//	return fields[column].getName();
//}
//
//@Override
//public boolean isCellEditable(int rowIndex, int columnIndex) {
//	return true;
//}
//
//public int getColumnCount() {
//	return fields.length;
//}
//
//public int getRowCount() {
//	return 1;
//}
//
//public Object getValueAt(int rowIndex, int columnIndex) {
//	return fields[columnIndex].get(pojo);
//}
//
//public JType getJType(int row, int col) {
//	return jTypes[col];
//}
//	
//
//}
//}
