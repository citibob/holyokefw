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
package citibob.util;

import java.util.*;
	
public class Poset<TT> {

HashMap<TT,Node> map = new HashMap();

static class Node<TT>
{
	public TT o;	// Underlying object of this node
	LinkedList<Node> greater = new LinkedList();	// Things greater than o
	LinkedList<Node> less = new LinkedList();	// Things less than o
	LinkedList<Node> gsort;	// Things greater than o (for the sort)
	LinkedList<Node> lsort;	// Things less than o (for the sort)
	public Node(TT o) { this.o = o; }
}

public Node add(TT o) throws Poset.Exception
{
	if (o == null) throw new Poset.Exception("Cannot add null to Poset");
	Node n = map.get(o);
	if (n != null) return n;
	n = new Node(o);
	map.put(o, n);
	return n;
}

/** Adds the relation b>a to the partial order. */
public void greater(TT b, TT a) throws Poset.Exception
{
//System.out.println("Greater: " + b + " > " + a);
	Node na = add(a);
	Node nb = add(b);
	na.greater.add(nb);
	nb.less.add(na);
}

void printGreaterLesser(Node n)
{
	System.out.print("   Greater:");
	for (Iterator<Node> ii = n.gsort.iterator(); ii.hasNext();) System.out.print(" " + ii.next().o);
	System.out.println();
	System.out.print("    Lesser:");
	for (Iterator<Node> ii = n.lsort.iterator(); ii.hasNext();) System.out.print(" " + ii.next().o);
	System.out.println();

}
void printGreaterLesserOrig(Node n)
{
	System.out.print("   Greater:");
	for (Iterator<Node> ii = n.greater.iterator(); ii.hasNext();) System.out.print(" " + ii.next().o);
	System.out.println();
	System.out.print("    Lesser:");
	for (Iterator<Node> ii = n.less.iterator(); ii.hasNext();) System.out.print(" " + ii.next().o);
	System.out.println();

}

/** For debugging */
Node findNode(String name)
{
	// Q <- Set of nodes with no incoming edge
	for (Map.Entry<TT,Node> e : map.entrySet()) {
		Node n = e.getValue();
		if (name.equals(n.o.toString())) return n;
	}
	return null;
}
/** See Wikipedia: topological sort */
public LinkedList<TT> sort() throws Poset.Exception
{
//System.out.println("Initial events.source.main:");
//printGreaterLesserOrig(findNode("Context(events.source.main)"));

LinkedList<Node> Q = new LinkedList();
	LinkedList<TT> out = new LinkedList();		// Sorted List

	// Q <- Set of nodes with no incoming edge
	for (Map.Entry<TT,Node> e : map.entrySet()) {
		Node n = e.getValue();
		n.gsort = (LinkedList<Node>)n.greater.clone();
		n.lsort = (LinkedList<Node>)n.less.clone();
		if (n.lsort.size() == 0) {
//System.out.println("Initial Q: " + n.o);
//if ("Context(events.source.main)".equals(n.o.toString())) {
//	printGreaterLesser(n);
//}
			Q.add(n);
		}
	}
	

	while (Q.size() > 0) {
		Node<TT> n = Q.remove();
		out.add(n.o);
//System.out.println("Sort: " + n.o);
//if ("Context(recorder)".equals(n.o.toString()) || "Context(events.source.main)".equals(n.o.toString())) {
//	printGreaterLesser(n);
//	System.out.print("sellVolSum:"); printGreaterLesser(findNode("Context(sellVolSum)"));
//	System.out.print("buyVolSum:"); printGreaterLesser(findNode("Context(buyVolSum)"));
//}
		for (Iterator<Node> ii = n.gsort.iterator(); ii.hasNext(); ) {
			Node m = ii.next();
			ii.remove();
			m.lsort.remove(n);
//System.out.println("   Removing edge: " + m.o + " > " + n.o);
			if (m.lsort.size() == 0) {
				Q.add(m);
//				System.out.println("   Adding node: " + m.o);
//if ("Context(events.source.main)".equals(m.o.toString())) {
//	printGreaterLesser(m);
//}
			}
//if ("Context(buyVolSum)".equals(m.o.toString()))
		}
	}
	
	// If graph has edges then there's an error
	boolean err = false;
	for (Map.Entry<TT,Node> e : map.entrySet()) {
		Node n = e.getValue();
		
		if (n.lsort.size() > 0) {
			err = true;
for (Iterator<Node> ii = n.lsort.iterator(); ii.hasNext(); ) {
	Node m = ii.next();
	System.out.println("Cycle edge: " + n.o + " > " + m.o);
}
		}
		n.gsort = null;
		n.lsort = null;
	}
	if (err) throw new Poset.Exception("Cycle found in DAG");
	return out;
}
// ============================================================
public static class Exception extends java.lang.Exception
{
	public Exception(String msg) { super(msg); }
}
// ============================================================
public static void main(String[] args) throws Exception
{
	String[] s = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
	Poset po = new Poset();
	po.greater(s[7], s[2]);
	po.greater(s[3], s[0]);
	po.greater(s[4], s[0]);
	po.greater(s[4], s[2]);
	po.greater(s[5], s[3]);
	po.greater(s[3], s[1]);
	po.greater(s[6], s[3]);
	po.greater(s[6], s[4]);
	po.greater(s[7], s[3]);
	
	po.sort();
}
}
