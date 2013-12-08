/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

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
package citibob.sql;

import citibob.io.sslrelay.SSLRelayClient;
import citibob.task.ExpHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class SSLConnFactory implements ConnFactory
{

String urlTemplate;		// Connecion URL, without the port
Properties props;
SSLRelayClient.Params prm;
ExpHandler expHandler;
ConnPool pool;		// OPTIONAL: Check ourselves in when connection dies

Map<Connection,SSLRelayClient> fwdMap = new HashMap();

public void setConnPool(ConnPool pool)
{ this.pool = pool; }

/**
 * 
 * @param urlTemplate Template of URL, with the port number replaced
 * by %port%.  Eg: "jdbc:postgresql://localhost:%port%/database"
 * @param props <p>The properties we use are:</p>
 * <ul>
 * <li>db.database</li>
 * <li>user</li>
 * <li>password</li>
 */
public SSLConnFactory(String urlTemplate,
SSLRelayClient.Params prm, Properties props,
ExpHandler expHandler)
{
	this.urlTemplate = urlTemplate;
	this.prm = prm;
	this.props = props;
	this.expHandler = expHandler;
}

/** Create an actual connection --- used by pool implementations, should not
 * be called by user. */
public Connection create() throws SQLException
{
SSLRelayClient relay;
	try {
		relay = new SSLRelayClient(prm, null) {
		protected void connectionClosed() {
			// Remove ourselves from the connection pool
			// if the SSL link goes down.
			try {
				System.out.println("***** Removing connection from pool: " + getDbb());
				if (pool != null) {
// Don't know why this is ever null.
					if (getDbb() != null) pool.close(this.getDbb());
//					pool.closeAll();
				}
			} catch(Exception e) {
				expHandler.consume(e);
			}
		}};
		relay.startRelays();
	} catch(Exception e) {
		SQLException ioe = new SQLException(e.getMessage());
		ioe.initCause(e);
		throw ioe;
	}

	// Set up JDBC connection through that tunnel
	int port = relay.getLocalPort();
	String url = urlTemplate.replace("%port%", ""+port);
	Connection dbb = DriverManager.getConnection(url, props);
System.out.println("SSLConnFactory created connection: " + dbb);
	relay.setDbb(dbb);
	
	fwdMap.put(dbb, relay);
	return dbb;
}

/** Closes an actual connection --- used by pool implementations, should not
 * be called by user. */
public void close(Connection dbb) throws SQLException
{
	// Close the database connection
	dbb.close();
	
	// Shu down the tunnel
	SSLRelayClient relay = fwdMap.get(dbb);
	if (relay != null) relay.stopRelays();
}



}
