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

import java.sql.*;

/** TODO: Make a JType superclass of this; then figure out where JType could be used (basically
 * in citibob.swing.typed) and where SqlType is needed.  JType should have getObjeClass() and isInstance(). */
public interface JType
{
	/** Java class used to represent this type.  NOTE: not all instances of that Java
	 class will pass isInstance(). */
	Class getObjClass();

	/** Is an object an instance of this type?  NOTE: isInstance(null) tells
	 whether or not this field in the DB accepts null values. */
	boolean isInstance(Object o);
	
	/** Name of type in the database.  The format varies too much to bother describing it in more detail here.  However, this field can be used (for example) for JTypedTextField to discover length limits. */
//	String getDbType();

	/** Subclasses of this implement a compareTo() that compares only class.
	 * This is so we can use SqlType objects as a key in a HashMap. */
}
