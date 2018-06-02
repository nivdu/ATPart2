package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public synchronized void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        String clientCommand;
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ByteArrayOutputStream compressOP = new ByteArrayOutputStream();
            int[] rowcol = (int[])(fromClient.readObject());
            toClient.flush();

            MyMazeGenerator mg = new MyMazeGenerator();
            Maze this_maze = mg.generate(rowcol[0], rowcol[1]);
            MyCompressorOutputStream compress = new MyCompressorOutputStream(compressOP);
            compress.write(this_maze.toByteArray());
            toClient.writeObject(compressOP.toByteArray());
            toClient.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}