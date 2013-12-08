package citibob.task;

public abstract class BaseExpHandler implements ExpHandler
{

public Throwable getRootCause(Throwable t)
{
	Throwable cause = t.getCause();
	while (cause != null) {
		t = cause;
		cause = t.getCause();
	}
	return t;
}

public String getNestedMessages(Throwable t)
{
	StringBuffer sb = new StringBuffer();
	Throwable cause = t.getCause();
	while (cause != null) {
		sb.append(t.getClass() + ": " + t.getMessage() + "\n");
		t = cause;
		cause = t.getCause();
	}
	return sb.toString();
}

/** Sees if this exception or any of its causes is of a certain class.
 * If so, returns the first one it finds.
 * @param t
 * @return
 */
public Throwable findCauseByClass(Throwable t, Class klass)
{
	for (; t!= null; t = t.getCause()) {
		if (klass.isInstance(t)) return t;
	}
	return null;
	
}

}
