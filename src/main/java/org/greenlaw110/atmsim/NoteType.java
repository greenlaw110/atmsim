package org.greenlaw110.atmsim;

/**
 * The note type. At the moment we support two types:
 * <ul>
 * <li>$20</li>
 * <li>$50</li>
 * </ul>
 */
public enum NoteType {
    /*fifth(5), tenth(10),*/ twentieth(20), fiftieth(50) /*, hundredth(100)*/;
    private final int value;

    NoteType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    /**
     * The greatest common divisor value of values of all note types
     */
    public static final int GCD_VALUE = gcdValue();

    private static int gcdValue() {
        NoteType[] all = values();
        int gcd = all[0].value;
        for (int i = all.length - 1; i >= 1; --i) {
            gcd = gcd(gcd, all[i].value);
        }
        return gcd;
    }

    // From http://introcs.cs.princeton.edu/java/23recursion/Euclid.java.html
    private static int gcd(int p, int q) {
        while (q != 0) {
            int temp = q;
            q = p % q;
            p = temp;
        }
        return p;
    }

}
