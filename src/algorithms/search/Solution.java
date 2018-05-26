package algorithms.search;
/**
 * solution of a problem which holds the start state and the end state of the problem
 * and creates the path of the described solution.
 */

import java.util.ArrayList;

public class Solution {
    ArrayList<AState> SolutionPath;
    AState start;
    AState end;

    /**
     * constructor of problem's solution that gets the start state and the goal state
     * @param iStart - the start state
     * @param iEnd - the goal state
     */
    public Solution(AState iStart,AState iEnd){
        SolutionPath = new ArrayList<>();
        start=iStart;//to-do deep?
        end=iEnd;//to-do deep?
    }

    /**
     * the function returns the path of the problem's solution from the start state to the goal state
     * @return array list of the path of the problem's solution from the start state to the goal state
     */
    public ArrayList<AState> getSolutionPath(){
        buildSolutionPath();
        return SolutionPath;
    }

    /**
     * a private function that builds the solution's path from the start state to the goal state
     */
    private void buildSolutionPath(){
        if( start == null || end == null)
            return;
        AState temp = end;
        while(temp.getFatherState() != start){
            SolutionPath.add(0,temp);
            temp=temp.getFatherState();
        }
        SolutionPath.add(0,temp);
        SolutionPath.add(0,start);
    }

}
