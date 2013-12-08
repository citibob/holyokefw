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
 * FlushedOutputStream.java
 *
 * Created on November 29, 2005, 12:58 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.io;

import java.io.*;

public class FlushedOutputStream extends FilterOutputStream
{

int n=0;
final int max;

private void addn(int len)
throws IOException
{
	n += len;
	if (n > max) {
System.err.println(n + " Flushing...");
		flush();
	}
}

public void flush()
throws IOException
{
	super.flush();
	n=0;
}

public void write(byte[] b)
throws IOException
{
	super.write(b);
	addn(b.length);
}
public void write(byte[] b, int off, int len)
throws IOException
{
	super.write(b,off,len);
	addn(len);
}
public void write(int b)
throws IOException
{
	super.write(b);
	addn(1);
}

 
/** Creates a new instance of FlushedOutputStream */
public FlushedOutputStream(OutputStream out, int max) {
	super(out);
	this.max = max;
}
	
}
