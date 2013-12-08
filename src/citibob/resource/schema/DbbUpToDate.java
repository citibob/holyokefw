/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource.schema;

import citibob.app.App;
import citibob.sql.RsTasklet;
import citibob.sql.RsTasklet2;
import citibob.sql.SqlRun;
import citibob.sql.pgsql.SqlString;
import citibob.util.IntVal;
import citibob.version.SvnVersion;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Simplified version of resources, just for keeping a databsae schema (or
 * part of schema) up to date.
 * @author citibob
 */
public class DbbUpToDate {

App app;
String jarPrefix;
String schemaName;	// Multiple schemas can be merged into one database
int sysVersion;		// Version of this system (front-end)
int dbbVersion;	// Version of current schema in database

protected Upgrader2[] upgraders;

public IntVal getDbbVersion(SqlRun str)
{
	final IntVal ret = new IntVal();
	
	// Make sure the database exists at all!
	// We could be starting from a blank database.
	String sql =
		" select count(*) from  information_schema.tables" +
		" where table_name in ('dbbversions');\n";
	str.execSql(sql, new RsTasklet2() {
	public void run(SqlRun str, ResultSet rs) throws Exception {
		rs.next();
		int count = rs.getInt(1);
		if (count == 0) {
			ret.val = -2;
		} else {
			String sql =
				" select * from dbbversions\n" +
				" where schemaname = " + SqlString.sql(schemaName) + ";\n";
			str.execSql(sql, new RsTasklet() {
			public void run(ResultSet rs) throws Exception {
				if (!rs.next()) {
					ret.val = -1;
				} else {
					ret.val = rs.getInt("version");
				}
			}});
		}
	}});
	return ret;
}


public void upgradeToCurrent() throws Exception
{
	// Figure out what version of schema we have
	IntVal DbbVersion = getDbbVersion(app.sqlRun());
	app.sqlRun().flush();
	this.dbbVersion = DbbVersion.val;
System.out.println("dbbVersion = " + dbbVersion);

	// Find the first upgrader we need to apply
	int start;
	for (start=upgraders.length - 1; start >= 0; --start) {
		if (upgraders[start].version1() < sysVersion) break;
	}
	++start;

	// Apply the upgraders
	for (int i=start; i<upgraders.length; ++i) {
		upgraders[i].upgrade(app.sqlRun(), app.pool(), jarPrefix, schemaName);
	}
}
	


public DbbUpToDate(App app, String schemaName) throws IOException
{
	this.app = app;
	this.schemaName = schemaName;

	jarPrefix = getClass().getPackage().getName().replaceAll(".", "/");

	// Dig out sysVersion
	String resourceName = "svn-version.txt";
	ClassLoader cl = getClass().getClassLoader();
	SvnVersion svers = new SvnVersion(cl.getResourceAsStream(resourceName));
	sysVersion = svers.maxVersion;
}





}
