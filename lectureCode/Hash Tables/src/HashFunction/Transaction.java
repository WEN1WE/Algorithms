package HashFunction;

public final class Transaction implements Comparable<Transaction> {
    private final String who;
    private final Date when;
    private final double amount;

    public Transaction(String who, Date when, double amount) { /* as before */ }


    public boolean equals(Object y) { /* as before */ }

    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + who.hashCode();
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((java.lang.Double) amount).hashCode();
        return hash;
    }
}
