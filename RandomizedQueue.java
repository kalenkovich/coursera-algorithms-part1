import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;

    private Item[] q;       // queue elements
    private int n;          // number of elements on queue

    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add null item to the queue");
        }

        if (n == q.length) {
            resize(2 * q.length);
        }
        q[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can\'t dequeue from an empty queue");
        }

        int i = StdRandom.uniformInt(n);
        Item item = q[i];
        q[i] = q[--n];
        q[n] = null;
        if (n > 0 && n < q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can\'t dequeue from an empty queue");
        }

        int i = StdRandom.uniformInt(n);
        return q[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator(n);
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] order;

        public ArrayIterator(int n) {
            order = new int[n];
            for (int j = 0; j < n; j++) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }


        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[order[i++]];
            return item;
        }
    }

    private void print() {
        for (int i = 0; i < n; i++) {
            System.out.print(q[i] + " ");
        }
        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 16; i++) {
            rq.enqueue(i);
        }
        rq.print();

        for (int i = 0; i < 10; i++) {
            System.out.println("Dequeued: " + rq.dequeue());
        }
        rq.print();

        System.out.println("Size: " + rq.size());
        System.out.println("Sample: " + rq.sample());
        System.out.println("Size: " + rq.size());

        System.out.println("Is empty:" + rq.isEmpty());

    }

}