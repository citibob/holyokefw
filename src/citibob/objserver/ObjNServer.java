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

public class ObjNServer implements Runnable
{
	
	String sockFilename;
	ObjServerMain objMain;
	Thread thread;
	
	Object syncObj;	// Synchronize on this object when handling a query.  This allows construction of an (effectively) single-threaded system, in which only one request at a time is being handled.  If null, don't synchro.
	NamedServerSocket serverSocket;
	
	public Thread getThread()	{ return thread; }
	public void start()			{ thread.start(); }
	public void stop()
	{
		thread.interrupt();
		try
		{
			serverSocket.close();
		}
		catch(IOException e)
		{}
		try
		{ thread.join(); }
		catch(InterruptedException ie)
		{}
	}
// -------------------------------------------------------
	public ObjNServer(String sockFilename) throws IOException
	{
		this.sockFilename = sockFilename;
		
		serverSocket = new NamedServerSocket(sockFilename);
		thread = new Thread(this);
	}
// -------------------------------------------------------
	public void init(ObjServerMain objMain, Object syncObj) throws IOException
	{
		this.objMain = objMain;
		this.syncObj = syncObj;
	}
// -------------------------------------------------------
	/** Loop, accepting connections and dispatching them to a thread. */
	public void run()
	{
		int queryNo = 0;
		for (;;)
		{
			boolean rupt = false;
			System.out.println("Server looking to accept...");
			try
			{
				Socket sock = serverSocket.accept();
				new ObjNServerThread(sock, queryNo++).start();
				System.out.println("response thread launched");
			}
			catch(IOException e)
			{
				rupt = true;
			}
			
			// We want a single-threaded server ONLY!!!
//		new ObjNServerThread(serverSocket.accept(), queryNo++).run();
			
			// Exit gracefully if we've been interrupted.
			rupt = rupt || thread.interrupted();
			if (rupt)
			{
				System.out.println("server interrupted.");
				try
				{ serverSocket.close(); }
				catch(IOException e)
				{}
				return;
			}
		}
		
	}
	
// ==============================================================
	private class ObjNServerThread extends Thread
	{
		private Socket socket;
		int queryNo;
		public ObjNServerThread(Socket socket, int queryNo)
		{
			this.socket = socket;
			this.queryNo = queryNo;
		}
		
		public void run()
		{
			InputStream in = null;
			OutputStream out = null;
//	ObjectOutputStream oout = null;
			try
			{
				in = socket.getInputStream();
				out = socket.getOutputStream();
				
				// Read the query.
				ObjectInputStream oin = new ObjectInputStream(in);
				ObjectOutputStream oout = new ObjectOutputStream(out);
				ObjQuery q = (ObjQuery)oin.readObject();

				if (syncObj != null) synchronized(syncObj) {
					objMain.handleQuery(queryNo, q, socket, oin, oout);
				} else {
					objMain.handleQuery(queryNo, q, socket, oin, oout);
				}
//System.out.println("writing 289");
//oout.writeObject(new Integer(289));
//System.out.println("Exiting query...");
			}
			catch(Exception e)
			{
				// Do nothing, just exit this query.
				e.printStackTrace(System.out);
			}
		}
	}	// ObjNServerThread
// -------------------------------------------------
	
// ==============================================================
	public static void main(String[] args) throws Exception
	{
//	ObjNServer s1 = new ObjNServer(new ObjMain(), "", null);
//	ObjNServer s2 = new ObjNServer(new ObjMain(), "", null);
//	ObjNServer s3 = new ObjNServer(new ObjMain(), "", null);
	}
	
	private static class ObjMain implements ObjServerMain
	{
		public void handleQuery(int queryNo, ObjQuery q, Socket sock,
				ObjectInputStream in, ObjectOutputStream out)
		{
			System.err.println("Query " + queryNo + ": " + q);
		}
	}
	
// ==============================================================
	
}


