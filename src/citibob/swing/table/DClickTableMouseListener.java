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
 * DoubleClickTableMouseListener.java
 *
 * Created on March 6, 2006, 11:04 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.swing.table;

import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author citibob
 */
public abstract class DClickTableMouseListener implements MouseListener
{
	
	JTable table;
	
	/** Creates a new instance of DoubleClickTableMouseListener */
	public DClickTableMouseListener(JTable table) {
		this.table = table;
	}
	
	public abstract void doubleClicked(int row);
	
// Mouse Listener
/**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  public void mouseClicked(MouseEvent e)
  {
    if( e.getClickCount() == 2 )
    {
      int row = this.table.rowAtPoint( e.getPoint() );
      if( row != -1 ) {
		  doubleClicked(row);
      }
    }
  }
  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  public void mousePressed(MouseEvent e)
  {
  }
  /**
   * Invoked when a mouse button has been released on a component.
   */
  public void mouseReleased(MouseEvent e)
  {
  }
  /**
   * Invoked when the mouse enters a component.
   */
  public void mouseEntered(MouseEvent e)
  {
  }
  /**
   * Invoked when the mouse exits a component.
   */
  public void mouseExited(MouseEvent e)
  {
  }
  
}
