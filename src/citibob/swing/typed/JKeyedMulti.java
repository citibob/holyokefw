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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.typed;

import citibob.jschema.Column;
import citibob.jschema.Schema;
import citibob.jschema.SchemaSet;
import citibob.text.KeyedMultiSFormat;
import citibob.types.JEnum;
import citibob.types.JEnumMulti;
import citibob.types.KeyedModel;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author citibob
 */
public class JKeyedMulti extends JTypedPopup
{

public JKeyedMulti()
{
	JKeyedMultiPanel tw = new JKeyedMultiPanel();
	super.setPopupWidget(tw);
//	super.setAllowNull(false);
}


// --------------------------------------------------------------
/** Convenience method */
public void setKeyedModel(JEnumMulti jenum)
	{ setKeyedModel(jenum.getKeyedModel(), jenum.getSegment()); }
/** Convenience method: sets dropdown equal to the type of the column;
 column must be of type JEnum. */
public void setKeyedModel(Column col)
	{ setKeyedModel((JEnumMulti)col.getType()); }
/** Convenience method: sets dropdown equal to the type of the column.
 Column must be of type JEnum*/
public void setKeyedModel(Schema schema, String colName)
	{ setKeyedModel(schema.getCol(colName)); }
public void setKeyedModel(SchemaSet sset, String schemaName, String colName)
	{ setKeyedModel(sset.get(schemaName), colName); }


public void setKeyedModel(KeyedModel kmodel)
	{ setKeyedModel(kmodel, null); }

public void setKeyedModel(KeyedModel kmodel, Object segment)
{
	JKeyedMultiPanel tw = (JKeyedMultiPanel)popupWidget;
	tw.setKeyedModel(kmodel, segment);
	super.setJType(new JEnum(kmodel), new KeyedMultiSFormat(kmodel));
	super.setAllowNull(false);
}
public void setSegment(Object segment)
{
	JKeyedMultiPanel tw = (JKeyedMultiPanel)popupWidget;
	tw.setSegment(segment);
}

public static void main(String[] args) throws Exception
{
	KeyedModel kmodel = KeyedModel.intKeys("Item 0", "Item 1", "item 2");
	
	JKeyedMulti multi = new JKeyedMulti();
	multi.setKeyedModel(kmodel);
	multi.setPreferredSize(new Dimension(200,20));
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	panel.add(multi);
	frame.getContentPane().add(panel);
	frame.pack();
	frame.setVisible(true);
}



}
