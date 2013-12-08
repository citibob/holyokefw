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

import citibob.swing.TableRowModel;

public class SchemaBufRowModel
extends TableRowModel
implements SchemaRowModel
{
	SchemaBuf schemaBuf;

	public SchemaBufRowModel(SchemaBuf buf)
	{
		super(buf);
		schemaBuf = buf;
	}

	/** Should we listen to the underlying CitibobTableModel for insert/delete events?
It's appropriate to be false only when this has been used as a JTable's
ListSelectionModel. */
	public SchemaBufRowModel(SchemaBuf buf, boolean listen)
	{
		super(buf,listen);
		schemaBuf = buf;
	}
	public Schema getSchema()
	{
		SchemaBuf buf = (SchemaBuf)getTableModel();
		return buf.getSchema();
	}
	public Object getOrigValue(int col)
		{ return schemaBuf.getOrigValueAt(getCurRow(), col); }
	public void resetValue(int col)
		{ set(col, getOrigValue(col)); }
	public boolean valueChanged(int col)
		{ return schemaBuf.valueChanged(getCurRow(), col); }

}
