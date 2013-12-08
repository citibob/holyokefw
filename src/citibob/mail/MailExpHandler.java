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
/*
 * MailExpHandler.java
 *
 * Created on April 12, 2006, 11:06 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.mail;

import citibob.app.App;
import citibob.task.AppError;
import citibob.task.FatalAppError;
import citibob.task.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.*;
import javax.swing.SwingUtilities;
import javax.swing.text.*;

/**
 *
 * @author citibob
 */
public class MailExpHandler extends BaseExpHandler
{
//	MailSender sender;
	InternetAddress bugRecipient;
	String programName;
	Document stdoutDoc;
	App app;
	
	public MailExpHandler(App app, InternetAddress bugRecipient,
	String programName, Document stdoutDoc)
	{
		this.stdoutDoc = stdoutDoc;
		this.app = app;
//		this.sender = sender;
		this.bugRecipient = bugRecipient;
		this.programName = programName;
	}
	public void consume(final Throwable eMain)
	{
		SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			Throwable e = getRootCause(eMain);

			// Get the last bit of the Java console stdout
			String outMsg = "";
			if (stdoutDoc != null) {
				try {
					int nsave = 4000;
					int docLen = stdoutDoc.getLength();
					int start = docLen - nsave;
					if (start < 0) start = 0;
					outMsg = stdoutDoc.getText(start, docLen - start);
				} catch(BadLocationException ee) {
				}
			}

			// Get the stack trace
			StringWriter ss = new StringWriter();
			PrintWriter pw = new PrintWriter(ss);
			pw.print(getNestedMessages(eMain));
			e.printStackTrace(pw);
//			URL url = null;
//			try {
//				url = app.getConfigResource("");
//			} catch(Exception e2) {
//				url = null;
//			}
			String msgText = 
				"Bug in: " + programName + " " + app.version() + "\n" +
				"Version: " + app.version() + "\n" +
				"User: " + System.getProperty("user.name") + "\n" +
					"Config Name: " + app.config().getName() + "\n\n" +
	//			e.toString() + "\n" +
				ss.getBuffer().toString() + "\n" +
				"=================================================\n" +
				outMsg + "\n";
			System.out.println(ss.toString());
	//		System.err.println(msgText);

			// Get other info
			String userName = System.getProperty("user.name");


			// Let user fiddle with the stack trace
			boolean askUser = app.props().getProperty("mail.bugs.askuser").toLowerCase().equals("true");
			askUser = askUser && !(e instanceof AppError);
			final MailExpDialog dialog = new MailExpDialog(null, programName,
				e, msgText, askUser,
				app.swingPrefs(), app.guiRoot().node("MailExpDialog"));
			dialog.setVisible(true);

			if (e instanceof FatalAppError) System.exit(-1);
			if (askUser && !dialog.isReportError()) return;


			new Thread() {
			public void run() {
				try {
					// Define message
					MimeMessage msg = new MimeMessage(app.mailSender().getSession());
					//msg.setFrom(new InternetAddress("citibob@earthlink.net"));
					msg.setSubject("Bug in " + programName);
					msg.setText(dialog.getMsg());
					msg.addRecipient(Message.RecipientType.TO, bugRecipient);

					app.mailSender().sendMessage(msg);
				} catch(Exception ee) {
					System.out.println("Could not send bug report!!!");
					ee.printStackTrace(System.out);
				}
			}}.start();
		}});
	}

	/** Creates a new instance of MailExpHandler */
	public MailExpHandler() {
	}
	
}
