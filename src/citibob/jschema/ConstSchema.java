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

public class ConstSchema implements Schema
{

protected Column[] cols;

public ConstSchema() {}
public ConstSchema(Column[] cols)
{
	this.cols = cols;
}

public int size()
	{ return cols.length; }
/** @Deprecated */
public int getColCount()
	{ return cols.length; }

public Column getCol(int colNo)
	{ return cols[colNo]; }
public Column getCol(String col)
{
	int ix = findCol(col);
	return (ix < 0 ? null : getCol(ix));
}
//public ColIterator colIterator()
//	{ return new MyColIterator(); }

public int findCol(String name)
{
	for (int i = 0; i < cols.length; ++i) {
		if (cols[i].getName().equals(name)) return i;
	}
	System.out.println("ConstSchema.findCol(" + name + ") == -1 (not found)!");
	return -1;
}
//public int findColByLabel(String label)
//{
//	for (int i = 0; i < cols.length; ++i) {
//		if (cols[i].label.equals(label)) return i;
//	}
//	return -1;
//}

/** Used to subclass schemas and append columns to them. */
protected void appendCols(Column[] add)
{
	Column[] newcols = new Column[cols.length + add.length];
	for (int i = 0; i < cols.length; ++i) newcols[i] = cols[i];
	for (int i = 0; i < add.length; ++i) newcols[i + cols.length] = add[i];
	cols = newcols;
}

// ===================================================
public void copyFrom(Schema[] typeSchemas)
{
	if (typeSchemas == null) return;
	
	for (int i=0; i<typeSchemas.length; ++i) {
		Schema schema = typeSchemas[i];
		copyFrom(schema);
	}	
}

/** Looks for corresponding names, and sets all column types the same
 as same-named columns in schema. */
public void copyFrom(Schema schema)
{
	for (int i=0; i<schema.size(); ++i) {
		SqlCol scol = (SqlCol)schema.getCol(i);
		int j = findCol(scol.getName());
		if (j < 0) continue;
		SqlCol tcol = (SqlCol)this.getCol(j);
		tcol.copyFrom(scol);
	}
}

//// ===============================================
//private class MyColIterator implements ColIterator
//{
//	int i;
//	public boolean hasNext()
//		{ return (i < cols.length); }
//	public Column next()
//		{ return cols[i++]; }
//}
// --------------------------------------------------------



}
