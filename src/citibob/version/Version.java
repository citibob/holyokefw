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
 * VersionNo.java
 *
 * Created on April 16, 2006, 3:17 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.version;

import java.util.prefs.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author citibob
 */
public class Version implements Comparable<Version>
{

int[] vers;

int getVers(int ix) { return vers[ix]; }

public String toString() { return toString(size()); }
public String toString(int level)
{
	StringBuffer sb = new StringBuffer(""+vers[0]);
	for (int i=1; i<level; ++i) sb.append("."+vers[i]);
	return sb.toString();
}

/** Gets version number out of a database table. */
public Version(java.sql.Statement st, String sqlTable)
throws SQLException
{
	ResultSet rs = st.executeQuery("select major,minor,rev from " + sqlTable);
	rs.next();
	vers = new int[3];
	vers[0] = rs.getInt("major");
	vers[1] = rs.getInt("minor");
	vers[2] = rs.getInt("rev");
	rs.close();
}
/** Creates a new instance of VersionNo */
public Version(String s) throws java.text.ParseException
{
	String[] parts = s.split("[.]");
	vers = new int[parts.length];
	try {
		for (int i=0; i<vers.length; ++i) vers[i] = Integer.parseInt(parts[i]);
	} catch(NumberFormatException e) {
		throw new java.text.ParseException("Bad version number: " + s, 0);
	}
}
public Version(int... vers)
{
	this.vers = vers;
}
public int size() { return vers.length; }
// ----------------------------------------------------------
public int compareTo(Version v)
{
	return compareTo(v, Math.min(this.vers.length, v.vers.length));
}

public int compareTo(Version v, int level)
{
	for (int i=0; i<level; ++i) {
		int diff = vers[i] - v.vers[i];
		if (diff != 0) return diff;
	}
	return 0;
}
public boolean equals(Object o)
{
	return (compareTo((Version)o) == 0);
}
// --------------------------------------------------------------
// --------------------------------------------------------------
/** Returns versions sorted from low to high. */
public static Version[] getAvailablePrefVersions(Preferences prefs)
throws BackingStoreException
{
	String[] sver = prefs.childrenNames();
	ArrayList l = new ArrayList(sver.length);
	for (int i=0; i<sver.length; ++i) {
		try {
			l.add(new Version(sver[i]));
		} catch(Exception e) {}
	}

	int n = 0;
	Version[] ret = new Version[l.size()];
	for (Iterator ii=l.iterator(); ii.hasNext(); ) {
		Version v = (Version)ii.next();
		ret[n++] = v;
	}
	Arrays.sort(ret);
	return ret;
}

// ========================================================
public static class Range
{
	Version min, max;
	public Range(Version min, Version max)
	{
		this.min = min;
		this.max = max;
	}
	public Range(Version v)
	{
		this.min = v;
		this.max = v;
	}
	public Range(String min, String max) throws java.text.ParseException
	{
		this(new Version(min), new Version(max));
	}
	public Range(String sv)
	throws java.text.ParseException
	{
		this(new Version(sv));
	}
	public Range(int major, int minor, int rev) { this(new Version(major,minor,rev)); }
	public boolean inRange(Version v)
	{
		if (min != null && v.compareTo(min) < 0) return false;
		if (max != null && v.compareTo(max) > 0) return false;
		return true;
	}
}
// ========================================================
public static class Entry
{
	public final Range range;
	public Object converter;
	public Entry(Range r, Object c) {
		range = r;
		converter = c;
	}
}
}
