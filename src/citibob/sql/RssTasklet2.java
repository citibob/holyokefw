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
package citibob.sql;


/** Takes multiple result sets from one segment of a batch query. */
public interface RssTasklet2 extends SqlTasklet {

/** Runnable gets run on results of a previous batch query.  If the runnable
 needs to produce additional queries, it stores them in nstr.next(). */
public void run(citibob.sql.SqlRun str, java.sql.ResultSet[] rss) throws Exception;
	
}
