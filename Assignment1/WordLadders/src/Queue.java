public class Queue<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;
    public Queue() { this.size = 0;}
    public int getSize() { return this.size; }
    public boolean isEmpty() { return this.size == 0; }
    public void enqueue(E value){
        if (isEmpty()) {
            head = new Node(value);
            tail = head;
        }else {
            tail.next = new Node(value);
            tail = tail.next;
        }
        size++;
    }
    public E dequeue() {
        if (isEmpty()){
            return null;
        }else {
            size--;
            E value = head.value;
            head = head.next;
            return value;
        }
    }
}

class Node<E> {
    public E value;
    public Node<E> next;
    public Node(E val) {this.value = val;}
}


