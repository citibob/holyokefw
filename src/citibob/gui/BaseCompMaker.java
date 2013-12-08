/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.gui;

import javax.swing.JComponent;

/**
 *
 * @author fiscrob
 */
public class BaseCompMaker implements CompMaker
{
String title;
String name;

public BaseCompMaker(String name, String title) {
	this.title = title;
	this.name = name;
}
public void dispose(JComponent instance) {
}
public JComponent newInstance() { return null; }

public String getTitle() {
	return title;
}

public String getName() { return name; }

public String toString() { return title; }

}
