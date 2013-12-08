/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.sql;

/**
 * Like StringBuffer, but for SqlSet
 * @author citibob
 */
public class SqlSetBuffer {

	public StringBuffer preSql;
	public StringBuffer sql;
	public StringBuffer postSql;

public SqlSetBuffer()
{
	preSql = new StringBuffer();
	sql = new StringBuffer();
	postSql = new StringBuffer();
}

public SqlSet toSqlSet()
{
	return new SqlSet(preSql.toString(), sql.toString(), postSql.toString());
}

public void appendPre(String s) { preSql.append(s); }
public void append(String s) { sql.append(s); }
public void appendPost(String s) { postSql.append(s); }

}
