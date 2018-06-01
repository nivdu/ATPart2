package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.DepthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    String tempDirectoryPath = System.getProperty("java.io.tmpdir");

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        DepthFirstSearch dfs = new DepthFirstSearch();
        SearchableMaze domain;

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Thread.sleep(5000);
            toClient.flush();
            Maze maze = (Maze) fromClient.readObject();
            domain = new SearchableMaze(maze);
            Solution solution = dfs.solve(domain);
            toClient.writeObject(solution);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
