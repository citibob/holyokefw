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

public class TeeOutputStream extends OutputStream
{
    OutputStream[] outs;
    OutputStream out2;

    public TeeOutputStream(OutputStream[] outs)
    {
		this.outs = outs;
    }

    // Override methods of OutputStream
    public void close()
        throws java.io.IOException
    {
		for (int i=0; i<outs.length; ++i) outs[i].close();
    }

    public void flush()
            throws java.io.IOException
    {
		for (int i=0; i<outs.length; ++i) outs[i].flush();
    }

    // Implementation of Outputstream's abstract method
    public void write(int b)
            throws java.io.IOException
    {
		for (int i=0; i<outs.length; ++i) outs[i].write(b);
    }
    public void write(byte b[], int off, int len) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(b,off,len);
	}
    public void write(byte b[]) throws IOException {
		for (int i=0; i<outs.length; ++i) outs[i].write(b);
	}

}