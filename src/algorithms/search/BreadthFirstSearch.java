package algorithms.search;

/**
 * class of the searching algorithm named Breadth First Search
 * which gets a problem and solve it.
 * it uses queue and two hash set that holds visited nodes, close nodes
 * and nodes that will be developed.
 */

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue<AState> queueOfStates;
    protected HashSet<AState> visitedNodes;
    protected HashSet<AState> closedNodes;

    /**
     * constructor of BreadthFirstSearch
     */
    public BreadthFirstSearch() {
        this.queueOfStates= new LinkedList<>();
        visitedNodes = new HashSet<>();
        closedNodes = new HashSet<>();
    }

    /**
     * function that solves the problem by the Breadth First Search algorithm
     * @param domain - the search's problem that will be solved
     * @return the Solution of the search problem
     */
    @Override
    public Solution solve(ISearchable domain) {
        if(domain==null)
            return null;
        AState start = domain.getStartState();
        if(start==null)
            return null;
        AState top;
        AState goalState = domain.getGoalState();
        if (goalState==null)
            return null;
        Solution thePathOfSolution;
        boolean found=false;
        visitedNodes.add(start);
        queueOfStates.add(start);
        while(queueOfStates.isEmpty() == false){
            top=queueOfStates.remove();
            List<AState> myNeighbors = domain.getAllPossibleStates(top);
            if(myNeighbors == null)
                return null;
            for(AState next:myNeighbors){
                if(!(visitedNodes.contains(next))){
                    visitedNodes.add(next);
                    next.setFatherState(top);
                    if(next == goalState)
                        found = true;
                    queueOfStates.add(next);
                }
            }
            closedNodes.add(top);
            if(found)
                break;
        }
        thePathOfSolution=new Solution(start,goalState);
        return thePathOfSolution;
    }

    /**
     * getter of class' name
     * @return the name of the class
     */
    @Override
    public String getName() {
        return BreadthFirstSearch.class.getSimpleName();
    }

    /**
     * getter of the evaluated nodes' number
     * @return the number of the evaluated nodes
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return closedNodes.size() + visitedNodes.size();
    }
}
