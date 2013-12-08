/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.gui;

import javax.swing.JComponent;

public interface CompMaker
{
	public JComponent newInstance() throws Exception;
	
	public void dispose(JComponent instance);
	
	public String getTitle();

	public String getName();
}