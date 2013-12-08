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

public interface SqlBuf extends SqlGen
{

// ============================================================
// Manage rows

// Getting and setting individual values in the SqlBuf is NOT a standard
// part of the interface.  It must be done by implementations in an
// implementation-specific manner.

/** Returns one row in the buffer (but external class cannot change). */
// SqlRow getRow(int row)
//public void setValueAt(Object val, int rowIndex, int colIndex)
//public Object getValueAt(int rowIndex, int colIndex)


/** Clears all rows in the buffer, sets getRowCount() to 0 */
void clear();

/** Manipulate status (DELETED & INSERTED bits) of a row. */
public void setStatus(int row, int status);

/** Mark a row as deleted */
//void setDeleted(int rowIndex, boolean deleted);

/** Add a new row to the buffer, and mark it as inserted.  All data are initialized to null. */
int insertRow(int rowIndex);

}
