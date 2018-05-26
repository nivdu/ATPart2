package algorithms.mazeGenerators;

/**
 * class of position that holds the value of the row and the column of the position.
 */
public class Position {
    private int row,col;

    /**
     * Constructor
     * @param row - the index of the row in the maze for the new Position.
     * @param col - the index of the column in the maze for the new Position.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Copy Constructor for Positions
     * @param copyMe - Position to copy
     */
    public Position (Position copyMe){
        //to-do check if legal
        row=copyMe.getRowIndex();
        col=copyMe.getColumnIndex();
    }

    /**
     * @return A epresentation of position by string
     */
    @Override
    public String toString() {
        return "{"+row+","+col+"}";
    }

    /**
     * Getter for the row index in the maze of a Position
     * @return the row index of the position.
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * Getter for the column index in the maze of a Position
     * @return the column index of the position.
     */
    public int getColumnIndex() {
        return col;
    }

    /**
     * Setter for the row of the position.
     * @param row - the row index to set to a position
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Setter for the column of the position.
     * @param col- the column index to set to a position
     */
    public void setCol(int col) {
        this.col = col;
    }
}