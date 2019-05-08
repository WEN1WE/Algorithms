import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int SMALLCAPACITY = 8;
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[SMALLCAPACITY];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (size == items.length) {
            resize(2 * size);
        }
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int i = StdRandom.uniform(0, size);
        Item item = items[i];
        items[i] = items[--size];
        items[size] = null;
        if (items.length > SMALLCAPACITY && size <= items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int i = StdRandom.uniform(0, size);
        return items[i];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int rest = size;
        private Item[] temp;

        RandomizedQueueIterator() {
            temp = (Item[]) new Object[size];
            System.arraycopy(items, 0, temp, 0, size);
        }

        @Override
        public boolean hasNext() {
            return rest > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            int i = StdRandom.uniform(0, rest);
            Item item = temp[i];
            temp[i] = temp[--rest];
            temp[rest] = null;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, temp, 0, size);
        items = temp;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> t = new RandomizedQueue<>();
        t.enqueue(1);
        t.enqueue(2);
        t.enqueue(3);
        t.enqueue(4);
        t.enqueue(5);
        t.enqueue(6);
        for (Integer item : t) {
            System.out.println(item);
        }
    }
}
