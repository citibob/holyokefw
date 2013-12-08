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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.mobilecode;

import citibob.reflect.ReflectUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

/**
 *
 * @author fiscrob
 */
public class MobileCodeLoader extends ClassLoader
{

TreeMap<String,MobileClass> classes = new TreeMap();

public void addClasses(MobileClass[] xclasses)
{
	for (MobileClass mclass : xclasses) classes.put(mclass.className, mclass);
}

public Class findClass(String name) throws ClassNotFoundException
{
	MobileClass mclass = classes.get(name);
	if (mclass == null) throw new ClassNotFoundException(name);
	return defineClass(name, mclass.bytecode, 0, mclass.bytecode.length);
}

public Class findClassFromParent(String name) throws ClassNotFoundException
{
	URL url = getParent().getResource(ReflectUtils.classToResource(name));
	if (url == null) return null;

	// Read the bytecode
	byte[] bytecode = null;
	try {
		byte[] buf = new byte[8192];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedInputStream in = new BufferedInputStream(url.openStream());
		int n;
		while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
		in.close();
		bytecode = out.toByteArray();
	} catch(IOException e) {
		ClassNotFoundException e2 = new ClassNotFoundException(name);
		e2.initCause(e);
		throw e2;
	}

	if (bytecode == null) throw new ClassNotFoundException(name);
	return defineClass(name, bytecode, 0, bytecode.length);
}


public Class<?> loadClass(String name) throws ClassNotFoundException {
//System.out.println("loadClass(" + name + ")");
	return loadClass(name, false);
}
/** Override normal delegation...
 See: http://tech.puredanger.com/2006/11/09/classloader/ */
protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
System.out.println("MobileCodeLoader.loadClass(" + name + ", " + resolve + ")");
//	if (!name.equals("citibob.mobilecode.MCObjectInputStream")) {
	if (!name.equals(MCObjectInputStream.class.getName())) {
		// Not necessarily delegation, just do deafault loadClass() for normal
		// delegation
		return super.loadClass(name, resolve);
	}

System.out.println("Loading from parent: " + name);
	// First check whether it's already been loaded, if so use it
	Class loadedClass = findLoadedClass(name);

	// Not loaded, try to load it
	if (loadedClass == null) {
		try {
			// Ignore parent delegation and just try to load locally
			loadedClass = findClassFromParent(name);
		} catch (ClassNotFoundException e) {
			// Swallow exception - does not exist locally
		}

//		// If not found locally, use normal parent delegation in URLClassloader
//		if (loadedClass == null) {
//			// throws ClassNotFoundException if not found in delegation hierarchy at all
//			loadedClass = super.loadClass(name);
//		}
	}
	// will never return null (ClassNotFoundException will be thrown)
	return loadedClass;
}

///** Override normal delegation...
// See: http://tech.puredanger.com/2006/11/09/classloader/ */
//public Class<?> loadClass(String name) throws ClassNotFoundException {
//	// First check whether it's already been loaded, if so use it
//	Class loadedClass = findLoadedClass(name);
//
//	// Not loaded, try to load it
//	if (loadedClass == null) {
//		try {
//			// Ignore parent delegation and just try to load locally
//			loadedClass = findClass(name);
//		} catch (ClassNotFoundException e) {
//			// Swallow exception - does not exist locally
//		}
//
//		// If not found locally, use normal parent delegation in URLClassloader
//		if (loadedClass == null) {
//			// throws ClassNotFoundException if not found in delegation hierarchy at all
//			loadedClass = super.loadClass(name);
//		}
//	}
//	// will never return null (ClassNotFoundException will be thrown)
//	return loadedClass;
//}

protected URL findResource(String name)
{
	try {
		return new URL("http://resource/" + name);
	} catch(MalformedURLException e) {
		e.printStackTrace();
		return null;
	}
}

public InputStream getResourceAsStream(String name)
{
	InputStream in = getParent().getResourceAsStream(name);
	if (in == null) {
		name = ReflectUtils.resourceToClass(name);
		MobileClass mclass = classes.get(name);
		if (mclass != null) in =  new ByteArrayInputStream(mclass.bytecode);
	}
System.out.println("MobileCodeLoader.getResourceAsStream(" + name + ") = " + in);
	return in;
}


}
