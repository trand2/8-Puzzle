import java.util.*;

class UninformedSearch {

    private static Queue<String> agenda = new LinkedList<>();    // Use of Queue Implemented using LinkedList for Storing All the Nodes in BFS.
    private static Map<String,Integer> stateDepth = new HashMap<>(); // HashMap is used to ignore repeated nodes
    private static Map<String,String> stateHistory = new HashMap<>(); // relates each position to its predecessor
    private static String traceState;
    private static int numNodesExpanded = 0;
    private static int numNodesCreated = 0;
    private static boolean solutionFound = false;
    private static int numSolutionPath = 0;
    private static String solvedPuzzle;
    private static List<String> pathToSolution = new ArrayList<>();

    static void breadthFirstSearch(){

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

    //Add method to add the new string to the Map and Queue
    static void add(String newState, String oldState){
        if(!stateDepth.containsKey(newState)){
            int newValue = oldState == null ? 0 : stateDepth.get(oldState) + 1;
            stateDepth.put(newState, newValue);
            agenda.add(newState);
            stateHistory.put(newState, oldState);
        }
        numNodesCreated++;
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
            add(newState, oldState);
        if(newState.equals("123456780")) {
            traceState = newState;
            solvedPuzzle = traceState;
            solutionFound = true;
        }
    }

    static void printHistory() {
        int col = 3;
        int counter = 0;

        while (traceState != null) {
            pathToSolution.add(traceState);
            traceState = stateHistory.get(traceState);
            counter++;
        }

        for(int i=0; i<pathToSolution.size()/2; i++) {
            String temp = pathToSolution.get(i);
            pathToSolution.set(i, pathToSolution.get(pathToSolution.size() - i - 1));
            pathToSolution.set(pathToSolution.size() - i - 1, temp);
        }

        counter = 0;

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
            numSolutionPath++;
            counter++;
        }
    }

    static void printSolvedPuzzle() {
        int index = 0;
        int col = 3;

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(solvedPuzzle.charAt(index) + " ");
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
}