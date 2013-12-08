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

/**
 *
 * @author citibob
 */
public abstract class BaseUpgrader2 implements Upgrader2
{
	
//protected String schemaName;
protected int version0, version1;		// Version we upgrade from and to

public BaseUpgrader2(int version0, int version1)
{
//	this.schemaName = schemaName;
	this.version0 = version0;
	this.version1 = version1;
}

//public String schemaName() { return schemaName; }
public int version0() { return version0; }

public int version1() { return version1; }


public String toString()
{
	return getClass().getSimpleName() + "(" + version0 + " -> " + version1 + ")";
}
}
