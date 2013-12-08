/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.gui;

import javax.swing.JComponent;

class CompRec {
//	public String name;
	public CompMaker maker;
	JComponent instance;		// The instance we're managing
	JComponent topLevel;		// The top-level component to display (may wrap instance)
}