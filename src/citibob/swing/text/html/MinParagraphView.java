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
package citibob.swing.text.html;

import javax.swing.text.html.*;
import javax.swing.*;

/**
 *
 * @author citibob
 */
public class MinParagraphView extends ParagraphView
{

public MinParagraphView(javax.swing.text.Element elem) {
	super(elem);
}

/** (Description from GNU Classpath 0.9.2)<br/>
* Calculates the minor axis requirements of this view. This is implemented
* to return the super class'es requirements and modifies the minimumSpan
* slightly so that it is not smaller than the length of the longest word.
*
* @param axis the axis
* @param r the SizeRequirements object to be used as return parameter;
*        if <code>null</code> a new one will be created
*
* @return the requirements along the minor layout axis
*/
protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r)
{
	SizeRequirements sr = super.calculateMinorAxisRequirements(axis, r);
	sr.minimum = 50;
	return sr;
}

}
