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
*//*
 * ERunnable.java
 *
 * Created on January 29, 2006, 7:50 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.task;

/**
 * A runnable with addiontal stuff..
 */
public class Job
{
//String name;				// Name used to bind this task
CBTask task;
//java.util.Date dTime;
String[] permissions;		// Describes who can and cannot run this task.

public Job(String[] permissions, CBTask runnable)
{
//	this.name = name;
	this.task = runnable;
//	this.dTime = new java.util.Date();	
	this.permissions = permissions;
}
public Job(CBTask runnable)
{
	this((String[])null, runnable);
}	

/** @param permissions Comma-separated list of permissions */
public Job(String permissions, CBTask runnable)
{
	this(permissions.split(","), runnable);
}

public String[] getPermissions() { return permissions; }
//public String getName() { return name; }
//public java.util.Date getDTime() { return dTime; }
public CBTask getCBRunnable() { return task; }

}
