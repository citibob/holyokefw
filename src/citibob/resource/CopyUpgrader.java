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

import citibob.sql.pgsql.*;

/**
 * For testing!
 * @author citibob
 */
public class CopyUpgrader extends DataUpgrader
{

public CopyUpgrader(Resource resource, int version0, int version1)
	{ super(resource, version0, version1); }

/** Does the semantic work of the actual upgrade! */
public byte[] upgrade(byte[] val) {
//	System.out.println("Nop Upgrading " + getResource() + " from " + version0 + " -> " + version1);
	return val;
}

public String getDescription()
{
	return "Copy version " + version0() + " to version " + version1(); 
}

}
