import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final String HIGH_SCORE_TABLE_TXT = "highscoretable.txt";
    public static final int MAX_CAPACITY = 12;
    static Queue nameQ = new Queue(MAX_CAPACITY);   // HighScore names maksimum 12
    static Queue scoreQ = new Queue(MAX_CAPACITY);  // HighScore scores maksimum 12
    static Stack player1 = new Stack(MAX_CAPACITY); // Player 1's cards
    static Stack player2 = new Stack(MAX_CAPACITY); // Player 2's cards
    static Stack bag1 = new Stack(13); // bag1
    static Stack bag2 = new Stack(13); // bag2

    public static void main(String[] args) {
        try {
            File myObj = new File(HIGH_SCORE_TABLE_TXT);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                nameQ.enqueue(myReader.next());
                scoreQ.enqueue(myReader.next());
            }
            sortQueue();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        takeInputAndInitializePlayerDecks();
        initializationOfBag();
        gameLoop();
    }

    private static void initializationOfBag() {
        for (int i = 13; i >= 1; i--) {
            bag1.push(i);
        }
    }

    private static void takeInputAndInitializePlayerDecks() {
        int stackSize = 7; //TODO: change to 0
        while (stackSize < 7 || stackSize > 10) {
            System.out.println("Please enter n (should be between 7 and 10): ");
            Scanner input = new Scanner(System.in);
            stackSize = input.nextInt();
        }
        Random rand = new Random();
        for (int j = 1; j <= stackSize; j++) {
            while (player1.size() < stackSize) {
                int num = rand.nextInt(13) + 1; // TODO: do not use magic numbers
                if (isStackContains(player1, num) == -1) {
                    player1.push(num);
                }
            }
        }
//        sortStack(player1);

        for (int j = 1; j <= stackSize; j++) {
            while (player2.size() < stackSize) {
                int num = rand.nextInt(13) + 1;
                if (isStackContains(player2, num) == -1) {
                    player2.push(num);
                }
            }
        }
//        sortStack(player2);

    }

    private static void gameLoop() {
//Player1: A 3 7 8 10 J K   Score: 0    Bag1 A 2 3 4 5 6 7 8 9 10 J Q K
//Player2: 2 3 5 6 8 9 Q    Score: 0    Bag2
//1. selected value: 3
//
//Player1: A 7 8 10 J K     Score: 10   Bag1 A 2 4 5 6 7 8 9 10 J Q K
//Player2: 2 5 6 8 9 Q      Score: 10   Bag2 3
//2. selected value: K

        boolean gameFinished = false;
        int round = 1;
        int score = 200;
        while (!gameFinished) {
            System.out.print("Player1: ");
            printStack(player1);
            System.out.print("Score: " + score + "    ");
            System.out.print("Bag1: ");
            printStack(bag1);
            System.out.println();

            System.out.print("Player2: ");
            printStack(player2);
            System.out.print("Score: " + score + "    ");
            System.out.print("Bag2: ");
            printStack(bag2);
            System.out.println();

            gameFinished =  true;
            round++;
        }

        System.out.print("What is your name: ");
        Scanner input = new Scanner(System.in);
        Object name = input.nextLine();

        nameQ.enqueue(name);
        scoreQ.enqueue(score);
    }


    // Stack methods
    private static int isStackContains(Stack stack, Object element) {
        for (int i = 0; i < stack.size(); i++) {
            if (getItemFromStack(stack, i) == element) {
                return i;
            }
        }
        return -1;
    }
    private static Object getItemFromStack(Stack stack, int i) {
        //get i.th element from stack
        Stack tempStack = new Stack(MAX_CAPACITY);
        Object item = null;
        int size = stack.size();
        for (int j = 0; j < size; j++) {
            Object element = stack.pop();
            if (j == i) {
                item = element;
            }
            tempStack.push(element);
        }
        for (int j = 0; j < size; j++) {
            stack.push(tempStack.pop());
        }
        return item;
    }
    private static void printStack(Stack stack) {
        Stack tempStack = new Stack(stack.size());
        while (!stack.isEmpty()) {
            Object element = stack.pop();
            System.out.print(getSymbol(element) + " ");
            tempStack.push(element);
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }
    private static String getSymbol(Object element) {
        if (element instanceof Integer) {
            int num = (int) element;
            switch (num) {
                case 1:
                    return "A";
                case 11:
                    return "J";
                case 12:
                    return "Q";
                case 13:
                    return "K";
                default:
                    return String.valueOf(num);
            }
        }
        return element.toString();
    }
    private static void sortStack(Stack stack){
        int size = stack.size();
        Stack sortedStack = new Stack(size);
        int max = 0;
        for (int i = 0; i < size; i++) {
            max = 0;
            for (int j = 0; j < size; j++) {
                int num = (int) getItemFromStack(stack, j);
                if (num > max && isStackContains(sortedStack, num) == -1) {
                    max = num;
                }
            }
            sortedStack.push(max);
        }
        while (!sortedStack.isEmpty()) {
            stack.push(sortedStack.pop());
        }
    }
    // End of Stack methods


    // Queue methods
    public static void sortQueue() {
        int size = scoreQ.size();

        Queue tempNameQ = new Queue(size);
        Queue tempScoreQ = new Queue(size);
        Queue sortedNameQ = new Queue(size);
        Queue sortedScoreQ = new Queue(size);
        int maxScore = 0;
        String maxName = "";
        int lastMaxScore = Integer.MAX_VALUE;

        for (int i = 0; i < size; i++) {
            maxScore = 0;
            maxName = "";
            for (int j = 0; j < size; j++) {
                int score = Integer.parseInt(scoreQ.peek().toString());
                String name = (String) nameQ.peek();
                if (score > maxScore && score <= lastMaxScore && isQueueContains(sortedNameQ, name) == -1) {
                    maxScore = score;
                    maxName = name;
                }
                tempNameQ.enqueue(nameQ.dequeue());
                tempScoreQ.enqueue(scoreQ.dequeue());
            }
            nameQ = tempNameQ;
            scoreQ = tempScoreQ;
            tempNameQ = new Queue(size);
            tempScoreQ = new Queue(size);
            sortedScoreQ.enqueue(maxScore);
            sortedNameQ.enqueue(maxName);
            lastMaxScore = maxScore;
        }
        nameQ = sortedNameQ;
        scoreQ = sortedScoreQ;

        System.out.println("sorted nameQ: " + printQueue(nameQ));
        System.out.println("sorted scoreQ: " + printQueue(scoreQ));
    }
    private static String printQueue(Queue queue) {
        int size = queue.size();
        Queue tempQ = new Queue(size);
        String result = "";
        for (int i = 0; i < size; i++) {
            Object data = queue.dequeue();
            tempQ.enqueue(data);
            result += data + " ";
        }
        for (int i = 0; i < size; i++) {
            queue.enqueue(tempQ.dequeue());
        }
        return result;
    }
    private static int isQueueContains(Queue queue, String target) {
        //copy queue to tempQ
        int size = queue.size();
        Queue tempQ = new Queue(size);

        int index = -1;
        for (int i = 0; i < size; i++) {
            String name = (String) queue.dequeue();
            tempQ.enqueue(name);
            if (name.equals(target)) {
                index = i;
            }
        }
        //copy tempQ to queue
        for (int i = 0; i < size; i++) {
            queue.enqueue(tempQ.dequeue());
        }
        return index;
    }
    // End of Queue methods
}