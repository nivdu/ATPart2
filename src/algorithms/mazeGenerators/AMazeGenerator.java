package algorithms.mazeGenerators;

/**
 * abstract class of maze's generator that implements function which count the milliseconds of creating a maze
 * and has an abstract function of generate a maze.
 */
public abstract class AMazeGenerator implements IMazeGenerator{

    /**
     * the function gets the number of rows and columns of a maze and return the time taken to create the maze
     * @param rows - number of maze's rows
     * @param columns- number of maze's columns
     * @return the time takes to create a maze in milliseconds
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long before = System.currentTimeMillis();
        generate(rows, columns);
        long after = System.currentTimeMillis();
        return after - before;
    }

    /**
     * this function gets the number of rows and columns of a maze, creates and returns it.
     * @param rows - number of maze's rows
     * @param columns - number of maze's columns
     * @return a maze with the given rows and columns.
     */
    @Override
    public abstract Maze generate(int rows, int columns);
}
