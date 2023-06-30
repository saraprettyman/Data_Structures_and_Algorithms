public class PriorityQueue<E extends Comparable<E>> implements Comparable<PriorityQueue<E>> {
    Node<E> node;
    public E value;

    public PriorityQueue(){
        this.node = null;
    }

    public PriorityQueue(E value) {
        if (value == null) {
            this.node = null;
            this.value = null;
        } else {
            this.node = new Node<>(value);
            this.value = value;
        }
    }

    // Add an item to the queue
    public void enqueue(E value) {
        // if trying to insert an invalid input
        if (value == null) return;
        // create a node for the inputed value
        PriorityQueue<E> newNode = new PriorityQueue<>(value);
        // if tree is empty
        if (node == null) {
            node = newNode.node;
        } else {
            // merge newNode with existing tree
            node = merge(node, newNode.node);
        }
    }

    // Remove an item from the queue, specifically the smallest value (root)
    public E dequeue() {
        if (isEmpty()) {
            return null;
        } else {
            // redefine the root by merging its left and right subtree
            E temp = node.value;
            node = merge(node.left, node.right);
            return temp;
        }
    }

    // Returns true if queue is empty(root is null), false otherwise
    public boolean isEmpty() {return node == null;}

    public Node<E> merge(Node<E> t1, Node<E> t2) {
        Node<E> small;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        if (t1.value.compareTo(t2.value) < 0) {
            t1.right = merge(t1.right, t2);
            small = t1;
        }
        else {
            t2.right = merge(t2.right, t1);
            small = t2;
        }
        if (getNpl(small.left) < getNpl(small.right))
            swapkids(small);
        setNullPathLength(small);

        return small;
    }

    private int getNpl(Node<E> t) {
        if (t == null) return -1;
        return t.npl;
    }

    private void setNullPathLength(Node<E> node) {
        node.npl = Math.min(getNpl(node.left), getNpl(node.right)) + 1;
    }

    private void swapkids(Node<E> node){
        Node<E> temp = node.left;
        node.left = node.right;
        node.right= temp;
    }

    @Override
    // Compares values in the queue for priority
    public int compareTo(PriorityQueue<E> o) {
        return this.node.value.compareTo(o.node.value);
    }

}

class Node<E extends Comparable<E>> {
    public E value; // The data in the node
    public Node<E> left; // Left child
    public Node<E> right; // Right child
    public int npl; // null path length

    // Default constructor
    Node(E value) {
        this(value, null, null);
    }

    // Value of each node
    Node(E value, Node<E> left, Node<E> right) {
        this.value = value;
        this.left = left;
        this.right = right;
        npl = 0;
    }

}
