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
///*
// * Licensor.java
// *
// * Created on July 12, 2007, 12:20 AM
// *
// * To change this template, choose Tools | Template Manager
// * and open the template in the editor.
// */

package citibob.licensor;

//import gnu.classpath.CPStreamTokenizer;
//import static gnu.classpath.CPStreamTokenizer.*;
import java.io.*;

/**
 *
 * @author citibob
 */
public class Licensor
{


static final int S_INIT = 0;
static final int S_SLASH1 = 1;
static final int S_INCOMMENT = 2;
static final int S_STAR2 = 3;

//StringBuffer comment;
String license;

String[] suffixes = new String[] {".c", ".cpp", ".java", ".mvc"};

public Licensor() throws IOException
{
	license = "/*\n" + readLicenseFile(getClass(), "license.txt") + "\n*/";
}

public static String readLicenseFile(Class klass, String licenseResourceName)
throws java.io.IOException
{
	String resourceName = klass.getPackage().getName().replace('.', '/') + "/" + licenseResourceName;
	Reader in = null;

System.out.println("Reading license resource: " + resourceName);
	in = new InputStreamReader(klass.getClassLoader().getResourceAsStream(resourceName));
	StringWriter out = new StringWriter();
	char[] buf = new char[8192];
	int len;
	while ((len = in.read(buf)) > 0) out.write(buf,0,len);
	in.close();
	out.close();
	String license = out.getBuffer().toString();
	return license;
}

public void relicenseDir(File dir) throws IOException
{
	if (dir.isHidden() || dir.getName().startsWith(".")) return;
	if (dir.isDirectory()) {
		String[] children = dir.list();
		for (int i=0; i<children.length; i++) {
			relicenseDir(new File(dir, children[i]));
		}
	} else {
		String name = dir.getName();
		for (int i=0; ; ++i) {
			if (i >= suffixes.length) return;	// not a valid suffix to relicense
			if (name.endsWith(suffixes[i])) break;	// good suffix
		}
		relicenseFile(dir);
	}
}

/** Creates a new instance of Licensor */
public void relicenseFile(File f) throws IOException
{
//	System.out.println("Relicensing " + f);
	FileReader in = new FileReader(f);
	int state = S_INIT;
	
	StringBuffer comment = new StringBuffer();
	int c;
outer :
	// Grab the first comment
	for (;;) {
//		if ((c = in.read()) < 0) return;	// no comments; prepend
		if ((c = in.read()) < 0) break outer;	// no comments add 
		switch(state) {
			case S_INIT :
				if (c == '/') state = S_SLASH1;
				break;
			case S_SLASH1 :
				if (c == '*') {
					state = S_INCOMMENT;
					comment.append("/*");
				}
				else state = S_INIT;
				break;
			case S_INCOMMENT :
				if (c == '*') state = S_STAR2;
				else comment.append((char)c);
				break;
			case S_STAR2 :
				if (c == '/') {
					comment.append("*/");
					// Finished finidng first comment
					break outer;
				} else {
					comment.append('*');
					comment.append((char)c);
					state = S_INCOMMENT;
				}
			break;
		}
	}

	// Evaluate that comment
	String scom = comment.toString();
	if (license.equals(scom)) {
//		System.out.println("    No relicensing needed for " + f);
		return;		// Nothing to do, file already up to date
	}
	if (scom.contains("NOTE: Third Party Code")) {
		// Licensed by someone else --- don't do anything.
		return;
	}
	
	System.out.println("Relicensing " + f);
	File fnew = new File(f.getPath() + ".__tmp");
	FileWriter out = new FileWriter(fnew);
	out.write(license);
	if (scom.contains("GNU General Public License")) {
		// It's a license comment --- remove it (and everything before it) and replace with our new license
	} else {
		out.write('\n');
		// It's not a license comment --- go back and prepend our license to the file
		in.close();
		in = new FileReader(f);
	}
	
	// Develop the final source file
	char[] buf = new char[8192];
	int len;
	while ((len = in.read(buf)) > 0) out.write(buf,0,len);
	in.close();
	out.close();

	// Replace original source file with new one
	f.delete();
	fnew.renameTo(f);
}

public static void main(String[] args) throws Exception
{
	Licensor l = new Licensor();
//	l.relicenseDir(new File("../../src/main/java/citibob"));	// Works for NetBeans running Maven project
	l.relicenseDir(new File("/export/home/citibob/mvn/holyokefw/src/main/java/citibob"));	// Works for NetBeans running Maven project
//	l.relicenseDir(new File("/home/citibob/svn/jschema/src/main/java/citibob"));
//	l.relicenseFile(new File("/home/citibob/svn/jschema/src/citibob/licensor/Licensor.java"));
//	l.relicenseDir(new File("/export/home/citibob/tmp/svnmac/jschema/src/citibob"));
}

}
