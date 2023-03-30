
public class Queue {
    private int tail, head;
    private Object[] elements;
    private int MAX_CAPACITY;

    Queue(int maxCapacity) {
        this.MAX_CAPACITY = maxCapacity;
        elements = new Object[9999];
        tail = 0;
        head = 0;
    }

    void enqueue(Object data) {
        if (isFull())
            System.out.println("Queue overflow");
        else {
            elements[tail] = data;
            tail++;
        }
    }

    Object dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        } else {
            Object retData = elements[head];
            elements[head] = null;
            head++;
            return retData;
        }
    }

    Object peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        } else
            return elements[head];
    }

    boolean isEmpty() {
        return tail < head;
    }

    boolean isFull() {
        return (size() == MAX_CAPACITY);
    }

    int size() {
        return tail - head;
    }
}