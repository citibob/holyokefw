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
import java.util.*;
import java.security.*;

public class NamedServerSocket extends ServerSocket
{

public static final int COOKIELEN = 32;
static Random random;
private byte[] cookie;

static {
	try {
		random = SecureRandom.getInstance("SHA1PRNG");
	} catch(java.security.NoSuchAlgorithmException e) {
		e.printStackTrace(System.err);
		System.exit(-1);
	}
}

/*
private boolean openSocket()	// true for success
{
	for (sockno = 49152; sockno < 65535; ++sockno) {
		try {
			serverSocket = new ServerSocket(sockno);
			return true;
		} catch(IOException e) {
			// Try next port...
		}
	}
	return false;
}
*/
private static final char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
  static public String hexEncode( byte[] aInput){
    StringBuffer result = new StringBuffer(aInput.length * 2);
    for ( int idx = 0; idx < aInput.length; ++idx) {
      byte b = aInput[idx];
      result.append( digits[ (b&0xf0) >> 4 ] );
      result.append( digits[ b&0x0f] );
    }
    return result.toString();
  }

static private int decodeDigit(char c)
{
	if (c >= '0' && c <= '9') return (c - '0');
	return (c - 'a' + 10);
}

static public byte[] hexDecode(String s)
{
//System.out.println("s.length = " + s.length());
	byte[] ret = new byte[s.length() / 2];
	for (int i = 0; i < ret.length; ++i) {
//System.out.println("i = " + i);
		ret[i] = (byte)(
			(decodeDigit(s.charAt(i<<1)) << 4) +
			decodeDigit(s.charAt((i<<1)+1))
		);
	}
	return ret;
}

public NamedServerSocket(String sockFilename)
throws IOException
	{ this(new File(sockFilename)); }

public NamedServerSocket(File sockFile)
throws IOException
{
	super(0);	// Create socket on any free port

	// Get the random cookie
	cookie = new byte[COOKIELEN];
	synchronized(random) {
		random.nextBytes(cookie);
	}

	// Write the socket number to out file.
	//new FileOutputStream(sockFilename).close();
	//OS.setPrivate(sockFilename);
	sockFile.getParentFile().mkdirs();
	PrintWriter out = new PrintWriter(new FileOutputStream(sockFile));
	out.println(getLocalPort());
	out.println(hexEncode(cookie));
	InetAddress thisIp = InetAddress.getLocalHost();
	out.println(thisIp.getHostAddress());
System.out.println("Opened NamedServerSocket on port " + getLocalPort());
	out.close();
}

void readBytes(InputStream in, byte[] b)
throws IOException
{
	for (int c = 0; c < b.length; ) {
		int n = in.read(b, c, b.length - c);
		if (n < 0) throw new IOException();
		c += n;
	}
}

public Socket accept() throws IOException
{
	for (;;) {
		Socket socket = null;
//		try {
			socket = super.accept();
//System.err.println("ACCEPT!");
			InputStream in = socket.getInputStream();

			// Read magic cookie
			// XXX: This is susceptible to denial of service attacks
			byte[] scookie = new byte[COOKIELEN];
			readBytes(in, scookie);
//System.err.println("ACCEPT!");

			// Compare it to true cookie
			for (int i = 0; i < COOKIELEN; ++i)
				if (cookie[i] != scookie[i]) {
					socket.close();
					continue; //throw new IOException();
				}

			// It's passed!  This socket is good.
			OutputStream out = socket.getOutputStream();
			out.write(1);
			out.flush();
			return socket;
//		} catch(IOException e) {
//			if (socket != null) try {
//				// OutputStream out = socket.getOutputStream();
//				// out.write(0);
//				// out.flush();
////System.err.println("Server closing socket.");
//				socket.close();
//			} catch(IOException ie) {}
//		}
	}
}
public static void main(String[] args)
{
    try {
     InetAddress thisIp =
        InetAddress.getLocalHost();
     System.out.println("IP:"+thisIp.getHostAddress());
     }
    catch(Exception e) {
     e.printStackTrace();
     }
}
}
