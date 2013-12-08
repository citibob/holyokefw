/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.reflect.ReflectUtils;
import java.lang.reflect.Field;
import java.util.Map;

/**
 *
 * @author fiscrob
 */
public class SchemaUtils {

public static void setColVars(Schema schema, Object obj)
//throws IllegalAccessException, IllegalArgumentException
{
	
	Map<String,Field> fields = ReflectUtils.getAllFields(obj.getClass());
	int keyCol = -1;
	for (int i=0; i<schema.size(); ++i) {
		Column col = schema.getCol(i);
		
		// Set index in the main table
		String name = col.getName();
		Field f = fields.get(name + "Col");
		if (f != null) try {
			f.setAccessible(true);
			f.set(obj, i);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		};

		// Set index in the keys list
		if (col.isKey()) {
			++keyCol;
			f = fields.get(name + "KeyCol");
			if (f != null) try {
				f.setAccessible(true);
				f.set(obj, keyCol);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(-1);
			};
		}
	}
}
	
}
