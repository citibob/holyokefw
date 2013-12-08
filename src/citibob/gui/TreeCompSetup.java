/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.gui;


import citibob.app.App;
import citibob.gui.BaseCompMaker;
import citibob.gui.CompMaker;
import citibob.gui.CompSet;
import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *
 * @author fiscrob
 */
public class TreeCompSetup {

DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("/"));
//Map<String, DefaultMutableTreeNode> nodeMap = new TreeMap();

protected App app;
protected CompSet compSet;
public TreeCompSetup(final App app, CompSet compSet)
{
	this.app = app;
	this.compSet = compSet;
}

public TreeModel getTreeModel() { return treeModel; }

public void addMaker(String dir, CompMaker maker)
{
	compSet.addMaker(maker);
	int lastSlash = 0;
	DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode)treeModel.getRoot();
	for (;;) {
		int slash = dir.indexOf('/', lastSlash+1);
		if (slash < 0) break;
		
		String nodeName = dir.substring(lastSlash+1, slash);
		DefaultMutableTreeNode node;
		for (int i=0; ; ++i) {
			if (i >= lastNode.getChildCount()) {
				// Create new node
				node = new DefaultMutableTreeNode(nodeName);
				lastNode.add(node);
			}
			node = (DefaultMutableTreeNode)lastNode.getChildAt(i);
			if (node.getUserObject().equals(nodeName)) break;
		}

		lastNode = node;
		lastSlash = slash;
	}

	lastNode.add(new DefaultMutableTreeNode(maker));
}


}
