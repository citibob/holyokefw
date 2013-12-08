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

/** General JType wrapper for Java classes */
public class JavaJType implements JType
{	
	boolean nullable = true;
	Class klass;
	
	public JavaJType(Class klass, boolean nullable) {
		this.klass = klass;
		this.nullable = nullable;
	}
	public JavaJType(Class klass) { this(klass, true); }

	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return klass; }

	public boolean isInstance(Object o)
	{
		if (klass.isPrimitive()) {
			return klass.isInstance(o);
		} else {
			return (klass.isInstance(o) || (nullable && o == null));
		}
	}

public String toString() { return "JavaJType(" + klass.getName() + ")"; }
// =================================================================
public static final JavaJType jtObject = new JavaJType(Object.class);
public static final JavaJType jtObjectNotNull = new JavaJType(Object.class, false);
public static final JavaJType jtInteger = new JavaJType(Integer.class);
public static final JavaJType jtIntegerNotNull = new JavaJType(Integer.class, false);
public static final JavaJType jtLong = new JavaJType(Long.class);
public static final JavaJType jtLongNotNull = new JavaJType(Long.class, false);
public static final JavaJType jtBoolean= new JavaJType(Boolean.class);
public static final JavaJType jtBooleanNotNull= new JavaJType(Boolean.class, false);
public static final JavaJType jtDouble= new JavaJType(Double.class);
public static final JavaJType jtDoubleNotNull= new JavaJType(Double.class, false);
public static final JavaJType jtString= new JavaJType(String.class);
public static final JavaJType jtStringNotNull= new JavaJType(String.class, false);
//public static final JavaJType jtFile= new JavaJType(File.class);
//public static final JavaJType jt= new JavaJType(.class);

}
