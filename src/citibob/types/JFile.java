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
package citibob.types;

import java.io.File;

/** For enumerate types... Nullable depends on KeyedModel.
This REALLY IS different from SqlEnum!!! */
public class JFile implements JType
{
	boolean nullable;
	protected javax.swing.filechooser.FileFilter filter;
	protected File defaultDir;		// Default place we'll chose from
	boolean chooseDirs;				// true if we're selecting directories.
	
	public javax.swing.filechooser.FileFilter getFilter() { return filter; }
	public File getDefaultDir() { return defaultDir; }
	
	public boolean isDir() { return chooseDirs; }
	
	/** nullText = string to use for null value, or else <null> if this is not nullable. */
	public JFile(javax.swing.filechooser.FileFilter filter, File defaultDir, boolean nullable,
	boolean chooseDirs)
	{
		this.nullable = nullable;
		this.filter = filter;
		this.defaultDir = defaultDir;
		this.chooseDirs = chooseDirs;
	}
	/** nullText = string to use for null value, or else <null> if this is not nullable. */
	public JFile(javax.swing.filechooser.FileFilter filter, File defaultDir, boolean nullable)
	{
		this(filter, defaultDir, nullable, false);
	}
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return File.class; }

	public boolean isInstance(Object o)
	{
		if (o == null) return nullable;
		return (o instanceof File);
	}

}
