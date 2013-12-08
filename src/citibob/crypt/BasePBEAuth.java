package citibob.crypt;

/**
 *
 * @author citibob
 */
public abstract class BasePBEAuth implements PBEAuth
{

int ntries = 0;
String envVar;		// If not null, see if we can fetch the password from this environment variable

public static final int PWD_NONE = 0;		// No password yet
public static final int PWD_GOOD = 1;		// Last password was good (assumption when pwd entered until proven otherwise)
public static final int PWD_BAD = 2;		// Last password was bad
	
protected int state = PWD_NONE;
protected char[] password;

public BasePBEAuth(String envVar)
{
	this.envVar = envVar;
}

/** Indicates that the last password entered was good.  From now on,
 * just return it again and again. */
public void validatePassword()
{
	state = PWD_GOOD;
}

public abstract char[] queryPassword();

/** Called repeatedly to get the password.
 @returns null if user declined to give a password (chose to cancel) */
public char[] getPassword()
{
	// Return last password, if it turned out to be good.
	if (state == PWD_GOOD) return password;

	// See about the password in the environment variable
	if (ntries == 0) {
		String envPassword = (envVar == null ? null : System.getenv(envVar));
		++ntries;
		if (envPassword != null) {
			password = envPassword.toCharArray();
			return password;
		}
	}

	password = queryPassword();
	++ntries;

	state = PWD_BAD;
	return password;
}



}
