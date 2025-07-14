package Task_3;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        try{ 
            ServerSocket Ssocket = new ServerSocket(1234);
            Socket socket = Ssocket.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            long totalTime = 0;
            for (int i=0; i<6; i++){
                long blobTime = 0;
                for (int j=0; j<1000; j++){
                    // START TIME
                    long start = System.nanoTime();
                    Message message = (Message) in.readObject();
                    
                    //System.out.println("Server received: " + message.getNumber());
                    // Slightly modify message before sending
                    message.setNumber(message.getNumber() + 1);
                    message.setSenderName("server");
                    
                    out.writeObject(message);
                    // END TIME
                    long end = System.nanoTime();
                    long time = end - start;
                    
                    totalTime += time;
                    blobTime += time;
                    //System.out.println("Server sent: " + message.getNumber()+ " in " + time + " ns");
                }
                System.out.println("Average blob SERVER time: " + blobTime/1000);
            }
            System.out.println("Average total SERVER time: " + totalTime/6000);

            // Close streams and socket
            in.close();
            out.close();
            socket.close();
            Ssocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
