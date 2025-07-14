package Task_2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234); 
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Send a number
            Message message = new Message(20, "client",  System.currentTimeMillis());
            //System.out.println("Client sent: " + message.getNumber());
            out.writeObject(message);

            // Print the response
            Message response = (Message) in.readObject();
            System.out.println("Client received: " + response.getNumber());

            // Close everything
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
