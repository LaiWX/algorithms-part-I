import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class BST<Key extends Comparable<Key>, Value> implements STIF<Key, Value> {
    private Node root = null;

    private class Node {
        Key key;
        Value val;
        Node left = null;
        Node right = null;
        int size = 1;

        Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException();

        if (val == null) {
            delete(key);
            return;
        }

        if (root == null) {
            root = new Node(key, val);
            return;
        }

        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null) return new Node(key, val);

        int cmp = key.compareTo(node.key);

        if (cmp > 0) {
            node.right = put(node.right, key, val);
        } else if (cmp < 0) {
            node.left = put(node.left, key, val);
        } else { // cmp == 0
            node.val = val;
        }

        reCountSize(node);
        return node;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();

        Node currNode = root;
        while (currNode != null) {
            int cmp = key.compareTo(currNode.key);
            if (cmp > 0) currNode = currNode.right;
            else if (cmp < 0) currNode = currNode.left;
            else return currNode.val; // cmp == 0
        }
        return null;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);

        if (cmp > 0) {
            node.right = delete(node.right, key);
        } else if (cmp < 0) {
            node.left = delete(node.left, key);
        } else { // cmp == 0;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = minNode(node.right);
            successor.right = deleteMin(node.right);
            successor.left = node.left;
            node = successor;
        }
        reCountSize(node);
        return node;
    }

    private Node minNode(Node node) {
        if (node == null) return null;

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;

        node.left = deleteMin(node.left);

        reCountSize(node);

        return node;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException();

        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private void reCountSize(Node node) {
        node.size = 1 + size(node.left) + size(node.right);
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    public int rank(Key key) {
        // todo
        return 0;
    }

    public Key max() {
        // todo
        return null;
    }

    public Key min() {
        // todo
        return null;
    }

    public Key floor(Key key) {
        // todo
        return null;
    }

    public Key ceiling(Key key) {
        // todo
        return null;
    }

    public Iterable<Key> keys() {
        Queue<Key> keyQueue = new Queue<>();

        inOrder(keyQueue, root);

        return keyQueue;
    }

    private void inOrder(Queue<Key> queue, Node node) {
        if (node == null) return;

        inOrder(queue, node.left);
        queue.enqueue(node.key);
        inOrder(queue, node.right);
    }

    public boolean isValid() {
        return isValid(root, null, null);
    }

    private boolean isValid(Node node, Key min, Key max) {
        if (node == null) return true;

        if (min != null && node.key.compareTo(min) <= 0) return false;
        if (max != null && node.key.compareTo(max) >= 0) return false;

        return isValid(node.left, min, node.key)
                && isValid(node.right, node.key, max);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(isValid());
        s.append("\n");

        for (Key key: keys()) {
            s.append(key);
            s.append(",");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        BST<Double, Integer> bst = new BST<>();
        int cnt = 0;
        int n = 1000;
        for (int i = 0; i < n; i++) {
            bst.put(StdRandom.uniformDouble(), 0);
            cnt++;
            if (bst.size() != cnt) throw new RuntimeException();
        }

        for (double key: bst.keys()) {
            if (StdRandom.uniformDouble() < 0.2) {
                bst.delete(key);
                cnt--;
            }
            if (bst.size() != cnt) throw new RuntimeException();
        }
    }
}
