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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.SqlRun;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author citibob
 */
public abstract class Resource
{

String name;
protected boolean required = false;
protected ResSet rset;
protected int resourceid;		// ID obtained from database
protected boolean editable = false;
String uversionType;		// Table or whatnot where uversionids are loaded from

public String getResourceGroup() { return uversionType; }

/** Can the user edit this resoure and save a new version? */
public boolean isEditable() { return editable; }

public ResSet getResSet() { return rset; }

public int getResourceID() { return resourceid; }

public Resource(ResSet rset, String uversionType, String name)
{ this(rset, uversionType, name, false); }

public Resource(ResSet rset, String uversionType, String name, boolean essential)
{
	this.rset = rset;
	this.uversionType = uversionType;
	this.name = name;
	this.required = essential;
}


public String getName() { return name; }
public String getSuffix()
{
	int dot = name.lastIndexOf('.');
	if (dot < 0) return "";
	return name.substring(dot);
}
public String getPrefix()
{
	int dot = name.lastIndexOf('.');
	if (dot < 0) return name;
	return name.substring(0,dot);	
}


TreeSet<Integer> versions = new TreeSet();	// All available versions (i.e. those referenced by an Upgrader)
Map<Integer,Node> nodes = new TreeMap();		// Each node has a set of upgraders with same version0

public SortedSet<Integer> getAllPossibleVersions() { return versions; }

// =============================================================
static class Version1Comparator implements Comparator<Upgrader> {
public int compare(Upgrader a, Upgrader b) {
	return a.version1() - b.version1();
}}
static Comparator<Upgrader> version1Comparator = new Version1Comparator();
static class Node extends TreeSet<Upgrader>
{
	public Node() { super(version1Comparator); }
}
// =============================================================
Node makeNode(int version0)
{
	Node node = nodes.get(version0);
	if (node == null) {
		node = new Node();
		nodes.put(version0, node);
	}
	return node;
}
public void add(Upgrader up)
{
	versions.add(up.version0());
	versions.add(up.version1());
	makeNode(up.version1());
	Node node = makeNode(up.version0());
	node.add(up);
}
// -------------------------------------------------

///** Gets a (sorted) list of all the versions we've seen. */
//TreeSet<Integer> getVersions() { return versions; }

private static class PNode implements Comparable<PNode>
{
	public Integer version;	// Identity of this node
	public Node info;
	public int ix;			// Index of this node in our array
	public int d;			// Best estimate of distance from source
	public PNode pred;	// Predecessor version # in best path
	public Upgrader predUpgrader;	// The link that gets to pred
	public int compareTo(PNode b) {
		return d - b.d;
	}
}

/** Plain and simple.
 Question: should this always prefer paths staring with a higher version0
 over paths with a lower version0, even if the one with higher version0 had
 a greater length?  Probably...
 Or maybe it should just return ALL the paths that it could find, the shortest
 one from each version0.
 See: http://renaud.waldura.com/doc/java/dijkstra/ */
public UpgradePlan getUpgradePlan(Integer ver0, Integer ver1)
{
	// Init our bookkeeping data structure
	int n = versions.size();
	PNode[] pnodes = new PNode[n];
	Map<Integer,PNode> map = new TreeMap();
	int i=0;
	for (Integer ver : versions) {
		PNode pn = new PNode();
			pn.version = ver;
			pn.ix = i;
			pn.d = Integer.MAX_VALUE;
			pn.info = nodes.get(ver);
		pnodes[i] = pn;
		map.put(ver, pn);
		++i;
	}
	SortedSet<PNode> S = new TreeSet();
	SortedSet<PNode> Q = new TreeSet();

System.out.println("start: ver0 = " + ver0);
	PNode start = map.get(ver0);
	if (start == null) return null;		// ver0 just doesn't exist, cannot make an upgrade plan starting from it.
	Q.add(start);
	start.d = 0;

	while (Q.size() > 0) {
		// Extract-minimum of Q
		PNode u = Q.first();
		Q.remove(u);
		
		// Add u to S
		S.add(u);
		
		// relax-neighbors
		for (Upgrader up : u.info) {
			PNode v = map.get(up.version1());
			if (S.contains(v)) continue;
			if (v.d > u.d + 1) {
				v.d = u.d + 1;
				v.pred = u;
				v.predUpgrader = up;
				Q.add(v);
			}
		}
	}
	
	
	// Extract the answer
	LinkedList<Upgrader> path = new LinkedList();
	for (PNode v = map.get(ver1); v != start; v = v.pred) {
		if (v == null) return null;		// We didn't find a path.
		path.addFirst(v.predUpgrader);
	}

	Upgrader[] ret = new Upgrader[path.size()];
	path.toArray(ret);
	if (ret == null) return null;
	UpgradePlan uplan = new UpgradePlan(ret);
	return uplan;
}

/** @return Finds a set of upgraders to carry out a desired upgrade ---
 * or null if no such path exists.  Uses Dijkstra's algorithm.  Prefers steps that
 * make large version leaps over small version leaps (i.e. find the shortest path
 * in terms of number of upgraders required).
 
 * @param versions0 Set of versions we wish to start from.  Includes -1 if we want
 * to consider just creating the resource.  versions0=-1 should be done in its own
 * separate getUpgradePlan(), and only if no upgrader was found...
 * @param version1
 * @param allowCreation if true, we will consider creator paths if we cannot find any
 * @return
 */
//protected UpgradePlan getUpgradePlan(int version0, int version1, boolean allowCreation)
//{
//	UpgradePlan uplan = getUpgradePlan(version0, version1);
//	if (uplan != null) return uplan;
//	return getUpgradePlan(-1, version1);
//}

protected UpgradePlan getCreatorPlan(int version1)
{
	return getUpgradePlan(-1, version1);
}


/** Auto-choose the preferred path from a bunch of shortest paths with
 * different starting points.  In GUI, this is not needed, user can
 * manually choose. */
public Upgrader[] getPreferredPlan(List<UpgradePlan> plans)
{
	return null;
}


/** Figures out all our options for upgrade based on what is available in the
 * database and what Upgraders we have on file.
 * @param uversionid
 * @param availVersions
 * @return
 */
public List<UpgradePlan> getAvailablePlans(int uversionid, Set<Integer> availVersions)
{
	return null;
}

public void applyPlan(SqlRun str, ConnPool pool, UpgradePlan uplan)
	throws Exception
{
	int uversionid = uplan.uversionid0();
	for (Upgrader up : uplan.getPath()) {
		up.upgrade(str, pool, uversionid, uplan.uversionid1());
		uversionid = uplan.uversionid0();
	}
}
// =============================================

//public String getName(int uversionid);



///** @returns The version of the currently stored default, which will be used
// * if no appropriate resource can be found. */
//public int storedVersion();

///** Creates this resource from scratch, at the current version.
// @overwrite If true, overwrite even a pre-existing resource. */
//public void create(SqlRunner str, int uversionid, boolean overwrite);

///** Upgrades existing resource to a new version
// @param uversionid if <0, then upgrade ALL resources of name */
//public void upgrade(SqlRunner str, int uversionid, int iversion0, int iversion1)
//{
//	// Get available versions...
//	
//	// Run shortest path...
//}

// ================================================================
// Runtime


/** Tells which version of this resource is required to support the given
 * system version.  Based on info of which versions we can have of this
 * resource (gleaned from the Upgraders).
 * @param systemVersion Version of current overall system
 @returns needed resource version. */
public int getRequiredVersion(int sysVersion)
{
	return versions.headSet(sysVersion+1).last();
	// If NullPointerException here, that means that we don't have
	// any record of this resource, or systemVersion is just too low.
}

///** Converts from raw bytes to the actual resource */
//public abstract Object toVal(byte[] bytes);
//
///** Converts from raw bytes to the actual resource */
//public abstract byte[] toBytes(Object val);


/** Loads the resource from the Classpath (JAR File) */
public ResResult loadJar(int version) throws IOException
{
	// Create JAR resource name (with version #)
	String rname;
	int dot = name.lastIndexOf('.');
	if (dot < 0) rname = name + "-" + version;
	else rname = name.substring(0,dot) + "-" + version + name.substring(dot);
	
	// File doesn't exist; read from inside JAR file instead.
	String resourceName = getResSet().getJarPrefix() + rname;
System.out.println("Loading template as resource: " + resourceName);
	InputStream in = rset.getJarClassLoader().getResourceAsStream(resourceName);
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buf = new byte[8192];
	int len;
	while ((len = in.read(buf)) > 0) baos.write(buf,0,len);
	in.close();
	baos.close();

	ResResult ret = new ResResult();
		ret.bytes = baos.toByteArray();
		ret.name = name;
		ret.uversionid = 0;
		ret.version = version;
	return ret;
//	return OffstageReports.class.getClassLoader().getResourceAsStream(resourceName);	
}



public ResResult load(SqlRun str, int uversionid, int version)
{
	return ResUtil.getResource(str, getName(), uversionid, version);
}
public ResResult loadRequiredVersion(SqlRun str, int uversionid, int sysVersion)
{
	return load(str, uversionid, getRequiredVersion(sysVersion));
}
//	// Get the bytes
//	final ResResult ret = new ResResult();
//	String sql =
//		" select rid.name,r.* from retources r, retourceids rid" +
//		" where rid.retourceid = r.retourceid" +
//		" and rid.name = " + SqlString.sql(getName()) +
//		" and uversionid = " + uversionid +
//		" and version = " +
//			" (select max(version) from retources r, retourceids rid" +
//			" where rid.retourceid = r.retourceid" +
//			" and name = " + SqlString.sql(getName()) +
//			" and uversionid = " + uversionid + ")";
//	str.execSql(sql, new RsRunnable() {
//	public void run(SqlRunner str, ResultSet rs) throws Exception {
//		ret.name = rs.getString("name");
//		ret.version = rs.getInt("version");
//		ret.uversionid = rs.getInt("uversionid");
//		ret.bytes = rs.getBytes("val");
//		// ret.bytes = toVal(bytes);
//	}});
//	return ret;

/** Should we refuse to run the application if this resource
 is not up to date?  Or should we run anyway, fialing gracefully when we
 cannot load the required resource? */
public boolean isRequired() { return required; }

}









