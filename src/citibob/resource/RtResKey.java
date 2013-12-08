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

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Key fields for a resource.  Used to identify which resources we need
 * current version of.
 * @author citibob
 */
public class RtResKey implements Comparable<RtResKey> {
	public Resource res;
	public int uversionid;
	public String uversionName;		// For display: name corresponding to uversionid.  Or null.
//	static final int[] noVersions = new int[0];
//	public int[] availVersions = noVersions;	// (from DB): available versions of this resource.

	public ArrayList<RtVers> availVersions = new ArrayList();
	
	
	public RtResKey(Resource res, int uversionid, String uversionName) {
		this.res = res;
		this.uversionid = uversionid;
		this.uversionName = uversionName;
	}
	public RtResKey(Resource res) { this(res, 0, "<Regular>"); }
	public int compareTo(RtResKey rk) {
		int cmp = res.getName().compareTo(rk.res.getName());
//		int cmp = res.resourceid = rk.res.resourceid; //getName().compareTo(rk.res.getName());
		if (cmp != 0) return cmp;
		return uversionid - rk.uversionid;
	}
	
	public UpgradePlan getCreatorPlan(int version1)
	{
		UpgradePlan uplan = res.getCreatorPlan(version1);
		if (uplan == null) return null;
		uplan.setUversionid0(uversionid, uversionName);
		uplan.setUversionid1(uversionid, uversionName);
		return uplan;
	}
//	public UpgradePlan getUpgradePlan(int version0, int version1)
//	{
//		return getUpgradePlan(version0, version1, false);
//	}
	public UpgradePlan getUpgradePlan(int version0, int version1)
	{
		UpgradePlan uplan = res.getUpgradePlan(version0, version1);
		if (uplan == null) return null;
		uplan.setUversionid0(uversionid, uversionName);
		uplan.setUversionid1(uversionid, uversionName);
		return uplan;
	}

	/** Gets an upgrade or creator plan to version1, from the
	latest existing version.  This assumes the required version
	  does NOT yet exist! */
	public UpgradePlan getUpgradePlanFromLatest(int reqVersion)
	{
		RtResKey rk = this;

		// Look at all existing versions, from highest number to lowest.
		UpgradePlan uplan = null;
		for (int i=rk.availVersions.size()-1; i >= 0; --i) {
			RtVers vers = rk.availVersions.get(i);
			// See if required version is already available
			if (vers.version == reqVersion) return null;
			
			// See if we can make required version from this version
			uplan = rk.getUpgradePlan(vers.version, reqVersion);
			if (uplan != null) return uplan;
		}
		
		// Cannot make from previous version.  Try to create it.
		uplan = rk.getCreatorPlan(reqVersion);
		if (uplan != null) return uplan;
		
		return null;
	}
	
	
	public String toString()
	{
		return "ResKey(" + res.getName() + ":" + res.getResourceID() + ", " + uversionName + ":" + uversionid + ")";
	}
	
	/** Returns the RtVers record for an available version, or null
	 if that version is not available.
	 @param version
	 @return
	 */
	public RtVers getRtVers(int reqVersion)
	{
		// See if we have the required version
		//int reqVersion = rk.res.getRequiredVersion(app.sysVersion());
		for (RtVers rv : availVersions) {
			if (rv.version == reqVersion) return rv;
		}
		return null;
	}
}
