package algorithms.mazeGenerators;

/**
 * interface of maze's generator which has two functions - creating a maze and counting the time taken to create it.
 */
public interface IMazeGenerator {

    /**
     * this function gets the number of rows and columns of a maze, creates and returns it.
     * @param rows - number of maze's rows
     * @param columns - number of maze's columns
     * @return a maze with the given rows and columns.
     */
    public Maze generate(int rows, int columns);

    /**
     * the function gets the number of rows and columns of a maze and return the time taken to create the maze
     * @param rows - number of maze's rows
     * @param columns- number of maze's columns
     * @return the time takes to create a maze in milliseconds
     */
    public long measureAlgorithmTimeMillis(int rows, int columns);

}

