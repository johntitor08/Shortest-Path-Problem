/**
 * Custom Stack implementation using linked nodes
 * Time Complexity Analysis:
 */
public class Stack<T> {
    private Node<T> top;
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
     * Push operation: O(1) - Constant time
     */
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
        size++;
    }

    /**
     * Pop operation: O(1) - Constant time
     */
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Peek operation: O(1) - Constant time
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

    /**
     * isEmpty operation: O(1) - Constant time
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Size operation: O(1) - Constant time
     */
    public int size() {
        return size;
    }
}