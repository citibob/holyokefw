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

import java.sql.*;
import citibob.sql.*;

/** Implements smart update and delete operations... */
public interface DbModel
{

// This method is like a constructor; will always differ.
// in every implementation.
// void setKey()

///** Initialize component to receive data.  Might be needed if some kind of database lookup is needed. */
//void doInit(SqlRunner str);

/** Changes the intrinsic key fields representing the row(s) this DbModel
 will select.  Not for external use. */
public void setSelectKeyFields(String... keyFields);

public void setKeys(Object... keys);

/** Sets just the first key field (most common case) */
public void setKey(Object key);

/** Sets a given key field */
public void setKey(int ix, Object key);

/** Sets a given key field */
public void setKey(String name, Object key);

//public Object[] getKeys();
public Object getKey(int ix);
public Object getKey();
public Object getKey(String name);

/** Get Sql query to re-select current record
* from database.  When combined with an actual
* database and the SqlDisplay.setSqlValue(), this
* has the result of refreshing the current display. */
void doSelect(SqlRun str);



/** Have any of the values here changed and not stored in the DB?  I.e. would calling doUpdate() cause any changes to the database? */
boolean valueChanged();

/** Get Sql query to flush updates to database.
* Only updates records that have changed; returns null
* if nothing has changed. */
void doUpdate(SqlRun str);

/** Get Sql query to insert record into database,
* assuming it isn't already there. */
void doInsert(SqlRun str);

/** Get Sql query to delete current record. */
void doDelete(SqlRun str);

/** Clear all buffered data from this component.  Then there
is no current record. */
void doClear();


}
