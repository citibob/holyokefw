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
package citibob.io;

import java.io.*;
import java.util.*;

public class GrepWriter extends AbstractLineWriter
{

void processLine()
{
	//if (line.length() == 0) return;
	String s = line.toString();
	Listener l = (Listener)patterns.get(s);
	if (l != null) l.matched(s);
//	System.err.println(">>>" + line + "<<<");
	line.delete(0, line.length());
}

// ----------------------------------------------------------------
public static interface Listener {
	public void matched(String line);
}
TreeMap patterns = new TreeMap();
public void setPattern(String pat, Listener l)
{
	if (l == null) patterns.remove(pat);
	else patterns.put(pat,l);
}
// ----------------------------------------------------------------
public static void main(String[] args) throws Exception
{
	GrepWriter gw = new GrepWriter();
	gw.setPattern("Hello There", new Listener() { public void matched(String line) {
		System.out.println("Found HelloThere!!!");
	}});
	gw.write("Hello ");
	gw.write("There\nHow are you today?");
	gw.write("\nI like to go");
	gw.close();
}
}
