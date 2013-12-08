package citibob.io.sslrelay;

/**  ********************************************************************
Disclaimer
This example code is provided "AS IS" without warranties of any kind.
Use it at your Risk!

Our SSL secured Relay Server to handle all incoming data stream and forward them to the relevant port on the localmachine 
It is a subclass of the SSLConnection

Chianglin Jan 2003

********************************************************************** */

import java.net.*;
import javax.net.ssl.*;
import java.util.Date ;
import java.io.*;

public class SSLRelayServer extends SSLConnection {

private SSLServerSocket ss;
protected int localPort;

public int getLocalPort() { return localPort; }

    public SSLRelayServer(byte[] key , byte[] trust, char[] storePass, char[]
    storeKeypass , char[] trustPass, int  localport, int destport  ) throws Exception 
    {
      super(key, trust , storePass, storeKeypass, trustPass ); 
      initSSLServerSocket(localport);
      startListen(localport , destport );
     }


public void initSSLServerSocket(int localPort) throws Exception
{
	//get the ssl socket factory
	SSLServerSocketFactory ssf =
		getMySSLContext().getServerSocketFactory();

	//create the ssl server socket
	if (localPort < 0) {
		ss = (SSLServerSocket)ssf.createServerSocket();
		localPort = ss.getLocalPort();
	} else {
		ss = (SSLServerSocket)ssf.createServerSocket(localPort);
	}
	this.localPort = localPort;
	ss.setNeedClientAuth(false);

}
     
     
public void startListen(int localport , int destport)
{

	System.out.println("SSLRelay server started at " + (new Date()) + "  " +
	"listening on port " + localport + "  " +
	"relaying to port " + destport );

	while(true) {

		try {

			SSLSocket incoming = (SSLSocket) ss.accept();
			//set a ten minutes timeout
			incoming.setSoTimeout( 10 * 60* 1000 );
			System.out.println((new Date() ) + " connection from " + incoming );
			System.out.println("Timeout setting for socket is " + incoming.getSoTimeout() );

			createHandlers(incoming, destport);

		} catch(IOException e ) {
			e.printStackTrace();
//			System.err.println(e);
		}

	}
}

     
     public void createHandlers(SSLSocket incoming, int destport) throws IOException {
     
           //create a normal socket to connect to actual Server
           Socket s= new Socket("localhost" , destport);
	   //get the input and output streams associated with the actual server
           DataInputStream destin = new DataInputStream(
                                      new BufferedInputStream(s.getInputStream()));
	   
	   DataOutputStream destout = new DataOutputStream(
                                           new BufferedOutputStream(s.getOutputStream()));
					 
	  //get our secured input and output streams of relay server
	   DataInputStream securein  = new DataInputStream(
                                         new BufferedInputStream(incoming.getInputStream()));
					    				 
	   DataOutputStream secureout = new DataOutputStream(
                                         new BufferedOutputStream(incoming.getOutputStream()));
	  
	   //create the two handler threads
	   new InOutRelay( securein , destout , "SecureintoApp", null);
	   new InOutRelay( destin , secureout ,"ApptoSecureout", null);
     
         }
     
     
     public static void print_usage(){
     
         System.out.println("Simple SSL Relay Server");
	 System.out.println("Usage: java SSLRelayServer [keystorepath]  [truststorepath] " +
	                     "[storepassword] [keypassword] [localport] [destination port] " ); 
     
     }
     
     

//     public static void main(String[] args) throws Exception
//	 {
//     
//	 
//			
//	// Keystore with the certificate in it
//	URL key = new File("/Users/citibob/mvn/oassl/serverstore").toURL();
//	
//	// Keystore with the private key in it.
//	URL trust = key;
//	
//	String serverStorepass = "oasslont1me";
//	
//	SSLRelayServer relays = new SSLRelayServer(
//		key, trust, serverStorepass.toCharArray(), serverStorepass.toCharArray(),
//		5433,5432);
//	 
//	  }
         
     
     
     
}     
     
