package Task_3;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234); 
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            long totalTime = 0;
            // Run 6 times, once for each data blob size
            int[] blobSizes = {1, 2, 4, 8, 16, 128};

            for (int size : blobSizes){
                long blobTime = 0;
                for (int i=0; i<1000; i++){
                    // Send a number
                    byte[] dataBlob = new byte[size*1024];
                    Message message = new Message(17, "client", System.currentTimeMillis(), dataBlob);
                    //System.out.println("Client sent: " + message.getNumber());
                    // START TIME
                    long start = System.nanoTime();
                    out.writeObject(message);

                    // Print the response
                    Message response = (Message) in.readObject();
                    // END TIME
                    long end = System.nanoTime();
                    long time = end - start;
                    //System.out.println("Client received: " + response.getNumber() + " in " + time + " ns");
                    totalTime += time;
                    blobTime += time;
                }
                System.out.println("Average blob CLIENT time: " + blobTime/1000);
            }
            System.out.println("Average total CLIENT time: " + totalTime/6000);
            // Close everything
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
