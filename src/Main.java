import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static Stack stack = new Stack();
    static Queue q1 = new Queue();
    static Queue q2 = new Queue();

    public static void main(String[] args) {


        try {
            File myObj = new File("highscoretable.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                q1.enqueue(new Node(line.split(" ")[0]));
                q2.enqueue(new Node(line.split(" ")[1]));
            }
            System.out.println("q1: " + q1.deepToString());
            System.out.println("q2: " + q2.deepToString());
            sortQueue();
            System.out.println("sorted q1: " + q1.deepToString());
            System.out.println("sorted q2: " + q2.deepToString());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    //    q1: Kaan Ali Yeliz Cem Can Pelin Ece Sibel Ayşe Berk Ege Ada
//q2: 130 100 120 140 100 110 140 150 120 160 120 80
    //Sort the queue q1 and q2 according to the scores in q2
    private static void sortQueue() {
        while (!q2.isEmpty()) {
            Node max = q2.peek();
            Node maxNode = q1.peek();
            while (!q2.isEmpty()) {
                Node temp = q2.dequeue();
                Node tempNode = q1.dequeue();
                if (Integer.parseInt(temp.getData().toString()) > Integer.parseInt(max.getData().toString())) {
                    max = temp;
                    maxNode = tempNode;
                } else {
                    q1.enqueue(tempNode);
                    q2.enqueue(temp);
                }
            }
            stack.push(maxNode);
            stack.push(max);
        }
        while (!stack.isEmpty()) {
            q1.enqueue(stack.pop());
            q2.enqueue(stack.pop());
        }
    }
}