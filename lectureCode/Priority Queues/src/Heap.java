public class Heap {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int k = N / 2; k >= 1; k--)
            sink(a, k, N);
        while (N > 1) {
            exch(a, 1, N);
            sink(a, 1, --N);
        }
    }

    private static void sink(Comparable[] a, int k, int N) { /* as before */ }

    private static boolean less(Comparable[] a, int i, int j) { /* as before */ }

    private static void exch(Comparable[] a, int i, int j) { /* as before */ }
}