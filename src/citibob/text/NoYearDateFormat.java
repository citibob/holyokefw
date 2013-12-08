package citibob.text;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Adds the current year automatically for dates in which no year is specified.
 */
public class NoYearDateFormat extends SimpleDateFormat
{
public NoYearDateFormat(String pattern)
{
	super(pattern);
}
public NoYearDateFormat(String pattern, TimeZone tz)
{
	super(pattern);
	setTimeZone(tz);
}

public Date parse(String source, ParsePosition pos)
{
	DateFormat dsub = this;
	Calendar cal = dsub.getCalendar();
	
	// Get the current year
	cal.setTimeInMillis(System.currentTimeMillis());
	int year = cal.get(Calendar.YEAR);
	
	// Parse the date
	Date dt = super.parse(source, pos);
	if (dt == null) return null;
//System.out.println("dt = " + dt);
	cal.setTime(dt);
	cal.set(Calendar.YEAR, year);
	dt.setTime(cal.getTimeInMillis());
	
	return dt;
}
// ============================================================
static DateFormat newFmt(String pattern, TimeZone tz)
{
	DateFormat dfmt = new SimpleDateFormat(pattern);
	dfmt.setTimeZone(tz);
	return dfmt;
}
public static void main(String[] args) throws Exception
{
	TimeZone tz = TimeZone.getDefault();
	
	DateFormat main = new SimpleDateFormat("yyyyMMdd-HHmm");
	FlexiFormat dff = new DateFlexiFormat(
		main,
		new DateFormat[] {main,
			new SimpleDateFormat("yyMMdd-HHmm"),
			new NoYearDateFormat("MMdd-HHmm"),
			new SimpleDateFormat("yyyyMMdd"),
			new SimpleDateFormat("yyMMdd"),
			new NoYearDateFormat("MMdd")
		}, tz);
	
	System.out.println(dff.parseObject("20081119-2305"));
	System.out.println(dff.parseObject("081119-2305"));
	System.out.println(dff.parseObject("1119-2305"));
	System.out.println(dff.parseObject("20081119"));
	System.out.println(dff.parseObject("081119"));
	System.out.println(dff.parseObject("1119"));
	
}
	
}
