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
 * TimeSKeyedModel.java
 *
 * Created on June 8, 2007, 11:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.jschema;

//import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.*;
import citibob.types.KeyedModel;

/**
 *
 * @author citibob
 */
public class TimeSKeyedModel extends KeyedModel
{

static NumberFormat n00 = new DecimalFormat("00");
		
/** Creates a new instance of TimeSKeyedModel */
public TimeSKeyedModel(int firstHr, int firstMin, int lastHr, int lastMin, int periodS)
{
	int firstS = firstHr * 3600 + firstMin * 60;
	int lastS = lastHr * 3600 + lastMin * 60;

	int dt = firstS;
	while (dt < lastS) {
		int hr = dt / 3600;
		int min = (dt - hr*3600) / 60;
		super.addItem(dt, n00.format(hr) + ":" + n00.format(min), null);
		dt += periodS;
	}	
}

}
