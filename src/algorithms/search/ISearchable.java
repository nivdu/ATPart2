package algorithms.search;

import java.util.List;

public interface ISearchable {

    /**
     * the function gets an AState and returns a list of all the legal AState's neighbors
     * @param s - the AState that we search it's legal neighbors
     * @return a list of all the legal neighbors
     */
    public List<AState> getAllPossibleStates(AState s);

    /**
     * the function returns the goal state of the problem
     * @return an AState of the problem's goal state
     */
    public AState getGoalState();

    /**
     * the function returns the start state of the problem
     * @return an AState of the problem's start state
     */
    public AState getStartState();
}