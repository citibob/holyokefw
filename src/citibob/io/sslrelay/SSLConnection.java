package citibob.io.sslrelay;

/** ********************************************************************
Disclaimer
This example code is provided "AS IS" without warranties of any kind.
Use it at your Risk!

SSLConnection class that will holds the common traits for both the 
client and the server relay. The client and server proxy will inherit from this 
class 

Chianglin Jan 2003

************************************************************ */


import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;
import java.security.cert.CertificateException;



public class SSLConnection {


private SSLContext ctx ;
private KeyStore mykey , mytrust ;
private byte[] keyBytes, trustBytes ;


//Default constructor takes the filename of the keystore and truststore , 
//the password of the stores and the password of the private key
public SSLConnection(byte[] keyBytes , byte[] trustBytes , char[] storepass, char[]
storeKeyPass, char[] trustPass)
throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
CertificateException, IOException, UnrecoverableKeyException, KeyManagementException
{
	this.keyBytes = keyBytes;
	this.trustBytes = trustBytes ;
	initSSLContext(storepass , storeKeyPass, trustPass);
}




/* mykey holding my own certificate and private key, mytrust holding all the 
certificates that I trust */
public void initKeyStores(byte[] keyBytes , byte[] trustBytes , char[] storepass, char[] trustpass)
throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
CertificateException, IOException
{
 	//get instances of the Sun JKS keystore
 	mykey = KeyStore.getInstance("JKS" , "SUN");
	mytrust = KeyStore.getInstance("JKS", "SUN");

	//load the keystores
	mykey.load(new ByteArrayInputStream(keyBytes), storepass);
	mytrust.load(new ByteArrayInputStream(trustBytes), trustpass);
//	InputStream storeStream = null;
//	InputStream trustStream = null;
//	try {
//		storeStream = key.openStream();
//		if (storeStream == null) throw new IOException("Cannot open URL: " + key);
//		trustStream = trust.openStream();
//		if (trustStream == null) throw new IOException("Cannot open URL: " + trust);
//		mykey.load(storeStream, storepass);
//		mytrust.load(trustStream, trustpass );
//	} finally {
//		if (storeStream != null) storeStream.close();
//		if (trustStream != null) trustStream.close();
//	}
}



public void initSSLContext(char[] storePass, char[] storeKeyPass , char[] trustPass)
throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
CertificateException, IOException, UnrecoverableKeyException, KeyManagementException
{ 
    ctx = SSLContext.getInstance("TLSv1" , "SunJSSE");
    initKeyStores(keyBytes , trustBytes , storePass, trustPass);
    //Create the key and trust manager factories for handing the cerficates 
    //in the key and trust stores
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509" , 
    "SunJSSE");
    tmf.init(mytrust);
    
    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509" , 
    "SunJSSE");
    kmf.init(mykey , storeKeyPass);
    
    ctx.init(kmf.getKeyManagers() , tmf.getTrustManagers() ,null) ;

}

public SSLContext getMySSLContext() {
return ctx ;
}

}
