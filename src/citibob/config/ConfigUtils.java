/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.io.IOUtils;
import citibob.template.Template;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 *
 * @author citibob
 */
public class ConfigUtils {
/** Merges config into base.
 */
public static void merge(WriteableConfig base, ListableConfig config) throws IOException
{
	MultiConfig mconfig = new MultiConfig(base, config);
	for (Iterator<String> ii = config.listStreams(); ii.hasNext();) {
		String name = ii.next();
		base.add(name, mconfig.getStreamBytes(name));
	}
//		
//		
//		String name = ii.next();
//		
//		if (name.endsWith(".properties")) {
//			InputStream in;
//			
//			in = 
//			InputStream in = base.openStream(name);
//			if (in == null) {
//				// Higher priority version doesn't have this, use lower priority version
//				base.add(name, config.getStreamBytes(name));
//			} else {
//				in.close();
//			}
//			
////			// Need to merge new low-priority properties with existing
////			// high-priority properties
////			Properties props = new Properties();
////			config.loadProperties(name, props);
////			base.loadProperties(name, props);
////			
////			// Now write out properties files
////			ByteArrayOutputStream out = new ByteArrayOutputStream();
////			props.store(out, "Merged properties file");
////			out.close();
////
////			// Store it back
////			base.add(name, out.toByteArray());
//			
//		} else {
//			InputStream in = base.openStream(name);
//			if (in == null) {
//				// Higher priority version doesn't have this, use lower priority version
//				base.add(name, config.getStreamBytes(name));
//			} else {
//				in.close();
//			}
//		}
//	}
}


/**
 * 
 * @param baseDir
 * @param inDir
 * @param outDir
 * @param map Use these values on any templates you find in the directory.
 * @param includeDirs
 * @param includeFiles
 * @param templateFiles
 * @throws java.io.IOException
 */
public static void copy(ListableConfig src, WriteableConfig dest,
Map<String,Object> map)
throws IOException
{
	for (Iterator<String> ii = src.listStreams(); ii.hasNext();) {
		String name = ii.next();
		if (map != null && name.endsWith(".template")) {
			Reader in = new InputStreamReader(src.openStream(name));
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			Writer out = new OutputStreamWriter(bout);
			Template.write(in, out, map);
			in.close();
			out.close();
			
			// Add in the new template-filled version
			String outName = name.substring(0, name.length() - ".template".length());
			dest.add(outName, bout.toByteArray());
		} else {
			dest.add(name, src.getStreamBytes(name));
		}
	}
}



}
