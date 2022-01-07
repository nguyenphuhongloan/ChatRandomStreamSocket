package laptrinhmangde1.Connection;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Loan (^._.^)ﾉ
 */
public class Worker implements Runnable {
    public String username;
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private volatile Worker receiver;
    private volatile ArrayList<Worker> block = new ArrayList<>();
    public volatile int status = 0;
    /*
     * status = 0 : username invalid
     * status = 1: username valid
     * status = 2: added to waiting list
     * status = 4: waiting for accepted
     * status = 5: accept stranger
     * status = 6: refuse stranger
     * status = 3: connected
     * */
    Worker(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    public boolean isBlockedByReceive(Worker worker){
        for (Worker w : worker.block){
            if(w.username.equals(this.username)){
                return true;
            }
        }
        return false;
    }

    public Worker findClient(){
        boolean blockALl = false;
        boolean isBlocked = false;
        System.out.println("FINDING IN "+ this.username);
        for (Worker worker : Server.waiting) {
            isBlocked = false;
            for (Worker worker1 : block) {
                if(worker1.equals(worker)){
                   isBlocked = true;
                   break;
                }
            }
            System.out.println(worker.username + isBlocked);
            if(!isBlocked && !worker.equals(this) && !isBlockedByReceive(worker) && worker!=null && worker.username!= null){
                return worker;
            }
        }
        return null;
    }
    @Override
    public void run() {
        System.out.println("Client " + username + " socket " + socket.toString() + " accepted");
        try {
            while (true) {
                if (status == 0) {
                    //System.out.println("CASE USERNAME INVALID OF "+ username);
                    String input = "";
                    if(in.ready()){
                        input = in.readLine();
                        this.username = input;
                        System.out.println("Server received username: " + input + " from " + socket.toString() + " # Client " + username);
                        if (!Server.listUsername.add(input)) {
                            System.out.println("USERNAME EXISTED");
                            out.write("USERNAME_EXISTED\n");
                            out.flush();
                        } else {
                            status = 1;
                        }
                    }
                }
                if (status == 1) {
                        System.out.println("...");
                        Worker finded = findClient();
                        if(finded != null){
                            System.out.println("Finded");
                            receiver = finded;
                            out.write("CONNECTED_" + receiver.username + "\n");
                            out.flush();
                            receiver.receiver = this;
                            receiver.out.write("CONNECTED_" + this.username+"\n");
                            receiver.out.flush();
                            Server.waiting.remove(this);
                            Server.waiting.remove(receiver);
                            this.status = 4;
                            receiver.status = 4;
                        }
                         else {
                            Server.waiting.add(this);
                            System.out.println("Waiting of "+ username);
                            out.write("WAITING\n");
                            out.flush();
                            this.status = 2;
                        }
                }
                if(status == 2){
                   if(in.ready()){
                       String input = in.readLine();
                       if(input.equals("CLIENT_EXIT")){
                           try {
                               Server.waiting.remove(this);
                               Server.listUsername.remove(this.username);
                               this.status = 0;
                               break;
                           }catch (Exception e){

                           }
                           throw new Exception();
                       }
                   }
                }
                if(status == 4){
                    if(in.ready()){
                        String input = in.readLine();
                        System.out.println("Server get yes no of "+ username + ": " + input);
                        if(input.equals("ACCEPT")){
                            System.out.println("Client "+ username + " accept");
                            status = 5;
                            System.out.println("Status of "+ username +" " +status);
                        }
                        if(input.equals("REFUSE")){
                            System.out.println("Client "+ username + " refuse");
                            status = 6;
                        }
                        if(input.equals("CLIENT_EXIT")){
                            status = 7;
                            System.out.println("client "+ username + " exit in accept");
                        }
                    }
                }
                if(status == 5){
                    int response  = receiver.status;
                    if(response== 6 || response == 7){
                        block.add(receiver);
                        Server.waiting.add(this);
                        if(response == 6)
                            Server.waiting.add(receiver);
                        receiver = null;
                        status = 1;
                    }
                    if (response == 5){
                        receiver.out.write("CLIENT_ACCEPT_"+username+"_to_"+receiver.username+"\n");
                        receiver.out.flush();
                        status = 3;
                        receiver.status = 3;
                    }

                }
                if(status == 6 || status == 7){
                    receiver.out.write("CLIENT_REFUSE_"+username+"_to_"+receiver.username+"\n");
                    receiver.out.flush();
                    block.add(receiver);
                    receiver.block.add(this);
                    if(status == 6)
                        Server.waiting.add(this);
                    Server.waiting.add(receiver);
                    receiver = null;
                    if(status == 7){
                        Server.listUsername.remove(this.username);
                        Server.waiting.remove(this);
                    }
                   status = (status == 6? 1: 0);

                }
                if (status == 3) {
                    String input = "";
                    if(in.ready()){
                        input = in.readLine();
                        System.out.println("Server received: " + input + " from " + socket.toString() + " # Client " + username);
                        if(input.equals("CLIENT_EXIT")){
                            receiver.out.write("WAITING_FOR_NEW_CLIENT\n");
                            receiver.out.flush();
                            receiver.status = 1;
                            receiver.receiver = null;
                            try {
                                Server.listUsername.remove(username);
                                this.status = 0;
                                socket.close();
                                in.close();
                                out.close();
                                break;
                            } catch (Exception e){
                                System.out.println("Đóng kết nối thành công");
                            }
                        }
                        else{
                            receiver.out.write(input + "\n");
                            receiver.out.flush();
                        }

                        System.out.println("Server write: " + input + " to " + receiver.username);
                    }

                }
            }
            /**/
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Client " + username + " exit");
            Server.listUsername.remove(this.username);
            Server.waiting.remove(this);
            try {
                if (receiver!= null){
                    receiver.status = 1;
                    receiver.receiver = null;
                    Server.waiting.add(receiver);
                }
                System.out.println("Closed socket for client " + username + " " + socket.toString());
                in.close();
                out.close();
                socket.close();
            } catch (IOException e1){

            }
        }

    }
}
