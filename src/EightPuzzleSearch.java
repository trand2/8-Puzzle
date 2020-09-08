import java.util.*;

class EightPuzzleSearch {

    private static LinkedList<String> agenda = new LinkedList<>();   // Use of Queue Implemented using LinkedList for Storing All the Nodes in BFS.
    private static Map<String,Integer> stateDepth = new HashMap<>(); // HashMap is used to ignore repeated nodes
    private static Map<String,String> stateHistory = new HashMap<>(); // relates each position to its predecessor
    private static Map<String,String> stateHistoryReverse = new HashMap<>();
    private static LinkedList<String> agendaReverse = new LinkedList<>();
    private static Map<String,Integer> stateDepthReverse = new HashMap<>();
    private static PriorityComparator priorityComparator = new PriorityComparator();
    private static PriorityQueue<Node> pq = new PriorityQueue<Node>(10, priorityComparator);
    private static String traceState;
    private static int numNodesExpanded = 0;
    private static int numNodesCreated = 0;
    private static boolean solutionFound = false;
    private static int numSolutionPath = 0;
    private static String solvedPuzzle;
    private static List<String> pathToSolution = new ArrayList<>();
    private static SearchType searchType;
    private static String direction = "front";
    private static String searchStart;
    private static String goalState = "123456780";
    private static Node node;
    private static Heuristic heuristic;

    static void breadthFirstSearch(){

        searchType = SearchType.BFS;

        while(!agenda.isEmpty() && !solutionFound){
            String currentState = agenda.remove();
            checkCompletion(null, currentState);
            if (!solutionFound) {
                up(currentState); // Move the blank space up and add new state to queue
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                down(currentState); // Move the blank space down
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                left(currentState); // Move left
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                right(currentState); // Move right and remove the current node from Queue
                numNodesExpanded++;
            } else
                break;
        }
    }

    static void depthFirstSearch(){

        searchType = SearchType.DFS;

        while(!agenda.isEmpty() && !solutionFound){
            String currentState = agenda.remove();
            checkCompletion(null, currentState);
            if (!solutionFound) {
                up(currentState); // Move the blank space up and add new state to queue
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                down(currentState); // Move the blank space down
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                left(currentState); // Move left
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                right(currentState); // Move right and remove the current node from Queue
                numNodesExpanded++;
            } else
                break;
        }
    }

    static void depthLimitedSearch(int d){

        int depth = d;
        int limit = 5;
        searchType = SearchType.DLS;

        while(!agenda.isEmpty() && !solutionFound){
            if (depth <= limit) {
                String currentState = agenda.remove();
                checkCompletion(null, currentState);
                if (!solutionFound) {
                    up(currentState); // Move the blank space up and add new state to queue
                    numNodesExpanded++;
                } else
                    break;
                if (!solutionFound) {
                    down(currentState); // Move the blank space down
                    numNodesExpanded++;
                } else
                    break;
                if (!solutionFound) {
                    left(currentState); // Move left
                    numNodesExpanded++;
                } else
                    break;
                if (!solutionFound) {
                    right(currentState); // Move right and remove the current node from Queue
                    numNodesExpanded++;
                } else
                    break;
            }
            else {
                System.out.println("\nGoal not found within depth limit");
                System.exit(0);
            }
            depth++;
        }
    }

    static void iterativeDeepening(int depth){
        int limit = 5;
        searchType = SearchType.ID;

        while(!agenda.isEmpty() && !solutionFound){
            if (depth <= limit) {
                String currentState = agenda.remove();
                checkCompletion(null, currentState);
                if (!solutionFound) {
                    up(currentState); // Move the blank space up and add new state to queue
                    numNodesExpanded++;
                } else
                    break;
                if (!solutionFound) {
                    down(currentState); // Move the blank space down
                    numNodesExpanded++;
                } else
                    break;
                if (!solutionFound) {
                    left(currentState); // Move left
                    numNodesExpanded++;
                } else
                    break;
                if (!solutionFound) {
                    right(currentState); // Move right and remove the current node from Queue
                    numNodesExpanded++;
                } else
                    break;
            }
            else {
                depth++;
                iterativeDeepening(depth);
            }
        }
    }

