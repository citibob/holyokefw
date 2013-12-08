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
package citibob.mail;

import javax.mail.*;

import java.util.prefs.*;
import javax.mail.*;
//import java.net.*;
import javax.mail.internet.*;
import org.solinger.cvspass.Scramble;

public class GuiAuthenticator extends Authenticator
{
public static final int OK = 0;
public static final int CANCEL = 1;

int buttonPressed;
String statusMessage = "";
String lastPassword = "";
String nodePath;
String guiNodePath;

public GuiAuthenticator(String nodePath, String guiNodePath)
{
	this.nodePath = nodePath;
	this.guiNodePath = nodePath;
}

public int getButtonPressed()
	{ return buttonPressed; }

// Sets an "extra" message to be displayed to the user.
public void setStatusMessage(String statusMessage)
{
	this.statusMessage = statusMessage;
}

protected  PasswordAuthentication getPasswordAuthentication()
{
	// Preferences p = Preferences.userNodeForPackage(this.getClass());
	Preferences prefs = Preferences.userRoot();
	prefs = prefs.node(nodePath);
	String host = prefs.get("mail.smtp.host", "<nohost>");
	String username = prefs.get("mail.smtp.user", "<nouser>");
	String password = prefs.get("mail.password", null);
	if (password.startsWith("A")) password =
		Scramble.descramble(prefs.get("mail.password", null));
	boolean rememberPassword = prefs.getBoolean("mail.rememberPassword", false);

	AuthenticatorDialog d = new AuthenticatorDialog(null,
		host, username,
		password == null ? 0 : password.length(),
		rememberPassword, statusMessage);
	d.show();

	if (!d.getOK()) {
		buttonPressed = CANCEL;
System.err.println("x CANCEL");
		return null;	// User cancelled.
	}
	buttonPressed = OK;
System.err.println("x OK");

	if (d.getPasswordChanged()) password = d.getPassword();

	rememberPassword = d.getRememberPassword();
	prefs.putBoolean("mail.rememberPassword", rememberPassword);
	if (rememberPassword) prefs.put("mail.password", Scramble.scramble(password));
	else prefs.remove("mail.password");

	return new PasswordAuthentication(username, password);
}


}
