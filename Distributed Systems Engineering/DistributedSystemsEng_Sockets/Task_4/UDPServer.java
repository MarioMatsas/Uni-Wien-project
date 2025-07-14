package Task_4;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 
 *  NO THREAD POOL
 */
/*public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(1234);
            while (true) {
                byte[] buffer = new byte[60000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                
                // Wait for a new packet.
                aSocket.receive(request);
                
                // New thread for each datagram
                new Thread(new ServerThreadUDP(request, aSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
    
    static class ServerThreadUDP implements Runnable {
        private DatagramPacket request;
        private DatagramSocket socket;
        
        ServerThreadUDP(DatagramPacket request, DatagramSocket socket) {
            this.request = request;
            this.socket = socket;
        }
        
        public void run() {
            try {
                // Deserialize the Message from the received datagram.
                ByteArrayInputStream byteInput = new ByteArrayInputStream(request.getData(), 0, request.getLength());
                ObjectInputStream objIn = new ObjectInputStream(byteInput);
                Message message = (Message) objIn.readObject();
                
                // Process the message: increment the number and swap sender/receiver names.
                message.setNumber(message.getNumber() + 1);
                message.setSenderName("server");
                
                // Serialize the updated Message to a byte array.
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
                objOut.writeObject(message);
                byte[] response = byteOut.toByteArray();
                
                // Create a reply packet with the serialized response.
                DatagramPacket reply = new DatagramPacket(response, response.length,
                                                          request.getAddress(), request.getPort());
                // Synchronized since the same socket is shared by multiple threads.
                synchronized(socket) {
                    socket.send(reply);
                }
            } catch (Exception e) {
            }
        }
    }
}*/
/*
 * 
 * 
 * THREAD POOL
 * 
 */
public class UDPServer {
    // Number of threads used to handle incoming datagrams
    private static final int THREAD_POOL_SIZE = 1;
    
    public static void main(String[] args) {
        DatagramSocket socket = null;
        ExecutorService serverPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        try {
            socket = new DatagramSocket(1234);
            // Continuously wait for incoming UDP datagrams.
            while (true) {
                byte[] buffer = new byte[60000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                
                serverPool.execute(new ServerThreadUDP(request, socket));
            }
        } catch (Exception e) {
        } finally {
            if (socket != null)
                socket.close();
        }
    }
    
    static class ServerThreadUDP implements Runnable {
        private DatagramPacket request;
        private DatagramSocket socket;
        
        public ServerThreadUDP(DatagramPacket request, DatagramSocket socket) {
            this.request = request;
            this.socket = socket;
        }
        
        public void run() {
            try {
                // Deserialize the Message object from the received datagram.
                ByteArrayInputStream byteInput = new ByteArrayInputStream(request.getData(), 0, request.getLength());
                ObjectInputStream objIn = new ObjectInputStream(byteInput);
                Message message = (Message) objIn.readObject();
                
                // Process the message: increment the number and swap sender/receiver names.
                message.setNumber(message.getNumber() + 1);
                message.setSenderName("server");
                
                // Serialize the updated Message to a byte array.
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ObjectOutputStream objOut = new ObjectOutputStream(byteOutput);
                objOut.writeObject(message);
                byte[] responseData = byteOutput.toByteArray();
                
                // Create and send a reply packet with the serialized response.
                DatagramPacket reply = new DatagramPacket(responseData, responseData.length,
                                                          request.getAddress(), request.getPort());
                // Synchronized because the same socket is shared.
                synchronized(socket) {
                    socket.send(reply); // MAYBE, TODO ASK!  
                }
            } catch (Exception e) {
            }
        }
    }
}
