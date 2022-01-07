package laptrinhmangde1.Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Loan (^._.^)ﾉ
 */
public class Server {
    public static int port = 5000;
    private static ServerSocket server = null;
    public static HashSet<Worker> waiting = new HashSet<>();
    public static HashSet<String> listUsername = new HashSet<>();
    Server(){
        System.out.println("Server is runing on port "+ port);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        try {
            server = new ServerSocket(port);
            while (true){
                Socket socket = server.accept();
                Worker client = new Worker("", socket);
                executor.execute(client);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
