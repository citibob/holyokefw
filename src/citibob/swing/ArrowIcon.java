/* NOTE: Third Party Code
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 
package citibob.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 *
 * @author fiscrob
 */
public class ArrowIcon implements Icon
{
private boolean descending;
private int size;
//private int priority;
private int dx;

public ArrowIcon(boolean descending, int size, int priority) {
	this.descending = descending;
	this.size = size;
//	this.priority = priority;
	dx = (int)(size/2*Math.pow(0.8, priority));
}

public void paintIcon(Component c, Graphics g, int x, int y) {
	Color color = c == null ? Color.GRAY : c.getBackground();             
	// In a compound sort, make each succesive triangle 20% 
	// smaller than the previous one. 
//	int dx = (int)(size/2*Math.pow(0.8, priority));
	int dy = descending ? dx : -dx;
	// Align icon (roughly) with font baseline. 
	y = y + 5*size/6 + (descending ? -dy : 0);
	int shift = descending ? 1 : -1;
	g.translate(x, y);

	// Right diagonal. 
	g.setColor(color.darker());
	g.drawLine(dx / 2, dy, 0, 0);
	g.drawLine(dx / 2, dy + shift, 0, shift);

	// Left diagonal. 
//	g.setColor(color.brighter());
	g.drawLine(dx / 2, dy, dx, 0);
	g.drawLine(dx / 2, dy + shift, dx, shift);

	// Horizontal line. 
//	g.setColor(color.darker().darker());
//	if (descending) {
//		g.setColor(color.darker().darker());
//	} else {
//		g.setColor(color.brighter().brighter());
//	}
	g.drawLine(dx, 0, 0, 0);

	g.setColor(color);
	g.translate(-x, -y);
}

public int getIconWidth() {
	return size;
}

public int getIconHeight() {
	return size;
}
}

