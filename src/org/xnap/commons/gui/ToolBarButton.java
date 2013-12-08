/*
 *  XNap Commons
 *
 *  Copyright (C) 2005  Steffen Pingel
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.xnap.commons.gui;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.JButton;

/**
 * This class provides a toolbar button with an appropriately sized icon.
 * The border of the button is only visible when the mouse hovers over the 
 * button.
 */
public class ToolBarButton extends JButton {

    private boolean showBorder = false;
    private boolean showMenuHint;

    public ToolBarButton(Action action)
    {
		super(action);

		setContentAreaFilled(false);
		setText(null);
		setMargin(new Insets(1, 1, 1, 1));

		putClientProperty("hideActionText", Boolean.TRUE);
    }

	/**
	 * Returns true, if the mouse is currently over the button.
	 */
	public boolean isMouseOver()
	{
		return showBorder;
	}

    @Override
    protected void paintBorder(Graphics g)
    {
		if (showBorder) {
			super.paintBorder(g);
		}
    }

    @Override
	protected void processMouseEvent(MouseEvent e)
	{
		super.processMouseEvent(e);
		
		if (e.getID() == MouseEvent.MOUSE_ENTERED) {
			showBorder = true;
			setContentAreaFilled(true);
			repaint();
		}
		else if (e.getID() == MouseEvent.MOUSE_EXITED) {
			showBorder = false;
			setContentAreaFilled(false);
			repaint();
		}
	}
	
}
