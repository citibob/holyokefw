/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.crypt;

import java.awt.Frame;

/**
 *
 * @author citibob
 */
public class DialogPBEAuth extends BasePBEAuth
{

char[] password;
PasswordDialog dialog;
boolean exitOnCancel = true;

public DialogPBEAuth(Frame parent, String message, String envVar)
{
	super(envVar);
	dialog = new PasswordDialog(parent);
	dialog.setMessage(message);
}

public void setExitOnCancel(boolean exitOnCancel)
{
	this.exitOnCancel = exitOnCancel;
}

/** Run when user hits Cacnel on the dialog.  Can be overridden. */
protected void userCancelled()
{
	if (exitOnCancel) System.exit(0);
}

public void dispose() {
	for (int i=0; i<password.length; ++i) password[i] = '\0';
	dialog.dispose();
	dialog = null;
}


public char[] queryPassword()
{
	if (state == PWD_NONE) dialog.setAlert("");
	else dialog.setAlert("Bad password; try again!");

	dialog.setVisible(true);
	if (!dialog.getOK()) {
		userCancelled();
		return null;
	}

	return dialog.getPassword();
}
}
