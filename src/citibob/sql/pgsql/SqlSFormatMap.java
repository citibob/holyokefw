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
///*
// * SqlSFormatMap.java
// *
// * Created on March 18, 2006, 8:24 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package citibob.sql.pgsql;
//
//import citibob.swing.typed.*;
//import citibob.sql.*;
//import citibob.sql.pgsql.*;
//import citibob.text.*;
//import citibob.swing.typed.*;
//import citibob.text.BoolSFormat;
//import citibob.types.JType;
//import java.util.*;
//
///**
// *
// * @author citibob
// */
//public class SqlSFormatMap extends JavaSFormatMap
//{
//
//
///**
// * Creates a new instance of SqlSFormatMap 
// */
//public SqlSFormatMap() { this(null); }
//
//public SqlSFormatMap(final TimeZone tz) {
//	super();
//	
//	// SqlBool
//	this.addMaker(SqlBool.class, new SFormatMap.Maker() {
//	public SFormat newSFormat(JType sqlType) {
//		return new BoolSFormat();
//	}});
//	
//	// SqlDate
//	this.addMaker(SqlDate.class, new SFormatMap.Maker() {
//	public SFormat newSFormat(JType sqlType) {
//		return new JDateSFormat("MM/dd/yyyy", tz);
//	}});
//
//	// SqlInteger
//	this.addMaker(SqlInteger.class, new SFormatMap.Maker() {
//	public SFormat newSFormat(JType sqlType) {
//		return new ToStringSFormat();
//	}});
//
//	// SqlString
//	this.addMaker(SqlString.class, new SFormatMap.Maker() {
//	public SFormat newSFormat(JType sqlType) {
//		return new ToStringSFormat();
//	}});
//
//	// SqlString
//	this.addMaker(SqlChar.class, new SFormatMap.Maker() {
//	public SFormat newSFormat(JType sqlType) {
//		return new ToStringSFormat();
//	}});
//
//	// SqlTime
//	this.addMaker(SqlTime.class, new SFormatMap.Maker() {
//	public SFormat newSFormat(JType sqlType) {
//		return new FormatSFormat(new SimpleTimeFormat("HH:mm:ss"));
////			JDateSFormat(java.util.TimeZone.getTimeZone("GMT"), "HH:mm:ss");
//	}});
//
//	// SqlTimestamp
//	this.addMaker(SqlTimestamp.class, new SFormatMap.Maker() {
//	public SFormat newSFormat(JType sqlType) {
//		return new JDateSFormat("MM-dd-yyyy HH:mm", tz);
//	}});
//}
//	
//}
