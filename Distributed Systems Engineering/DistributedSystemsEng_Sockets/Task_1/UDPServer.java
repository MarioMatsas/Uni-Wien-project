package Task_1;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(1234);
            byte[] buffer = new byte[1000];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);

            String s = new String(request.getData(), 0, request.getLength()).trim();
            int value = Integer.parseInt(s);
            //System.out.println("Server received: " + value);
            byte[] response = Integer.toString(value + 1).getBytes();
            //System.out.println("Server sent: " + (value+1));
            DatagramPacket reply = new DatagramPacket(response, response.length, request.getAddress(), request.getPort());
            aSocket.send(reply);
            
            // Close everything
            aSocket.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
}
