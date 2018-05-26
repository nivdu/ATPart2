package algorithms.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * class of the searching algorithm named Best First Search, which similar to the BFS Algorithm,
 * gets a problem and solve it.
 * it uses queue and two hash set that holds visited nodes, close nodes and nodes that
 * going to be developed.
 */
public class BestFirstSearch extends BreadthFirstSearch {

    /**
     * constructor of BestFirstSearch
     */
    public BestFirstSearch () {
        this.queueOfStates= new PriorityQueue<AState>(Comparator.comparing(AState::getCost));
        visitedNodes = new HashSet<>();
        closedNodes = new HashSet<>();
    }

    /**
     * getter of class' name
     * @return the name of the class
     */
    @Override
    public String getName() {
        return BestFirstSearch.class.getSimpleName();
    }
}
