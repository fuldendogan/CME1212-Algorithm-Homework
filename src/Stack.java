public class Stack {
    private int size;
    private Node[] elements;

    public Stack() {
        size = 0;
        elements = new Node[12];
    }

    public void push(Node data) {
        if (isFull()) {
            System.out.println("Stack is full");
            return;
        }
        elements[size] = data;
        size++;
    }

    public Node pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        Node node = elements[size];
        elements[size] = null;
        size--;
        return node;
    }

    public Node peek() {
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
