import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final String HIGH_SCORE_TABLE_TXT = "highscoretable.txt";
    public static final int MAX_CAPACITY = 12;
    public static final int MAX_CARD_VALUE = 13;
    static Queue nameQ = new Queue(MAX_CAPACITY);   // HighScore names maksimum 12
    static Queue scoreQ = new Queue(MAX_CAPACITY);  // HighScore scores maksimum 12
    static Stack player1 = new Stack(MAX_CARD_VALUE); // Player 1's cards
    static Stack player2 = new Stack(MAX_CARD_VALUE); // Player 2's cards
    static Queue bag1 = new Queue(MAX_CARD_VALUE); // bag1
    static Queue bag2 = new Queue(MAX_CARD_VALUE); // bag2
    static int score1 = 0;  // Player 1's score
    static int score2 = 0;  // Player 2's score
    static boolean gameFinished = false;
    static boolean programFinished = false;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (!programFinished) {
            readHighScoreTableTxt();
            takeInputAndInitializePlayerDecks();
            initializationOfBag();
            gameLoop();
        }
    }

    private static void readHighScoreTableTxt() {
        try {
            nameQ = new Queue(MAX_CAPACITY);
            scoreQ = new Queue(MAX_CAPACITY);
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
    }

    private static void takeInputAndInitializePlayerDecks() {
        int stackSize = 0;
        while (stackSize < 7 || stackSize > 10) {
            System.out.println("");
            System.out.println("Please enter n (should be between 7 and 10): ");
            Scanner input = new Scanner(System.in);
            stackSize = input.nextInt();
        }
        player1 = new Stack(MAX_CARD_VALUE);
        player2 = new Stack(MAX_CARD_VALUE);
        Random rand = new Random();
        for (int j = 1; j <= stackSize; j++) {
            while (player1.size() < stackSize) {
                int num = rand.nextInt(13) + 1; // TODO: do not use magic numbers
                if (isStackContains(player1, num) == -1) {
                    player1.push(num);
                }
            }
        }
        sortStack(player1);

        for (int j = 1; j <= stackSize; j++) {
            while (player2.size() < stackSize) {
                int num = rand.nextInt(13) + 1;
                if (isStackContains(player2, num) == -1) {
                    player2.push(num);
                }
            }
        }
        sortStack(player2);

    }

    private static void initializationOfBag() {
        bag1 = new Queue(MAX_CARD_VALUE);
        for (int i = 1; i <= 13; i++) {
            bag1.enqueue(i);
        }
    }

    private static void gameLoop() {
        int roundNumber = 0;
        Random rand = new Random();
        int player1CompletedCount = 0;
        int player2CompletedCount = 0;
        boolean isP1T1Completed, isP2T1Completed, isP1T2Completed, isP2T2Completed;
        gameFinished = false;
        bag2 = new Queue(MAX_CARD_VALUE);
        score1 = 0;
        score2 = 0;
        while (!gameFinished) {
            isP1T1Completed = false;
            isP2T1Completed = false;
            isP1T2Completed = false;
            isP2T2Completed = false;
            roundNumber++;
            int randOfBag1 = rand.nextInt(bag1.size());
            int selectedValue = getItemFromQueue(bag1, randOfBag1);
            printPlayer_Score_Bag();
            System.out.println(roundNumber + ". selected value: " + getSymbol(selectedValue) + "\n");
            bag2.enqueue(selectedValue);
            removeItemFromQueueWithItem(bag1, selectedValue);
            if (isStackContains(player1, selectedValue) != -1) {
                removeItemFromStackWithItem(player1, selectedValue);
                score1 += 10;
                player1CompletedCount++;
                if (player1CompletedCount == 4) isP1T1Completed = true;
                else if (player1CompletedCount == 8) isP1T2Completed = true;
            } else {
                score1 -= 5;
            }
            if (isStackContains(player2, selectedValue) != -1) {
                removeItemFromStackWithItem(player2, selectedValue);
                score2 += 10;
                player2CompletedCount++;
                if (player2CompletedCount == 4) isP2T1Completed = true;
                else if (player2CompletedCount == 8) isP2T2Completed = true;
            } else {
                score2 -= 5;
            }

            if (isP1T1Completed || isP2T1Completed || isP1T2Completed || isP2T2Completed) {

                printPlayer_Score_Bag();
                if (isP1T1Completed) System.out.println("First tournament is completed (player1)" + "\n");
                if (isP2T1Completed) System.out.println("First tournament is completed (player2)" + "\n");
                if (isP1T2Completed) System.out.println("Second tournament is completed (player1)" + "\n");
                if (isP2T2Completed) System.out.println("Second tournament is completed (player2)" + "\n");

                if ((isP1T1Completed && isP2T1Completed) || (isP1T2Completed && isP2T2Completed)) {
                    score1 += 15;
                    score2 += 15;
                } else if (isP1T1Completed || isP1T2Completed) {
                    score1 += 30;
                } else if (isP2T1Completed || isP2T2Completed) {
                    score2 += 30;
                } else
                    System.out.println("Error in tournament completion");
            }

            if (player1.size() == 0 || player2.size() == 0) {
                gameFinished = true;
                printPlayer_Score_Bag();
                if (player1.size() == 0 && player2.size() == 0) {
                    score1 += 25;
                    score2 += 25;
                } else if (player1.size() == 0) {
                    score1 += 50;
                } else if (player2.size() == 0) {
                    score2 += 50;
                }
                System.out.println("Game over!"+ "\n");

                if (score1 == score2)
                    System.out.println("Tie, it's a draw!"+ "\n");
                else {
                    if (score1 > score2) {
                        System.out.println("Winner: Player1 with " + score1 + " points"+ "\n");
                        addWinnerToHighScoreTable(score1);
                    } else {
                        System.out.println("Winner: Player2 with " + score2 + " points"+ "\n");
                        addWinnerToHighScoreTable(score2);
                    }
                }

                System.out.println("Play again? (y/n): ");
                char answer = sc.nextLine().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    return;
                } else if (answer == 'n' || answer == 'N') {
                    System.out.println("Goodbye!");
                    System.exit(0);
                } else {
                    System.out.println("Invalid input, goodbye!");
                    System.exit(0);
                }
            }
        }
    }

    private static void addWinnerToHighScoreTable(int score) {
        System.out.print("What is your name: "+ "\n");
        String name = sc.nextLine();

        //check if the name is already in the file
        int nameIndex = isQueueContains(nameQ, name);
        if (nameIndex == -1) {//if no, then add the name and score to the file
            if (nameQ.size() < 12) {
                nameQ.enqueue(name);
                scoreQ.enqueue(score);
            } else {
                //Son degerden buyukse son degeri silip en sona ekle
                //Son degerden kucukse ekleme yapma
                if (score > getItemFromQueue(scoreQ, scoreQ.size() - 1)) {
                    changeScoreToQueueSpecificNode(nameQ, scoreQ.size() - 1, name);
                    changeScoreToQueueSpecificNode(scoreQ, scoreQ.size() - 1, score);
                } else {
                    System.out.println("Your score is not enough to be in the high score table"+"\n");
                }
            }
        } else {//if yes, then check if the score is higher than the score in the file
            if (score > getItemFromQueue(scoreQ, nameIndex)) {
                changeScoreToQueueSpecificNode(scoreQ, nameIndex, score);
            } else {
                System.out.println("Your score is not enough to be update high score table"+"\n");
            }
        }

        //sort and update name and score
        sortQueue();
        //update highscoretable.txt
        try {
            FileWriter fileWriter = new FileWriter(HIGH_SCORE_TABLE_TXT);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            while (!nameQ.isEmpty() && !scoreQ.isEmpty()) {
                String newHighScoreText = nameQ.dequeue() + " " + scoreQ.dequeue();
                if (newHighScoreText.contains("null")) continue;
                printWriter.println(newHighScoreText);
            }
            printWriter.close();
            System.out.println("");
            System.out.println("The file writing process has been successfully completed."+ "\n");
        } catch (IOException e) {
            System.out.println("");
            System.out.println("An error occurred during the file writing process: " + e.getMessage());
        }
    }

    private static void printPlayer_Score_Bag() {
        System.out.printf("Player1:"+ String.format("%-22s",getPrintableStack(player1)));
        System.out.print("Score: " + score1 + "    ");
        System.out.print("Bag1: " + getPrintableQueue(bag1));
        System.out.println();

        System.out.printf("Player2:"+ String.format("%-22s",getPrintableStack(player2)));
        System.out.printf(String.format("%-12s","Score: " + score2 + "    "));
        System.out.print("Bag2: " + getPrintableQueue(bag2));//reverse gerekebilir
        System.out.println("\n");
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
        Stack tempStack = new Stack(MAX_CARD_VALUE);
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

    private static String getPrintableStack(Stack stack) {
        String result = "";
        Stack tempStack = new Stack(stack.size());
        while (!stack.isEmpty()) {
            Object element = stack.pop();
            result += getSymbol(element) + " ";
            tempStack.push(element);
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        return result;
    }

//    private static String getReversePrintableStack(Stack stack) {
//        return reverse(getPrintableStack(stack));
//    }

//    public static String reverse(String s) {
//        //https://www.scaler.com/topics/reverse-a-sentence-in-java/
//        // Finding the index of the whitespaces
//        int x = s.indexOf(" ");
//
//        //Base condition
//        if (x == -1)
//            return s;
//
//        //Recursive call
//        return reverse(s.substring(x + 1)) + " " + s.substring(0, x);
//    }

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
        } else if (element instanceof String) {
            return (String) element;
        }
        return null;
    }

    private static void sortStack(Stack stack) {
        int size = stack.size();
        Stack reversedStack = new Stack(size);
        Stack sortedStack = new Stack(size);
        while (!stack.isEmpty()) {
            reversedStack.push(stack.pop());
        }
        int min;
        for (int i = 0; i < size; i++) {
            min = MAX_CARD_VALUE;
            for (int j = 0; j < size; j++) {
                int num = (int) getItemFromStack(reversedStack, j);
                if (num < min && isStackContains(sortedStack, num) == -1) {
                    min = num;
                }
            }
            sortedStack.push(min);
        }
        while (!sortedStack.isEmpty()) {
            stack.push(sortedStack.pop());
        }

    }

    private static void removeItemFromStackWithItem(Stack stack, Object item) {
        Stack tempStack = new Stack(stack.size());
        while (!stack.isEmpty()) {
            Object element = stack.pop();
            if (element != item) {
                tempStack.push(element);
            }
        }
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
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
        int maxScore;
        String maxName;
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

        System.out.println("Names: " + getPrintableQueue(nameQ));
        System.out.println("Scores: " + getPrintableQueue(scoreQ));
        printScore();
    }

    private static String getPrintableQueue(Queue queue) {
        int size = queue.size();
        Queue tempQ = new Queue(size);
        String result = "";
        for (int i = 0; i < size; i++) {
            Object data = queue.dequeue();
            tempQ.enqueue(data);
            result += getSymbol(data) + " ";
        }
        for (int i = 0; i < size; i++) {
            queue.enqueue(tempQ.dequeue());
        }
        return result;
    }

    //printScore
    public static void printScore() {
        int size = scoreQ.size();
        Queue tempNameQ = new Queue(size);
        Queue tempScoreQ = new Queue(size);
        System.out.println("High Score Table");
        for (int i = 0; i < size; i++) {
            String name = (String) nameQ.dequeue();
            int score = Integer.parseInt(scoreQ.dequeue().toString());
            tempNameQ.enqueue(name);
            tempScoreQ.enqueue(score);
            System.out.printf(String.format("%-20s", name));
            System.out.print(" : ");
            System.out.printf(String.format("%-20s", score));
            System.out.println();
        }
        nameQ = tempNameQ;
        scoreQ = tempScoreQ;
    }


    private static int isQueueContains(Queue queue, String target) {
        //copy queue to tempQ
        int size = queue.size();
        Queue tempQ = new Queue(size);

        int index = -1;
        for (int i = 0; i < size; i++) {
            String name = (String) queue.dequeue();
            tempQ.enqueue(name);
            if (name.equalsIgnoreCase(target)) {
                index = i;
            }
        }
        //copy tempQ to queue
        for (int i = 0; i < size; i++) {
            queue.enqueue(tempQ.dequeue());
        }
        return index;
    }

    private static int getItemFromQueue(Queue queue, int i) {
        int size = queue.size();
        Queue tempQ = new Queue(size);
        int item = 0;
        for (int j = 0; j < size; j++) {
            int data = Integer.parseInt(queue.dequeue().toString());
            if (j == i) {
                item = data;
            }
            tempQ.enqueue(data);
        }
        for (int j = 0; j < size; j++) {
            queue.enqueue(tempQ.dequeue());
        }
        return item;
    }

    private static void changeScoreToQueueSpecificNode(Queue queue, int index, Object newValue) {
        int size = queue.size();
        Queue tempQ = new Queue(size);
        for (int i = 0; i < size; i++) {
            Object data = queue.dequeue();
            if (i == index) {
                data = newValue;
            }
            tempQ.enqueue(data);
        }
        for (int i = 0; i < size; i++) {
            queue.enqueue(tempQ.dequeue());
        }
    }

    private static void removeItemFromQueueWithItem(Queue queue, Object item) {
        Queue tempQ = new Queue(queue.size());
        while (!queue.isEmpty()) {
            Object element = queue.dequeue();
            if (element != item) {
                tempQ.enqueue(element);
            }
        }
        while (!tempQ.isEmpty()) {
            queue.enqueue(tempQ.dequeue());
        }
    }
    // End of Queue methods
}