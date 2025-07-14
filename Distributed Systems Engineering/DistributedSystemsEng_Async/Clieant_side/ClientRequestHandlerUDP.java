package Clieant_side;
import java.net.*;

public class ClientRequestHandlerUDP {
    private final InetAddress serverAddress;
    private final int serverPort;

    public ClientRequestHandlerUDP() throws UnknownHostException {
        this.serverAddress = InetAddress.getByName("localhost");
        this.serverPort = 1234;
    }

    public void send(byte[] data) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
        socket.send(packet);
        socket.close(); // fire and forget - no response expected
    }
}