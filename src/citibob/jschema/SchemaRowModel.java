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

import citibob.swing.RowModel;

 /** A row model that is attached to data associated with one SqlSchema.
  * The columns in the RowModel must match EXACTLY the columns in the schema.
  * Thus, schema.findCol() returns column indices that are relevant
  * to the SchemaRowModel.
  * NOTE: (x instanceof SchemaRowModel) ==> (x instanceof TableRowModel) */
public interface SchemaRowModel extends RowModel
{
	Schema getSchema();
	Object getOrigValue(int col);
	int getCurRow();
}
