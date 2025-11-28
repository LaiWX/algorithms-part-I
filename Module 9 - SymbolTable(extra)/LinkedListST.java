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
        if (key == null) throw new NoSuchElementException();

        if (val == null) {
            delete(key);
            return;
        }

        Node currNode = head;
        while (currNode != null) {
            if (currNode.key.equals(key)) {
                currNode.val = val;
                return;
            }
            currNode = currNode.next;
        }

        Node newHead = new Node(key, val);
        newHead.next = head;
        head = newHead;
        n++;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
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
        if (key == null) throw new IllegalArgumentException();

        if (head == null) return;

        if (head.key.equals(key)) {
            head = head.next;
            n--;
            return;
        }

        Node preNode = head;
        Node currNode = head.next;
        while (currNode != null && !currNode.key.equals(key)) {
            preNode = currNode;
            currNode = currNode.next;
        }

        if (currNode != null) {
            preNode.next = currNode.next;
            n--;
        }
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException();
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

    public static void main(String[] args) {
        LinkedListST<String, Integer> st = new LinkedListST<>();
        st.put("123", 123);
        st.put("1", 1);
        st.delete("1");
        st.delete("1");
        st.delete("123");
        st.put("1", 111);
        st.put("1", 1);
        st.put("123", 1);
        for (String key: st.keys()) {
            System.out.println(key + " :" + st.get(key));
        }
    }
}