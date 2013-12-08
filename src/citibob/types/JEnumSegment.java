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
package citibob.types;

import java.sql.*;
import citibob.jschema.*;
import citibob.sql.*;
import citibob.types.KeyedModel;

/** Create one of these to specify that we want the same TYPE as a full JEnum,
 * but we only want to DISPLAY one segment of it. */
public class JEnumSegment extends JEnum
{
	protected Object segment;
	
	public Object getSegment() { return segment; }

	/** nullText = string to use for null value, or else <null> if this is not nullable. */
	public JEnumSegment(JEnum jenum, Object segment) {
		super(jenum.getKeyedModel());
		this.segment = segment;
	}
}
