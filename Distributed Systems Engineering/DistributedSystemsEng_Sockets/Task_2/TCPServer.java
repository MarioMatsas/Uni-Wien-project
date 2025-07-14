package Task_2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

            Message message = (Message) in.readObject();
            //System.out.println("Server received: " + message.getNumber());
            // Slightly modify message before sending
            message.setNumber(message.getNumber() + 1);
            message.setSenderName("server");
            out.writeObject(message);

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
