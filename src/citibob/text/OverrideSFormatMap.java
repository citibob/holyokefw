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
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package citibob.text;
//
///**
// *
// * @author fiscrob
// */
//public class OverrideSFormatMap implements SFormatMap
//{
//
//	SFormatMap[] maps;
//	
//	public OverrideSFormatMap(SFormatMap map0)
//	{
//		maps = new SFormatMap[] {map0, new DefaultSFormatMap()};
//	}
//
//public SFormat newSFormat(JType t, String colName)
//{
//	for (int i=maps.length; i >= 0; --i) {
//		SFormat fmt = maps[i].newSFormat(t, colName);
//		if (fmt != null) return fmt;
//	}
//	return null;
//}
//public SFormat[] newSFormats(JTypeTableModel model);
//public SFormat[] newSFormats(JTypeTableModel model, String[] scol, SFormat[] sfmt);
//
//
//}
