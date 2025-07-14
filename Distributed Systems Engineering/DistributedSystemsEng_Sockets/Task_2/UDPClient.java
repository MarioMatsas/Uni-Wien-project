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

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            Message message = new Message(22, "client", System.currentTimeMillis());
            // Serialize the Message object to a byte array
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(message);
            byte[] m = byteOutput.toByteArray();

            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 1234;

            //System.out.println("Client sent: " + message.getNumber());
            DatagramPacket request = new DatagramPacket(m,m.length, aHost, serverPort);
            aSocket.send(request);
            
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);

            // Deserialize the received Message object
            ByteArrayInputStream byteInput = new ByteArrayInputStream(reply.getData(), 0, reply.getLength());
            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
            Message replyMessage = (Message) objectInput.readObject();

            System.out.println("Client received: " + replyMessage.getNumber());

            } catch (Exception e) {
            } finally {
            if (aSocket != null)

            // Close everything
            aSocket.close();
            }

    }
}
