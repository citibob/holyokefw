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

public class ObjNClient
{
    
// -------------------------------------------------------
    
    private String sockFilename;
    private NamedSocket socket;
    
    public ObjNClient()
    {}
    
    
    public void close() throws IOException
    {
        //System.err.println("ObjNClient: close() method of ObjNClient called.");
        if (socket != null)
        {
///            System.err.println("ObjNClient: socket is not null.");
            if ( ! socket.isClosed())
            {
                socket.close();
//                System.err.println("ObjNClient: socket successfully closed.");
            }
//            else System.err.println("ObjNClient: socket was already closed!!!");
        }
//        else System.err.println("ObjNClient: socket was null, so close not attempted.");
    }
    
    public ObjNClient(String sockFilename)
    {
        this.init(sockFilename);
    }
    public void init(String sockFilename)
    {
        try
        {
            this.close();
        }
        catch (IOException ioe)
        {
            // This should only happen if the socket is not null and is not closed
            // and was not able to close normally by calling its .close() method.
            // It should be a rare occurence.  For now I am just going to set the 
            // socket to null and hope that the garbage collector gets rid of it. - Jon
            socket = null;
        }
        // we continue initializing by setting the new sockFilename
        this.sockFilename = sockFilename;
    }
    
    public SendQueryReturn sendQuery(ObjQuery q) throws IOException
    { return sendQuery(sockFilename, q); }
    
    public InputStream sendSelectQuery(ObjQuery q) throws IOException
    { return sendQuery(sockFilename, q).in; }
    
    public OutputStream sendUpdateQuery(ObjQuery q) throws IOException
    { return sendQuery(sockFilename, q).out; }
    
    public Object sendSelectQueryRet(ObjQuery q) throws IOException, ClassNotFoundException
    {
        ObjectInputStream ooin = new ObjectInputStream(sendQuery(sockFilename, q).in);
        Object ret = ooin.readObject();
        ooin.close();
        return ret;
    }
    
    
    
    private SendQueryReturn sendQuery(String sockFilename, ObjQuery q)
    throws IOException
    {
        SendQueryReturn ret = new SendQueryReturn();
        
        socket = new NamedSocket(sockFilename);
        ret.out = socket.getOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(ret.out);
        oout.writeObject(q);
        oout.flush();
        // For some reason, this socket.getInputStream() must come AFTER the
        // above writeObject().
        ret.in = socket.getInputStream();
        return ret;
    }
// --------------------------------------------------------
    /** Structure allowing the return of two objects from the {@link ViperClient#sendQuery} method.
     */
    public static class SendQueryReturn
    {
        /** The stream from the server to the client. */
        public InputStream in;
        /** The stream from the client to the server. */
        public OutputStream out;
    }
    
    
    
//public static void closeSocket() throws IOException
//{
//    socket.close();
//}
    
}
