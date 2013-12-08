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

/**
 *
 * @author citibob
 */
public class UpgradePlan {
	Upgrader[] path;
	private int uversionid0, uversionid1;
	private String uversionName0, uversionName1;
	
	public Upgrader getLast() { return path[path.length-1]; }
	public Resource getResource() { return getLast().getResource(); }
	
	public void applyPlan(SqlRun str, ConnPool pool)
	throws Exception
	{
		getResource().applyPlan(str, pool, this);
	}
	
	public UpgradePlan(Upgrader[] path)
		{ this.path = path; }
	public Upgrader[] getPath() { return path; }
	public int version0() { return path[0].version0(); }
	public int version1() { return path[path.length - 1].version1(); }
	
	public int uversionid0() { return uversionid0; }
	public int uversionid1() { return uversionid1; }
	public String uversionName0() { return uversionName0; }
	public String uversionName1() { return uversionName1; }
	public void setUversionid0(int v, String name) {
		uversionid0 = v;
		if (version0() < 0) uversionName0 = "<Default>";
		else uversionName0 = name;
	}
	public void setUversionid1(int v, String name) {
		uversionid1 = v;
		uversionName1 = name;
	}
	public boolean isBackCompatible()
	{
		for (int i=0; i<path.length; ++i) if (!path[i].isBackCompatible()) return false;
		return true;
	}
	public String toString() {
		StringBuffer sbuf = new StringBuffer("UpgradePlan(");
		if (path == null || path.length < 1) sbuf.append(")");
		else {
			sbuf.append(path[0].getResource().getName() + "-" + uversionName1 + ": ");
			sbuf.append(path[0].version0());
			for (int i=(path.length == 1 ? 0 : 1); i<path.length; ++i) {
				sbuf.append(" -> " + path[i].version1());
			}
			sbuf.append(")");
		}
		return sbuf.toString();
	}
	public String getDescription()
	{
		StringBuffer sbuf = new StringBuffer();
		if (version0() >= 0) {
			sbuf.append("Start &lt; with &gt; " + encodeHTML(uversionName0) + " version " + version0() + "<br>\n");
		}
		for (Upgrader up : path) {
			sbuf.append(encodeHTML(up.getDescription()) + "<br>\n");
		}
		sbuf.append("Store in " + encodeHTML(uversionName1) + " version " + version1() + "<br>\n");
		return sbuf.toString();
	}




public static String encodeHTML(String s)
{
    StringBuffer out = new StringBuffer();
    for(int i=0; i<s.length(); i++)
    {
        char c = s.charAt(i);
        if(c > 127 || c=='"' || c=='<' || c=='>')
        {
           out.append("&#"+(int)c+";");
        }
        else
        {
            out.append(c);
        }
    }
    return out.toString();
}
}
