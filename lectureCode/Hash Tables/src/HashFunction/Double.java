package HashFunction;

public final class Double {
    private final double value;


    /** convert to IEEE 64-bit representation;
     xor most significant 32-bits
     with least significant 32-bits
     */
    public int hashCode() {
        long bits = doubleToLongBits(value);
        return (int) (bits ^ (bits >>> 32));
    }
}