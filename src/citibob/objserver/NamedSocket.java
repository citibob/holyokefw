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
package citibob.objserver;

import java.net.*;
import java.io.*;

public class NamedSocket extends Socket
{
	
public NamedSocket(String sockFilename) throws IOException
{ this(new File(sockFilename)); }
	
// private int readSockfile() throws IOException
public NamedSocket(File sockFile) throws IOException
{
	super();

	// Read the socket file
	BufferedReader in = new BufferedReader(new FileReader(sockFile));
	String sPortno = in.readLine();
	String sCookie = in.readLine();
	String sHost = in.readLine();
	in.close();
	int portno;
	try {
		portno = Integer.parseInt(sPortno);
	} catch(NumberFormatException e) {
		throw new IOException("Could not read socket number in " + sockFile);
	}
	byte[] cookie = NamedServerSocket.hexDecode(sCookie);
//System.err.println("sCookie = " + sCookie);
//System.err.println("cookie  = " + NamedServerSocket.hexEncode(cookie));


	// Try to open specified port
	connect(new InetSocketAddress(sHost, portno));

	// Write magic cookie to the socket; see if we remain alive!
	OutputStream out = getOutputStream();
	out.write(cookie);
	out.flush();

	// Read the reply; this will throw IOException if other
	// side has rejected the magic cookie
	InputStream iin = getInputStream();
	if (iin.read() == -1) throw new IOException("Bad Magic Cookie connecting to Socket!");


}


}
