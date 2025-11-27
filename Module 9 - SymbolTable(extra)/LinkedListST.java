import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListST<Key, Value> implements STIF<Key, Value> {
    private int n = 0;
    private Node head = null;

    private class Node {
        Key key;
        Value val;
        Node next = null;

        Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(Key key, Value val) {
        if (head == null) {
            head = new Node(key, val);
            n++;
            return;
        }

        Node currNode = head;

        while (currNode.next != null) currNode = currNode.next;

        currNode.next = new Node(key, val);
        n++;
    }

    public Value get(Key key) {
        Node currNode = head;

        while (currNode != null) {
            if (currNode.key.equals(key)) {
                return currNode.val;
            }
            currNode = currNode.next;
        }

        return null;
    }

    public void delete(Key key) {
        if (head == null) {
            throw new NoSuchElementException();
        }

        if (head.key.equals(key)) {
            head = head.next;
            n--;
            return;
        }

        Node preNode = head;
        Node currNode = head.next;

        while (currNode != null) {
            if (currNode.key.equals(key)) {
                preNode.next = currNode.next;
                n--;
                return;
            }
            preNode = currNode;
            currNode = currNode.next;
        }

        throw new NoSuchElementException();
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Iterable<Key> keys() {
        return KeysIterator::new;
    }

    private class KeysIterator implements Iterator<Key> {
        Node currNode = head;

        public boolean hasNext() {
            return currNode != null;
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            Key result = currNode.key;
            currNode = currNode.next;
            return result;
        }
    }

    public int rank(Key key) {
        return 0;
    }

    public static void main(String[] args) {
        LinkedListST<String, Integer> st = new LinkedListST<>();
        st.put("123", 123);
        st.put("1", 1);
        st.delete("1");
        st.put("1", 111);
        for (String key: st.keys()) {
            System.out.println(key + " :" + st.get(key));
        }
    }
}