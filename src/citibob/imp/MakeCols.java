/*
OffstageArts: Enterprise Database for Arts Organizations
This file Copyright (c) 2005-2008 by Robert Fischer

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

package citibob.imp;

import citibob.reflect.ClassPathUtils;
import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a CSV file, and creates a Java file that defines its columns
 * @author citibob
 */
public class MakeCols {

static String[] getTableNames(File dir)
{
	File[] files = dir.listFiles();
	List<String> names = new ArrayList();
	for (File f : files) {
		String name = f.getName();
		if (!name.endsWith(".csv")) continue;
		names.add(name.substring(0, name.length()-4));
	}
	String[] ret = new String[names.size()];
	names.toArray(ret);
	return ret;
}


public static void makeCols(File csvDir, String[] tableNames,
String[] classNames,
File srcRootDir, String packageName)
throws IOException
{
	for (int i=0; i<tableNames.length; ++i) {
		String tableName = tableNames[i];
		String className = classNames[i];
		MakeCols.makeCols(csvDir, tableName, className, srcRootDir, packageName);
	}
}

/** @param csvDir Directory containing a bunch of CSV files to import.
 * @param srcRootDir Root of Java source tree in which to write
 * @param packageName Name of package of output file
 * @param tableName Name of table we're going to read (sans .csv)
 */
public static void makeCols(File csvDir, String tableName, String className,
File srcRootDir, String packageName)
throws IOException
{
	className = (className == null ? tableName : className);
	File csvFile = new File(csvDir, tableName + ".csv");
	File javaFileDir = new File(srcRootDir, packageName.replace('.', File.separatorChar));
	File javaFile = new File(javaFileDir, className + ".java");
	makeCols(csvFile, javaFile, packageName, className);
}

public static void makeCols(File csvFile, File javaFile,
String packageName, String className) throws IOException
{
	// Open output
	Writer out = new FileWriter(javaFile);

	out.write(
		"package " + packageName + ";\n" +
		"public interface " + className + " {\n");

	// Open input
	CSVParser csv = new CSVParser(new FileReader(csvFile));
	String[] colNames = csv.getLine();
	csv.close();

	// Write out constants
	for (int i=0; i < colNames.length; ++i) {
		String name = colNames[i];
		StringBuffer oname = new StringBuffer();

		if (Character.isDigit(name.charAt(0))) oname.append('_');
		boolean lastUnderscore;
		for (int j=0; j<name.length(); ++j) {
			lastUnderscore = false;
			switch(name.charAt(j)) {
				case ' ' :
				case '-' :
				case '_' :
				case '(' :
				case '.' :
					if (!lastUnderscore) {
						oname.append('_');
						lastUnderscore = true;
					}
					break;
				case '#' :
					oname.append("num_"); break;
				case ')' :
				case '/' :
				case ',' :
				case '\n' :
				case ':' :
				case '?' :
				case '\'' :
					break;
				default :
					oname.append(name.charAt(j)); break;
			}
		}
		out.write(
			"\tpublic static final int " + oname.toString() + " = " + i + ";\n");
	}

	out.write("}\n");
	out.close();

}

	
	
	
	
	
	
	
	
	
public static void main(String[] args) throws Exception
{
	File root = ClassPathUtils.getMavenProjectRoot();
	File csvDir = new File(root, "../2008NutcrackerAudition");
	String[] tableNames = new String[] {"nutcast2"};
	String[] classNames = new String[] {null};
	File srcRootDir = new File(root, "src/main/java");
	String packageName = "schemas";
	
	makeCols(csvDir, tableNames, classNames, srcRootDir, packageName);
}


}
