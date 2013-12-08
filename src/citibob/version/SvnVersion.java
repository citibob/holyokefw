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

package citibob.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reads the output of the svnversion command
 * @author citibob
 */
public class SvnVersion {

public int minVersion;
public int maxVersion;
public char modifier = '\0';

public SvnVersion(InputStream xin) throws IOException
{
	BufferedReader in = new BufferedReader(new InputStreamReader(xin));
	String line = in.readLine();
	in.close();
	
	// ================= Parse it
	
	// Get the trailing modifier
	char last = line.charAt(line.length() - 1);
	if (!Character.isDigit(last)) {
		modifier = last;
		line = line.substring(0, line.length() - 1);
	}
	
	// Get min version and max version
	int colon = line.indexOf(':');
	if (colon < 0) {
		maxVersion = Integer.parseInt(line);
		minVersion = maxVersion;
	} else {
		minVersion = Integer.parseInt(line.substring(0,colon));
		maxVersion = Integer.parseInt(line.substring(colon+1));
	}
}
}
