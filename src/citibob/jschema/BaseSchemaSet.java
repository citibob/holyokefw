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
 * BaseSchemaSet.java
 *
 * Created on January 19, 2007, 9:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.types.KeyedModel;
import java.util.*;

/**
 *
 * @author citibob
 */
public class BaseSchemaSet extends SchemaSet
{

protected TreeMap<String,SqlSchema> map = new TreeMap();
protected TreeMap<String,KeyedModel> keyedModels = new TreeMap();

public SqlSchema get(String name) {
	SqlSchema sc = map.get(name);
	if (sc == null) throw new NullPointerException("Schema " + name + " not in SchemaSet");
	return sc;
}

public KeyedModel getKeyedModel(String name)
{
	KeyedModel km = keyedModels.get(name);
	if (km == null) throw new NullPointerException("KeyedModel " + name + " not in SchemaSet");
	return km;
}

public void add(SqlSchema schema)
{
	map.put(schema.getDefaultTable(), schema);
}

public void addKeyedModel(String name, KeyedModel km)
{
	keyedModels.put(name, km);
}
}
