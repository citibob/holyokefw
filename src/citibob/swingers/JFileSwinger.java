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
package citibob.swingers;

import citibob.swing.typed.*;
import static citibob.swing.typed.JTypedTextField.*;
import citibob.text.FileSFormat;
import citibob.types.JFile;
import java.io.File;

/**
 *
 * @author citibob
 */
public class JFileSwinger extends AbstractSwinger
{

public JFileSwinger(JFile jFile)
{
	super(jFile, new FileSFormat());
}

/** Just create the widget, do not configure it. */
protected citibob.swing.typed.TypedWidget createWidget()
{
	JTypedFileName tw = new JTypedFileName();
	return tw;
}

///** Override this.  Most swingers don't have to configure their widgets,
//but Swingers for complex widget types do. */
public void configureWidget(TypedWidget tw)
{
	JTypedFileName fn = (JTypedFileName)tw;
	fn.setJType((JFile)getJType());
}


}
