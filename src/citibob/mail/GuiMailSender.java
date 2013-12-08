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

import java.util.*;
import java.io.*;
//import gnu.net.local.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.prefs.*;

public class GuiMailSender implements MailSender
{
public static final int SENT = 0;			// message sent successfully
public static final int CANCELLED = 1;		// User chose not to send
public static final int FAILED = 2;			// Could not send
// ====================================================
GuiAuthenticator auth;
Session session;		// JavaMail session
Properties props;	// Properties for the session
// -----------------------------------------------------
public GuiMailSender() throws IOException
{
	// Get session
	props = new Properties();
	auth = new GuiAuthenticator("citibob/mail", "citibob/mail");
	session = Session.getInstance(props, auth);

	updateMailProps();
}
public Session getSession() { return session; }
// -----------------------------------------------------
public void updateMailProps()
{
	// Set up mail sending...
	// This WILL come from preferences...
	Preferences p = Preferences.userNodeForPackage(GuiMailSender.class);
	props.setProperty("mail.transport.protocol", "smtp");
	props.setProperty("mail.smtp.host", p.get("mail.smtp.host", ""));
	props.setProperty("mail.smtp.auth", p.get("mail.smtp.auth", ""));
	props.setProperty("mail.smtp.user", p.get("mail.smtp.user",""));
	props.setProperty("mail.smtp.port", p.get("mail.smtp.port","25"));
}
// -----------------------------------------------------
/** Returns status of sending... */
public int sendMessage(MimeMessage msg) throws Exception
{
	Preferences p = Preferences.userNodeForPackage(GuiMailSender.class);

	msg.setFrom(new InternetAddress(
		p.get("mailx.from.name", "") + "<" + p.get("mail.from", "") + ">"));
	String replyTo = p.get("mailx.replyto", "");
	if (!"".equals(replyTo)) msg.setReplyTo(new InternetAddress[] {
		new InternetAddress(replyTo)});


	// Keep trying to send as long as authentication errors happen.
	// Look up into auth exactly what happened.
	auth.setStatusMessage("");
	auth.getPasswordAuthentication();
	for (;;) {
		try {
			Transport.send(msg);
			return SENT;
		} catch(SendFailedException sfe) {
			Exception e = sfe.getNextException();
			if (e instanceof AuthenticationFailedException) {
				if (auth.getButtonPressed() == GuiAuthenticator.OK) {
System.err.println("OK pressed");
					// User had invalid password or username
					auth.setStatusMessage("Bad username or password; please try again.");
					continue;
				}
System.err.println("CANCEL pressed");
				return CANCELLED;
			} else {
System.err.println("FAILED");
				sfe.printStackTrace(System.out);
				return FAILED;
			}
		}
	}
}
}
