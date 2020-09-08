import java.util.ArrayList;
import java.util.List;

class Node {
    int[] puzzle = new int[9];
    private int col = 3;
    private String stringPuzzle;
    private int totalCost;

    Node(int[] p) {
        setPuzzle(p);
    }

    Node (String s, int cost) {
        stringPuzzle = s;
        totalCost = cost;
    }

    private void setPuzzle(int[] p) {
        // Set this.puzzle to the value of p
        System.arraycopy(p, 0, this.puzzle, 0, puzzle.length);
    }

    String getStringPuzzle() {
        return stringPuzzle;
    }

    int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int cost) {
        totalCost = cost;
    }

    void setTotalCost(int cost, int estimatedCost) {
        totalCost = cost + estimatedCost;
    }
}