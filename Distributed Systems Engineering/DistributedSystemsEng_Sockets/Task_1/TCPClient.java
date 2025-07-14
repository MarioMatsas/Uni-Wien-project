package Task_1;




import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
     public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234); 
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // Send a number
            int message = 17;
            //System.out.println("Client send: " + message);
            out.writeInt(message);

            // Print the response
            int response = in.readInt();
            System.out.println("Client received: " + response);

            // Close everything
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
