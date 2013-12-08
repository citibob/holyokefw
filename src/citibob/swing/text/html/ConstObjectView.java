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
package citibob.swing.text.html;

import java.awt.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.util.*;

public class ConstObjectView extends ObjectView
{

	
Component obj;		// The component we map to

  /**
   * Creates a new <code>ObjectView</code>.
   *
   * @param el the element for which to create a view
   */
  public ConstObjectView(Element el, HashMap map)
  {
    super(el);
    AttributeSet atts = el.getAttributes();
    String classId = (String) atts.getAttribute("classid");
	this.obj = (Component)map.get(classId);
  }

  /**
   * Creates a component based on the specification in the element of this
   * view. See the class description for details.
   */
  protected Component createComponent()
  {
	  if (obj != null) return obj;
	  else return super.createComponent();
  }
}
