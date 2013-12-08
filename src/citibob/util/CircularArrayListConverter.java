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
package citibob.util;
/*
 * CircularArrayListConverter.java
 *
 * Created on September 7, 2007, 1:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.text.*;
import java.util.*;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.converters.collections.*;
import com.thoughtworks.xstream.io.*;
import com.thoughtworks.xstream.io.xml.*;
import com.thoughtworks.xstream.mapper.*;

import com.thoughtworks.xstream.*;

public class CircularArrayListConverter extends CollectionConverter {
public CircularArrayListConverter(Mapper mapper) { super(mapper); }

        public boolean canConvert(Class clazz) {
                return CircularArrayList.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
			CircularArrayList o = (CircularArrayList)value;
			writer.startNode("circularSize");
			writer.setValue(""+o.circularSize);
			writer.endNode();
			super.marshal(value, writer, context);
//			for (int i=0; i<o.size(); ++i) {
//				writeItem(o.get(i), context, writer);
//			}
        }

        public Object unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context) {
			reader.moveDown();
			int size = Integer.parseInt(reader.getValue());
			CircularArrayList arr = new CircularArrayList(size);
			reader.moveUp();
			populateCollection(reader, context, arr);
			return arr;
        }
// =================================================================
public static void main(String[] args) {
	CircularArrayList a = new CircularArrayList(4);
	a.addCircular((Integer)17);
	a.addCircular((Integer)34);
	a.addCircular((Integer)51);
//	a.addCircular((Integer)68);
//	a.addCircular((Integer)85);
//	a.addCircular((Integer)102);
//	a.addCircular((Integer)119);
	XStream xstream = new XStream(new DomDriver());
	xstream.registerConverter(new CircularArrayListConverter(xstream.getMapper()));
	String xml = xstream.toXML(a);
	System.out.println(xml);
	Object obj = xstream.fromXML(xml);
	
	CircularArrayList a2 = (CircularArrayList)obj;
	for (int i=0; i<a2.size(); ++i) {
		System.out.println(a2.get(i));
	}
}
}