//Dijkstra's algorithm is probably the best-known and thus most implemented shortest path algorithm. It is simple, easy to understand and implement, yet impressively efficient. By getting familiar with such a sharp tool, a developer can solve efficiently and elegantly problems that would be considered impossibly hard otherwise. Be my guest as I explore a possible implementation of Dijkstra's shortest path algorithm in Java.
//What It Does
//
//Dijkstra's algorithm, when applied to a graph, quickly finds the shortest path from a chosen source to a given destination. (The question "how quickly" is answered later in this article.) In fact, the algorithm is so powerful that it finds all shortest paths from the source to all destinations! This is known as the single-source shortest paths problem. In the process of finding all shortest paths to all destinations, Dijkstra's algorithm will also compute, as a side-effect if you will, a spanning tree for the graph. While an interesting result in itself, the spanning tree for a graph can be found using lighter (more efficient) methods than Dijkstra's.
//How It Works
//
//First let's start by defining the entities we use. The graph is made of vertices (or nodes, I'll use both words interchangeably), and edges which link vertices together. Edges are directed and have an associated distance, sometimes called the weight or the cost. The distance between the vertex u and the vertex v is noted [u, v] and is always positive.
//
//Dijkstra's algorithm partitions vertices in two distinct sets, the set of unsettled vertices and the set of settled vertices. Initially all vertices are unsettled, and the algorithm ends once all vertices are in the settled set. A vertex is considered settled, and moved from the unsettled set to the settled set, once its shortest distance from the source has been found.
//
//We all know that algorithm + data structures = programs, in the famous words of Niklaus Wirth. The following data structures are used for this algorithm:
//
//d 	stores the best estimate of the shortest distance from the source to each vertex
//π 	stores the predecessor of each vertex on the shortest path from the source
//S 	the set of settled vertices, the vertices whose shortest distances from the source have been found
//Q 	the set of unsettled vertices
//
//With those definitions in place, a high-level description of the algorithm is deceptively simple. With s as the source vertex:
//
//// initialize d to infinity, π and Q to empty
//d = ( ∞ )
//π = ()
//S = Q = ()
//
//add s to Q
//d(s) = 0
//
//while Q is not empty
//{
//     u = extract-minimum(Q)
//     add u to S
//     relax-neighbors(u)
//}
//
//Dead simple isn't it? The two procedures called from the main loop are defined below:
//
//relax-neighbors(u)
//{
//     for each vertex v adjacent to u, v not in S
//     {
//          if d(v) > d(u) + [u,v]    // a shorter distance exists
//          {
//               d(v) = d(u) + [u,v]
//               π(v) = u
//               add v to Q
//          }
//     }
//}
//
//extract-minimum(Q)
//{
//    find the smallest (as defined by d) vertex in Q
//    remove it from Q and return it
//}
//


