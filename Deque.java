import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    private Node<Item> first;
    private Node<Item> last;
    private int nElements;

    public Deque() {
        first = null;
        last = null;
        nElements = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null);
    }

    // return the number of items on the deque
    public int size() {
        return nElements;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> node = new Node<Item>();
        node.item = item;
        node.next = first;
        node.previous = null;

        if (!isEmpty()) {
            first.previous = node;
        }
        else {
            last = node;
        }

        first = node;

        nElements++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> node = new Node<Item>();
        node.item = item;

        node.next = null;
        node.previous = last;

        if (!isEmpty()) {
            last.next = node;
        }
        else {
            first = node;
        }

        last = node;

        nElements++;

        if (isEmpty()) {
            first = node;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) {
            throw new NoSuchElementException("Can't remove first element: the deque is empty");
        }

        Item item = first.item;
        if (size() == 1) {
            first = null;
            last = null;
            nElements = 0;
        }
        else {  // size() > 1
            first = first.next;
            first.previous = null;
            nElements--;
        }
        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) {
            throw new NoSuchElementException("Can't remove last element: the deque is empty");
        }

        Item item = last.item;

        if (size() == 1) {
            first = null;
            last = null;
            nElements = 0;
        }
        else {
            // size() > 1
            last = last.previous;
            last.next = null;
            nElements--;
        }
        
        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    // a linked-list iterator
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void print() {
        for (Item item : this) {
            System.out.print(item + " ");
        }
        System.out.println();
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> stringDeque = new Deque<>();

        System.out.println("Adding 'hello' to front");
        stringDeque.addFirst("hello");
        stringDeque.print();
        System.out.println("Adding 'world' to back");
        stringDeque.addLast("world");
        stringDeque.print();
        System.out.println("Removing from front: " + stringDeque.removeFirst());
        stringDeque.print();
        System.out.println("Removing from back: " + stringDeque.removeLast());
        stringDeque.print();

        // Additional tests (size(), isEmpty(), iterator)
        System.out.println("Is deque empty? " + stringDeque.isEmpty());
        System.out.println("Size of deque: " + stringDeque.size());
        stringDeque.addFirst("first");
        stringDeque.addLast("last");
        System.out.println("Size of deque after adding two elements: " + stringDeque.size());
        System.out.println("Iterating over deque:");
        for (String s : stringDeque) {
            System.out.println(s);
        }

    }

}
