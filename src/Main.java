import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Queue nameQ = new Queue();   // HighScore names
    static Queue scoreQ = new Queue();  // HighScore scores
    static Stack player1 = new Stack(); // Player 1's cards
    static Stack player2 = new Stack(); // Player 2's cards

    public static void main(String[] args) {
        try {
            File myObj = new File("highscoretable.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                nameQ.enqueue(new Node(myReader.next()));
                scoreQ.enqueue(new Node(myReader.next()));
            }
            System.out.println("q1: " + nameQ.deepToString());
            System.out.println("q2: " + scoreQ.deepToString());
//            sortQueue();
//            System.out.println("sorted q1: " + nameQ.deepToString());
//            System.out.println("sorted q2: " + scoreQ.deepToString());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        takeInput();
    }

    private static void takeInput() {
        int stackSize = 7;
        while (stackSize < 7 || stackSize > 10) {
            System.out.println("Please enter n");
            Scanner input = new Scanner(System.in);
            stackSize = input.nextInt();
        }

        Random rand = new Random();
        for (int j = 1; j <= stackSize; j++) {
            while (player1.size() < stackSize) {
                int num = rand.nextInt(13) + 1; // TODO: do not use magic numbers
                if (!isStackContains(player1,num)) {
                    player1.push(new Node(num));
                }
            }
        }

        for (int j = 1; j <= stackSize; j++) {
            while (player2.size() < stackSize) {
                int num = rand.nextInt(13) + 1;
                if (!isStackContains(player2,num)) {
                    player2.push(new Node(num));
                }
            }
        }
        boolean gameFinished = false;
        int round = 1;
        while (!gameFinished) {
            gameFinished = gameLoop(round);
            round++;
        }
        System.out.print("What is your name: ");
        Scanner input = new Scanner(System.in);
        Node name = new Node(input.nextLine());

        nameQ.enqueue(new Node(name));//TODO: ask: is this name already in the queue?
        scoreQ.enqueue(new Node(round)); //TODO: ask: is this score better than the first 12 scores?


    }

    private static boolean isStackContains(Stack stack, Object element) {
        for (int i = 0; i < stack.size(); i++) {
            if (getItemFromStack(stack,i) != null && getItemFromStack(stack,i) == element) {
                return true;
            }
        }
        return false;
    }

    private static Node getItemFromStack(Stack stack, int i) {
        //get i.th element from stack
        Stack tempStack = new Stack();
        Object item = null;
        for (int j = 0; j < stack.size(); j++) {
            Object element = stack.pop();
            if (j == i) {
                item = element;
            }
            tempStack.push(element);
        }
    }

    //    q1: Kaan Ali Yeliz Cem Can Pelin Ece Sibel Ayşe Berk Ege Ada
//q2: 130 100 120 140 100 110 140 150 120 160 120 80
    //Sort the queue q1 and q2 according to the scores in q2
    private static void sortQueue() {
        //todo: sort the queue according to the scores in q2
    }

    private static void sortStack(Stack stack) {
        while (!stack.isEmpty()) {
            Node max = stack.peek();
            while (!stack.isEmpty()) {
                Node temp = stack.pop();
                if (Integer.parseInt(temp.getData().toString()) > Integer.parseInt(max.getData().toString())) {
                    max = temp;
                } else {
                    stack.push(temp);
                }
            }
            stack.push(max);
        }
    }

    private static boolean gameLoop(int round) {
//        Player1: A 3 7 8 10 J K Score: 0 Bag1 A 2 3 4 5 6 7 8 9 10 J Q K
//Player2: 2 3 5 6 8 9 Q Score: 0 Bag2
//1. selected value: 3
        System.out.print("Player1: ");
        printStack(player1);
        System.out.print("Player2: ");
        printStack(player2);
        return true;
    }

    private static void printStack(Stack stack) {
        Stack tempStack = new Stack();
        while (!stack.isEmpty()) {
            Object element = stack.pop();
            System.out.print(element + " ");
            tempStack.push(element);
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }
}
