/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Coverts a config directory into a zips file.
 * @author citibob
 */
public class WriteZips {
public static void main(String[] args) throws Exception
{
	if (args.length < 2) {
		System.out.println("Usage: WriteZips <config dir> <config zips file> [password]");
		System.exit(0);
	}
	
	File configDir = new File(args[0]);
	File configsFile = new File(args[1]);
	char[] password = null;
	if (args.length >= 3) {
		password = args[2].toCharArray();
	}
	
	// Write it out!
	ListableConfig config = new DirConfig(configDir);
	OutputStream out = new FileOutputStream(configsFile);
	ZipConfig.writeToStream(config, out);
	out.close();
//	ZipConfigWriter zzout = new ZipConfigWriter(out, password);
//	zzout.writeDir(configDir, null, null, null);
//	zzout.close();
	
	// Add in JKS files...
	// Parse properties files to figure out which JKS files to use.
	// (Use a base properties files from server directory)
}

}
