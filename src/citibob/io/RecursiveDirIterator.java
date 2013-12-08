/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Iterates over all the files in a directory.  ls -lR
 * @author citibob
 */
public class RecursiveDirIterator implements Iterator<File>
{

FileFilter fileFilter;	// Return just these files and directories
//FileFilter dirFilter;	// Recurse down just these directories	

LinkedList<File> seen;		// Files & directories we've seen but not yet processed

//// Our progress at one particular level
//static class Frame {
//	File[] files;
//	int ix;
//}


public RecursiveDirIterator(File dir, FileFilter fileFilter)
{
	if (fileFilter == null) fileFilter =
		new FileFilter() {
			public boolean accept(File file) { return true; }
		};
	this.fileFilter = fileFilter;
	
	seen = new LinkedList();
	seen.add(dir);
	processDirs();
}

public boolean hasNext() {
	return seen.size() > 0;
}

public File next()
{
	File ret = seen.removeLast();
	processDirs();
	return ret;
}

private void processDirs()
{
	// Convert any directories to their list of files
	for (;;) {
		if (seen.size() == 0) break;
		if (seen.getLast().isFile()) break;
		File dir = seen.removeLast();
		File[] files = dir.listFiles(fileFilter);
if (files == null) System.out.println("Directory " + dir + " has no files!");
		if (files != null) for (File f : files) seen.addFirst(f);
	}
}

public void remove() {
	throw new UnsupportedOperationException("Not supported yet.");
}
// ===========================================
public static void main(String[] args)
{
	RecursiveDirIterator rdi = new RecursiveDirIterator(
		new File("/export/home/citibob/mvn/offstagearts/src"), null);
	while (rdi.hasNext()) {
		System.out.println(rdi.next());
	}
}
}
