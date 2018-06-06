package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Configurations {

    private static Properties prop;
    private static OutputStream output;

    public Configurations() {
        try {
            prop = new Properties();
            output = new FileOutputStream("config.properties");
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}