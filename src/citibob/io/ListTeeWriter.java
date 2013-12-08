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
package citibob.io;

import java.io.*;
import java.util.*;

public class ListTeeWriter extends Writer
{
    List<Writer> outs;
    Writer out2;

    public ListTeeWriter(List<Writer> outs)
    {
		this.outs = outs;
    }

    // Override methods of Writer
    public void close()
        throws java.io.IOException
    {
		for (Writer w : outs) w.close();
    }

    public void flush()
            throws java.io.IOException
    {
		for (Writer w : outs) w.flush();
    }

    // Implementation of Writer's abstract method
    public void write(int b)
            throws java.io.IOException
    {
		for (Writer w : outs) w.write(b);
    }
    public void write(char b[], int off, int len) throws IOException {
		for (Writer w : outs) w.write(b,off,len);
	}
    public void write(char b[]) throws IOException {
		for (Writer w : outs) w.write(b);
	}
	public void write(String str) throws IOException {
		for (Writer w : outs) w.write(str);
	}
	public void write(String str, int off, int len) throws IOException {
		for (Writer w : outs) w.write(str, off, len);
	}

}