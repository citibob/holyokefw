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
import citibob.sql.SqlDateType;
import citibob.sql.SqlRun;
import citibob.sql.SqlTypeSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Reads Resource data relevant to a ResSet from the database
 * @author citibob
 */
public class ResData {

SqlTypeSet tset;
ResSet rset;			// Not needed, we have it in rtres
ArrayList<RtRes> rtres;
SortedSet<RtResKey> relevant;
boolean schemaExists = false;		// true if database schema existed at construction

public ArrayList<RtRes> getRtRes() { return rtres; }

public ResData(SqlRun str, ResSet xrset, SqlTypeSet xtset)
{
	this.rset = xrset;
	this.tset = xtset;

//	// Make sure the database exists at all!
//	// We could be starting from a blank database.
//	String sql =
//		" select count(*) from  information_schema.tables" +
//		" where table_name in ('resources', 'resourceids');";
//	str.execSql(sql, new RsRunnable() {
//	public void run(SqlRunner str, ResultSet rs) throws Exception {
//		rs.next();
//		int count = rs.getInt(1);
//		switch(count) {
//			case 2 : schemaExists = true;
//				break;
//			case 1 : // Error: one table exists, not the other!
//				throw new SQLException("Corrupt database.  Only one of resources and resourceids exists.");
//			case 0 : // New database; fake our available versions!
//				schemaExists = false;
//			break;
//		}
		
		relevant = rset.newRelevant();
		rtres = newRtRes(relevant);	
		if (rset.dbbExists()) readData(str);
//	}});
	
}

/** Re-read the data */
public void readData(SqlRun str)
{
	fetchAvailableVersions(str, tset, relevant);
}


/** Returns same info as newRelevant(), but arranged
 as lists in which all items in each list have same Resource.
 @return
 */
public static ArrayList<RtRes> newRtRes(SortedSet<RtResKey> keys)
{
	Resource lastRes = null;
	ArrayList<RtRes> ret = new ArrayList();
	RtRes curRes = null;
	for (RtResKey rk : keys) {
		if (lastRes != rk.res) {
			curRes = new RtRes();
				curRes.res = rk.res;
			ret.add(curRes);
			lastRes = rk.res;
		}
		curRes.relevant.add(rk);
	}
	return ret;
}	






/** Looks in database for available versions of each requested resource. */
private static void fetchAvailableVersions(SqlRun str,
final SqlTypeSet tset, final SortedSet<RtResKey> keys)
{
	StringBuffer sql = new StringBuffer(
		" create temporary table _keys (serial serial, resourceid int, uversionid int);\n");
	for (RtResKey key : keys) {
		sql.append(" insert into _keys (resourceid, uversionid) values (" + key.res.resourceid + ", " + key.uversionid + ");\n");
	}
	sql.append(
		" select rid.name,k.serial,r.resourceid,r.uversionid," +
		" r.version,r.lastmodified,length(r.val) as size" +
		" from resources r, resourceids rid, _keys k" +
		" where k.resourceid = r.resourceid" +
		" and k.uversionid = r.uversionid" +
		" and r.resourceid = rid.resourceid\n" +
		" order by k.serial,r.version;\n" +
		" drop table _keys;");
	final List<Set<Integer>> list = new LinkedList();
	str.execSql(sql.toString(), new RsTasklet() {
	public void run(java.sql.ResultSet rs) throws Exception {
		SqlDateType tstamp = tset.newTimestamp(true);
		
		for (RtResKey rk : keys) rk.availVersions.clear();
		
		RtResKey rk = null;
//		int lastSerial = -1;
		int serial = 0;		// start of SQL serial - 1...
		Iterator<RtResKey> ii = keys.iterator();
		while (rs.next()) {
			int rsSerial = rs.getInt("serial");
//			if (serial < 0) serial = rsSerial;
			// Handle change...
//			if (serial != lastSerial) {
				// Some ResKey pairs will be skipped if no versions available.
				while (rsSerial != serial) {
					rk = ii.next();
					++serial;
				} //while (serial != lastSerial);
////System.out.println("Skipping to " + rs.getString("name") + " for " + rk.res.name);
//				} while (rk.res.resourceid != rs.getInt("resourceid")
//					|| rk.uversionid != rs.getInt("uversionid"));
//				
//				lastSerial = serial;
//			}
			rk.availVersions.add(new RtVers(rs.getInt("version"), rs.getLong("size"),
				(Date)tstamp.get(rs, "lastmodified")));
System.out.println("rk = " + rk);
System.out.println("avail = " + rk.availVersions);
		}
	}});
//	return list;
}

}
