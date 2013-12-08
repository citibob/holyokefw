/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 *
 * @author citibob
 */
public class MemConfig extends BaseConfig
implements ListableConfig, WriteableConfig
{
	
private Map<String,byte[]> streams = new TreeMap();

// Our properties files, already read-in and interpreted.
//Map<String,Properties> properties = new TreeMap();

public MemConfig() {
	super(null);
}

/** Stores a StreamSet in memory... */
public InputStream openStream(String name) throws IOException
{
	InputStream ret;
	byte[] bytes = streams.get(name);
	if (bytes == null) ret = null;
	else {
		ret = new ByteArrayInputStream(bytes);
	}
//System.out.println("MemConfig.openStream(" + name + ") = " + ret);
	return ret;
}

public byte[] getStreamBytes(String name) throws IOException
{
	return streams.get(name);
}

public int size() { return streams.size(); }

public Iterator<String> listStreams() {
	return streams.keySet().iterator();
}
// ============================================================
public void add(String name, byte[] bytes)
{
	streams.put(name,bytes);
}

}
