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

import citibob.sql.pgsql.SqlInteger;
import citibob.jschema.*;
import java.sql.*;
import citibob.sql.*;
//import java.util.*;

/** Description of a set of queries we would like to do and load into a PersonsBuf. */
public class IntKeyedDbModel extends SchemaBufDbModel
{


	
	/** Key fields to control who gets displayed. */
//int idValue;
//String keyField;
//int keyCol;


//public void setKey(int idValue)
//{
//	this.idValue = idValue;
//}
//public void setKey(Object... key)
//{
//	if (key[0] == null) setKey(-1);
//	else setKey((Integer)(key[0]));
//}


public Integer getIntKey() { return (Integer)super.getKey(); }
///** Gets the key column of a row from the underlying SchemaBuf */
//public int getKeyValueAt(int row)
//{
//	Integer I = (Integer)getSchemaBuf().getValueAt(row, keyCol);
//	return (I == null ? -1 : I.intValue());
//}
// -----------------------------------------------------------
///** Overrides to protect against int key < 0 --- meaning
// don't select anything. */
//public void doSelect(SqlRunner str)
//{
//	if (getKey() != null) super.doSelect(str);
//	else sbuf.clear();
//}
// -----------------------------------------------------------
// --------------------------------------------------------------
//public static class Params {
//	public Params(boolean doInsertKeys, boolean selectAllOnNull) {
//		this.doInsertKeys = doInsertKeys;
//		this.selectAllOnNull = selectAllOnNull;
//	}
//	public Params(boolean doInsertKeys) {
//		this.doInsertKeys = doInsertKeys;
//	}
//	public Params() {}
//	
//	/** Should we add the key field to the SQL statement when we insert records?  Generally,
//	this will be false for main tables (because they have auto-insert), and
//	true for subsidiary tables. Defaults to true. */
//	public boolean doInsertKeys = true;
//	/** Should we select entire table if key value is null (-1)? */
//	public boolean selectAllOnNull = false;
//}
//Params prm;
// --------------------------------------------------------------
//public IntKeyedDbModel(SchemaBuf buf, String keyField)
//{ this(buf, keyField, new Params()); }
//public IntKeyedDbModel(SchemaBuf buf, String keyField, Params prm)
//{ this(buf, keyField, null, prm); }

public IntKeyedDbModel(SchemaBuf buf, String keyField, DbChangeModel dbChange)
{
	super(buf, buf.getDefaultTable(), dbChange);
	super.setSelectKeyFields(keyField);
//	this.prm = prm;
//	this.keyField = keyField;
//	this.keyCol = buf.findColumn(keyField);
}

public IntKeyedDbModel(SqlSchema schema, String keyField)
	{ this(schema, keyField, null); }
public IntKeyedDbModel(SqlSchema schema, String keyField, DbChangeModel dbChange)
	{ this(new SchemaBuf(schema), keyField, dbChange); }
// --------------------------------------------------------------

//public void setSelectWhere(ConsSqlQuery q)
//{
//	super.setSelectWhere(q);
//	if (idValue >= 0 || !prm.selectAllOnNull) q.addWhereClause(keyField + " = " + idValue);
//}
//public void setInsertKeys(int row, ConsSqlQuery q)
//{
//	super.setInsertKeys(row, q);
//	if (prm.doInsertKeys) q.addColumn(keyField, SqlInteger.sql(idValue));
////	q.addColumn("lastupdated", "now()");
//}
// -----------------------------------------------------------

}
