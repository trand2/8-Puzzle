import java.util.ArrayList;
import java.util.List;

class Node {
    List<Node> children = new ArrayList<>();
    Node parent;
    int[] puzzle = new int[9];
    private int x = 0;
    private int col = 3;
    private int[] goalState = {1,2,3,4,5,6,7,8,0};
    private static int numNodesExpanded = 0;

    Node(int[] p) {
        setPuzzle(p);
    }

    private void setPuzzle(int[] p) {
        // Set this.puzzle to the value of p
        System.arraycopy(p, 0, this.puzzle, 0, puzzle.length);
    }

    void ExpandNode() {
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) {
                x = i;
                break;
            }
        }

        moveRight(puzzle, x);
        moveLeft(puzzle, x);
        moveUp(puzzle, x);
        moveDown(puzzle, x);
        numNodesExpanded += 4;

    }

    private void moveRight(int[] p, int i) {

        // Make sure you are not in column 2
        if (i % col < 2) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);

            int temp = pc[i + 1];
            pc[i+1] = pc[i];
            pc[i] = temp;

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    private void moveLeft(int[] p, int i) {
        if (i % col > 0) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);

            int temp = pc[i - 1];
            pc[i - 1] = pc[i];
            pc[i] = temp;

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    private void moveUp(int[] p, int i) {
        if (i - col >= 0) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);

            int temp = pc[i - 3];
            pc[i - 3] = pc[i];
            pc[i] = temp;

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    private void moveDown(int[] p, int i) {
        if (i + col < puzzle.length) {
            int[] pc = new int[9];
            copyPuzzle(pc, p);

            int temp = pc[i + 3];
            pc[i + 3] = pc[i];
            pc[i] = temp;

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }
    }

    void printPuzzle() {

        System.out.println();

        int m = 0;

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(puzzle[m] + " ");
                m++;
            }
            System.out.println();
        }
    }

    boolean isSamePuzzle(int[] p) {
        boolean samePuzzle = true;

        for (int i = 0; i < p.length; i++) {
            if (puzzle[i] != p[i]) {
                samePuzzle = false;
                break;
            }
        }
        return samePuzzle;
    }

    private void copyPuzzle(int[] a, int[] b) {
        // copy puzzle b to puzzle a
        System.arraycopy(b, 0, a, 0, b.length);
    }

    // Check if puzzle matches the goal
    boolean goalTest() {
        boolean isGoal = true;

        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != goalState[i]) {
                isGoal = false;
                break;
            }
        }
        return isGoal;
    }

    public static int getNumNodesExpanded() {
        return numNodesExpanded;
    }
}