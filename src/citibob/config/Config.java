/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents one configuration, or a set of chained configurations
 * @author citibob
 */
public interface Config {

public InputStream openStream(String name) throws IOException;

public byte[] getStreamBytes(String name) throws IOException;

/** Descriptive name of this configuration */
public String getName();
public void setName(String name);

/** Retrieves a composite properties file from the Config.
 * @param name Name of file to read properties from
 * @param props OUT: The properties object properties will be written into.
 * @throws java.io.IOException
 * @return true if properties file existed and we were able to load.  false
 * if properties file did not exist in the Config.
 */
public boolean loadProperties(String name, Properties props)
throws IOException;

//public Properties getProperties(String name);

/** Overall load the properties.  This loads two properties files:
 * "app.properties" is the base, overridden by <os.name>.properties.
 * @return
 * @throws java.io.IOException
 */
public Properties loadAppProperties() throws IOException;


}
