/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.gui;

import citibob.swing.prefs.SwingPrefs;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import org.xnap.commons.gui.CloseableTabbedPane;

/**
 *
 * @author fiscrob
 */
public class TabbedCompSet extends CloseableTabbedPane implements CompSet
{

Map<String,CompRec> map;
Map<JComponent,CompRec> cmap;
SwingPrefs swingPrefs;
Preferences prefsRoot;

public TabbedCompSet()
{ super(); }

/** Creates a new instance of FrameSet */
public void initRuntime(SwingPrefs swingPrefs, Preferences prefsRoot) {
	this.swingPrefs = swingPrefs;
	this.prefsRoot = prefsRoot;
	map = new TreeMap();
	cmap = new HashMap();
	
	setCloseListener(new CloseListener() {
	public void closeRequested(Component component) {
		CompRec rec = cmap.get(component);
		close(rec);
	}});
	
}	

public void addMaker(CompMaker maker) {
	CompRec rec = new CompRec();
//		rec.name = dirName;
		rec.maker = maker;
		rec.instance = null;
		rec.topLevel = null;
	map.put(rec.maker.getName(), rec);
}

void close(CompRec rec)
{
	super.remove(rec.instance);
	cmap.remove(rec.instance);
	rec.maker.dispose(rec.instance);	
	rec.instance = null;
	rec.topLevel = null;
}

public CompRec close(String name) {
	CompRec rec = map.get(name);
//	if (rec == null) return null;
	close(rec);
	return rec;
}

public void remove(String name)
{
	CompRec rec = close(name);
	rec.instance = null;
	rec.topLevel = null;
}

public JComponent getInstance(String name) {
	CompRec rec = map.get(name);
//	if (rec == null) return null;
	return rec.instance;
}

public JComponent getTopLevel(String name) {
	CompRec rec = map.get(name);
//	if (rec == null) return null;
	return rec.topLevel;
}

public JComponent open(String name) throws Exception {
	final CompRec rec = map.get(name);
	
	if (rec.instance == null) {
		rec.instance = rec.maker.newInstance();
		rec.topLevel = rec.instance;

//		swingPrefs.setPrefs(rec.instance, prefsRoot.node(rec.instance.getClass().getSimpleName()));
		swingPrefs.setPrefs(rec.instance, prefsRoot.node(rec.maker.getName()));
		
		cmap.put(rec.instance, rec);
		addTab(rec.maker.getTitle(), rec.instance);
	}
	
	super.setSelectedComponent(rec.instance);
	return rec.instance;
	
}

}
