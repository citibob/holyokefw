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
//import gnu.net.local.*;

public class ObjSClient
{

// -------------------------------------------------------

InetAddress addr;
int portNo;

public ObjSClient(InetAddress addr, int portNo)
{
	this.addr = addr;
	this.portNo = portNo;
}

public SendQueryReturn sendQuery(ObjQuery q) throws IOException
	{ return sendQuery(addr, portNo, q); }

public ObjectInputStream sendSelectQuery(ObjQuery q) throws IOException
	{ return sendQuery(addr, portNo, q).in; }

public ObjectOutputStream sendUpdateQuery(ObjQuery q) throws IOException
	{ return sendQuery(addr, portNo, q).out; }

public static SendQueryReturn sendQuery(InetAddress addr, int portNo, ObjQuery q)
throws IOException
{
	SendQueryReturn ret = new SendQueryReturn();

	Socket socket = new Socket(addr, portNo);
	ret.out = new ObjectOutputStream(socket.getOutputStream());
	ret.out.writeObject(q);
	ret.out.flush();
	// For some reason, this socket.getInputStream() must come AFTER the
	// above writeObject().
	ret.in = new ObjectInputStream(socket.getInputStream());
	return ret;
}
// --------------------------------------------------------
/** Structure allowing the return of two objects from the {@link ViperClient#sendQuery} method.
*/
public static class SendQueryReturn
{
	/** The stream from the server to the client. */
	public ObjectInputStream in;
	/** The stream from the client to the server. */
	public ObjectOutputStream out;
}
// ==========================================================
private static class TestQuery implements ObjQuery, Serializable
{
	String s;
}
public static void main(String[] args)
throws Exception
{
	InetAddress addr = InetAddress.getByName("127.0.0.1");
	ObjSClient cli = new ObjSClient(addr, 2503);
	TestQuery q = new TestQuery();
	q.s = "17";
	ObjectInputStream oin = cli.sendSelectQuery(q);
	Object o = oin.readObject();
	System.out.println("o = " + o);	
}
}
