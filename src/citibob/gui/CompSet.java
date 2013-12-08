package citibob.gui;

import javax.swing.JComponent;

public interface CompSet
{
	
public void addMaker(CompMaker maker);

/** Returns the instance */
public JComponent open(String name) throws Exception;

/** Disposes of this item. */
public CompRec close(String name) throws Exception;

public JComponent getInstance(String name);

public JComponent getTopLevel(String name);

}
