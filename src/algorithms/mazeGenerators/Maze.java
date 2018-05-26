package algorithms.mazeGenerators;

/**
 * class of maze which holds the board of the maze,
 * the start point's position and the goal start's position.
 *
 */
public class Maze {
    private int[][] theMaze;
    private int rows,columns;
    private Position startPos,finishPos;

    /**
     * Constructor
     * @param maze - Two-dimensional int array to represent the maze.
     * @param startP - The Position of the start cell of the maze.
     * @param finishP - The Position of the finish cell of the maze.
     */
    public Maze(int[][] maze, Position startP, Position finishP) {
        if(maze==null || startP==null || finishP==null)
            return;
        this.rows = maze.length;
        this.columns = maze[0].length;
        if (maze.length <= 0 || maze[0].length <= 0 || !checkLegalPos(startP) || !checkLegalPos(finishP))
            return;
        theMaze = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns ; j++) {
                theMaze[i][j]=maze[i][j];
            }
        }
        this.startPos = new Position(startP);
        this.finishPos = new Position(finishP);
    }

    /**
     * The method check if a given cell is legal
     * Legal cell - if the cell is in the maze bounds and it not a wall cell
     * @param pos - the position to check.
     * @return true if the cell is legal (the cell is in the maze bounds and it not a wall cell). else false.
     */
    private boolean checkLegalPos(Position pos){
        if ( pos!=null && pos.getColumnIndex()>=0 && pos.getColumnIndex()<= columns && pos.getRowIndex()>=0 && pos.getRowIndex()<=rows)
            return true;
        return false;
    }

    /**
     * The method check if a given cell is legal
     * Legal cell - if the cell is in the maze bounds and it not a wall cell
     * @param row - the row index to check
     * @param column - the col index to check
     * @return true if the cell is legal (the cell is in the maze bounds and it not a wall cell). else false.
     */
    private boolean checkLegalPos(int row, int column){
        if ( column>=0 && column<columns && row>=0 && row<rows)
            return true;
        return false;
    }

    /**
     * Setter for the start Position of the Maze.
     * @param startRow - the row index
     * @param startCol - the column index
     */
    public void setStartPos(int startRow, int startCol) {
        if (checkLegalPos(startRow,startCol)) {
            this.startPos.setRow(startRow);
            this.startPos.setCol(startCol);
        }
    }

    /**
     * Setter for the Finish Position of the Maze.
     * @param finishRow - the row index
     * @param finishCol - the column index
     */
    public void setFinishPos(int finishRow, int finishCol) {
        if (checkLegalPos(finishRow,finishCol)){
            this.finishPos.setRow(finishRow);
            this.finishPos.setCol(finishCol);
        }
    }

    /**
     * Getter for the start position of the current maze.
     * @return the position of the starting point of the maze.
     */
    public Position getStartPosition() {
        return startPos;
    }

    /**
     * Getter for the Goal position of the current maze.
     * @return the position of the Goal point of the maze.
     */
    public Position getGoalPosition() {
        return finishPos;
    }

    /**
     * The method prints the Maze (Mark the start cell with "S" and the Goal cell with "E").
     */
    public void print (){
        if(theMaze == null)
            return;
        for (int i = 0; i < rows; i++) {
            System.out.print("{");
            for (int j = 0; j < columns; j++) {
                if(i==finishPos.getRowIndex() && j==finishPos.getColumnIndex())
                    System.out.print("E");
                else if(i==startPos.getRowIndex() && j==startPos.getColumnIndex())
                    System.out.print("S");
                else  System.out.print(theMaze[i][j]);
                if(j<columns-1)
                    System.out.print(",");
                else System.out.println("},");
            }
        }
    }

    /**
     * Getter to a wanted cell in the maze
     * @param row - the row index to check
     * @param col - the column index to check
     * @return the value of the cell in the given row and column. if the coordinates not legal returns -1.
     */
    public int getVal(int row, int col){
        if(checkLegalPos(row,col))
            return theMaze[row][col];
        else return -1;
    }

    /**
     * Getter for the rows length.
     * @return the rows length of the maze.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter for the column length
     * @return the column length of the maze.
     */
    public int getColumns() {
        return columns;
    }
}