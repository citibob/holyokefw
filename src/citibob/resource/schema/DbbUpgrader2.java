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

package citibob.resource.schema;

import citibob.resource.*;
import citibob.sql.ConnPool;
import citibob.sql.RemoveSqlCommentsReader;
import citibob.sql.SqlRun;
import citibob.sql.pgsql.SqlInteger;
import citibob.sql.pgsql.SqlString;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author citibob
 */
public class DbbUpgrader2 extends BaseUpgrader2
{

public DbbUpgrader2(int version0, int version1)
{
	super(version0, version1);
}

public String getDescription() {
	return "Upgrade2 from version " +version0 + " to " + version1;
	
}

public void upgrade(SqlRun str, final ConnPool pool, String jarPrefix, String schemaName)
throws Exception
{
	StringBuffer sql = new StringBuffer("BEGIN;\n");
	String resourceName = jarPrefix + "/" + schemaName + "-" + version0 + "-" + version1 + ".sql";
System.out.println("Loading resource of name: " + resourceName);

	ClassLoader cl = getClass().getClassLoader();
	InputStream iin = new BufferedInputStream(
		cl.getResourceAsStream(resourceName));
	Reader in = new RemoveSqlCommentsReader(new InputStreamReader(iin));

	// Read the SQL
	char[] buf = new char[8192];
	int n;
	StringWriter out = new StringWriter();
	while ((n = in.read(buf)) > 0) sql.append(buf, 0, n);
	in.close();

	// Upgrade the main tables
	sql.append(out.toString() + ";\n");
	
	// send it off...
	sql.append(
		// Record the upgrade
		" update dbbersions set version = " + SqlInteger.sql(version1) +
		" where schemaname = " + SqlString.sql(schemaName) + ";\n");
	sql.append("COMMIT;\n");
	str.execSql(sql.toString());

	// Do each upgrade step separately.
	str.flush();
}

protected void execSqlIgnoreError(ConnPool pool, String sql)
throws SQLException
{
	Connection dbb = pool.checkout();
	try {
		Statement st = dbb.createStatement();
		try {
			st.execute(sql);
		} catch(SQLException e) {
			// Ignore
		} finally {
			st.close();
		}
	} finally {
		pool.checkin(dbb);
	}
}

protected void dropTable(ConnPool pool, String tableName, boolean cascade)
throws SQLException
{
	execSqlIgnoreError(pool, "drop table " + tableName +
		(cascade ? " cascade;" : ";"));
}
public String toString()
{
	return getClass().getSimpleName() + "(" + version0 + " -> " + version1 + ")";
}
}
