package laptrinhmangde1.Connection;

import laptrinhmangde1.GUI.Main;


import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Loan (^._.^)ﾉ
 */

class ReceiveMessage implements Runnable {
    Socket socket;
    BufferedReader in;
    Main screen;
    boolean connected = false;
    ReceiveMessage(Socket socket, BufferedReader in, Main screen){
        this.socket = socket;
        this.in = in;
        this.screen = screen;
    }
    @Override
    public void run() {
        try {
            String name = "";
            while (true) {
                System.out.println("waiting for response");
                String data = "";
                data = in.readLine();
                System.out.println("Receive: " + data);
                String message  = "";
                    if (data  == null){
                        System.out.println("GET NULL");
                        break;
                    }
                    if(data.equals("USERNAME_EXISTED")){
                        JOptionPane.showMessageDialog(screen,"User name này đã được dùng rồi, vui lòng chọn username khác");
                    }
                    if(data.equals("WAITING")){
                       screen.waitingScreen();
                    }
                    if(data.startsWith("CONNECTED")){
                        name = data.substring(10);
                        screen.showacceptScreen(name);
                    }
                    if(data.startsWith("CLIENT_REFUSE")){
                        JOptionPane.showMessageDialog(screen,name + " đã từ chối yêu cầu kết nối");
                        screen.waitingScreen();
                    }
                    if(data.startsWith("CLIENT_ACCEPT")){
                        connected = true;
                        screen.showChatScreen(name);
                    }
                    if(data.equals("WAITING_FOR_NEW_CLIENT")){
                        screen.waitingScreen();
                    }
                    if(data.startsWith("CLIENT:")){
                        message  = data.substring(7);
                        System.out.println(message);
                        screen.main.showReceiveMessage(message);
                    }
            }
        } catch (Exception e){
            //e.printStackTrace();
        }
        System.out.println("Đã đóng");
    }

}


public class Client {
    private static String host = "localhost";
    private static int port = 5000;
    public static Socket socket;
    private static BufferedReader in;
    private static Main main;
    private static BufferedWriter out;
    private static ExecutorService executor;
    public Client() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        main = new Main();

    }

    public static void connect() throws IOException {
        socket = new Socket(host, port);
        System.out.println("Client connected");
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        executor = Executors.newCachedThreadPool();
        ReceiveMessage recv = new ReceiveMessage(socket, in, main);
        executor.execute(recv);
    }


    public static void disConnect(){
        try{
            System.out.println("DISCONNECT");
            //executor.shutdownNow();
            socket.close();
            in.close();
            out.close();
        }catch (Exception e){
          //  e.printStackTrace();
        }
    }

    public static boolean sendMessage (String message) {
        try {
            if(socket != null) {
                System.out.println("Write: "+ message);
                out.write(message + '\n');
                out.flush();
                return true;
            }
        } catch (Exception e){
           // e.printStackTrace();
            return false;
        }
        return false;
    }
    public static boolean sendMessageToReceiver (String message) {
        try {
            if(socket != null) {
                System.out.println("Write: "+ message);
                out.write("CLIENT:"+message + '\n');
                out.flush();
                return true;
            }
        } catch (Exception e){
         //   e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

