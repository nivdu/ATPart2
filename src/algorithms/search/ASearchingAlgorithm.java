package algorithms.search;

/**
 * an abstract class of searching algorithm which solve a given problem
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    /**
     * function that solves the problem by a Search algorithm
     * @param domain - the search's problem that will be solved
     * @return the Solution of the search problem
     */
    public abstract Solution solve(ISearchable domain);
}
