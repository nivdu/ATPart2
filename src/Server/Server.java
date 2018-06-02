package Server;

//import Server.IServerStrategy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPoolExecutor;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        threadPoolExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2);
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
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
                    System.out.println("SocketTimeout - No clients pending!");
                }
            }
            server.close();
        } catch (IOException e) {//todo exception
            e.printStackTrace();
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
            e.printStackTrace();
//            LOG.error("IOException", e);
        }
    }

    public void stop() {
        System.out.println("Stopping server..");
        stop = true;
    }
}
