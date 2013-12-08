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
import citibob.sql.SqlRun;

/**
 * Encapsulates a procedure to upgrade one resource to the next version.
 * Could involve just throwing it out, or could involve patching it.
 * @author citibob
 */
public interface Upgrader2 {

//public String schemaName();
	
public int version0();

public int version1();

/** Does the actual upgrade! */
public void upgrade(SqlRun str, final ConnPool pool, String jarPrefix, String schemaName)
	throws Exception;

}
