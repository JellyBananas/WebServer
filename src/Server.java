import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jellyBananas on 2016/8/28.
 */
public class Server {
    public Server() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8889);
            System.out.println("Launching...");
            while (true){
                Socket socket = new Socket();
                socket = serverSocket.accept();  //!!!
                System.out.println("Request from "+socket.getInetAddress().getHostAddress());
                Handler handler = new Handler(socket);
                new Thread(handler).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
