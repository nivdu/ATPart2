package algorithms.mazeGenerators;
/**
 * the class creates a maze based on the prim algorithm,
 * converts walls by a value of 1,
 * valid transitions by a value of 0,
 * and sets a finish point and a starting point.
 */

import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator {
    private int rowsL,columnsL;
    private int[][] maze;
    private ArrayList<Position> arrayofwalls;

    /**
     * The method accepts line length and column length.
     * Constructs a maze based on the prim algorithm, converts walls by a value of 1, valid transitions by a value of 0,
     * and sets a finish point and a starting point, and returns a Maze object
     * Maze minimum and default size is 5*5 if the row or columns given is less then 5 the maze will be 5*5.
     * @param rows - The length of the rows in the maze
     * @param columns - The length of the columns in the maze
     * @return new Maze.
     */
    @Override
    public Maze generate(int rows, int columns) {
        //if small maze board case, the default maze will be 5*5.
        if(rows<5 || columns<5) {
            rows = 5;
            columns = 5;
        }
        this.rowsL = rows;
        this.columnsL = columns;
        this.maze =new int[rowsL][columnsL];
        this.arrayofwalls = new ArrayList<>();
        //init the maze with 2
        buildTheMaze();
        //init the start cell of the maze.
        int startProw = (int)(Math.random()*rows);
        int startPcol = (int)(Math.random()*columns);
        maze[startProw][startPcol] = 0;

        //build the maze path (represented by cells with value 0)
        buildThePathsByPrim(startProw,startPcol);
        Position GoalP = createGoalPos(startProw,startPcol);

        return new Maze(maze,new Position(startProw,startPcol), GoalP);
    }


    /**
     * find the given cell legal neighbors (cell that didn't yet visited and are walls) and add them to the linked array of walls.
     * @param i - the row index of the given cell.
     * @param j - the column index of the given cell.
     */
    private void findNeighborWalls(int i, int j) {
        if (i < rowsL - 1 && maze[i + 1][j] == 2)
            arrayofwalls.add(new Position(i + 1, j));
        if (i > 0 && maze[i - 1][j] == 2)
            arrayofwalls.add(new Position(i - 1, j));
        if (j < columnsL - 1 && maze[i][j + 1] == 2)
            arrayofwalls.add(new Position(i, j + 1));
        if (j > 0 && maze[i][j - 1] == 2)
            arrayofwalls.add(new Position(i, j - 1));
    }

    /**
     * The method returns the position of a cell whose value is "2" and not a morning
     * @return The position of a cell whose value "2" (a cell that is not already morning) is randomized from the arraylist.
     */
    private Position RandomWall(){
        int toTake = (int)(Math.random()*arrayofwalls.size());
        Position toCheck = arrayofwalls.get(toTake);
        arrayofwalls.remove(toTake);
        return toCheck;
    }

    //check if the wall need to chane to 1 or 0
    // 1 represent visited wall cell.
    //0 represent a path.

    /**
     * The method accepts a position of a cell. If the cell is between two cells (either vertically or horizontally but not diagonally), one of them is 0 and the second is 2.
     * The method will change the value of the cell received and the cell whose value was 2 to 0. And return the cell whose value was 2.
     * If the cell in the postion that we received is not between 2 cells that meet the condition, we change the value to "1" and return null.
     *  1 represent visited wall cell.
     *  0 represent a path.
     * @param checkMe - the Position of the cell to check.
     * @return position according to the explanation in the class description.
     * Or null or no match found
     */
    private Position changeGivenWall(Position checkMe) {
        int i = checkMe.getRowIndex();
        int j = checkMe.getColumnIndex();
        if (i < rowsL - 1 && maze[i + 1][j] == 0) {
            if (i > 0 && maze[i - 1][j] == 2) {
                maze[i - 1][j] = 0;
                maze[i][j] = 0;
                return new Position(i-1,j);
            }
        }
        if (i < rowsL - 1 && maze[i + 1][j] == 2) {
            if (i > 0 && maze[i - 1][j] == 0) {
                maze[i + 1][j] = 0;
                maze[i][j] = 0;
                return new Position(i+1,j);
            }
        }
        if (j < columnsL - 1 && maze[i][j + 1] == 0) {
            if (j > 0 && maze[i][j - 1] == 2) {
                maze[i][j - 1] = 0;
                maze[i][j] = 0;
                return new Position(i,j-1);
            }
        }
        if (j < columnsL - 1 && maze[i][j + 1] == 2) {
            if (j > 0 && maze[i][j - 1] == 0) {
                maze[i][j + 1] = 0;
                maze[i][j] = 0;
                return new Position(i,j+1);
            }
        }
        maze[i][j]=1;
        return null;
    }

    /**
     * The method initializes all the maze cells with a value of "2" - value 2 indicates a cell that is not yet morning
     */
    private void buildTheMaze(){
        //build walls all over the maze array
        for (int i = 0; i < rowsL; i++) {
            for (int j = 0; j < columnsL; j++) {
                maze[i][j]=2;
            }
        }
    }


    /**
     * If at the end of the maze construction there are cells of value 2, the method will change their value to 1.
     * They will form walls in the maze
     */
    private void changeUnvisitedCell2Walls(){
        for (int i = 0; i <rowsL ; i++) {
            for (int j = 0; j <columnsL ; j++) {
                if(maze[i][j]==2)
                    maze[i][j]=1;
            }
        }
    }

    /**
     * The method passes through the maze cells from the end to the beginning in two loops.
     * When the function reaches a cell whose value is 0 and the cell is not the start position of the maze
     * it defines it as the end point of the maze
     * @param SrowP - the row of the start Position.
     * @param ScolP - the column of the start Position.
     * @return Returns the position of the end of the defined maze.
     * If no cell is found to be a goal position return null-(Should never happen from an application we have defined a minimum 5 * 5 maze size).
     */
    private Position createGoalPos(int SrowP, int ScolP){
        for (int i = rowsL-1; i > 0; i--) {
            for (int j = columnsL-1; j > 0 ; j--) {
                if(maze[i][j]==0 && !(i==SrowP && j==ScolP))
                    return new Position(i,j);
            }
        }
        return null;
    }

    /**
     * The method is the body of the maze building run according to the prim algorithm
     * @param startPr - the row of the start position in the maze
     * @param startPc - the column of the start position in the maze
     */
    private void buildThePathsByPrim(int startPr, int startPc){
        //find the neighbors of the start Position
        findNeighborWalls(startPr, startPc);
        //build the maze path until there are no more unvisited walls. (cell value of "2").
        while (arrayofwalls.size()>0) {
            //Position of the wall to check
            Position posOfWall = RandomWall();
            Position posOfNewPathCell = changeGivenWall(posOfWall);
            if (posOfNewPathCell != null) {
                findNeighborWalls(posOfNewPathCell.getRowIndex(), posOfNewPathCell.getColumnIndex());
            }
        }
        changeUnvisitedCell2Walls();
    }
}