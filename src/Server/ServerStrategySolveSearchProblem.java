package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.DepthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public synchronized void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        DepthFirstSearch dfs = new DepthFirstSearch();
        SearchableMaze domain;

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            Maze this_maze = (Maze) fromClient.readObject();
            int this_hashCode = Arrays.hashCode(this_maze.toByteArray());
            // check whether the hashcode's maze exists in the file
            domain = new SearchableMaze(this_maze);
            File dir = new File(tempDirectoryPath);
            File[] directoryListing = dir.listFiles();
            Solution solution = null;
            if (directoryListing != null) {
                Boolean found_match= false;
                for (File child : directoryListing) {
                    if(child.getName().equals(String.valueOf(this_hashCode)+"tmp")){//todo equals or '==' compare the Strings
                        //check todo
                        FileInputStream f = new FileInputStream(child);
                        ObjectInputStream o = new ObjectInputStream(f);
                        found_match = true;
                        solution = (Solution) o.readObject();
                        if (!(solution.getStart().getStringState().equals(this_maze.getStartPosition().toString())&&solution.getEnd().getStringState().equals(this_maze.getGoalPosition().toString()))){
                            found_match = false;
                        }
                        o.close();
                        f.close();
                        break;
                    }
                }
                if(!found_match){
                    solution = dfs.solve(domain);
                    //add new file with the hashcode name and the content of the solution
                    FileOutputStream f = new FileOutputStream(new File(tempDirectoryPath + "\\" + this_hashCode));
                    ObjectOutputStream o = new ObjectOutputStream(f);
                    // Write objects to file
                    o.writeObject(solution);// todo was maze changed to solution.
                    o.close();
                    f.close();
                }
            }
//            else {//todo else maybe do try and catch
//                // Handle the case where dir is not really a directory.
//                // Checking dir.isDirectory() above would not be sufficient
//                // to avoid race conditions with another process that deletes
//                // directories.
//
//            }
            toClient.writeObject(solution);//todo maybe check if solution is null
            toClient.flush();
        } catch (IOException|ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
