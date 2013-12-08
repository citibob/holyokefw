/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.crypt;

/**
 *
 * @author citibob
 */
public class ConstPBEAuth implements PBEAuth
{

char[] password;

public ConstPBEAuth(char[] password)
	{ this.password = password; }

	public void dispose() {}

	public char[] getPassword() { return password; }
	
	public void validatePassword() {}	
}
