package algorithms.mazeGenerators;

/**
 * a simple maze, converts walls by a value of 1, valid transitions by a value of 0,
 * and sets randomly a finish point and a starting point
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    private int rowsL, columnsL;
    private int[][] maze;

    /**
     * The method accepts line length and column length.
     * Constructs a simple maze, converts walls by a value of 1, valid transitions by a value of 0,
     * and sets a finish point and a starting point, and returns a Maze object
     * Maze minimum and default size is 5*5 if the row or columns given is less then 5 the maze will be 5*5.
     * @param rows    - The length of the rows in the maze
     * @param columns - The length of the columns in the maze
     * @return new Maze object.
     */
    @Override
    public Maze generate(int rows, int columns) {
        //minimal board is 5*5 (if the given row or columns is smaller the default board is 5*5)
        if (rows < 5 || columns < 5) {
            rows = 5;
            columns = 5;
        }
        maze = new int[rows][columns];
        rowsL = rows;
        columnsL = columns;
        // put 1 all over the array
        buildTheMaze();
        // init start position
        Position startP = findStartPosition();
        //build more paths in the maze from the startPosition
        buildMoreWrongPaths(startP.getColumnIndex());
        // init end position
        Position endP = findGaolPosition();

        return buildTheSimplePath(startP,endP);
    }


    /**
     * The method is the body of the simple maze building.
     * @param startP - the start Position of the maze.
     * @param endP - the end Position of the maze.
     * @return new Maze object
     */
    private Maze buildTheSimplePath(Position startP, Position endP) {
        boolean arrived = false;
        int i = 0, j = startP.getColumnIndex();
        while (!arrived) {
            //find what direction to move
            if (i < endP.getRowIndex())
                i++;
            else if (j < endP.getColumnIndex())
                j++;
            else if (j > endP.getColumnIndex())
                j--;
            else if (i > endP.getRowIndex())
                i--;
            //check if legal move
            if (i >= 0 && i < rowsL && j >= 0 && j < columnsL)
                maze[i][j] = 0;

            //check arrived condition
            if (i == endP.getRowIndex() && j == endP.getColumnIndex())
                arrived = true;

        }
        return new Maze(maze, startP, endP);
    }

    /**
     * The method initializes all the maze cells with a value of "1" - value 1 represent a wall cell.
     */
    private void buildTheMaze(){
        //build walls all over the maze array
        for (int i = 0; i < rowsL; i++) {
            for (int j = 0; j < columnsL; j++) {
                maze[i][j]=1;
            }
        }
    }

    /**
     * the method contain 2 for loop to build more wrong options to moves in the maze
     * @param startColSaver - the index of the start position column
     */
    private void buildMoreWrongPaths(int startColSaver) {
        for (int k = 0; k < columnsL; k++)
            maze[0][k] = 0;
        for (int k = 0; k < rowsL; k++)
            maze[k][startColSaver] = 0;
    }

    /**
     * the method find a Start Position for the maze in a random way.
     * @return the position of the new start position.
     */
    private Position findStartPosition() {
        int startColSaver = (int) (Math.random() * columnsL);
        maze[0][startColSaver] = 0;
        return new Position(0, startColSaver);
    }

    /**
     * the method find a goal Position for the maze in a random way.
     * @return - the position of the new goal position.
     */
    private Position findGaolPosition() {
        int goalRowSaver = (int) (Math.random() * rowsL);
        if (goalRowSaver <= 1)
            goalRowSaver = 2;
        maze[goalRowSaver][columnsL - 1] = 0;
        return new Position(goalRowSaver, columnsL - 1);
    }
}