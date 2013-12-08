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
 * SchemaSet.java
 *
 * Created on March 31, 2006, 9:54 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.jschema;

import citibob.types.JEnum;
import citibob.types.JType;
import citibob.types.KeyedModel;

/**
 *
 * @author citibob
 */
public abstract class SchemaSet {

	public abstract KeyedModel getKeyedModel(String name);

	public int getEnumInt(String keyedModelName, String enumVal)
	{
		KeyedModel km = getKeyedModel(keyedModelName);
		Integer ret = km.getIntKey(enumVal);
		if (ret == null) {
			System.err.println("getEnum(" + keyedModelName + ", " + enumVal + ") is null");
		}
		return ret;
	}

	
	
	public abstract SqlSchema get(String name);
	
	/** Convenience Function */
	public Column get(String schemaName, String colName)
	{
		SqlSchema schema = get(schemaName);
		return schema.getCol(colName);
	}
	
	/** Convenience Function */
	public JType getJType(String schemaName, String colName)
	{
		Column col = get(schemaName, colName);
		return col.getType();
	}
	
	/** Convenience Function */
	public KeyedModel getKeyedModel(String schemaName, String colName)
	{
		return ((JEnum)getJType(schemaName, colName)).getKeyedModel();
	}
	
	public int getEnumInt(String schemaName, String colName, String enumVal)
	{
		KeyedModel km = getKeyedModel(schemaName, colName);
		Integer ret = km.getIntKey(enumVal);
		if (ret == null) {
			System.err.println("getEnum(" + schemaName + ", " + colName + ", " + enumVal + ") is null");
		}
		return ret;
	}
}
