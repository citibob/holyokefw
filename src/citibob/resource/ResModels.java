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

import citibob.app.App;
import citibob.swing.table.ArrayListTableModel;
import citibob.text.BaseSFormat;
import citibob.types.JType;
import citibob.types.JavaJType;
import citibob.types.JavaJType;
import java.util.ArrayList;

/**
 *
 * @author citibob
 */
public class ResModels
{

App app;
int sysVersion;
ResData rdata;
String resourceGroup;

public ResModels.ResModel resMod;
public ResModels.ResKeyModel rkMod;
public ResModels.AvailModel availMod;
public ResModels.UPlanModel uplanMod;

/** @param resourceGroup Only pay attention to Resources that match this
 (or all resources if null).
 @param rdata
 @param xapp
 @param sysVersion
 @param resourceGroup
 */
public ResModels(ResData rdata, App xapp, int sysVersion, String resourceGroup)
{
	this.app = xapp;
	ResSet rset = app.resSet();
	this.rdata = rdata;
	this.sysVersion = sysVersion;
	this.resourceGroup = resourceGroup;
	
	availNames = new String[] {"RtVers", "version", "lastmodified", "size"};
	availTypes = new JType[] {new JavaJType(RtVers.class), JavaJType.jtInteger,
			app.sqlTypeSet().getSqlType(java.sql.Types.TIMESTAMP, 0, 0, true),
			JavaJType.jtLong};

	
	resMod = new ResModel();
	rkMod = new ResKeyModel();
	availMod = new AvailModel();
	uplanMod = new UPlanModel();
	
	resMod.setData(rdata);
}

public ResModel newResModel()
	{ return new ResModel(); }
/** Lists ResKeys for a model */
public ResKeyModel newResKeyModel(Resource res)
	{ return new ResKeyModel(); }
//	ResRow row = rowMap.get(res);
//	return new ResKeyModel(row.relevant);
//}
public AvailModel newAvailModel(RtResKey rk)
	{ return new AvailModel(); }
//	return new AvailModel(rk.availVersions);
//}
//public UpgradePlansModel newUpgradePlansModel(RtResKey rk, int reqVersion)
//{
//	// Scope out upgrade plans first
//	
//	return new UPlanModel()
//}
// =======================================================================
static final String[] resNames = {"RtRes", "name", "reqversion"};
static final JType[] resTypes = {new JavaJType(RtRes.class), JavaJType.jtString, JavaJType.jtInteger};
public class ResModel extends ArrayListTableModel<RtRes>
{
	public ResModel()
	{
		super(resNames, resTypes, null);
	}
	public void setData(ResData rdata) {
		data = new ArrayList();
		for (RtRes rr : rdata.rtres) {
			if (resourceGroup == null || 
				resourceGroup.equals(rr.res.getResourceGroup())) data.add(rr);
		}
		fireTableDataChanged();
	}
	public Object getValueAt(int irow, int icol)
	{
		RtRes r = data.get(irow);
		switch(icol) {
			case 0 : return r;
			case 1 : return r.res.getName();
			case 2 : return r.res.getRequiredVersion(sysVersion);
		}
		return null;
	}
}
// =======================================================================
static final String[] rkNames = {"ResKey", "name"};
static final JType[] rkTypes = {new JavaJType(RtResKey.class), JavaJType.jtString};
public class ResKeyModel extends ArrayListTableModel<RtResKey>
{
	public ResKeyModel() {
		super(rkNames, rkTypes, null);		
	}
	public void setData(RtRes res)
	{
		this.data = res.relevant;
		fireTableDataChanged();
	}
	public Object getValueAt(int irow, int icol)
	{
		RtResKey rk = data.get(irow);
		switch(icol) {
			case 0 : return rk;
			case 1 : return rk.uversionName;
		}
		return null;
	}
}
// =======================================================================
String[] availNames;		// Set above in constructor
JType[] availTypes;
public class AvailModel extends ArrayListTableModel<RtVers>
{
	public AvailModel() {
		super(availNames, availTypes, null);
	}
	public void setData(RtResKey rk)
	{
		if (rk == null || rk.availVersions == null) clear();
		else {
			this.data = rk.availVersions;
			fireTableDataChanged();
		}
	}
	public Object getValueAt(int irow, int icol)
	{
		RtVers version = data.get(irow);
		switch(icol) {
			case 0 : return version;
			case 1 : return version.version;
			case 2 : return version.lastmodified;
			case 3 : return version.size;
		}
		return null;
	}
}
// =======================================================================
static final String[] uplanNames = {"UpgradePlan",
	"uversionid0", "uversionName0",
	"uversionid1", "uversionName1",
	"required", "backcompatible", "resourceName"};
static final JType[] uplanTypes = {new JavaJType(UpgradePlan.class),
	JavaJType.jtInteger, JavaJType.jtString,
	JavaJType.jtInteger, JavaJType.jtString,
	JavaJType.jtBoolean, JavaJType.jtBoolean, JavaJType.jtString};
public static class UPlanModel extends ArrayListTableModel<UpgradePlan>
{
	public UPlanModel() {
		super(uplanNames, uplanTypes, null);
	}
	public void setData(ArrayList<UpgradePlan> uplans) {
		this.data = uplans;
		fireTableDataChanged();
	}
	public Object getValueAt(int irow, int icol)
	{
		UpgradePlan up = data.get(irow);
		switch(icol) {
			case 0 : return up;
			case 1 : return up.uversionid0();
			case 2 : return up.uversionName0();
			case 3 : return up.uversionid1();
			case 4 : return up.uversionName1();
			case 5 : return up.getLast().getResource().isRequired();
			case 6 : return up.isBackCompatible();
			case 7 : {
				Upgrader uu = up.path[up.path.length-1];
				return uu.getResource().getName();
			}
		}
		return null;
	}
}
public static class PathSFormat extends BaseSFormat
{
	public String valueToString(Object val)
	{
		if (val == null) return super.nullText;
		UpgradePlan up = (UpgradePlan)val;
		Upgrader[] path = up.getPath();
		if (path == null || path.length < 1) return "<none>";
		StringBuffer sbuf = new StringBuffer(""+path[0].version0());
		for (int i=0; i<path.length; ++i) {
			sbuf.append(" -> " + path[i].version1());
		}
		return sbuf.toString();
	}
	
}
// =======================================================================

}
