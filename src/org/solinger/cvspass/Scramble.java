package org.solinger.cvspass;

/**
 * See: http://sourceforge.net/projects/solinger
 *
 * Java port of scramble.c from the CVS distribution.  None of this code
 * was originally written by Justin Chapweske, I just ported the stuff over
 * to Java.
 *
 * Trivially encode strings to protect them from innocent eyes (i.e.,
 * inadvertent password compromises, like a network administrator
 * who's watching packets for legitimate reasons and accidentally sees
 * the password protocol go by).
 * 
 * This is NOT secure encryption.
 *
 * It would be tempting to encode the password according to username
 * and repository, so that the same password would encode to a
 * different string when used with different usernames and/or
 * repositories.  However, then users would not be able to cut and
 * paste passwords around.  They're not supposed to anyway, but we all
 * know they will, and there's no reason to make it harder for them if
 * we're not trying to provide real security anyway.
 */

public class Scramble { 

    /** Map characters to each other randomly and symmetrically, A <--> B.
     *
     * We divide the ASCII character set into 3 domains: control chars (0
     * thru 31), printing chars (32 through 126), and "meta"-chars (127
     * through 255).  The control chars map _to_ themselves, the printing
     * chars map _among_ themselves, and the meta chars map _among_
     * themselves.  Why is this thus?
     *
     * No character in any of these domains maps to a character in another
     * domain, because I'm not sure what characters are legal in
     * passwords, or what tools people are likely to use to cut and paste
     * them.  It seems prudent not to introduce control or meta chars,
     * unless the user introduced them first.  And having the control
     * chars all map to themselves insures that newline and
     * carriage-return are safely handled.
     */
    public static final byte[] shifts = {
        (byte)   0, (byte)   1, (byte)   2, (byte)   3, (byte)   4, (byte)  5, 
        (byte)   6, (byte)   7, (byte)   8, (byte)   9, (byte)  10, (byte)  11,
        (byte)  12, (byte)  13, (byte)  14, (byte)  15, (byte)  16, (byte)  17,
        (byte)  18, (byte)  19, (byte)  20, (byte)  21, (byte)  22, (byte)  23,
        (byte)  24, (byte)  25, (byte)  26, (byte)  27, (byte)  28, (byte)  29,
        (byte)  30, (byte)  31, (byte) 114, (byte) 120, (byte)  53, (byte)  79,
        (byte)  96, (byte) 109, (byte)  72, (byte) 108, (byte)  70, (byte)  64,
        (byte)  76, (byte)  67, (byte) 116, (byte)  74, (byte)  68, (byte)  87,
        (byte) 111, (byte)  52, (byte)  75, (byte) 119, (byte)  49, (byte)  34,
        (byte)  82, (byte)  81, (byte)  95, (byte)  65, (byte) 112, (byte)  86,
        (byte) 118, (byte) 110, (byte) 122, (byte) 105, (byte)  41, (byte)  57,
        (byte)  83, (byte)  43, (byte)  46, (byte) 102, (byte)  40, (byte)  89,
        (byte)  38, (byte) 103, (byte)  45, (byte)  50, (byte)  42, (byte) 123,
        (byte)  91, (byte)  35, (byte) 125, (byte)  55, (byte)  54, (byte)  66,
        (byte) 124, (byte) 126, (byte)  59, (byte)  47, (byte)  92, (byte)  71,
        (byte) 115, (byte)  78, (byte)  88, (byte) 107, (byte) 106, (byte)  56,
        (byte)  36, (byte) 121, (byte) 117, (byte) 104, (byte) 101, (byte) 100,
        (byte)  69, (byte)  73, (byte)  99, (byte)  63, (byte)  94, (byte)  93,
        (byte)  39, (byte)  37, (byte)  61, (byte)  48, (byte)  58, (byte) 113,
        (byte)  32, (byte)  90, (byte)  44, (byte)  98, (byte)  60, (byte)  51,
        (byte)  33, (byte)  97, (byte)  62, (byte)  77, (byte)  84, (byte)  80,
        (byte)  85, (byte) 223, (byte) 225, (byte) 216, (byte) 187, (byte) 166,
        (byte) 229, (byte) 189, (byte) 222, (byte) 188, (byte) 141, (byte) 249,
        (byte) 148, (byte) 200, (byte) 184, (byte) 136, (byte) 248, (byte) 190,
        (byte) 199, (byte) 170, (byte) 181, (byte) 204, (byte) 138, (byte) 232,
        (byte) 218, (byte) 183, (byte) 255, (byte) 234, (byte) 220, (byte) 247,
        (byte) 213, (byte) 203, (byte) 226, (byte) 193, (byte) 174, (byte) 172,
        (byte) 228, (byte) 252, (byte) 217, (byte) 201, (byte) 131, (byte) 230,
        (byte) 197, (byte) 211, (byte) 145, (byte) 238, (byte) 161, (byte) 179,
        (byte) 160, (byte) 212, (byte) 207, (byte) 221, (byte) 254, (byte) 173,
        (byte) 202, (byte) 146, (byte) 224, (byte) 151, (byte) 140, (byte) 196,
        (byte) 205, (byte) 130, (byte) 135, (byte) 133, (byte) 143, (byte) 246,
        (byte) 192, (byte) 159, (byte) 244, (byte) 239, (byte) 185, (byte) 168,
        (byte) 215, (byte) 144, (byte) 139, (byte) 165, (byte) 180, (byte) 157,
        (byte) 147, (byte) 186, (byte) 214, (byte) 176, (byte) 227, (byte) 231,
        (byte) 219, (byte) 169, (byte) 175, (byte) 156, (byte) 206, (byte) 198,
        (byte) 129, (byte) 164, (byte) 150, (byte) 210, (byte) 154, (byte) 177,
        (byte) 134, (byte) 127, (byte) 182, (byte) 128, (byte) 158, (byte) 208,
        (byte) 162, (byte) 132, (byte) 167, (byte) 209, (byte) 149, (byte) 241,
        (byte) 153, (byte) 251, (byte) 237, (byte) 236, (byte) 171, (byte) 195,
        (byte) 243, (byte) 233, (byte) 253, (byte) 240, (byte) 194, (byte) 250,
        (byte) 191, (byte) 155, (byte) 142, (byte) 137, (byte) 245, (byte) 235,
        (byte) 163, (byte) 242, (byte) 178, (byte) 152 };
    
    
    /**
     * This method scrambles a password.
     * @param s The string to scramble.
     * @return The scrambled string. 
     */
    public final static String scramble(String s) {
		if (s == null) return null;		// RPF 4/16/2006
        return new String(scramble(s.getBytes()));
    }

