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

import citibob.objserver.NamedSocket;
import citibob.reflect.ClassAnalyzer;
import citibob.reflect.ClassPathUtils;
import citibob.reflect.JarURL;
import citibob.reflect.ReflectUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;


public class MobileCodeClient {

protected String[] serverClasspath;
protected File sockFile;

public MobileCodeClient(File sockFile, String... serverClasspath) {
	this.sockFile = sockFile;
	this.serverClasspath = serverClasspath;
}


/** Sends a request, which is a serialized object w/ mobile code. */
public Socket open(Object obj) throws IOException
{
	MobileClass[] classes = getRequiredClasses(obj);

	// Send the request
	NamedSocket sock = new NamedSocket(sockFile);
	
	ObjectOutputStream oout = new ObjectOutputStream(sock.getOutputStream());
	oout.writeObject(classes);
	oout.writeObject(obj);
	oout.flush();
	
////	SerializationContext scon = new SerializationContext();
////	DataOutputStream out = new DataOutputStream(sock.getOutputStream());
////	ObjectOutputStream out = new ObjectOutputStream(
////		new BufferedOutputStream(sock.getOutputStream()));
////	out.writeObject(classes);
////	out.writeObject(obj);
//	scon.serialize(classes, out);
//	scon.serialize(obj, out);
//	out.flush();
	
	return sock;
}

/** Given an object to send to the server, figures out which classes to send with it. */
MobileClass[] getRequiredClasses(Object obj)
throws IOException
{
	// Get the classpath that the client does NOT share with the server.
	URLClassLoader loader = (URLClassLoader)ClassAnalyzer.class.getClassLoader();
	List<JarURL> classpath = ClassPathUtils.getClassPath(loader);
	ClassPathUtils.subtractCP(loader, classpath, serverClasspath);

System.out.println("Classpath...");
for (JarURL jurl : classpath) System.out.println("    " + jurl.getName() + " = " + jurl.toString());

	// Analyze our object...
	ClassAnalyzer ca = new ClassAnalyzer(loader, classpath);
	ca.addObjectsSeen(obj);
	ca.analyzeObjectsSeen();		// See what objects we will need to serialize
	ca.analyzeClassesSeen();		// Find classes required for those objects

	// Read the classes into a big array
System.out.println("Here are the classes being sent...");
	MobileClass[] mclasses = new MobileClass[ca.getClassesFound().size()];
	byte[] buf = new byte[8192];
	int ix=0;
	for (String name : ca.getClassesFound()) {		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		URL url = loader.getResource(ReflectUtils.classToResource(name));
		BufferedInputStream in = new BufferedInputStream(url.openStream());
		int n;
		while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
		in.close();
System.out.println("Sending class: " + name);
		mclasses[ix++] = new MobileClass(name, out.toByteArray());
	}
	return mclasses;
}

//public static void main(String[] args)
//throws ClassAnalyzerException, IOException
//{
//	URLClassLoader loader = (URLClassLoader)ClassAnalyzer.class.getClassLoader();
//	List<JarURL> fullCP = ClassPathTest.getClassPath(loader);
//	
//	ClassPathTest.subtractCP(loader, fullCP, "jooreports", "jasperreports");
//	
//	for (JarURL url : fullCP) {
//		System.out.println(url);
//	}
//	System.exit(0);
////	List<JarURL> classPath = Class
//	
//}

public static void main(String[] args) throws Exception
{
	MobileCodeClient client = new MobileCodeClient(new File("c:\\sock"),
		"holyokefw");
	Object obj = new TestObj();
	Socket sock = client.open(obj);
	Thread.currentThread().sleep(2000);
}

static public class TestObj implements Serializable {
	String hello = "Hoi";
}

}

