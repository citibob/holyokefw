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

import java.util.*;
import java.sql.*;
import citibob.jschema.log.*;
import citibob.sql.*;

/** A DbModel that controls many sub DbModels */
public class MultiDbModel implements DbModel
{

ArrayList<DbModel> models = new ArrayList();
protected boolean inSelect;

public boolean inSelect() { return inSelect; }

public MultiDbModel() {}
public MultiDbModel(DbModel... mm) {init(mm); }
public void init(DbModel... mm)
{
	for (DbModel m : mm) add(m);
}
public DbModel getModel(int ix) { return models.get(ix); }
// ---------------------------------------------------
public void add(DbModel m)
	{ models.add(m); }
public void remove(DbModel m)
	{ models.remove(m); }
// ---------------------------------------------------
private int getStatus()
	{ return 0; }	// Not needed, we're overriding doUpdate() and doDelete().

// ---------------------------------------------------
public void doUpdate(SqlRun str)
//throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doUpdate(str);
	}
}

// public void doSimpleUpdate(SqlRunner str)
//
// {
// 	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
// 		DbModel m = (DbModel)ii.next();
// 		m.doSimpleUpdate(st);
// 	}
// }

public void doDelete(SqlRun str)
//throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doDelete(str);
	}
}
// public void doSimpleDelete(SqlRunner str)
//
// {
// 	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
// 		DbModel m = (DbModel)ii.next();
// 		m.doSimpleDelete(st);
// 	}
// }
// ---------------------------------------------------
//public void doInit(SqlRunner str)
////throws java.sql.SQLException
//{
//	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
//		DbModel m = (DbModel)ii.next();
//		m.doInit(str);
//	}
//}
public void doSelect(SqlRun str)
//throws java.sql.SQLException
{
	inSelect = true;
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doSelect(str);
	}
	inSelect = false;
}
public void doInsert(SqlRun str)
//throws java.sql.SQLException
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doInsert(str);
	}
}
public boolean valueChanged()
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		if (m.valueChanged()) return true;
	}
	return false;
}
public void doClear()
{
	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
		DbModel m = (DbModel)ii.next();
		m.doClear();
	}
}
//public void setKey(Object[] key)
//{
//	for (Iterator ii = models.iterator(); ii.hasNext(); ) {
//		DbModel m = (DbModel)ii.next();
//		m.setKey(key);
//	}	
//}
// ---------------------------------------------------
//protected int intKey;
///** This method will only work if all our sub-models are IntKeyedDbModel. */
//public void setKey(Integer ID)
//{
//	intKey = (ID == null ? -1 : ID);
////	if (ID != null) intKey = ID;
//	setKey(new Integer[] {ID});
//}
///** This method will only work if all our sub-models are IntKeyedDbModel. */
//public void setKey(int id)
//{
//	intKey = id;
//	setKey(new Integer[] {id});
//}
//public int getIntKey()
//{ return intKey; }


public void setSelectKeyFields(String... keyFields)
{
	for (DbModel m : models) m.setSelectKeyFields(keyFields);
}
public void setKeys(Object... keys)
{
	for (DbModel m : models) m.setKeys(keys);	
}

/** Sets just the first key field (most common case) */
public void setKey(Object key)
{
	for (DbModel m : models) m.setKey(key);	
}

/** Sets a given key field */
public void setKey(int ix, Object key)
{
	for (DbModel m : models) m.setKey(ix, key);
}

/** Sets a given key field */
public void setKey(String name, Object key)
{
	for (DbModel m : models) m.setKey(name, key);
}

// ---------------------------------------------------
public Object getKey(int ix) { return models.get(0).getKey(ix); }
public Object getKey() { return models.get(0).getKey(); }
public Object getKey(String name) { return models.get(0).getKey(name); }

// ---------------------------------------------------
/** Convenience method */
protected void logadd(QueryLogger logger, SchemaBufDbModel m)
{
	add(m);
	m.setLogger(logger);
}
}
