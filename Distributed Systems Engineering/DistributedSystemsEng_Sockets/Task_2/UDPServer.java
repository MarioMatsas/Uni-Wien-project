package Task_2;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(1234);
            byte[] buffer = new byte[1000];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);

            // Deserialize the received Message object
            ByteArrayInputStream byteInput = new ByteArrayInputStream(request.getData(), 0, request.getLength());
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
            Message message = (Message) objectInput.readObject();

            //System.out.println("Server received: " + message.getNumber());

            message.setNumber(message.getNumber() + 1);
            message.setSenderName("server");

            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(message);
            byte[] response = byteOutput.toByteArray();

            //System.out.println("Server sent: " + message.getNumber());
            DatagramPacket reply = new DatagramPacket(response, response.length, request.getAddress(), request.getPort());
            aSocket.send(reply);
            
            // Close everything
            aSocket.close();
        } catch (Exception e) {
            
        } finally {
            if (aSocket != null)
                aSocket.close();
        }

    }
}
