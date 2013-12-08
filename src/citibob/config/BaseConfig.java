/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author citibob
 */
public abstract class BaseConfig implements Config
{
	
private String name;

public BaseConfig(String name) {
	this.name = name;
}

public String getName()
	{ return name; }

public void setName(String name)
{
	this.name = name;
}

//public void setNameFromAppProperties()
//throws IOException
//{
//	// Determine the Config's name by parsing from the first StreamSet
//	Config sset = this;
//	InputStream xin = sset.openStream("app.properties");
//	if (xin != null) {
//		Properties props = new Properties();
//		props.load(xin);
//		xin.close();
//		name = props.getProperty("config.name");
//	}
//}

public byte[] getStreamBytes(String name) throws IOException
{
	InputStream in = openStream(name);
	if (in == null) return null;
	byte[] ret = IOUtils.getBytes(in);
	in.close();
	return ret;
}

/** Retrieves a composite properties file from the Config. */
public boolean loadProperties(String name, Properties props)
throws IOException
{
	InputStream xin = openStream(name);
	if (xin == null) return false;
	
	// Load properties file
	props.load(xin);
	xin.close();

	return true;
}

/** Overall load the properties.  This loads two properties files:
 * "app.properties" is the base, overridden by <os.name>.properties.
 * @return
 * @throws java.io.IOException
 */
public Properties loadAppProperties() throws IOException
{
	Properties props = new Properties();

	loadProperties("app.properties", props);
	String os = System.getProperty("os.name");
        int space = os.indexOf(' ');
        if (space >= 0) os = os.substring(0,space);
	loadProperties(os + ".properties", props);
	
	return props;
}

}
