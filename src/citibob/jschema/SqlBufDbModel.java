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
 * SqlBufDbModel.java
 *
 * Created on December 31, 2007, 1:06 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.sql.SqlRun;
import citibob.app.App;
import citibob.sql.SqlSet;
import citibob.sql.SqlTypeSet;
import citibob.sql.UpdTasklet;

/**
 *
 * @author citibob
 */
public abstract class SqlBufDbModel extends SchemaBufDbModel
{

/** @param proto True if we just want the columns. */
public abstract SqlSet getSelectSql(boolean proto);

/** We can override this to customize the class used for our SchemaBuf */
protected SchemaBuf newSchemaBuf(SqlRun str, SqlSet protoSql, SqlSchema[] typeSchemas, String[] keyFields, SqlTypeSet tset)
{
	return new SchemaBuf(str, protoSql, typeSchemas, keyFields, tset);
}
/** Creates a new instance of SqlBufDbModel
@param typeSchemas Schemas used to determine type of each column (beyond that from the SQL query)
@param updateKeyFields Which columns are update-key fields (beyond that as determined by typeSchemas).
@param sinfos Schemas/Tables used to send updates back to database */
protected void init(SqlRun str, final App app,
SqlSchema[] typeSchemas, String[] updateKeyFields, final SqlSchemaInfo[] updateSchemas)
{
	final SchemaBuf sb = new SchemaBuf(str, getSelectSql(true), typeSchemas, updateKeyFields, app.sqlTypeSet());
	str.execUpdate(new UpdTasklet() {
	public void run() {
		init(sb, null, updateSchemas, app.dbChange());
	}});
}

/** Creates a new instance of SqlBufDbModel
@param typeSchemas Schemas used to determine type of each column (beyond that from the SQL query)
@param updateKeyFields Which columns are key fields (beyond that as determined by typeSchemas).
@param sinfos Schemas/Tables used to send updates back to database */
public SqlBufDbModel(SqlRun str, final App app,
SqlSchema[] typeSchemas, String[] updateKeyFields, final SqlSchemaInfo[] updateSchemas)
	{ init(str,app,typeSchemas,updateKeyFields,updateSchemas); }


/** Read-only! */
public SqlBufDbModel(App app, String... sTypeSchemas)
{
	this(app.sqlRun(), app, sTypeSchemas, (String[])null, (String[])null);
}

protected SqlBufDbModel() {}

/** Convenience method.  Takes schemas as strings, looks them up in App */
public SqlBufDbModel(SqlRun str, App app,
String[] sTypeSchemas, String[] updateKeyFields, String[] sUpdateSchemas)
{
	this.init(str, app, sTypeSchemas, updateKeyFields, sUpdateSchemas);
}
/** Convenience method.  Takes schemas as strings, looks them up in App */
public void init(SqlRun str, App app,
String[] sTypeSchemas, String[] updateKeyFields, String[] sUpdateSchemas)
{
	SqlSchema[] typeSchemas = null;
	SqlSchemaInfo[] updateSchemas = null;

	if (sTypeSchemas != null) {
		typeSchemas = new SqlSchema[sTypeSchemas.length];
		for (int i=0; i<typeSchemas.length; ++i)
			typeSchemas[i] = app.getSchema(sTypeSchemas[i]);
	}
	if (sUpdateSchemas != null) {
		updateSchemas = new SqlSchemaInfo[sUpdateSchemas.length];
		for (int i=0; i<updateSchemas.length; ++i)
			updateSchemas[i] = new SqlSchemaInfo(app.getSchema(sUpdateSchemas[i]));
	}

	init(str, app, typeSchemas, updateKeyFields, updateSchemas);
}

//public void setKey(int ix, Object val) {}

//public void setKey(Object[] key)
//{
//	this.key = key;
//}

/** Get Sql query to re-select current records
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
public void doSelect(SqlRun str)
{
	sbuf.setRows(str, getSelectSql(false));
}


}
