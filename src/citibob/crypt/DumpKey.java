package citibob.crypt;

/* DumpKey.java
 * Copyright (c) 2007 by Dr. Herong Yang, http://www.herongyang.com/
 */
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class DumpKey {

/**
 * @param jksFile
 * @param jksPass
 * @param keyName
 * @param keyPass
 * @param out
 * @return The key in binary form.  Can write to file using OutputStream.write(byte[])
 */
public static byte[] dumpKey(
File jksFile, char[] jksPass,
String keyName, char[] keyPass)
throws KeyStoreException, IOException, NoSuchAlgorithmException,
CertificateException, UnrecoverableKeyException
{
	KeyStore jks = KeyStore.getInstance("jks");
	jks.load(new FileInputStream(jksFile), jksPass);
	Key key = jks.getKey(keyName, keyPass);
	System.out.println("Key algorithm: "+key.getAlgorithm());
	System.out.println("Key format: "+key.getFormat());
	System.out.println("Writing key in binary form");

	return key.getEncoded();
}

static public void main(String[] a)
throws Exception
{
	if (a.length<5) {
		System.out.println("Usage:");
		System.out.println(
		"java DumpKey jks storepass alias keypass out");
		return;
	}
	
	String jksFile = a[0];
	char[] jksPass = a[1].toCharArray();
	String keyName = a[2];
	char[] keyPass = a[3].toCharArray();
	String outFile = a[4];

	byte[] key = dumpKey(new File(jksFile), jksPass, keyName, keyPass);
	OutputStream out = new FileOutputStream(new File(outFile));
	out.write(key);
	out.close();
}
}
