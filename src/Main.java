import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int[] initialBoard;

    // Driver
    public static void main(String[] args) throws FileNotFoundException {
        String board = "";
        Scanner input = new Scanner(System.in);
        int searchToRun;
        String fileName;
        int depth = 0;

        System.out.println("Welcome to 8-Puzzle!!!\n");
        System.out.println("1 - Breadth First Search");
        System.out.println("2 - Depth First Search");
        System.out.println("3 - Depth Limited Search");
        System.out.println("4 - Iterative Deepening");
        System.out.println("5 - Bi-Directional");
        System.out.println("6 - Greedy");
        System.out.print("\nEnter the number for the search you would like to run to solve the puzzle: ");
        searchToRun = input.nextInt();

        if (searchToRun == 3) {
            System.out.println("\nYou have selected \"Depth First Search\".");
            System.out.print("Enter a depth-limit: ");
            depth = input.nextInt();
        }

        System.out.print("Now enter the file name with the starting puzzle configuration: ");
        fileName = input.next();

        Scanner scanner = new Scanner(new FileReader(fileName));
        while (scanner.hasNext()) {
            board += scanner.next();
        }
        if (board.contains("_")) {
            int underscorePosition = board.indexOf('_');
            char[] boardChars = board.toCharArray();
            boardChars[underscorePosition] = '0';
            board = String.valueOf(boardChars);
        }

        // Make sure board has valid numbers
        if (validBoard(board)) {
            initializeBoard(board.split(""));
            printInitConfig();
        } else {
            System.out.println("The file doesn't contain a correct puzzle state");
            System.exit(0);
        }

        // Make sure board is solvable
        if (!isSolvable(initialBoard)) {
            System.out.println("Non solvable puzzle!!!");
            System.exit(0);
        }

        Node root = new Node(initialBoard);
        Node stringRoot = new Node(board, 0);
//        UninformedSearch ui = new UninformedSearch();

        long startTime = System.currentTimeMillis();

        UninformedSearch uninformedSearch = new UninformedSearch(); // New Instance of the UninformedSearch

        switch (searchToRun) {
            case 1:
                uninformedSearch.add(board, null, SearchType.BFS);
                uninformedSearch.breadthFirstSearch();
            case 2:
                uninformedSearch.add(board, null, SearchType.DFS);
                uninformedSearch.depthFirstSearch();
            case 3:
                uninformedSearch.add(board, null, SearchType.DLS);
                uninformedSearch.depthLimitedSearch(depth);
            case 4:
                uninformedSearch.add(board, null, SearchType.ID);
                uninformedSearch.iterativeDeepening(0);
            case 5:
                uninformedSearch.add(board, null, SearchType.BD);
                uninformedSearch.biDirectional();
            case 6:
                uninformedSearch.add(board, null, SearchType.GREEDY);
                uninformedSearch.greedySearch();
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        String separator = "#####################################################";
        System.out.println(separator);
        System.out.println("Time to find a solution: "+ elapsedTime + " ms");
        System.out.println(separator);
        System.out.println("Number of nodes created: " + uninformedSearch.getNumNodesCreated());
        System.out.println(separator);
        System.out.println("Number of nodes expanded: " + uninformedSearch.getNumNodesExpanded());
        System.out.println(separator);
        System.out.println("Solution Path:  ");

        uninformedSearch.printHistory();

        System.out.println(separator);
        System.out.println("Number of moved along the solutions path: " + uninformedSearch.getNumSolutionPath());
        System.out.println(separator);
        System.out.println("Solved Puzzle: ");
        uninformedSearch.printSolvedPuzzle();

    }

    // Initialize the board into a 2D array
    private static void initializeBoard(String[] splitText) {
        initialBoard = new int[9];
        int counter = 0;

        String test = splitText.toString();
        if (test.contains("1")) {
            System.out.println("Yes");
        }

        for (int row = 0; row < 9; row++) {

            if (splitText[counter].equals("_")) {
                splitText[counter] = "0";
            }
            initialBoard[row] = Integer.parseInt(splitText[counter]);
            counter++;
        }
    }

    // Make sure that the board contains all valid numbers (1-9) and an underscore (_)
    private static boolean validBoard(String text) {

        return text.contains("1") && text.contains("2") && text.contains("3") && text.contains("4")
                && text.contains("5") && text.contains("6") && text.contains("7") && text.contains("8")
                && (text.contains("_") || text.contains("0"));
    }

    // This function returns true if given 8 puzzle is solvable.
    private static boolean isSolvable(int[] puzzle)
    {
        int count = 0;
        List<Integer> array = new ArrayList<>();

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                array.add(puzzle[i]);
            }
        }

        Integer[] anotherArray = new Integer[array.size()];
        array.toArray(anotherArray);

        for (int i = 0; i < anotherArray.length - 1; i++) {
            for (int j = i + 1; j < anotherArray.length; j++) {
                if (anotherArray[i] != 0 && anotherArray[j] != 0 && anotherArray[i] > anotherArray[j]) {
                    count++;
                }
            }
        }

        return count % 2 == 0;
    }

    private static void printInitConfig() {
        int m = 0;
        System.out.println("\nStarting puzzle configuration:");
        int col = 3;
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(initialBoard[m] + " ");
                m++;
            }
            System.out.println();
        }
    }
}
