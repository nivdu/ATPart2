package algorithms.mazeGenerators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * class of maze which holds the board of the maze,
 * the start point's position and the goal start's position.
 *
 */
public class Maze implements Serializable {
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
        copy_maze(maze);
        this.startPos = new Position(startP);
        this.finishPos = new Position(finishP);
    }

    private void copy_maze(int[][] maze_2copy){
        for (int i = 0; i < rows; i++)
            theMaze[i] = Arrays.copyOf(maze_2copy[i] ,maze_2copy[i].length);
    }

    public Maze(byte[] mazeBybyte){
        int last_touch_index = first_part_of_compression(mazeBybyte);
        if (rows <= 0 || columns <= 0 || !checkLegalPos(startPos) || !checkLegalPos(finishPos))
            return;
        theMaze = new int[rows][columns];
        init_maze_from_byteArr(mazeBybyte, last_touch_index);
    }

    private void init_maze_from_byteArr(byte[] mazeBybyte, int index){
        for (int i = 0; i < rows && index<mazeBybyte.length ; i++) {
            for (int j = 0; j < columns && index<mazeBybyte.length ; j++) {
                theMaze[i][j] = mazeBybyte[index];
                index++;
            }
        }
    }

    private int first_part_of_compression(byte[] b){
        int last_comma = 0;
        int location_in_b = 0;
        int i;
        int[] temp = new int[6];
        for (i = 0; i < b.length; i++) {
            if (location_in_b < 6) {
                if (b[i] == 0) {
                    if (b[i - 1] != 0) {
                        location_in_b = location_in_b + 1;
                        last_comma = i;
                    } else {
                        if (last_comma != i - 1) {
                            location_in_b = location_in_b + 1;
                            last_comma = i;
                        }
                    }
                }
                if (location_in_b < 6)
                    temp[location_in_b] += b[i];
            }
            else {
                rows = temp[0] & 0xFF;
                columns = temp[1] & 0xFF;
                startPos = new Position(temp[2]& 0xFF,temp[3]& 0xFF);
                finishPos = new Position(temp[4]& 0xFF,temp[5]& 0xFF);
                break;
            }
        }
        return i;
    }

    /**
     * The method check if a given cell is legal
     * Legal cell - if the cell is in the maze bounds and it not a wall cell
     * @param pos - the position to check.
     * @return true if the cell is legal (the cell is in the maze bounds and it not a wall cell). else false.
     */
    private boolean checkLegalPos(Position pos){
        return ( pos!=null && pos.getColumnIndex()>=0 && pos.getColumnIndex() < columns && pos.getRowIndex()>=0 && pos.getRowIndex()<rows);
    }

    /**
     * The method check if a given cell is legal
     * Legal cell - if the cell is in the maze bounds and it not a wall cell
     * @param row - the row index to check
     * @param column - the col index to check
     * @return true if the cell is legal (the cell is in the maze bounds and it not a wall cell). else false.
     */
    private boolean checkLegalPos(int row, int column){
        return ( column>=0 && column<columns && row>=0 && row<rows);
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

    /**
     * build byte that represent the maze - "0" byte used like a buffer
     * @return array of bytes represent of the maze
     */
    public byte[] toByteArray(){
        ArrayList<Byte> to_array_of_Bytes = new ArrayList<>();
        number_2byte(rows, to_array_of_Bytes);
        to_array_of_Bytes.add((byte)0);
        number_2byte(columns, to_array_of_Bytes);
        to_array_of_Bytes.add((byte)0);
        number_2byte(startPos.getRowIndex(), to_array_of_Bytes);
        to_array_of_Bytes.add((byte)0);
        number_2byte(startPos.getColumnIndex(), to_array_of_Bytes);
        to_array_of_Bytes.add((byte)0);
        number_2byte(finishPos.getRowIndex(), to_array_of_Bytes);
        to_array_of_Bytes.add((byte)0);
        number_2byte(finishPos.getColumnIndex(), to_array_of_Bytes);
        to_array_of_Bytes.add((byte)0);
        return arrayList_2array(to_array_of_Bytes);
    }

    private void number_2byte(int number, ArrayList<Byte> to_array_of_Bytes){
        int rows_2byte = number;
        if (number == 0)
            to_array_of_Bytes.add((byte) rows_2byte);
        else
            while (rows_2byte>0) {
                if (rows_2byte < 255) {
                    to_array_of_Bytes.add((byte) rows_2byte);
                    break;
                }
                else {
                    to_array_of_Bytes.add((byte) 255);
                    rows_2byte = rows_2byte - 255;
                }
            }//while
    }

    private byte[] arrayList_2array(ArrayList<Byte> to_array_of_Bytes){
        byte[] to_return = new byte[to_array_of_Bytes.size()+ rows*columns];
        int index=0;
        for (Byte b: to_array_of_Bytes){
            to_return[index] = b;
            index++;
        }
        return maze_toByte_array(index, to_return);
    }

    private byte[] maze_toByte_array(int index, byte[] to_return){
        for (int i=0 ; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                to_return[index] = (byte)(theMaze[i][j]);
                index++;
            }
        }
        return to_return;
    }
}