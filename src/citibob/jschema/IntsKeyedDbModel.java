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
package citibob.jschema;

import citibob.jschema.*;
import java.sql.*;
import citibob.sql.*;
//import java.util.*;

/** Description of a set of queries we would like to do and load into a PersonsBuf. */
public class IntsKeyedDbModel extends SchemaBufDbModel
{

///** Key fields to control who gets displayed. */
//int[] idValue;
//String[] keyField;
//int[] keyCol;

///** Should we add the key field to the SQL statement when we insert records?  Generally,
//this will be false for main tables (because they have auto-insert), and
//true for subsidiary tables. Defaults to true. */
//boolean doInsertKeys;

public void setKey(int... idValue)
{
	for (int i=0; i<idValue.length; ++i) setKey(i, idValue[i]);
}
public void setKey(int ix, Object value)
{
	if (value == null) super.setKey(ix, null);
	else super.setKey(ix, ((Integer)value).intValue() < 0 ? null : value);
}

public Integer getIntKey(int ix)
{
	Object val = getKey(ix);
	return (Integer)val;
//	return (val == null ? -1 : ((Integer)val).intValue());
}
//public void setKey(Object[] key)
//{
//	for (int i=0; i<idValue.length; ++i) this.idValue[i] = (Integer)key[i];
//}
//
//
//public int[] getIntsKey() { return idValue; }
// --------------------------------------------------------------
public IntsKeyedDbModel(SchemaBuf buf, String[] keyField, boolean doInsertKeys)
{ this(buf, keyField, doInsertKeys, null); }

public IntsKeyedDbModel(SchemaBuf sbuf, String[] keyFields, boolean doInsertKeys, DbChangeModel dbChange)
{
	super(sbuf, sbuf.getDefaultTable(), dbChange);
	super.setSelectKeyFields(keyFields);
////	this.keyField = keyField;
////	keyCol = new int[keyField.length];
////	idValue = new int[keyField.length];
//	for (int i=0; i<idValue.length; ++i) keyCol[i] = sbuf.findColumn(keyField[i]);
	this.doInsertKeys = doInsertKeys;	
}
public IntsKeyedDbModel(SqlSchema schema, String[] keyField, boolean doInsertKeys)
{ this(schema, keyField, doInsertKeys, null); }
public IntsKeyedDbModel(SqlSchema schema, String[] keyField, boolean doInsertKeys, DbChangeModel dbChange)
{
	this(new SchemaBuf(schema), keyField, doInsertKeys, dbChange);
}

public IntsKeyedDbModel(SqlSchema schema, String[] keyField, DbChangeModel dbChange)
	{ this(schema, keyField, true, dbChange); }
public IntsKeyedDbModel(SqlSchema schema, String[] keyField)
	{ this(schema, keyField, null); }
// --------------------------------------------------------------

//public void setSelectWhere(ConsSqlQuery q)
//{
//	super.setSelectWhere(q);
//	for (int i=0; i<keyField.length; ++i) {
//		q.addWhereClause(keyField[i] + " = " + idValue[i]);
//	}
//}
//public void setInsertKeys(int row, ConsSqlQuery q)
//{
//	super.setInsertKeys(row, q);
//	if (doInsertKeys) for (int i=0; i<keyField.length; ++i) {
//		q.addColumn(keyField[i], citibob.sql.ansi.SqlInteger.sql(idValue[i]));
//	}
//}
// -----------------------------------------------------------

}
