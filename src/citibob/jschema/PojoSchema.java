/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.types.JavaJType;
import java.lang.reflect.Field;

/**
 *
 * @author fiscrob
 */
public class PojoSchema extends ConstSchema
{
	
Class klass;		// Class from which this Schema was made

public Class getKlass() { return klass; }

public PojoSchema(Class klass)
{
	this.klass = klass;
	Field[] fields = klass.getFields();
	super.cols = new Column[fields.length];
	for (int i=0; i<fields.length; ++i) {
		Field f = fields[i];
		cols[i] = new Column(new JavaJType(f.getType()), f.getName());
	}
}

//public Object newInstance() throws InstantiationException, IllegalAccessException
//	{ return klass.newInstance(); }
}
