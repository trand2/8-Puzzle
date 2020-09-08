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

    Node (String s) {
        stringPuzzle = s;
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


    void setTotalCost(int cost, int estimatedCost) {
        totalCost = cost + estimatedCost;
    }
}