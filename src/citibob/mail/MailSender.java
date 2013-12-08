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

public interface MailSender
{
public static final int SENT = 0;			// message sent successfully
public static final int CANCELLED = 1;		// User chose not to send
public static final int FAILED = 2;			// Could not send
// ====================================================
	public Session getSession();
	public int sendMessage(MimeMessage msg) throws Exception;
}
