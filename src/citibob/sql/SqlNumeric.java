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
package citibob.sql;

import java.sql.*;
import java.text.*;

public class SqlNumeric implements SqlType
{
	int precision;		// total digits
	int scale;			// # digits after decimal point
	boolean nullable = true;
	
	public int getPrecision() { return precision; }
	public int getScale() { return scale; }
	
	public SqlNumeric(int precision, int scale, boolean nullable) {
		this.precision = precision;
		this.scale = scale;
		this.nullable = nullable;
	}
	public SqlNumeric(int precision, int scale)
	{ this(precision, scale, true); }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Double.class; }

	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "numeric(" + precision + "," + scale + ")"; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
		{ return (o == null ? "null" : o.toString()); }

	public boolean isInstance(Object o)
	{
		if (o == null) return nullable;
		if (!(o instanceof Double)) return false;
		
		double d = ((Double)o).doubleValue();
		int intDigits = (precision - scale);
		double max = Math.exp((double)intDigits * Math.log(10.0));
		return (Math.abs(d) < max);
	}
	public Double get(java.sql.ResultSet rs, int col) throws SQLException
	{
		Number num = (Number)rs.getObject(col);
		if (num == null) return null;
		return num.doubleValue();
	}
	public Double get(java.sql.ResultSet rs, String col) throws SQLException
	{
		Number num = (Number)rs.getObject(col);
		if (num == null) return null;
		return num.doubleValue();
	}
// ================================================
	public static String sql(Double ii)
		{ return ii == null ? "null" : ii.toString(); }
	public static String sql(int i)
		{ return ""+i; }
	public static String sql(double ii)
		{ return "" + ii; }

/** Makes a NumberFormat object appropriate to this SQL Type */
public NumberFormat newNumberFormat()
{
	NumberFormat nf;
	if (getScale() == 0) {
		nf = NumberFormat.getIntegerInstance();
	} else {
		nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(0);
		int max = getPrecision() - getScale();
		nf.setMaximumIntegerDigits(max < 0 ? 100 : max);
		nf.setMinimumFractionDigits(getScale());
		nf.setMaximumFractionDigits(getScale());
		nf.setGroupingUsed(true);
	}
	return nf;
}


}
