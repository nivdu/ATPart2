package test;
import Client.Client;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import Client.IClientStrategy;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import sun.awt.Mutex;
import java.time.Clock;

/*
public class RunCommunicateWithServers {
    static Mutex m;
    static Maze clali;
    static int countThreads;
    public static void main(String[] args) throws InterruptedException {
        clali = null;
//Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        m = new Mutex();
        countThreads = 0;
//Server stringReverserServer = new Server(5402, 1000, newServerStrategyStringReverser());
//Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
//stringReverserServer.start();
//Communicating with servers
        int[][] imaze = {{1,1,1,1,1,1,1,1,1,1},
        {1,0,1,0,1,0,1,0,0,0},
        {1,0,1,0,1,0,1,1,1,0},
        {1,0,0,0,1,0,1,0,1,0},
        {1,1,1,0,1,0,1,0,1,0},
        {1,0,0,0,0,0,1,0,0,0},
        {1,0,1,1,1,1,1,0,1,0},
        {1,0,0,0,1,0,0,0,1,0},
        {1,0,1,1,1,1,1,0,1,1},
        {1,0,0,0,0,0,0,0,0,0}};
        Position start=new Position(7,3);
        Position end=new Position(9,9);

        clali = new Maze(imaze,start,end);

        Thread[] t = new Thread[50];
        long startTime = System.nanoTime();
        for (int i = 0; i < 50 ; i++) {
            t[i] = new Thread(()-> CommunicateWithServer_SolveSearchProblem());
            t[i].start();
        }
        for (int i = 0; i < 50 ; i++) {
            t[i].join();
        }
        long endTime = System.nanoTime();
        System.out.println(endTime-startTime + "  time ");
//        Thread[] t = new Thread[1];
//        for (int i = 0; i < 1 ; i++) {
//            t[i] = new Thread(()-> CommunicateWithServer_SolveSearchProblem());
//            t[i].start();
//        }
//        for (int i = 0; i < 1 ; i++) {
//            t[i].join();
//        }

        //CommunicateWithServer_MazeGenerating();

//CommunicateWithServer_StringReverser();
//Stopping all servers
        mazeGeneratingServer.stop();
//        solveSearchProblemServer.stop();
//stringReverserServer.stop();
    }

    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                int[] mazeDimensions = new int[]{50, 50};
                                toServer.writeObject(mazeDimensions); //send mazedimensions to server
                                toServer.flush();
                                byte[] compressedMaze = (byte[])fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
                                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                                byte[] decompressedMaze = new byte[100 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE]; //allocating byte[] for the decompressedmaze -
                                        is.read(decompressedMaze); //Fill decompressedMazewith bytes
                                Maze maze = new Maze(decompressedMaze);
                                m.lock();
                                System.out.println("Thread number: -" + countThreads + "-");
                                countThreads++;
                                maze.print();
                                System.out.println();
                                m.unlock();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new
                                        ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new
                                        ObjectInputStream(inFromServer);
                                toServer.flush();
                                MyMazeGenerator mg = new MyMazeGenerator();
                                Maze maze;
//                                if (clali != null)
                                    maze = new Maze(clali.toByteArray());
//                                else{ clali = mg.generate(10, 10);
//                                    maze = new Maze(clali.toByteArray());}
                                m.lock();
                                System.out.println("Thread number: -" + countThreads + "-");
                                countThreads++;
                                maze.print();
                                System.out.println();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution)
                                        fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
//Print Maze Solution retrieved from the server
                                System.out.println(String.format("Solution steps: %s", mazeSolution));
                                        ArrayList<AState> mazeSolutionSteps =
                                                mazeSolution.getSolutionPath();
                                for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                                    System.out.println(String.format("%s. %s", i,
                                            mazeSolutionSteps.get(i).toString()));
                                }
                                m.unlock();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                BufferedReader fromServer = new BufferedReader(new
                                        InputStreamReader(inFromServer));
                                PrintWriter toServer = new PrintWriter(outToServer);
                                String message = "Client Message";
                                String serverResponse;
                                toServer.write(message + "\n");
                                toServer.flush();
                                serverResponse = fromServer.readLine();
                                System.out.println(String.format("Server response: %s", serverResponse));
                                        toServer.flush();
                                fromServer.close();
                                toServer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
*/

public class RunCommunicateWithServers {
    public static void main(String[] args) {
//Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new
                ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new
                ServerStrategySolveSearchProblem());
//Server stringReverserServer = new Server(5402, 1000, newServerStrategyStringReverser());
//Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
//stringReverserServer.start();
//Communicating with servers
        CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();
//CommunicateWithServer_StringReverser();
//Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
//stringReverserServer.stop();
    }
    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new
                                        ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new
                                        ObjectInputStream(inFromServer);
                                toServer.flush();
                                int[] mazeDimensions = new int[]{50, 50};
                                toServer.writeObject(mazeDimensions); //send mazedimensions to server
                                toServer.flush();
                                byte[] compressedMaze = (byte[])
                                        fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
                                InputStream is = new MyDecompressorInputStream(new
                                        ByteArrayInputStream(compressedMaze));
                                byte[] decompressedMaze = new byte[1000 /*CHANGE
SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressedmaze -
                                        is.read(decompressedMaze); //Fill decompressedMazewith bytes
                                Maze maze = new Maze(decompressedMaze);
                                maze.print();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new
                                        ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new
                                        ObjectInputStream(inFromServer);
                                toServer.flush();
                                MyMazeGenerator mg = new MyMazeGenerator();
                                Maze maze = mg.generate(50, 50);
                                maze.print();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution)
                                        fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
//Print Maze Solution retrieved from the server
                                System.out.println(String.format("Solution steps: %s", mazeSolution));
                                        ArrayList<AState> mazeSolutionSteps =
                                                mazeSolution.getSolutionPath();
                                for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                                    System.out.println(String.format("%s. %s", i,
                                            mazeSolutionSteps.get(i).toString()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                BufferedReader fromServer = new BufferedReader(new
                                        InputStreamReader(inFromServer));
                                PrintWriter toServer = new PrintWriter(outToServer);
                                String message = "Client Message";
                                String serverResponse;
                                toServer.write(message + "\n");
                                toServer.flush();
                                serverResponse = fromServer.readLine();
                                System.out.println(String.format("Server response: %s", serverResponse));
                                        toServer.flush();
                                fromServer.close();
                                toServer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}