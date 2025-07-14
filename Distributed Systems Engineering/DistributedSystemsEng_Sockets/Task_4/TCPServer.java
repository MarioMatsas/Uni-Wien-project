package Task_4;


import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
 * 
 *  NO THREAD POOL
 */
/*public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket Ssocket = new ServerSocket(1234);
            while (true) {
                Socket socket = Ssocket.accept();
                // Create a new thread for each incoming connection
                new Thread(new ServerThread(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class ServerThread implements Runnable {
        private Socket socket;
        public ServerThread(Socket socket) {
            this.socket = socket;
        }
        public void run() {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                    Message message = (Message) in.readObject();
                    // Process the message: increment the number and swap sender/receiver names.
                    message.setNumber(message.getNumber() + 1);
                    message.setSenderName("server");
                    
                    out.writeObject(message);
                }
            } catch (Exception e) {
            }
        }
    }
}*/
/*
 * 
 * 
 * THREAD POOL
 * 
 */
public class TCPServer {
    // Number of threads used to handle connections on the server side.
    private static final int THREAD_POOL_SIZE = 4;
    
    public static void main(String[] args) {
        ExecutorService serverPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try {
            ServerSocket Ssocket = new ServerSocket(1234);
            while (true) {
                Socket socket = Ssocket.accept();
                serverPool.execute(new ServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class ServerHandler implements Runnable {
        private Socket socket;
        public ServerHandler(Socket socket) {
            this.socket = socket;
        }
        public void run() {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                    Message message = (Message) in.readObject();
                    // Process the message: increment the number and swap sender/receiver names.
                    message.setNumber(message.getNumber() + 1);
                    message.setSenderName("server");
                    out.writeObject(message);
                }
            } catch (Exception e) {
            }
        }
    }
}
