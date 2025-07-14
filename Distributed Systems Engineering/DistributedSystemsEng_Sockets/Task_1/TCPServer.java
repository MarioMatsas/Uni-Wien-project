package Task_1;




import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        try{ 
            ServerSocket Ssocket = new ServerSocket(1234);
            Socket socket = Ssocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            int message = in.readInt();
            //System.out.println("Server received: " + message);
            // Slightly modify message before sending
            out.writeInt(message + 1);

            // Close streams and socket
            in.close();
            out.close();
            socket.close();
            Ssocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}