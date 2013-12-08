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

package citibob.task;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author citibob
 */
public class JobMap extends HashMap<String,Job>
{

//HashMap<String,Task> map = new HashMap();
//TaskRunner runner;
//
//public TaskMap() {}
//public TaskMap(TaskRunner runner)
//	{ this.runner = runner; }
//public void run(String key)
//	{ runner.doRun(get(key)); }

public void add(String key, Job task)
{ this.put(key, task); }
	
public void add(String key,
String[] permissions, CBTask runnable)
{
	this.put(key, new Job(permissions, runnable));
}
public void add(String key, CBTask runnable)
{
	this.put(key, new Job((String[])null, runnable));
}	

/** @param permissions Comma-separated list of permissions */
public void add(String key, String permissions, CBTask runnable)
{
	this.put(key, new Job(permissions, runnable));
}

//public Task get(String key) { return this.get(key); }

}
