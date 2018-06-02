package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.DepthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        DepthFirstSearch dfs = new DepthFirstSearch();
        SearchableMaze domain;

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Thread.sleep(5000);
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
                    if( child.getName() == String.valueOf(this_hashCode)){
                        //check todo
                        FileInputStream f = new FileInputStream(child);
                        ObjectInputStream o = new ObjectInputStream(f);
                        found_match = true;
                        solution = (Solution) o.readObject();
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
                    o.writeObject(this_maze);
                    o.close();
                    f.close();
                }
            } else {//todo else maby do try and catch
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }
            toClient.writeObject(solution);//todo maby check if solution is null
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
