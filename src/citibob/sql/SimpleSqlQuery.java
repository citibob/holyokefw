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
 * SimpleSqlQuery.java
 *
 * Created on May 15, 2007, 10:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql;

/**
 *
 * @author citibob
 */
public class SimpleSqlQuery
{
String sql, cleanup;

	/** Creates a new instance of SimpleSqlQuery */
	public SimpleSqlQuery(String sql, String cleanup)
	{
		this.sql = sql;
		this.cleanup = cleanup;
	}
	public String getSql() { return sql; }
	/** Sql required to free any database resources after this query is finished running. */
	public String getCleanupSql() { return cleanup; }
}
