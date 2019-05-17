package HashFunction;

public final class StringCache {
    private int hash = 0;  // cache of hash code
    private final char[] s;


    public int hashCode() {
        int h = hash;
        if (h != 0) return h;
        for (int i = 0; i < length(); i++)
            h = s[i] + (31 * h);
        hash = h;
        return h;
    }
}