/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.io.sslrelay;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** Specialized version of RelayIntoOut, hacked to serve
 *  the needs of SSLRelayClient.
 */
public class InOutRelay extends Thread {

public interface Listener {
	/** Called when the connection was closed, for a reason
	 * other than normal EOF termination.
	 * @param e Exception that caused the connection to close; or null if none.
	 */
	void onConnectionClosed(Exception e);
}


	private InputStream in ;
	private OutputStream out ;
	private String name;
	private Listener listener;

	// ---------------------------------------------------------------
	private void init (InputStream in,
	OutputStream out , String name, Listener listener)
	{
		this.listener = listener;
		this.name = name;
		this.in = in;
		this.out = out ;
//		setDaemon(true);
		this.start();
	}
	public InOutRelay ( InputStream in,
	OutputStream out , String name, Listener listener)
	{
		super(name);
		init(in, out, name, listener);
	}

	public InOutRelay ( InputStream in, OutputStream out, Listener listener)
	{
		super();
		init(in, out, null, listener);
	}
	// ---------------------------------------------------------------


	public void run(){

		int size ;
		byte[] buffer = new byte[8192];

		try {
			while(true) {
				size = in.read(buffer);
				if(size > 0 ) {
//System.out.println(name + " receive from in connection " + size + " bytes");
					out.write(buffer,0, size);
					out.flush();
//System.out.println(name  + " finish forwarding to out connection " + size + " bytes");
				} else if (size == -1) { //end of stream
//System.out.println(name + " EOF detected!");
//					out.close();
					closeAll(null);
					return ;
				}
			}
		}
		catch(Exception e){
//			System.err.println("Error in " + name);
//			e.printStackTrace(System.err);
			//	       System.err.println(name + e);
			closeAll(e);
		}


	}


//	public void closeAll() { closeAll(null); }

	/** Causes the thread to stop! */
	public void closeAll(Exception e){
//System.err.println("Running closeAll");
//		try{
			if(in != null ) try { in.close(); } catch(Exception e3) {}
			if(out != null) try { out.close(); } catch(Exception e3) {}
//System.err.println("closeAll: listener = " + listener);
			if (listener != null) listener.onConnectionClosed(e);
//		} catch(IOException e2){
//			e2.printStackTrace(System.err);
//	//		    System.err.println(e);
//		}
	}
}
