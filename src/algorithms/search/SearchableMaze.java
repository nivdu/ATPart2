package algorithms.search;

/**
 * an adapter of the maze and a searching algorithm which has functions of returning the list of the legal neighbors
 * and returning the start position and goal position.
 */

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;
import java.util.List;

public class SearchableMaze implements ISearchable {
    private Maze theMaze;
    private int rowsL,colL;
    private AState[][] allStateSpace;

    /**
     * constructor of the adapter named searchableMaze
     * @param m - the maze that the adapter gets
     */
    public SearchableMaze(Maze m){
        this.theMaze=m;
        this.rowsL=m.getRows();
        this.colL=m.getColumns();
        allStateSpace = new AState[rowsL][colL];
        stateSpace();
    }

    /**
     * a private function returns the AState of the given position
     * @param row - the row of the position
     * @param col - the column of the position
     * @return the AState of the given position
     */
    private AState fromRowColToAState(int row,int col){
        return allStateSpace[row][col];
    }

    /**
     * this function gets a string of AState and returns the integer number of the AState's row
     * @param rowStr - string of the AState
     * @return - the integer number of the AState's row
     */
    private int getRowFromState(String rowStr){
        if (rowStr==null ||  rowStr.length()<5)
            return -1; //i think should be fail {1,1} => min 5
        String ans = "";
        int i=1;
        //insert the row string to ans.
        while (rowStr.charAt(i)!=',') {
            ans = ans + rowStr.charAt(i);
            i++;
        }
        return Integer.parseInt(ans);
    }


    /**
     * this function gets a string of AState and returns the integer number of the AState's column
     * @param colStr - string of the AState
     * @return - the integer number of the AState's column
     */
    private int getColFromState(String colStr){
        if (colStr == null || colStr.length()<5)
            return -1;
        String ans = "";
        int i=0;
        while(colStr.charAt(i)!=',')
            i++;
        //jump over the ','
        i++;
        //insert the col string to ans.
        while (colStr.charAt(i)!='}') {
            ans = ans + colStr.charAt(i);
            i++;
        }
        return Integer.parseInt(ans);
    }

    /**
     * private function that gets two parameters that represent a position in the maze and insert all the
     * legal neighbors of the given position to a list and returns it.
     * @param i - the row of the position
     * @param j - the column of the position
     * @return List of all the legal neighbors of the given state's position
     */
    private List<AState> findLegalNeighbor(int i, int j) {
        if (theMaze==null)
            return null;
        List<AState> successorStates = new ArrayList<>();
        //check all the option neighbor clockwise.
        if (j < colL - 1 && theMaze.getVal(i,j+1) == 0){
            allStateSpace[i][j+1].setCost(allStateSpace[i][j+1].getCost()+10);
            successorStates.add(fromRowColToAState(i,j+1));
        }
        if(j<colL && i<rowsL && theMaze.getVal(i+1,j+1) == 0) {
            allStateSpace[i+1][j+1].setCost(allStateSpace[i+1][j+1].getCost()+15);
            successorStates.add(fromRowColToAState(i + 1, j + 1));
        }
        if (i < rowsL - 1 && theMaze.getVal(i+1,j) == 0){
            allStateSpace[i+1][j].setCost(allStateSpace[i+1][j].getCost()+10);
            successorStates.add(fromRowColToAState(i+1,j));
        }
        if(j>0 && i<rowsL && theMaze.getVal(i+1,j-1) == 0) {
            allStateSpace[i+1][j-1].setCost(allStateSpace[i+1][j-1].getCost()+15);
            successorStates.add(fromRowColToAState(i + 1, j - 1));
        }
        if (j > 0 && theMaze.getVal(i,j-1) == 0){
            allStateSpace[i][j-1].setCost(allStateSpace[i][j-1].getCost()+10);
            successorStates.add(fromRowColToAState(i,j-1));
        }
        if(j<colL && i>0 && theMaze.getVal(i-1,j+1) == 0) {
            allStateSpace[i-1][j+1].setCost(allStateSpace[i-1][j+1].getCost()+15);
            successorStates.add(fromRowColToAState(i - 1, j + 1));
        }
        if (i > 0 && theMaze.getVal(i-1,j) == 0){
            allStateSpace[i-1][j].setCost(allStateSpace[i-1][j].getCost()+10);
            successorStates.add(fromRowColToAState(i-1,j));
        }
        if(j>0 && i>0 && theMaze.getVal(i-1,j-1) == 0) {
            allStateSpace[i-1][j-1].setCost(allStateSpace[i-1][j-1].getCost()+15);
            successorStates.add(fromRowColToAState(i - 1, j - 1));
        }

        return successorStates;
    }


    /**
     * the function gets an AState and return a list of all the legal AState's neighbors
     * @param s - the AState that we search it's legal neighbors
     * @return a list of all the legal neighbors
     */
    @Override
    public List<AState> getAllPossibleStates(AState s) {
        int row,col;
        if(s==null || theMaze==null)
            return null;
        row = getRowFromState(s.getStringState());
        col = getColFromState(s.getStringState());
        //create the neighbor list.
        return findLegalNeighbor(row,col);
    }

    /**
     * getter function of the goal state
     * @return the AState of the goal state
     */
    @Override
    public AState getGoalState() {
        if(theMaze == null)
            return null;
        int row = theMaze.getGoalPosition().getRowIndex();
        int col = theMaze.getGoalPosition().getColumnIndex();
        return allStateSpace[row][col];
    }


    /**
     * getter of the start state
     * @return the AState of the start state
     */
    @Override
    public AState getStartState() {
        if (theMaze==null)
            return null;
        int row = theMaze.getStartPosition().getRowIndex();
        int col = theMaze.getStartPosition().getColumnIndex();
        return allStateSpace[row][col];
    }

    /**
     * the function returns a list of all the legal states of the maze
     * @return a list of all the legal states of the maze
     */
    private List<AState> stateSpace() {
        if(theMaze==null)
            return null;
        List<AState> toReturn = new ArrayList<>();
        for (int i = 0; i < rowsL; i++) {
            for (int j = 0; j < colL; j++) {
                if(theMaze.getVal(i,j)==0) {
                    allStateSpace[i][j] = new MazeState("{" + i + "," + j + "}");
                    toReturn.add(allStateSpace[i][j]);
                }
            }
        }
        return toReturn;
    }
}