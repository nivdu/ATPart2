package Server;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;



public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPoolExecutor;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        Configurations.SetConfigurations();
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Configurations.getNumOfThreads());
    }

    public void start() {
        new Thread(() -> { runServer(); }).start();
    }

    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            System.out.println(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    System.out.println(String.format("Client excepted: %s", clientSocket.toString()));
                    threadPoolExecutor.execute(() -> handleClient(clientSocket));
                } catch (SocketTimeoutException e) {
//                    System.out.println("SocketTimeout - No clients pending!");
                }
            }
            threadPoolExecutor.shutdown();
            server.close();
        } catch (IOException e) {//todo exception
            //e.printStackTrace();
//            LOG.error("IOException", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            System.out.println("Client excepted!");
            System.out.println(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
//            clientSocket.getInputStream().close();
//            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {//todo exception

            //e.printStackTrace();
//            LOG.error("IOException", e);
        }
    }

    public void stop() {
        stop = true;
    }

    static class Configurations{
        private static Properties prop;

        private static void SetConfigurations(){
            InputStream input=null;
            prop = new Properties();
            try {
                input = new FileInputStream("Resources/config.properties");
                // load a properties file
                prop.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        static String getMazeGenerationMethod(){ return prop.getProperty("GenerateAlgorithm"); }
        static String getSearchingAlgorithm(){ return prop.getProperty("SearchingAlgorithm"); }

        private static int getNumOfThreads(){
            try{
                int to_return = Integer.parseInt(prop.getProperty("NumberOfThreads"));
                if (to_return >= 1)
                    return to_return;
            }
            catch (NumberFormatException e){
                System.out.println(e.getMessage());
            }
            return Runtime.getRuntime().availableProcessors() * 2;//default value of threads
        }
    }


}

