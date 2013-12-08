/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.io.IOUtils;
import citibob.io.RecursiveDirIterator;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

/**
 *
 * @author citibob
 */
public class DirConfig extends BaseConfig
implements ListableConfig, WriteableConfig
{

File root;

public DirConfig(File root, String name)
{
	super(name);
	this.root = root;

}
public DirConfig(File root)
{
	this(root, root.getAbsolutePath());

}
public File getRoot() { return root; }

public InputStream openStream(String name)
throws IOException
{
	File f = new File(root, name);
	InputStream ret;
	if (!f.exists()) ret = null;
	else ret = new FileInputStream(f);
System.out.println("DirConfig.openStream(" + f + ") = " + ret + "(root = " + root + ")");
	return ret;
}

public Iterator<String> listStreams()
{
	return new IOUtils.RelativeIterator(root,
		new RecursiveDirIterator(root, new NoTildeFilter()));
}

public void add(String name, byte[] bytes)
throws IOException
{
	File outFile = new File(root, name);
	outFile.getParentFile().mkdirs();
	OutputStream out = new FileOutputStream(outFile);
	out.write(bytes);
	out.close();
}

static class NoTildeFilter implements FileFilter {
public boolean accept(File pathname) {
	if (pathname.getName().endsWith("~")) return false;
	return true;
}}


}
