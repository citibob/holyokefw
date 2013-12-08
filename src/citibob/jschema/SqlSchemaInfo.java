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
 * SchemaInfo.java
 *
 * Created on December 30, 2007, 10:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

/** Stuff going with a SqlSchema in a SchemaBuf2Writer */
public class SqlSchemaInfo {
	public SqlSchema schema;	// Schemas used for SQL update generation against sbuf
	public String table;	// Tables to generate SQL for each...
	public int[] schemaMap;	// Column i in schema = column colmap[i] in sbuf.getSchema().  User need not set.
	public SqlSchemaInfo(SqlSchema schema, String table) {
		this.schema = schema;
		//if (table == null) table = schema.getDefaultTable();
		this.table = table;
	}
	public SqlSchemaInfo(SqlSchema schema) {
		this(schema, schema.getDefaultTable());
	}
}
