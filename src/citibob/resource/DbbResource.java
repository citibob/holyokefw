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

import citibob.sql.ConnPool;
import citibob.sql.SqlRun;
import java.sql.SQLException;

/**
 *
 * @author citibob
 */
public class DbbResource extends Resource
{

public DbbResource(ResSet rset, String uversionType, String name) {
	super(rset, uversionType, name);
	editable = false;
	super.required = true;
}

public void applyPlan(SqlRun str, final ConnPool pool, final UpgradePlan uplan)
throws Exception
{
	int uversionid = uplan.uversionid0();
	for (Upgrader up : uplan.getPath()) {
		BaseDbbUpgrader dup = (BaseDbbUpgrader)up;
		dup.upgrade(str, pool, uversionid, uplan.uversionid1());
		uversionid = uplan.uversionid1();
	}
//	
//	final ResResult rr = load(str, uplan.uversionid0(), uplan.version0());
//	str.execUpdate(new UpdRunnable() {
//	public void run(SqlRunner str) throws Exception {
//		byte[] bytes = rr.bytes;
//		for (Upgrader up : uplan.getPath()) {
//			DbbUpgrader dup = (DbbUpgrader)up;
//			bytes = dup.upgrade(bytes);
//		}
//		final byte[] xbytes = bytes;
//		Exception e = pool.exec(new citibob.task.DbRunnable() {
//		public void run(java.sql.Connection dbb) throws Exception {
//			Upgrader[] path = uplan.getPath();
//			ResUtil.setResource(dbb, getName(), uplan.uversionid1(), uplan.version1(), xbytes);
//		}});
//		if (e != null) throw e;
//	}});
}
// ========================================================

}
