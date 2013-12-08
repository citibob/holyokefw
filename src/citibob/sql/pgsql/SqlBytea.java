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
package citibob.sql.pgsql;

import java.sql.*;

public class SqlBytea implements citibob.sql.SqlType
{

	boolean nullable = true;

	public SqlBytea(boolean nullable)
		{ this.nullable = nullable; }
	
	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "bytea"; }
	public SqlBytea()
		{ this(true); }

	/** Java class used to represent this type */
	public Class getObjClass()
		{ return byte[].class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
	{
		return sql((byte[])o, true);
	}

	public Object get(java.sql.ResultSet rs, int col) throws SQLException
		{ return rs.getObject(col); }
	public Object get(java.sql.ResultSet rs, String col) throws SQLException
		{ return rs.getObject(col); }
// ------------------------------------------------
private static char toDigit(int digit)
	{ return (char)('0' + digit); }
static String toOctal(int byt)
{
	return "" +
		toDigit((byt & 0xc0) >> 6) +
		toDigit((byt & 0x38) >> 3) +
		toDigit((byt & 0x07));
}
// ------------------------------------------------
	
/** Converts a Java String to a form appropriate for inclusion in an
Sql query.  This is done by single-quoting the input and repeating any
single qoutes found in it (Sql convention for quoting a quote).  If
the input is null, the string "null" is returned. */
	public static String sql(byte[] o, boolean quotes)
	{
		
		StringBuffer sbuf = new StringBuffer();
		if (quotes) sbuf.append("('");
		byte[] bo = (byte[])o;
		for (int i=0; i<bo.length; ++i) {
			int byt = bo[i] & 0xff;
			switch(byt) {
				case 0 :
				case 39 :
				case 92 :
					sbuf.append("\\\\" + toOctal(byt));
				break;
				default :
					if (byt > 31 && byt < 127) {
						sbuf.append((char)byt);
					} else {
						sbuf.append("\\\\" + toOctal(byt));
					}
				break;
			}
			
//			if ((i%70) == 69) {	// Add line break
//				sbuf.append("' ||\n'");
//			}
			
		}
		if (quotes) sbuf.append("')");
		return sbuf.toString();
	}
	public static String sql(byte[] o)
		{ return sql(o, true); }

	public boolean isInstance(Object o)
	{
		if (nullable && o == null) return true;
		return (o instanceof byte[]);
	}

}
