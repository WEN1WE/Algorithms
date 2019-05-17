package HashFunction;

/** An int between 0 and M - 1 (for use as array index). */
public class HashFunction {
    // bug : Hash code. An int between -pow(2, 31) and pow(2, 31) - 1
    private int hash(Key key) {
        return key.hashCode() % M;
    }

    // 1-in-a-billion bug : hashCode() of "polygenelubricants" is -pow(2, 31)
    private int hash(Key key) {
        return Math.abs(key.hashCode()) % M;
    }

    // correct
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
}
