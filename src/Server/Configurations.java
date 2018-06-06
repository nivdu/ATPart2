package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Configurations {
    public static void main(String[] args) {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("Resources/config.properties");

            // set the properties value
            prop.setProperty("SearchingAlgorithm", "DepthFirstSearch");
            prop.setProperty("NumberOfThreads", "" + Runtime.getRuntime().availableProcessors() * 2);
            prop.setProperty("GenerateAlgorithm", "MyMazeGenerator");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
