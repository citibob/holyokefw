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

public class ObjSServer implements  Runnable {

ObjServerMain objMain;
Thread thread;

Object syncObj;	// Synchronize on this object when handling a query.  This allows construction of an (effectively) single-threaded system, in which only one request at a time is being handled.  If null, don't synchro.
ServerSocket serverSocket;
int portNo;

// -------------------------------------------------------
// -------------------------------------------------------
public ObjSServer(ObjServerMain objMain, int portNo, Object syncObj) throws IOException
{

	serverSocket = new ServerSocket(portNo);
	thread = new Thread(this);
	//thread.start();
}
public Thread getThread()
	{ return thread; }
public void start()
	{ thread.start(); }
public void stop()
{
	try {
		serverSocket.close();
	} catch(IOException e) {}
	thread.interrupt();
}
public ObjSServer(int portNo) throws IOException
{
	this(null, portNo, null);
}
public void init(ObjServerMain objMain, Object syncObj)
{
	this.portNo = portNo;
	this.objMain = objMain;	
}
// -------------------------------------------------------
/** Loop, accepting connections and dispatching them to a thread. */
public void run()
{
	int queryNo = 0;
	try {
		for (;;) {
			System.out.println("Server looking to accept...");
			// We want a multi-threaded server
			//System.out.println("Multi-threaded");
			new ObjSServerThread(serverSocket.accept(), queryNo++).start();

			// We want a single-threaded server ONLY!!!
			// MATLAB is single-threaded in any case.
			//new ObjSServerThread(serverSocket.accept()).run();
		}
	} catch(IOException e) {
		// Exit...
		if (thread.isInterrupted()) {
			// We were asked to stop, socket is already closed.
			System.out.println("Server Exiting");
			return;
		}
	}
	try {
		serverSocket.close();
	} catch(IOException e) {}
}
// ==============================================================
private class ObjSServerThread extends Thread
{
private Socket socket;
private int queryNo;
public ObjSServerThread(Socket socket, int queryNo)
{
	this.socket = socket; 
	this.queryNo = queryNo;
}

public void run()
{

	InputStream in = null;
	OutputStream out = null;
	ObjectOutputStream oout = null;
	try {
		in = socket.getInputStream();
		out = socket.getOutputStream();

		// Read the query.
		ObjectInputStream oin = new ObjectInputStream(in);
		ObjQuery q = (ObjQuery)oin.readObject();

		// Run the query.
		oout = new ObjectOutputStream(out);
		if (syncObj != null) {
			synchronized(syncObj) {
				objMain.handleQuery(queryNo, q, socket, oin, oout);
			}
		} else {
			objMain.handleQuery(queryNo, q, socket, oin, oout);
		}
		// ObjQueryContext qcon = objMain.newQueryContext();
		// q.run(qcon, oin, oout);

	} catch(Exception e) {
		// Do nothing, just exit this query.
		e.printStackTrace(System.err);
	}

//	if (closeSocket) {
		// Flush for good measure, in case the stream is still
		// open and the query has not flushed it.  If it's already
		// closed, that's not a problem.
		try {
			if (oout != null) oout.flush();
		} catch(IOException ie) {}
		try {
			// out.close();
			socket.close();
		} catch(IOException e) {
			// Some low-level I/O error, we can't even reply.  Just punt.
		}
//	}
}
} // ObjSServerThread
// -------------------------------------------------

// ==============================================================
public static void main(String[] args) throws Exception
{
	ObjSServer s1 = new ObjSServer(new ObjMain(), 2502, null);
//	ObjSServer s2 = new ObjSServer(new ObjMain(), 2501, null);
//	ObjSServer s3 = new ObjSServer(new ObjMain(), 2502, null);
}

private static class ObjMain implements ObjServerMain
{
	public void handleQuery(int queryNo, ObjQuery q, Socket sock, ObjectInputStream in, ObjectOutputStream out)
	{
		System.err.println("Query: " + q);
		try {
			out.writeObject("My Reply");
		} catch(Exception e) {
			System.err.println("Cannot write reply!");
		}
	}
}

// ==============================================================

} // ObjSServer
