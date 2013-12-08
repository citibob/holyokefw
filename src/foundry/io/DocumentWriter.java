/*
 * $Id: DocumentWriter.java,v 1.3 2001/04/14 04:06:42 ljnelson Exp $
 *
 * @copyright@
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * The original copy of this license is available at 
 * http://www.opensource.org/license/mit-license.html.
 */
package foundry.io;

import java.io.IOException;
import java.io.Writer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

/**
 * A {@link Writer} that writes to a {@link Document}.
 *
 * @author     <a href="mailto:ljnelson@unix.amherst.edu">Laird Nelson</a>
 * @version    $Revision: 1.3 $
 * @since      0.9.0
 */
public class DocumentWriter extends Writer {

  /**
   * The {@link Document} to write to; may not be <code>null</code>.
   */
  private final Document document;

  /**
   * A flag indicating whether this <code>DocumentWriter</code> is closed.
   */
  private boolean isClosed;

  /**
   * Creates a new <code>DocumentWriter</code>.
   */
  public DocumentWriter() {
    this(new PlainDocument());
  }

  /**
   * Creates a new <code>DocumentWriter</code>.
   *
   * @param      component
   *               a {@link JTextComponent} from which a {@link
   *               Document} will be extracted (via the {@link
   *               JTextComponent#getDocument()} method); may not be
   *               <code>null</code>
   * @exception  <code>NullPointerException</code>
   *               if <code>component</code> is <code>null</code>
   */
  public DocumentWriter(final JTextComponent component) 
    throws NullPointerException {
    this(component.getDocument());
  }

  /**
   * Creates a new <code>DocumentWriter</code>.
   *
   * @param      document
   *               a {@link Document} to write to; may not be <code>null</code>
   * @exception  <code>NullPointerException</code>
   *               if <code>document</code> is <code>null</code>
   */
  public DocumentWriter(final Document document) {
    super();
    if (document == null) throw new NullPointerException();
    this.document = document;
    this.isClosed = false;
  }

  /**
   * Returns the {@link Document} that will be written to.  This method will 
   * never return <code>null</code>.
   *
   * @return     the {@link Document} that will be written to; never
   *               <code>null</code>
   */
  public Document getDocument() {
    return this.document;
  }

  /**
   * Flushes the buffer.  This implementation simply throws an 
   * {@link IOException} if this <code>DocumentWriter</code> is closed.
   *
   * @exception  <code>IOException</code>
   *               if this <code>DocumentWriter</code> is closed
   */
  public void flush() throws IOException {
    if (this.isClosed) throw new IOException("Writer closed");
  }

  /**
   * Closes this <code>DocumentWriter</code>.  Once closed, this
   * <code>DocumentWriter</code> cannot be used again.
   *
   * @exception  <code>IOException</code>
   *               if the closing operation fails
   */
  public void close() throws IOException {
    this.isClosed = true;
  }

  /**
   * Writes a portion of a {@link String} to this
   * <code>DocumentWriter</code>'s associated {@link Document}.
   * 
   * @param      string
   *               the {@link String} from which the portion to be written
   *               should be extracted
   * @param      offset
   *               the position within the supplied {@link String} to begin
   *               writing characters from; must be greater than <code>0</code>
   * @param      length
   *               the number of characters to write from the supplied
   *               {@link String}, starting at <code>offset</code>; must be
   *               greater than <code>0</code> and less than
   *               <code>string.length() - offset</code>
   * @exception  <code>IOException</code>
   *               if an input/output error occurs
   */
  public void write(final String string, int offset, int length)
    throws IOException {
    if (string == null) return;
    if (this.document == null) return;
    if (this.isClosed) throw new IOException("Writer closed");
    final String writeMe = string.substring(offset, offset + length);
    try {
      this.document.insertString(this.document.getLength(), writeMe, null);
    } catch (final BadLocationException ohWell) {
      throw new IOException("Bad location: " + ohWell.toString());
    }
  }

  /**
   * Writes a portion of a character array to this
   * <code>DocumentWriter</code>'s associated {@link Document}.
   * 
   * @param      chars
   *               the character array from which the portion to be written
   *               should be extracted
   * @param      offset
   *               the position within the supplied character array to begin
   *               writing characters from; must be greater than <code>0</code>
   * @param      length
   *               the number of characters to write from the supplied
   *               character array, starting at <code>offset</code>; must be
   *               greater than <code>0</code> and less than
   *               <code>chars.length - offset</code>
   * @exception  <code>IOException</code>
   *               if an input/output error occurs
   */
  public void write(final char[] chars, int offset, int length)
    throws IOException {
    this.write(new String(chars), offset, length);
  }

}