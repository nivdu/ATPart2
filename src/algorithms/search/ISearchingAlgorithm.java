package algorithms.search;

/**
 * interface of searching algorithm which has three functions -
 * solving a problem, getting a name of the class
 * and getting the number of evaluated nodes
 */
public interface ISearchingAlgorithm {
    /**
     * function that solves the problem by a Search algorithm
     * @param domain - the search's problem that will be solved
     * @return the Solution of the search problem
     */
    public Solution solve(ISearchable domain);

    /**
     * getter of class' name
     * @return the name of the class
     */
    public String getName();

    /**
     * getter of the evaluated nodes' number
     * @return the number of the evaluated nodes
     */
    public int getNumberOfNodesEvaluated();
}
