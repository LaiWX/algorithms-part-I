import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[1];
    }

    private void resizeQueue(int n) {
        Item[] newQueue = (Item[]) new Object[n];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (queue.length == size) {
            resizeQueue(size * 2);
        }
        queue[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int randomIndex = StdRandom.uniformInt(size);
        Item result = queue[randomIndex];
        queue[randomIndex] = queue[size - 1];
        queue[size - 1] = null;
        size--;

        if (size > 0 && size <= queue.length / 4) {
            resizeQueue(queue.length / 2);
        }

        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int randomIndex = StdRandom.uniformInt(size);
        return queue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] randomIndex = new int[size];
        private int currentIndex = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < size; i++) {
                randomIndex[i] = i;
            }
            StdRandom.shuffle(randomIndex);
        }

        public boolean hasNext() {
            return currentIndex != randomIndex.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = queue[randomIndex[currentIndex]];
            currentIndex++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        StdOut.printf("size = %d\n", queue.size());
        StdOut.println(queue.dequeue() + " has been remove");
        StdOut.println(queue.dequeue() + " has been remove");
        StdOut.println(queue.dequeue() + " has been remove");
        StdOut.println(queue.isEmpty());
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        StdOut.println("sampling " + queue.sample());
        StdOut.println("sampling " + queue.sample());
        StdOut.println("sampling " + queue.sample());
        for (int i : queue) {
            for (int j : queue) {
                StdOut.print(i + "-" + j + " ");
            }
            StdOut.println();
        }
    }

}