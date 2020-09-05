import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int[] initialBoard;

    // Driver
    public static void main(String[] args) throws FileNotFoundException {
        String text = "";

        if (args.length < 2) {
            System.out.println("Please enter the name of the file with the puzzle config.");
        } else {
            Scanner scanner = new Scanner(new FileReader(args[1]));
            while (scanner.hasNext()) {
                text += scanner.next();
            }
        }

        // Make sure board has valid numbers
        if (validBoard(text)) {
            initializeBoard(text.split(""));
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
        UninformedSearch ui = new UninformedSearch();

        long startTime = System.currentTimeMillis();
        List<Node> solution = ui.BreadthFirstSearch(root);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        String separator = "#####################################################";
        System.out.println(separator);
        System.out.println("Time to find a solution: "+ elapsedTime + " ms");
        System.out.println(separator);
        System.out.println("Number of nodes created: " + UninformedSearch.getNumNodesCreated());
        System.out.println(separator);
        System.out.println("Number of nodes expanded: " + Node.getNumNodesExpanded());
        System.out.println(separator);
        System.out.println("Solution Path:  ");

        Collections.reverse(solution);
        if (solution.size() > 0) {
            for (int i = 0; i < solution.size(); i++) {
                solution.get(i).printPuzzle();
            }
        }

        System.out.println(separator);
        System.out.println("Number of moved along the solutions path: " + solution.size());
        System.out.println(separator);
        System.out.println("Solved Puzzle: ");
        solution.get(solution.size() - 1).printPuzzle();

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
