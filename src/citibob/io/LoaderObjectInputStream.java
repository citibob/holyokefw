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
package citibob.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/** An ObjectInputStream that loads its classes from a specified classloader. */
public class LoaderObjectInputStream extends ObjectInputStream
{
ClassLoader loader;

public LoaderObjectInputStream(InputStream in, ClassLoader loader) throws IOException {
	super(in);
	this.loader = loader;
}

// =========================================================================
// Code below this line is copied (with slight modification) from Sun's Java 1.5 sources.


    /** table mapping primitive type names to corresponding class objects */
    private static final HashMap primClasses = new HashMap(8, 1.0F);
    static {
	primClasses.put("boolean", boolean.class);
	primClasses.put("byte", byte.class);
	primClasses.put("char", char.class);
	primClasses.put("short", short.class);
	primClasses.put("int", int.class);
	primClasses.put("long", long.class);
	primClasses.put("float", float.class);
	primClasses.put("double", double.class);
	primClasses.put("void", void.class);
    }

    protected Class<?> resolveClass(ObjectStreamClass desc)
	throws IOException, ClassNotFoundException
    {
	String name = desc.getName();
	try {
//	    return Class.forName(name, false, latestUserDefinedLoader());
	    return Class.forName(name, false, loader);
	} catch (ClassNotFoundException ex) {
	    Class cl = (Class) primClasses.get(name);
	    if (cl != null) {
		return cl;
	    } else {
		throw ex;
	    }
	}
    }

    protected Class<?> resolveProxyClass(String[] interfaces)
	throws IOException, ClassNotFoundException
    {
//	ClassLoader latestLoader = latestUserDefinedLoader();
	ClassLoader latestLoader = loader;
	ClassLoader nonPublicLoader = null;
	boolean hasNonPublicInterface = false;

	// define proxy in class loader of non-public interface(s), if any
	Class[] classObjs = new Class[interfaces.length];
	for (int i = 0; i < interfaces.length; i++) {
	    Class cl = Class.forName(interfaces[i], false, latestLoader);
	    if ((cl.getModifiers() & Modifier.PUBLIC) == 0) {
		if (hasNonPublicInterface) {
		    if (nonPublicLoader != cl.getClassLoader()) {
			throw new IllegalAccessError(
			    "conflicting non-public interface class loaders");
		    }
		} else {
		    nonPublicLoader = cl.getClassLoader();
		    hasNonPublicInterface = true;
		}
	    }
	    classObjs[i] = cl;
	}
	try {
	    return Proxy.getProxyClass(
		hasNonPublicInterface ? nonPublicLoader : latestLoader,
		classObjs);
	} catch (IllegalArgumentException e) {
	    throw new ClassNotFoundException(null, e);
	}
    }

	
	

}
