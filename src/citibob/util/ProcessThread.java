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
package citibob.util;

import java.io.*;


public class ProcessThread extends Thread
{
//PrintWriter out;
Writer out;
String[] cmdArray;
Process process;
String[] envp;
File dir;

/** Sets up thread that will run a system command and pipe output to outputWriter */
public ProcessThread(String[] cmdArray, String[] envp, File dir, Writer outputWriter)
{
	init(cmdArray, envp, dir, outputWriter);
}

public ProcessThread() {}

public void init(String[] cmdArray, String[] envp, File dir,
Writer outputWriter)
{
	// this.out = new PrintWriter(outputWriter);
	this.out = outputWriter;
	this.cmdArray = cmdArray;
	this.envp = envp;
	this.dir = dir;
}

public void run()
{
	try {
out.write("Running: " + cmdArray[0] + " in " + dir + "\n");
		process = Runtime.getRuntime().exec(cmdArray, envp, dir);

		char[] cbuf = new char[1024];
		Reader in = new InputStreamReader(process.getInputStream());
		int n;
		while ((n = in.read(cbuf, 0, cbuf.length)) != -1) {
			out.write(cbuf, 0, n);
		}
/*
		BufferedReader pin = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String s;
		while ((s = pin.readLine()) != null) {
			out.write("[ornet] " + s + "\n");
		}
*/

		out.write("\nExiting...\n");
		try { out.close(); } catch(Exception ee) {}

	} catch(Exception e) {
		try {
			out.close();
		} catch(Exception ee) {}
	}
}

public void stopProcess()
{
	process.destroy();
}

}
