/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.jschema.log.QueryLogger;

/**
 *
 * @author citibob
 */
public class LoggedMultiDbModel extends MultiDbModel
{

QueryLogger logger;

public LoggedMultiDbModel(QueryLogger logger)
{
	super();
	this.logger =logger;
}

public void logadd(SchemaBufDbModel m)
{
	add(m);
	m.setLogger(logger);
}

}