    /**
     * This method scrambles a password.
     * @param pass The password to scramble.
     * @return The scrambled byte array.
     */
    public final static byte[] scramble(byte[] pass) {
        return scramble(pass,0,pass.length);
    }

    /**
     * This method scrambles a password.
     * @param pass The password to scramble.
     * @return The scrambled byte array.
     */
    public final static byte[] scramble(byte[] pass, int off, int len) {
        byte[] b = new byte[len+1];
        
        b[0] = (byte) 'A';
        
        for (int i = 0; i<len; i++) {
            b[i+1] = shifts[pass[off+i] & 0xff];
        }
        return b;
    }

    /** 
     * Descrambles a scrambled pass.
     * @param s The string to descramble.
     * @return The descrambled string.
     */
    public static final String descramble(String s) {
		if (s == null) return null;		// RPF 4/16/2006
        return new String(descramble(s.getBytes()));
    }

    /** 
     * Descrambles a scrambled pass.
     * @param s The pass to descramble.
     * @return The descrambled pass.
     */
    public static final byte[] descramble(byte[] pass) {

        if (pass[0] != (byte) 'A') {
            throw new IllegalArgumentException
                ("pass doesn't begin with \"A\". Only \"A\" type scrambling "+
                 "supported at this time.");
        }
                                
        // scramble is symmetric, so we just plunk it through again.
        byte[] b = new byte[pass.length-1];
        System.arraycopy(scramble(pass,1,pass.length-1),1,b,0,b.length);
        return b;
    }

    
    public static void main(String[] args) {
        System.out.println((byte) 255);
        System.out.println((byte) 0);
        System.out.println((byte) 127);
        System.out.println((int) ((byte) 128));
        System.out.println((byte) 'A');
        byte[] biggie = new byte[256];

        byte[][] cleartexts = {
            "first".getBytes(),
            "the second".getBytes(),
            "this is the third".getBytes(),
            "$#% !!\\3".getBytes(),
            biggie};

        // Set up the most important test string:
        for (int i = 0; i < 256; i++) {
            biggie[i] = (byte) i;
        }
        
        /* Test all the strings. */
        for (int i = 0; i < cleartexts.length; i++) {
            System.out.println("clear["+i+"]: "+new String(cleartexts[i]));
            byte[] e = scramble(cleartexts[i]);
            System.out.println(("scram["+i+"]: "+new String(e)));
            byte[] m = descramble (e);
            System.out.println("clear["+i+"]: "+new String(m));
        }
    }
}