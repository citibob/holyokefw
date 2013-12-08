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

/** Data storage for SchemaBuf --- represents one SQL record. */
public class Row
{

private Object[] data;
/** The "original" version of this data.  Used to see if values have changed. */
private Object[] origData;

public Row(SqlSchema s, int ncols)
{
	data = new Object[ncols];
	origData = new Object[ncols];
}
// --------------------------------------------
public Object getValue(int col)
	{ return data[col]; }
public void setValue(int col, Object val)
	{ data[col] = val; }

// --------------------------------------------

}
