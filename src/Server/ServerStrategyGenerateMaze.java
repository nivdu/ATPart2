package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        String clientCommand;
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            int[] rowcol = (int[])(fromClient.readObject());
            toClient.flush();
            Thread.sleep(5000);
            MyMazeGenerator mg = new MyMazeGenerator();
            Maze this_maze = mg.generate(rowcol[0], rowcol[1]);
            MyCompressorOutputStream compress = new MyCompressorOutputStream(toClient);
            compress.write(this_maze.toByteArray());
            } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}