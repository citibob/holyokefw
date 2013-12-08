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
package citibob.swing;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import gnu.xml.dom.ls.WriterOutputStream;
import foundry.io.DocumentWriter;
import javax.swing.event.*;
import java.io.*;
import citibob.swing.text.CircularPlainDocument;
import citibob.io.*;
import java.awt.*;
import java.awt.event.*;

public class JLogScrollPane extends JScrollPane
{
	
	final JTextArea textArea;
	Writer logWriter;
	boolean autoScroll = true;
	boolean scrollLock = false;			// True if user scrolled down to the very end; stop autoScroll temporary in that case.
	Color oldBG;						// Original background color of scrollbar
	boolean timed_buffer;
	int time_delay;
	int buffersize;
	
//public JTextArea getTextArea() { return textArea; }
	
	/** Creates a new instance of JLogScrollPanel */
	public JLogScrollPane()
	{
		super();
		
//		getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener() {
//	    public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
//			if (e.getValueIsAdjusting()) {
//				scrollLock = true;
//			} else {
//				Adjustable adj = e.getAdjustable();
//				scrollLock = !(adj.getValue() + adj.getVisibleAmount() == adj.getMaximum());
////					e.getValue() == e.getAdjustable().getMaximum());
////System.err.println("scrollLock = " + scrollLock + " x" + adj.getVisibleAmount() + " " +
////	adj.getValue() + " " + adj.getMaximum());
//			}
//System.err.println("scrollLock = " + scrollLock);
//
//		}});
		
		
		getVerticalScrollBar().addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
			if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
//            if (evt.getClickCount() == 2) {          // Double-click
				scrollLock = !scrollLock;
				JScrollBar sb = getVerticalScrollBar();
				if (scrollLock) {
					oldBG = sb.getBackground();
					sb.setBackground(Color.RED);
					
//					System.err.println("caret: " + textArea.getCaretPosition() + " " + textArea.getDocument().getLength());
					int len = textArea.getDocument().getLength();
//					int scrollVal = getVerticalScrollBar().getValue();
					if (textArea.getCaretPosition() == len) {
						textArea.setCaretPosition(len-1);
					}
//					getVerticalScrollBar().setValue(scrollVal);
//					if (textArea.getCaretPosition() >= this.textArea.getDocument().getLength())
//		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
				} else {
					sb.setBackground(oldBG);
//					int len = textArea.getDocument().getLength();
//					textArea.setCaretPosition(len-1);
					scrollToEnd();
				}
				
//System.err.println("scrollLock = " + scrollLock);
			}
		}});
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		setViewportView(textArea);

		this.timed_buffer = false;
		this.time_delay = 0;
		this.buffersize=0;
	}
	
	public JLogScrollPane(boolean timed_buffer, int delayMS, int bufsize)
	{
		this();
		setTimedBuffering(timed_buffer, delayMS, bufsize);
	}
	
	public void setTimedBuffering(boolean timed_buffering, int delayMS, int bufsize)
	{
		this.timed_buffer = timed_buffering;
		this.time_delay = delayMS;
		this.buffersize = bufsize;
	}
	
	public Writer getWriter()
	{ return logWriter; }
	
	public void setAutoScroll(boolean as)
	{
		if (!autoScroll && as) scrollToEnd();
		autoScroll = as;
	}
	
	public void setDocument(Document doc)
	{
		textArea.setDocument(doc);
//		textArea.setCaretPosition(textArea.getDocument().getLength());
//System.out.println("textArea text is (this = " + this + "):" + textArea.getText());
//	if (autoScroll) {
//		doc.addDocumentListener(new DocumentListener() {
//			public void changedUpdate(DocumentEvent e) {}
//			public void removeUpdate(DocumentEvent e) {}
//			public void insertUpdate(DocumentEvent e) {
//				scrollToEnd();
//			}
//		});
//	}
	}
	
	public void scrollToEnd()
	{
		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
	
//		// Scroll to end
//		final JScrollBar sb = getVerticalScrollBar();
//		final int max = sb.getMaximum() - sb.getVisibleAmount();
//		if (sb.getValue() != max) {
//			java.awt.EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				sb.setValue(max);
//			}});
//		}
////		if (sb.getValue() != sb.getMaximum())
////		{
////			java.awt.EventQueue.invokeLater(new Runnable()
////			{
////				public void run()
////				{
////					sb.setValue(sb.getMaximum());
////				}
////			});
////		}
	}
	
	public void newDocument(int nchars)
	{
		Document doc = new CircularPlainDocument(nchars);
		doc.addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				if (autoScroll && !scrollLock) scrollToEnd();
			}
			public void insertUpdate(DocumentEvent e)
			{
				if (autoScroll && !scrollLock) scrollToEnd();
			}
			public void removeUpdate(DocumentEvent e)
			{
				//if (autoScroll) scrollToEnd();
			}
		});
		
		if (timed_buffer)
		{
//			System.err.println("Making TimedBufferedWriter");
			logWriter = new TimedBufferedWriter(new DocumentWriter(doc), time_delay, buffersize);
		}
		else
		{
//			System.err.println("Making LineBufferWriter");
			logWriter = new LineBufferWriter(new DocumentWriter(doc));
		}
		setDocument(doc);
//try {
//logWriter.write("Hello World\n");
//System.err.println("Text is: " + doc.getText(0,doc.getLength()));
//} catch(Exception e) {
//	e.printStackTrace(System.out);
//}
		
	}
	
	public void setHistorySize(int nchars) {
		newDocument(nchars);
	}
	public void redirectStdout()
	{
		OutputStream screenOut = new WriterOutputStream(logWriter);
		System.setOut(new PrintStream(screenOut));
	}
	
	
	public JTextArea getTextArea()
	{ return textArea; }
	
// =====================================================================
//	public class ScrolledLineBufferWriter extends LineBufferWriter
//	{
//		public ScrolledLineBufferWriter(Writer out)
//		{ super(out); }
//		
//		public void processLine() throws IOException
//		{
//			super.processLine();
//			if (autoScroll)
//			{
//				System.err.println("invoking the scrollToEnd() method.");
//				scrollToEnd();
//			}
//		}
//	}
// =====================================================================
	public static void main(String[] args)
	throws Exception
	{
		JFrame f = new JFrame();
		f.setSize(200,200);
		JLogScrollPane sp = new JLogScrollPane();
		sp.getTextArea().setText("Hoi 3");
		f.getContentPane().add(sp);
		sp.getTextArea().setText("Hoi 4");
		f.setVisible(true);
		
		try { Thread.sleep(4000); } catch (InterruptedException ie) { }
		
		sp.setHistorySize(10000);
		sp.redirectStdout();
		
		System.out.println("Hello World Redirect");
		System.out.println("I like you too");
		
		for (int i=0; i<20; i++)
		{
			System.out.println(i+"\tHello World Redirect");
			System.out.println(i+"\tI like you too");
			System.out.println();
			try { Thread.sleep(250); } catch (InterruptedException ie) { }
		}
		
		
	}
	
	
	
}


