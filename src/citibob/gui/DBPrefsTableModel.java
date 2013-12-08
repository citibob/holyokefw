///*
//Holyoke Framework: library for GUI-based database applications
//This file Copyright (c) 2006-2008 by Robert Fischer
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/
///*
// * DBPrefsTableModel.java
// *
// * Created on March 11, 2006, 6:23 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package citibob.gui;
//
//import java.awt.event.*;
//import java.util.prefs.*;
//import de.jppietsch.prefedit.*;
//import java.util.*;
//import java.sql.*;
//import citibob.sql.*;
//
///**
// *
// * @author citibob
// */
//public class DBPrefsTableModel extends PreferencesTableModel {
//
//Preferences prefs;
//TreeSet keyset;
//
//public static final String HOSTADDR = "DB Host Address";
//public static final String PORT = "Port";
//public static final String DATABASE = "Database";
//public static final String USER = "User";
////public static final String PASSWORD = "Password";
//public static final String SSL = "SSL";
//public static final String DRIVERCLASS = "Driver Class";
//public static final String DRIVERTYPE = "Driver Type";
//public static final String DEFPASSWORD = "Default Password";
//
//private void addField(String key, String dflt)
//{
//	if (!keyset.contains(key)) {
//		prefs.put(key, dflt);
//		keyset.add(key);
//	}
//}
//
//public Preferences getPrefs() { return prefs; }
//
///** Creates new form DBPrefsPanel */
//public DBPrefsTableModel(Preferences prefs)
//throws BackingStoreException
//{
//	super();
//	this.prefs = prefs;
////	prefs = Preferences.userRoot();
////	prefs = prefs.node(nodePath);
//
//	// Set up to add defaults
//	String[] keys = prefs.keys();
//	keyset = new TreeSet();
//	for (int i=0; i<keys.length; ++i) keyset.add(keys[i]);
//
//	// Add defaults if they don't exist already
//	addField(HOSTADDR, "127.0.0.1");
//	addField(PORT, "5432");
//	addField(DATABASE, "");
//	addField(USER, "");
//	addField(SSL, "true");
//	addField(DRIVERTYPE, "postgresql");
//	addField(DRIVERCLASS, "org.postgresql.Driver");
//	addField(DEFPASSWORD, "");
//	this.setPreferences(prefs);
//}
//public ConnPool newConnPool(String password)
//throws SQLException, java.lang.ClassNotFoundException
//{
//	return new DBConnPool(getPrefs(), password);
//}
//
//// ==========================================================
//static class DBConnPool extends RealConnPool
//{
//
//Properties props = new Properties();
//String url;
//
//public DBConnPool(Preferences prefs, String password)
//throws SQLException, ClassNotFoundException
//{
//	super(null, null);
//	
//	Class.forName(prefs.get(DRIVERCLASS, null));
//	props.setProperty("user", prefs.get(USER, null));
////	props.setProperty("ssl", prefs.get(SSL, null));
//	
//	String pwd = password;
//	if (pwd == null || "".equals(pwd)) pwd = prefs.get(DEFPASSWORD, null);
//	props.setProperty("password", pwd);
//
//	url = "jdbc:" + prefs.get(DRIVERTYPE, null) + "://" +
//		prefs.get(HOSTADDR, null) +
//		":" + prefs.get(PORT, null) +
//		"/" + prefs.get(DATABASE, null);
////System.out.println("DBConnPool: url = " + url);
//}
//
//    /** Creates a new instance of JMBTConnection */
//    protected Connection create() throws SQLException
//	{
//		return DriverManager.getConnection(url, props);
////		return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/jmbt", "jmbt", "fiercecookie");
////		return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/jmbt2", "jmbt", "fiercecookie");
//    }
//
//}
//
//
//}
