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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.RsTasklet;
import citibob.sql.RssTasklet;
import citibob.sql.SqlRun;
import citibob.sql.pgsql.SqlString;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Set of resources used by the current application.
 * @author citibob
 */
public abstract class ResSet
{
protected boolean dbbExists = false;		// true if the database has been verified to exist.
public boolean dbbExists() { return dbbExists; }

protected ClassLoader jarClassLoader;	// Used to read resources from JAR file.
protected String jarPrefix;
// "offstage/resources/"

/** Constructor of app-specific res set will add to this. */
protected TreeMap<String,Resource> resources = new TreeMap();

protected int sysVersion;

public ResSet(SqlRun str, int sysVersion, ClassLoader jarClassLoader, String jarPrefix)
throws SQLException
{
	this.jarClassLoader = jarClassLoader;
	this.jarPrefix = jarPrefix;
	this.sysVersion = sysVersion;

	// Make sure the database exists at all!
	// We could be starting from a blank database.
	String sql =
		" select count(*) from  information_schema.tables" +
		" where table_name in ('resources', 'resourceids');";
	str.execSql(sql, new RsTasklet() {
	public void run(ResultSet rs) throws Exception {
		rs.next();
		int count = rs.getInt(1);
		switch(count) {
			case 2 : dbbExists = true;
				break;
			case 1 : // Error: one table exists, not the other!
				throw new SQLException("Corrupt database.  Only one of resources and resourceids exists.");
			case 0 : // New database; fake our available versions!
				dbbExists = false;
			break;
		}
	}});

}

public ClassLoader getJarClassLoader() { return jarClassLoader; }
public String getJarPrefix() { return jarPrefix; }

public void add(Resource res)
	{resources.put(res.getName(), res); }
public Resource get(String name)
	{ return resources.get(name); }


public ResResult load(SqlRun str, String name, int uversionid)
{
	Resource res = get(name);
	int reqVersion = res.getRequiredVersion(sysVersion);
	return res.load(str, uversionid, reqVersion);
}

public void saveResource(SqlRun str, String name, int uversionid,
final File outFile)
{
	Resource res = get(name);
	int reqVersion = res.getRequiredVersion(sysVersion);
	ResUtil.saveResource(str, res.getName(), uversionid, reqVersion, outFile);
}


/* List of resource-uversionid pairs required by this app at this time. */
//public abstract List<RtResKey> getRequired();
/** Set of resource-uversionid pairs relevant to the app at this time.  By default,
 base it on the Resources registered with this class.
 @param useDbb if false, DO NOT try to read the database (it doesn't exist)*/
public SortedSet<RtResKey> newRelevant()
{
	SortedSet<RtResKey> ret = new TreeSet();

	for (Resource res : resources.values()) {
		ret.add(new RtResKey(res));
	}
	return ret;
}



public void createAllResourceIDs(SqlRun str)
{
	if (!dbbExists) return;

	StringBuffer sql = new StringBuffer();
	for (Resource res : resources.values()) {
		sql.append("select w_resourceid_create(" +
			SqlString.sql(res.getName()) + ");\n");
	}
	str.execSql(sql.toString(), new RssTasklet() {
	public void run(java.sql.ResultSet[] rss) throws Exception {
		int i = 0;
		for (Resource res : resources.values()) {
			ResultSet rs = rss[i++];
			rs.next();
			res.resourceid = rs.getInt(1);
		}
		
	}});
}

}