    static void biDirectional(){

        searchType = SearchType.BD;
        direction = "back";
        String goal = "123456780";
        add(goal, null, searchType);
        direction = "front";


        while(!agenda.isEmpty() && !solutionFound){
            String currentState = agenda.remove();
            String currentStateReverse = agendaReverse.remove();
            checkCompletion(null, currentState);
            if (!solutionFound) {
                up(currentState); // Move the blank space up and add new state to queue
                numNodesExpanded++;
                direction = "back";
                up(currentStateReverse); // Move the blank space up and add new state to queue
                numNodesExpanded++;
                direction = "front";
            } else
                break;
            if (!solutionFound) {
                down(currentState); // Move the blank space down
                numNodesExpanded++;
                direction = "back";
                down(currentStateReverse); // Move the blank space down
                numNodesExpanded++;
                direction = "front";
            } else
                break;
            if (!solutionFound) {
                left(currentState); // Move left
                numNodesExpanded++;
                direction = "back";
                left(currentStateReverse); // Move left
                numNodesExpanded++;
                direction = "front";
            } else
                break;
            if (!solutionFound) {
                right(currentState); // Move right and remove the current node from Queue
                numNodesExpanded++;
                direction = "back";
                right(currentStateReverse); // Move right and remove the current node from Queue
                numNodesExpanded++;
                direction = "front";
            } else
                break;
        }
    }

    static void greedySearch(){

        searchType = SearchType.GREEDY;

        while(!pq.isEmpty() && !solutionFound){
            Node currentNode = pq.poll();
            String currentState = currentNode.getStringPuzzle();
            checkCompletion(null, currentState);
            if (!solutionFound) {
                up(currentState); // Move the blank space up and add new state to queue
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                down(currentState); // Move the blank space down
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                left(currentState); // Move left
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                right(currentState); // Move right and remove the current node from Queue
                numNodesExpanded++;
            } else
                break;
        }
    }

    static void aStarSearch(Heuristic h){

        searchType = SearchType.A_STAR;
        heuristic = h;

        while(!pq.isEmpty() && !solutionFound){
            Node currentNode = pq.poll();
            String currentState = currentNode.getStringPuzzle();
            checkCompletion(null, currentState);
            if (!solutionFound) {
                up(currentState); // Move the blank space up and add new state to queue
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                down(currentState); // Move the blank space down
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                left(currentState); // Move left
                numNodesExpanded++;
            } else
                break;
            if (!solutionFound) {
                right(currentState); // Move right and remove the current node from Queue
                numNodesExpanded++;
            } else
                break;
        }
    }


