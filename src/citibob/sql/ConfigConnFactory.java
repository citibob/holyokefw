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

package citibob.sql;

import citibob.config.Config;
import citibob.io.sslrelay.SSLRelayClient;
import citibob.task.ExpHandler;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Gets connection parameters from a Config object.
 * @author citibob
 */
public class ConfigConnFactory extends WrapConnFactory
{

/** @param props Must equal config.loadAppProperties(). */
public static SSLRelayClient.Params newtSSLRelayClientParams(Config config,
Properties props)
throws IOException
	{ return newtSSLRelayClientParams(config, props, "keyst0re"); }

/** @param props Must equal config.loadAppProperties(). */
public static SSLRelayClient.Params newtSSLRelayClientParams(Config config,
Properties props, String defaultPass)
throws IOException
{
	SSLRelayClient.Params prm = new SSLRelayClient.Params();
	String dbUserName = props.getProperty("db.user", null);

	prm.storeBytes = config.getStreamBytes("client-store.jks");
	prm.trustBytes = config.getStreamBytes("client-trust.jks");

	prm.dest = InetAddress.getByName(props.getProperty("db.host", null));
	prm.destPort = Integer.parseInt(props.getProperty("db.port", null));

	String storePass = (String)props.getProperty("db.storePass", defaultPass);
	prm.storePass = storePass.toCharArray();
	String storeKeyPass = (String)props.getProperty("db.storeKeyPass", storePass);
	prm.storeKeyPass = storeKeyPass.toCharArray();
	String trustPass = (String)props.getProperty("db.trustPass", storePass);
	prm.trustPass = trustPass.toCharArray();

	return prm;
}
/** @param props Must equal config.loadAppProperties(). */
private static ConnFactory newSSLSub(Config config, Properties props, ExpHandler expHandler)
throws ClassNotFoundException, UnknownHostException, MalformedURLException, IOException
{
	
	final Properties p2 = new Properties();
	final String url;

	// Set up the sub-properties
	p2.setProperty("user", props.getProperty("db.user", null));
	String pwd = props.getProperty("db.password", null);
	if (pwd != null) p2.setProperty("password", pwd);

	// Put together as URL
	Class.forName(props.getProperty("db.driverclass", null));
	url = "jdbc:" + props.getProperty("db.drivertype", null) + "://" +
		"127.0.0.1" +
//		props.getProperty("db.host", null) +
		":%port%/" + props.getProperty("db.database", null);
	
	// Set the SSL tunnel parameters
	SSLRelayClient.Params prm = newtSSLRelayClientParams(config, props, "keyst0re");

	return new SSLConnFactory(url, prm, p2, expHandler);
}
	
private static ConnFactory newPlainSub(Properties props)
throws ClassNotFoundException
//throws java.util.prefs.BackingStoreException, java.sql.SQLException, ClassNotFoundException
{
//	Properties props = app.props();
	final Properties p2 = new Properties();
	final String url;

//for (Enumeration en = props.keys(); en.hasMoreElements();) {
//	Object key = en.nextElement();
//	System.out.println(key + " = " + props.get(key));
//}
//System.out.println("db.driverclass = " + props.getProperty("db.driverclass", null));
	Class.forName(props.getProperty("db.driverclass", null));
	p2.setProperty("user", props.getProperty("db.user", null));

//	// PostgreSQL interprets any setting of the "ssl" property
//	// as a request for SSL.
//	// See: http://archives.free.net.ph/message/20080128.165732.7c127d6b.en.html
//	String sssl = props.getProperty("db.ssl", "false");
//	boolean ssl = (sssl.toLowerCase().equals("true"));
//	if (ssl) p2.setProperty("ssl", "true");
	
	String pwd = props.getProperty("db.password", null);
	if (pwd != null) p2.setProperty("password", pwd);

	url = "jdbc:" + props.getProperty("db.drivertype", null) + "://" +
		props.getProperty("db.host", null) +
		":" + props.getProperty("db.port", null) +
		"/" + props.getProperty("db.database", null);
	return new JDBCConnFactory(url, p2);
}


/**
 * 
 * @param config
 * @param appProperties Should equal config.loadAppProperties()
 * @param expHandler
 * @throws java.lang.ClassNotFoundException
 * @throws java.net.UnknownHostException
 * @throws java.net.MalformedURLException
 * @throws java.io.IOException
 */
public ConfigConnFactory(Config config, Properties appProperties, ExpHandler expHandler)
throws ClassNotFoundException, UnknownHostException, MalformedURLException, IOException
{
//	if (propFileName == null) propFileName = "app.properties";
//	Properties props = new Properties();
//	config.loadProperties(propFileName);
//	Properties props = config.loadAppProperties();
	
	String sssl = appProperties.getProperty("db.ssl", "false");
	boolean ssl = (sssl.toLowerCase().equals("true"));
	ConnFactory sub = (ssl ?
		newSSLSub(config, appProperties, expHandler) :
		newPlainSub(appProperties));
	init(sub);
}
	
}
