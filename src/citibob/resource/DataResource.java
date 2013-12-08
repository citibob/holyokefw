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
import citibob.sql.UpdTasklet;

/**
 *
 * @author citibob
 */
public class DataResource extends Resource
{

public DataResource(ResSet rset, String uversionType, String name) {
	super(rset, uversionType, name);
	editable = true;
	super.required = false;
}

public void applyPlan(SqlRun str, final ConnPool pool, final UpgradePlan uplan)
{
	final ResResult rr = load(str, uplan.uversionid0(), uplan.version0());
	str.execUpdate(new UpdTasklet() {
	public void run() throws Exception {
		byte[] bytes = rr.bytes;
		for (Upgrader up : uplan.getPath()) {
			DataUpgrader dup = (DataUpgrader)up;
			bytes = dup.upgrade(bytes);
		}
		final byte[] xbytes = bytes;
		Exception e = pool.exec(new citibob.task.DbTask() {
		public void run(java.sql.Connection dbb) throws Exception {
			Upgrader[] path = uplan.getPath();
			ResUtil.setResource(dbb, getName(), uplan.uversionid1(), uplan.version1(), xbytes);
		}});
		if (e != null) throw e;
	}});
}
// ========================================================

}
