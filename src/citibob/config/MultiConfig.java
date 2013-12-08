/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Stores a set of StreamSets, in order of decreasing priority.
 * @author citibob
 */
public class MultiConfig extends BaseConfig
{

ArrayList<Config> configs;

public MultiConfig()
{
	super(null);
	configs = new ArrayList();
}
public Iterator<Config> iterator() { return configs.iterator(); }

public MultiConfig(Config... configs)
{
	this();
	for (Config config : configs) add(config);
	setNameFromFirst();
}
public void add(Config config)
	{ configs.add(config); }
public Config get(int i)
	{ return configs.get(i); }
public int size() { return configs.size(); }

/** Opens the named stream from the first (highest priority) StreamSet that has it. */
public InputStream openStream(String name) throws IOException
{
	if (name.endsWith(".properties")) {
		byte[] bytes = getStreamBytes(name);
		if (bytes == null) return null;
		return new ByteArrayInputStream(bytes);
	} else {
		for (int i=0; i<size(); ++i) {
			InputStream in = get(i).openStream(name);
			if (in != null) return in;
		}
		return null;
	}
}

/** Gets bytes from the named stream from the first (highest priority) StreamSet that has it. */
public byte[] getStreamBytes(String name) throws IOException
{
	if (name.endsWith(".properties")) {
		// Concatenate all files together, starting with lowest
		// priority first.
		int nfound = 0;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		Writer wout = new OutputStreamWriter(bout);
		for (int i=size()-1; i >= 0; --i) {
			Config config = get(i);
			wout.write("\n# ------ " + config.getName() + "\n");
			byte[] bytes = config.getStreamBytes(name);
			if (bytes == null) continue;
			bout.write(bytes);
			wout.flush();
			++ nfound;
		}
		return (nfound == 0 ? null : bout.toByteArray());
	} else {
		for (int i=0; i<size(); ++i) {
			byte[] bytes = get(i).getStreamBytes(name);
			if (bytes != null) return bytes;
		}
		return null;	
	}
}

///** Retrieves a composite properties file from the Config. */
//public boolean loadProperties(String name, Properties props)
//throws IOException
//{
//	int nloaded = 0;
//	for (int i=size()-1; i >= 0; --i) {
//		Config sset = get(i);
//		
//		// Load properties file
//		Properties nprops = new Properties();
//		if (sset.loadProperties(name, nprops)) ++nloaded;
//			
//		// Copy to our master properties
//		for (Entry en : nprops.entrySet()) {
//			String key = (String)en.getKey();
//			String val = (String)en.getValue();
//			props.setProperty(key, val);
//		}
//	}
//	return (nloaded > 0);
//}


public static MultiConfig merge(MultiConfig... configs)
{
	MultiConfig ret = new MultiConfig();
	ret.setName(configs[0].getName());
	for (MultiConfig config : configs) {
		for (Config c : config.configs) ret.add(c);
//		ret.addAll(config);
	}
	return ret;
}

// =====================================================
/** Read configurations from config server */
public static MultiConfig loadFromStream(InputStream in)
throws IOException
{
	MultiConfig config = new MultiConfig();
	
	// Load the configurations over the port
	for (;;) {
		try {
			Config sset = ZipConfig.loadFromStream(in);
			if (sset == null) break;
			config.add(sset);
		} catch(EOFException e) {
			break;
		}
	}
	in.close();
	
	config.setNameFromFirst();
	return config;
}

void setNameFromFirst()
//throws IOException
{
	setName(get(0).getName());
}

/** Read configurations from launcher on local machine */
public static MultiConfig loadFromFile(File file)
throws UnknownHostException, IOException
{
	InputStream in = new FileInputStream(file);
	MultiConfig ret = loadFromStream(in);
	ret.setName(file.getName());
	return ret;
}
/** Read configurations from launcher on local machine */
public static MultiConfig loadFromLauncher(InetAddress host, int port)
throws UnknownHostException, IOException
{
	if (host == null) host = InetAddress.getLocalHost();
	Socket sock = new Socket(host, port);
	InputStream in = sock.getInputStream();
	return loadFromStream(in);
}

/** Read configurations from config server (via SSL Socket) */
public static MultiConfig loadFromConfigServer(InetAddress host, int port)
throws UnknownHostException, IOException
{
	if (host == null) host = InetAddress.getLocalHost();
	Socket sock = new Socket(host, port);
	InputStream in = sock.getInputStream();
	return loadFromStream(in);
}

//public static MultiConfig loadFromRawConfig(RawConfigChain rconfig)
//throws IOException
//{
//	MultiConfig config = new MultiConfig();
//	for (byte[] bytes : rconfig) {
//		Config sset = ZipConfig.loadFromStream(new ByteArrayInputStream(bytes));
//		config.add(sset);
//	}
//	return config;
//}

}
