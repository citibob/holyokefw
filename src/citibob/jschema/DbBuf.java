/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.swing.table.JTypeTableModel;

/**
 *
 * @author citibob
 */
public interface DbBuf extends JTypeTableModel
{

	/** Mark a row for deletion. */
	public void deleteRow(int rowIndex);

	/** Mark all rows for deletion. */
	public void deleteAllRows();

	public void undeleteRow(int rowIndex);

	/** Mark all rows for deletion. */
	public void undeleteAllRows();

}
