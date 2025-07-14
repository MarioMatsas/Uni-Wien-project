package Server_side;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerRequestHandlerUDP {
    private final DatagramSocket socket;
    private final Invoker invoker = new Invoker();

    public ServerRequestHandlerUDP() throws Exception {
        this.socket = new DatagramSocket(1234);
    }

    public void listen() throws Exception {
        byte[] buffer = new byte[2048];
        while (true) {
            // Receive request
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            byte[] requestBytes = new byte[packet.getLength()];
            System.arraycopy(packet.getData(), 0, requestBytes, 0, packet.getLength());

            // Handle request synchronously (one at a time)
            //Thread.sleep(500);
            invoker.invoke(requestBytes);  // No threading, handle sequentially
        }
    }
}