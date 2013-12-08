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

//import java.util.Properties;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import org.solinger.cvspass.Scramble;

//import javax.mail.*;
//
//import java.util.prefs.*;
//import javax.mail.*;
//import javax.mail.internet.*;
//import org.solinger.cvspass.Scramble;

/** Fishes authentication info out of a Java Properties object.  No user interaction */
public class ConstAuthenticator extends Authenticator
{

Properties props;

public ConstAuthenticator(Properties props)
{
	this.props = props;
}

protected  PasswordAuthentication getPasswordAuthentication()
{
	String username = props.getProperty("mail.smtp.user");
	String password = props.getProperty("mail.password");
	//password = Scramble.descramble(password);

	return new PasswordAuthentication(username, password);
}


}
