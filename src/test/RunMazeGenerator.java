package test;
import algorithms.mazeGenerators.*;
public class RunMazeGenerator {
    public static void main(String[] args) {
        testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }
    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
// prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s",
                mazeGenerator.measureAlgorithmTimeMillis(10/*rows*/,50/*columns*/)));

// generate another maze
        Maze maze = mazeGenerator.generate(50/*rows*/, 10/*columns*/);
// prints the maze
        maze.print();
        //check maze class 2 methods: toByteArray and constructor maze from byte array
        System.out.println("```````````````````````````````````````````");
        maze.toByteArray();
        Maze check_byteconstructor = new Maze(maze.toByteArray());
        check_byteconstructor.print();
        for (int i = 0; i <maze.getRows() ; i++) {
            for (int j = 0; j <maze.getColumns() ; j++) {
                if(maze.getVal(i,j)!=check_byteconstructor.getVal(i,j))
                    System.out.println("NO!!");
            }
        }
        System.out.println("good");
        //finish check toByteArray and constructor maze from byte array

// get the maze entrance
        Position startPosition = maze.getStartPosition();
// print the position
        System.out.println(String.format("Start Position: %s",
                startPosition)); // format "{row,column}"
// prints the maze exit position
        System.out.println(String.format("Goal Position: %s",
                maze.getGoalPosition()));

    }
}