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

import java.util.*;
import javax.mail.*;
//import java.net.*;
import javax.mail.internet.*;

public class MailTest
{

public static void main(String[] args) throws Exception
{
System.out.println("Hello");
	String to = "citibob@earthlink.net";

	// Get system properties
//	Properties props = System.getProperties();
	Properties props = new Properties();

	// Setup mail server
	props.put("mail.from", "citibob@earthlink.net");
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.host", "smtpauth.earthlink.net");
	props.put("mail.smtp.auth", "true");
	props.put("mail.user", "citibob@earthlink.net");

	// Get session
//	Authenticator auth = new GuiAuthenticator();
//	Session session = Session.getDefaultInstance(props, auth);
	GuiMailSender sender = new GuiMailSender();
	
	// Define message
	MimeMessage msg = new MimeMessage(sender.getSession());
	//msg.setFrom(new InternetAddress("citibob@earthlink.net"));
	msg.addRecipient(Message.RecipientType.TO, 
		new InternetAddress(to));
	msg.setSubject("Hello JavaMail");
	msg.setText("Welcome to JavaMail");

	msg.writeTo(System.out);

	// Send message
	sender.sendMessage(msg);
	//Transport.send(msg);

	System.exit(0);
}

}