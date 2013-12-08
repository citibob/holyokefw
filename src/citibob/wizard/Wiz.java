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
 * Wiz.java
 *
 * Created on January 27, 2007, 6:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard;

/**
 * Represents (logically) one Wizard screen.
 * @author citibob
 */
public interface Wiz
{

public void setTitle(String title);

/** Should this Wiz screen be cached when "Back" is pressed? */
public boolean getCacheWiz();

/** Should old version of this Wiz be used when we go back "forward" over it again? */
public boolean getCacheWizFwd();

/** After the Wiz is done running, report its output into a Map. */
public void getAllValues(java.util.Map map);

///** Presents the Wiz to the user; when it is finished, reports output values into map. */
//public void showWiz(java.util.Map map);

}
