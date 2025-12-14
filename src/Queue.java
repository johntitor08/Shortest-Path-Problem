/**
 * Custom Queue implementation using linked nodes
 * Time Complexity Analysis:
 */
public class Queue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Enqueue operation: O(1) - Constant time
     */
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Dequeue operation: O(1) - Constant time
     */
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    /**
     * isEmpty operation: O(1) - Constant time
     */
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * Size operation: O(1) - Constant time
     */
    public int size() {
        return size;
    }
}