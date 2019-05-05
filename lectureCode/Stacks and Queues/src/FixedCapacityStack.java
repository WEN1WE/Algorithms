import java.util.Iterator;

public class FixedCapacityStack<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;

    public FixedCapacityStack(int capacity) {
        //s = new Item[capacity]; Java does not allow generic array creation.
        s = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(Item item) {
        s[N++] = item;
    }

    /* Loitering
    public String pop() {
        return s[--N];
    }
    */

    public Item pop() {
        Item item = s[--N];
        s[N] = null;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return s[--i];
        }
    }
}
