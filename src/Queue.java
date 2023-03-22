
public class Queue {
    private int rear, front; private Object[] elements;
    Queue (int capacity) {

    elements = new Object[capacity];
    rear = -1;
    front = 0;
    }

    void enqueue(Object data) {
        if (isFull())
            System.out.println("Queue overflow");
        else {
            rear++;
            elements [rear] = data;
        }
    }

    Object dequeue() {
        if (isEmpty())
        {
            System.out.println("Queue is empty");
            return null;
        }
        else
        {
            Object retData = elements[front];
            elements[front]= null;
            front++;
            return retData;
        }
    }
    Object peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        else
            return elements [front];
    }
        boolean isEmpty() {
        return rear < front;
    }
        boolean isFull() {
        return (rear + 1 == elements.length);
    }
        int size() {
        return rear - front + 1;
    }
}


//
//public class Queue {
//    private int size;
//    private int capacity;
//    private Node head;
//    private Node end;
//
//
//
//    public Queue() {
//        size = 0;
//        capacity = 12;
//    }
//    // ali->Emre->Furkan
//
//    public void enqueue(Node data) {
//        if (isFull()) {
//            System.out.println("Queue is full");
//            return;
//        }
//        if(this.end == null){
//            this.head = data;
//            this.end = this.head;
//        }
//        else {
//            this.end.setNext(data);
//            this.end = data;
//        }
//        size++;
//    }
//
//    public Node dequeue() {
//        if (isEmpty()) {
//            System.out.println("Queue is empty");
//            return null;
//        }
//        if (head == end){
//            end = null;
//        }
//
//        Node headNext = this.head.getNext();
//        head = headNext;
//        size--;
//        return headNext;
//    }
//
//    public Node peek() {
//        if (isEmpty()) {
//            System.out.println("Queue is empty");
//            return null;
//        }
//        if (head == end){
//            end = null;
//        }
//
//        return this.head.getNext();
//    }
//
//    public boolean isFull() {
//        return size == capacity;
//    }
//
//    public int size() {
//        return size;
//    }
//
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    @Override
//    public String toString() {
//        return head.getData().toString();
//    }
//
//    public String deepToString() {
//
//        StringBuilder res = new StringBuilder();
//        res.append(head.getData());
//
//        Node cur = head;
//        while (null != (cur = cur.getNext())){
//            res.append(" ");
//            res.append(cur.getData());
//
//        }
//        return res.toString();
//    }
//}
