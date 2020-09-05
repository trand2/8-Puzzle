import java.util.ArrayList;
import java.util.List;

class UninformedSearch {

    private static int numNodesCreated = 0;

    private List<Node> pathToSolution = new ArrayList<>();
    private List<Node> openList = new ArrayList<>();
    private List<Node> closedList = new ArrayList<>();

    List<Node> BreadthFirstSearch(Node root) {
        openList.add(root);
        boolean goalFound = false;

        while (openList.size() > 0 && !goalFound) {
            Node currentNode = openList.get(0);
            closedList.add(currentNode);
            openList.remove(0);

            currentNode.ExpandNode();

            for (int i = 0; i < currentNode.children.size(); i++) {

                Node currentChild = currentNode.children.get(i);

                if (currentChild.goalTest()) {
                    goalFound = true;

                    // trace path to root node
                    pathTrace(pathToSolution, currentChild);
                }

                if (!contains(openList, currentChild) && !contains(closedList, currentChild)) {
                    openList.add(currentChild);
                }
            }
        }
        numNodesCreated += openList.size();
        return pathToSolution;
    }
//
//    List<Node> DepthFirstSearch(Node root) {
//        openList.add(root);
//        boolean goalFound = false;
//
//        while (openList.size() > 0 && !goalFound) {
//            Node currentNode = openList.get(0);
//            closedList.add(currentNode);
//            openList.remove(0);
//
//        }
//
//    }

    private void pathTrace(List<Node> path, Node n) {
        Node current = n;
        path.add(current);

        while (current.parent != null) {
            current = current.parent;
            path.add(current);
        }

    }

    private static boolean contains(List<Node> list, Node c) {
        boolean contains = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSamePuzzle(c.puzzle)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    static int getNumNodesCreated() {
        return numNodesCreated;
    }
}