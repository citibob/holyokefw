/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.sql;

/**
 * A pre-sql, sql and post-sql, ready to be folded into a larger transaction.
 * @author citibob
 */
public class SqlSet
{

String preSql;
String sql;
String postSql;

	public SqlSet(SqlSet orig, String sql)
	{
		this(
			orig.preSql,
			sql,
			orig.postSql);
	}
	public SqlSet(SqlSet orig, String preSql, String sql, String postSql)
	{
		this(
			orig.preSql + (preSql == null ? "" : ";\n" + preSql),
			sql,
//			orig.sql + (sql == null ? "" : ";\n" + sql),
			orig.postSql + (postSql == null ? "" : ";\n" + postSql));
	}

	public SqlSet(String sql) {
		preSql = "";
		this.sql = sql;
		postSql = "";
	}
	public SqlSet(String preSql, String sql, String postSql) {
		this.preSql = preSql;
		this.sql = sql;
		this.postSql = postSql;
	}
	public SqlSet(StringBuffer preSql, StringBuffer sql, StringBuffer postSql) {
		this.preSql = preSql.toString();
		this.sql = sql.toString();
		this.postSql = postSql.toString();
	}

	public String getPostSql() {
		return postSql;
	}

	public String getPreSql() {
		return preSql;
	}

	public String getSql() {
		return sql;
	}

	
	public SqlSet add(SqlSet b) {
		return new SqlSet(
			preSql + b.preSql,
			sql + ";\n" + b.sql,
			postSql + b.postSql);
	}
	public String toString() {
		return "SqlSet(" +
			preSql + ",\n" + sql + ",\n" + postSql + ")";
	}
	
//	public String toString() {
//		return preSql + sql + ";\n" + postSql;
//	}
	
}
