package Task_1;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            Integer message = 13;
            byte[] m = Integer.toString(message).getBytes();
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 1234;

            //System.out.println("Client sent: " + message);
            DatagramPacket request = new DatagramPacket(m,m.length, aHost, serverPort);
            aSocket.send(request);
            
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            System.out.println("Client received: " + new String(reply.getData(), 0, reply.getLength()).trim());

            } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
            } finally {
            if (aSocket != null)

            // Close everything
            aSocket.close();
        }

    }
}
