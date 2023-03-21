public class Stack {
    private int size;
    private Object[] elements;

    public Stack() {
        size = 0;
        elements = new Node[12];
    }

    public void push(Object data) {
        if (isFull()) {
            System.out.println("Stack is full");
            return;
        }
        elements[size] = data;
        size++;

    }

    public Object pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        Object retData = elements[size];
        elements[size] = null;
        size--;
        return retData;
    }

    public Object peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        return elements[size];
    }

    public boolean isFull() {
        return size == elements.length;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