    //Add method to add the new string to the Map and Queue
    static void add(String newState, String oldState, SearchType searchType){
        if (searchType.equals(SearchType.BD) && direction.equals("front") && (!stateDepth.containsKey(newState))) {
            int newValue = oldState == null ? 0 : stateDepth.get(oldState) + 1;
            stateDepth.put(newState, newValue);
            agenda.add(newState);
            stateHistory.put(newState, oldState);
            numNodesCreated++;
        } else if (searchType.equals(SearchType.BD) && direction.equals("back") && !stateDepthReverse.containsKey(newState)) {
            int newValue = oldState == null ? 0 : stateDepthReverse.get(oldState) + 1;
            stateDepthReverse.put(newState, newValue);
            agendaReverse.add(newState);
            stateHistoryReverse.put(newState, oldState);
            numNodesCreated++;
        } else if (searchType.equals(SearchType.BFS) && (!stateDepth.containsKey(newState))) {
            int newValue = oldState == null ? 0 : stateDepth.get(oldState) + 1;
            stateDepth.put(newState, newValue);
            agenda.add(newState);
            stateHistory.put(newState, oldState);
            numNodesCreated++;
        } else if ((searchType.equals(SearchType.DFS) || searchType.equals(SearchType.DLS) || searchType.equals(SearchType.ID))
                && (!stateDepth.containsKey(newState))) {
            int newValue = oldState == null ? 0 : stateDepth.get(oldState) + 1;
            stateDepth.put(newState, newValue);
            agenda.addFirst(newState);
            stateHistory.put(newState, oldState);
            numNodesCreated++;
        } else if (searchType.equals(SearchType.GREEDY) && (!stateDepth.containsKey(newState))) {
            int newValue = oldState == null ? 0 : stateDepth.get(oldState) + 1;
            stateDepth.put(newState, newValue);
            node = new Node(newState, 0);
            node.setTotalCost(0, heuristicOne(node.getStringPuzzle(), goalState));
            pq.add(node);
            stateHistory.put(newState, oldState);
            numNodesCreated++;
        } else if (searchType.equals(SearchType.A_STAR) && (!stateDepth.containsKey(newState))) {
            int newValue = oldState == null ? 0 : stateDepth.get(oldState) + 1;
            stateDepth.put(newState, newValue);
            node = new Node(newState, 0);
            Node currentNode = new Node(oldState);
            if (oldState != null) {
                if (heuristic == Heuristic.H_ONE)
                    node.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(node.getStringPuzzle().charAt(currentNode.getStringPuzzle().indexOf('0'))), heuristicOne(node.getStringPuzzle(), goalState));
                else if (heuristic == Heuristic.H_TWO)
                    node.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(node.getStringPuzzle().charAt(currentNode.getStringPuzzle().indexOf('0'))), heuristicTwo(node.getStringPuzzle(), goalState));
            }
            pq.add(node);
            stateHistory.put(newState, oldState);
            numNodesCreated++;
        }

    }

    /* Each of the Methods below Takes the Current State of Board as String. Then the operation to move the blank space is done if possible.
      After that the new string is added to the map and queue.If it is the Goal State then the Program Terminates.
     */
    private static void up(String currentState){
        int a = currentState.indexOf("0");
        if(a>2){
            String nextState = currentState.substring(0,a-3)+"0"+currentState.substring(a-2,a)+currentState.charAt(a-3)+currentState.substring(a+1);
            checkCompletion(currentState, nextState);
        }
    }

    private static void down(String currentState){
        int a = currentState.indexOf("0");
        if(a<6){
            String nextState = currentState.substring(0,a)+currentState.substring(a+3,a+4)+currentState.substring(a+1,a+3)+"0"+currentState.substring(a+4);
            checkCompletion(currentState, nextState);
        }
    }
    private static void left(String currentState){
        int a = currentState.indexOf("0");
        if(a!=0 && a!=3 && a!=6){
            String nextState = currentState.substring(0,a-1)+"0"+currentState.charAt(a-1)+currentState.substring(a+1);
            checkCompletion(currentState, nextState);
        }
    }
    private static void right(String currentState){
        int a = currentState.indexOf("0");
        if(a!=2 && a!=5 && a!=8){
            String nextState = currentState.substring(0,a)+currentState.charAt(a+1)+"0"+currentState.substring(a+2);
            checkCompletion(currentState, nextState);
        }
    }

    private static void checkCompletion(String oldState, String newState) {
        if (oldState != null)
            add(newState, oldState, searchType);

        if (searchType.equals(SearchType.BD)) {
            if (direction.equals("front")) {
                if (stateHistoryReverse.containsKey(newState)) {
                    traceState = newState;
                    solutionFound = true;
                }
            } else if (direction.equals("back")) {
                if (stateHistory.containsKey(newState)) {
                    traceState = newState;
                    solutionFound = true;
                }
            }
        } else {
            if (newState.equals("123456780")) {
                traceState = newState;
                solvedPuzzle = traceState;
                solutionFound = true;
            }
        }
    }

    static void printHistory() {
        int col = 3;
        int counter = 0;

        if (searchType.equals(SearchType.BD)) {
            searchStart = traceState;
            while (traceState != null) {
                pathToSolution.add(traceState);
                traceState = stateHistoryReverse.get(traceState);
                counter++;
            }
            counter = 0;

            Collections.reverse(pathToSolution);

            traceState = stateHistory.get(searchStart);
            while (traceState != null) {
                pathToSolution.add(traceState);
                traceState = stateHistory.get(traceState);
                counter++;
            }
        } else {
            while (traceState != null) {
                pathToSolution.add(traceState);
                traceState = stateHistory.get(traceState);
                counter++;
            }
        }

        // Reverse list to print in correct order
        Collections.reverse(pathToSolution);
        numSolutionPath = pathToSolution.size();
        if (pathToSolution.size() > 20) {
            counter = pathToSolution.size() - 20;
        } else {
            counter = 0;
        }

        while (counter != pathToSolution.size()) {
            int index = 0;
            for (int i = 0; i < col; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.print(pathToSolution.get(counter).charAt(index) + " ");
                    index++;
                }
                System.out.println();
            }
            System.out.println();
            counter++;
        }
    }

    static void printSolvedPuzzle() {

        int index = 0;
        int col = 3;

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(goalState.charAt(index) + " ");
                index++;
            }
            System.out.println();
        }
        System.out.println();
    }

    static int getNumNodesCreated() {
        return numNodesCreated;
    }

    static int getNumNodesExpanded() {
        return numNodesExpanded;
    }

    static int getNumSolutionPath() {
        return numSolutionPath;
    }

    // This heuristic estimates the cost to the goal from current state by counting the number of misplaced tiles
    private static int heuristicOne(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            if (currentState.charAt(i) != goalSate.charAt(i))
                difference += 1;
        return difference;
    }

//    The heuristic I chose is almost similar to the manhattan distance heuristic. The main difference is that is has a
//    higher value. For example, when we estimate the cost by calculating the manhattan distance, we underestimate this
//    cost because we don’t count the number of movies the intermediate tiles should move in order to make space for the
//    source tile to move. In this circumstance we think if the manhattan distance is, for example, 4, then it means
//    there are 3 tiles in between that they should also move. So the estimate for my heuristic is:
//    (manhattan distance + manhattan distance - 1 = 2 * manhattan distance - 1)
    private static int heuristicTwo(String currentState, String goalSate) {
        int difference = 0;
        int manhattanDistance = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            for (int j = 0; j < goalSate.length(); j += 1)
                if (currentState.charAt(i) == goalSate.charAt(j))
                    manhattanDistance = (Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3);
        difference = difference + 2 * manhattanDistance - 1;
        return difference;
    }
}