/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Wraps the results of one ConnFactory is some way.  This class
 * is meant to be extended.
 * @author citibob
 */
public abstract class WrapConnFactory implements ConnFactory
{

ConnFactory sub;

public WrapConnFactory(ConnFactory sub)
	{ init(sub); }
protected WrapConnFactory() {}
protected void init(ConnFactory sub)
{
	this.sub = sub;
}
public void close(Connection dbb) throws SQLException {
	sub.close(dbb);
}

public Connection create() throws SQLException {
	return sub.create();
}

public void setConnPool(ConnPool pool)
{
	sub.setConnPool(pool);
}

	
}
