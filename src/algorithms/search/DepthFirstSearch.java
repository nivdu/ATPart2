package algorithms.search;

/**
 * class of the searching algorithm named Depth First Search
 * which gets a problem and solve it.
 * it uses stack and hash set that holds visited nodes
 * and nodes that will be developed.
 */
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm  {

    private Stack<AState> stackNodes;
    private HashSet<AState> visitedNodes;

    /**
     * constructor of BreadthFirstSearch
     */
    public DepthFirstSearch() {
        stackNodes= new Stack<>();
        visitedNodes = new HashSet<>();
    }


    /**
     * function that solves the problem by the Depth First Search algorithm
     * @param domain - the search's problem that will be solved
     * @return the Solution of the search problem
     */
    public Solution solve(ISearchable domain) {
        if(domain==null)
            return null;
        AState start = domain.getStartState();
        if(start==null)
            return null;
        AState top;
        AState goalState = domain.getGoalState();
        if(goalState==null)
            return null;
        Solution thePathOfSolution;
        stackNodes.push(start);
        while(!stackNodes.isEmpty()){
            top = stackNodes.pop();
            if(top == goalState){
                visitedNodes.add(top);
                while(!stackNodes.isEmpty()) {
                    top = stackNodes.pop();
                    visitedNodes.add(top);
                }
                break;
            }
            if(!visitedNodes.contains(top)){
                visitedNodes.add(top);
                List<AState> myNeighbors = domain.getAllPossibleStates(top);
                if(myNeighbors == null)
                    return null;
                for (AState a:myNeighbors) {
                    if(!(visitedNodes.contains(a))) {
                        stackNodes.push(a);
                        a.setFatherState(top);
                    }
                }
            }
        }
        thePathOfSolution=new Solution(start,goalState);
        return thePathOfSolution;
    }


    /**
     * getter of class' name
     * @return the name of the class
     */
    public String getName() {
        return DepthFirstSearch.class.getSimpleName();
    }

    /**
     * getter of the evaluated nodes' number
     * @return the number of the evaluated nodes
     */
    public int getNumberOfNodesEvaluated() {
        return visitedNodes.size();
    }
}
