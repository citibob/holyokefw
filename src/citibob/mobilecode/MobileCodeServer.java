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
package citibob.mobilecode;

import citibob.io.LoaderObjectInputStream;
import citibob.objserver.NamedServerSocket;
import java.net.*;
import java.io.*;

public class MobileCodeServer implements Runnable
{
	
	File sockFile;
	MobileCodeServerMain objMain;
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
	public MobileCodeServer(File sockFile) throws IOException
	{
		this.sockFile = sockFile;
		
		serverSocket = new NamedServerSocket(sockFile);
		thread = new Thread(this);
	}
// -------------------------------------------------------
	public void init(MobileCodeServerMain objMain, Object syncObj) throws IOException
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
				//out = socket.getOutputStream();
				
				// Set up classloader, and deserializer no classes yet
				MobileCodeLoader classloader = new MobileCodeLoader();
//				DeserializationContext dcon = new DeserializationContext(classloader);

				// Create a deserializer relative to our classloader
//				DataInputStream oin = new DataInputStream(in);
//				ObjectInputStream oin = new ObjectInputStream(in);
//				Class klass = classloader.loadClass(MCObjectInputStream.class.getName());
//				Constructor con = klass.getConstructor(InputStream.class);
//				ObjectInputStream oin = (ObjectInputStream)con.newInstance(in);

				ObjectInputStream oin = new LoaderObjectInputStream(in, classloader);
				
				// Read the classes
//				MobileClass[] classes = (MobileClass[]) dcon.deserialize(oin);
				MobileClass[] classes = (MobileClass[]) oin.readObject();
				classloader.addClasses(classes);

				// Deserialize the object
//				Object obj = dcon.deserialize(oin);
				Object obj = oin.readObject();
				
				// Dispatch based on object's class
				objMain.handleQuery(queryNo, obj, socket);
//System.out.println("writing 289");
//oout.writeObject(new Integer(289));
//System.out.println("Exiting query...");
			}
			catch(Exception e)
			{
				// Do nothing, just exit this query.
				e.printStackTrace(System.out);
				
				try { socket.close(); } catch(IOException ioe) {
//					ioe.printStackTrace(System.out);
				}
			}
		}
	}	// ObjNServerThread
// -------------------------------------------------
	
// ==============================================================
	public static void main(String[] args) throws Exception
	{
		MobileCodeServer s1 = new MobileCodeServer(new File("c:\\sock"));
		s1.init(new ObjMain(), null);
		s1.start();
		s1.thread.join();
	}
	
	private static class ObjMain implements MobileCodeServerMain
	{
		public void handleQuery(int queryNo, Object query, Socket sock)
//				DataInputStream in, DeserializationContext dcon)
		{
			System.err.println("Query " + queryNo + ": " + query);
		}
	}
	
// ==============================================================

}


