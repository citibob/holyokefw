/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.crypt.ConstPBEAuth;
import citibob.crypt.PBECrypt;
import java.io.IOException;

/**
 *
 * @author citibob
 */
public class WriteablePBEConfig extends PBEConfig
implements WriteableConfig
{

public WriteablePBEConfig(WriteableConfig sub, char[] password)
{
	super(sub, new ConstPBEAuth(password));
}

public void add(String name, byte[] bytes) throws IOException {
	if (name.endsWith(".crypt")) {
		((WriteableConfig)sub).add(name, bytes);
	} else {
		// Copy encrypted
		try {
			name = name + ".crypt";
			String cipherText = PBECrypt.encrypt(bytes, auth.getPassword());
			bytes = cipherText.getBytes();
			((WriteableConfig)sub).add(name, bytes);
		} catch(Exception e) {
			IOException ioe = new IOException(e.getMessage());
			ioe.initCause(e);
			throw ioe;
		}
	}
}

}
