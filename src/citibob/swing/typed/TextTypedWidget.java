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
package citibob.swing.typed;

import citibob.types.JType;

/** A TypedWidget that involves some kind of formatted text. */
public interface TextTypedWidget extends TypedWidget
{
	public void setJType(JType jt, citibob.text.SFormat sformat);
	
	/** Once a formatter has figured out what the underlying value and display
	 should be, set it.  This is for DBFormatter, when we need to make a DB
	 query to format an item.  Only need to implement this method if we're
	 planning on making a "DB" subclass of this widget. */
	public void setDisplayValue(Object val, String display);
	
	/** Also for DBFormatter. */
	public void setHorizontalAlignment(int align);
}
