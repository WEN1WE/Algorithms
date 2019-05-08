import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private final Node sentinel;
    private int size;

    public Deque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node temp = sentinel.next;
        sentinel.next = new Node(sentinel, item, temp);
        temp.prev = sentinel.next;
        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node temp = sentinel.prev;
        sentinel.prev = new Node(temp, item, sentinel);
        temp.next = sentinel.prev;
        size += 1;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Node temp = sentinel.next;
        sentinel.next = temp.next;
        temp.next.prev = sentinel;
        size -= 1;
        return temp.item;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Node temp = sentinel.prev;
        sentinel.prev = temp.prev;
        temp.prev.next = sentinel;
        size -= 1;
        return temp.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = sentinel.next;

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private class Node {
        private Node prev;
        private final Item item;
        private Node next;

        private Node(Node prev, Item item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> t = new Deque<>();
        t.addFirst(1);
        t.addFirst(2);
        t.addFirst(3);
        t.addLast(4);
        t.addLast(5);
        t.removeFirst();
        t.removeLast();

        for (Integer item : t) {
            System.out.println(item);
        }
    }
}
