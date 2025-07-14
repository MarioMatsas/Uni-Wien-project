package Task_3;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        long totalTime = 0;
        try {
            aSocket = new DatagramSocket();
            
            int[] blobSizes = {1, 2, 4, 8, 16, 128};

            for (int size : blobSizes){
                long blobTime = 0;
                for (int i=0; i<1000; i++){
                    if (size==128){
                        byte[] totalBlobs = new byte[size*1024]; // We will collect all the data here
                        Message finMessage = null;
                        int offset = 0; // To track where to copy each received blob
                        for (int j = 0; j < 4; j++) {
                            // We want to send 4 packages each one containing 32KB of the 128KB blob
                            byte[] dataBlob = new byte[32 * 1024];
                            Message message = new Message(13, "client", System.currentTimeMillis(), dataBlob);

                            // Serialize the Message object to a byte array
                            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
                            objectOutput.writeObject(message);
                            byte[] m = byteOutput.toByteArray();

                            InetAddress aHost = InetAddress.getByName("localhost");
                            int serverPort = 1234;

                            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                            
                            // START TIME
                            long start = System.nanoTime();
                            aSocket.send(request);

                            byte[] buffer = new byte[size * 3000];
                            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                            aSocket.receive(reply);
                            
                            // END TIME
                            long end = System.nanoTime();

                            // Deserialize the received Message object
                            ByteArrayInputStream byteInput = new ByteArrayInputStream(reply.getData(), 0, reply.getLength());
                            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                            Message replyMessage = (Message) objectInput.readObject();
                            finMessage = replyMessage;

                            long time = end - start;
                            totalTime += time;
                            blobTime += time;

                            // Extract received blob
                            byte[] receivedBlob = replyMessage.getDataBlob();

                            // Copy received data into totalBlobs
                            System.arraycopy(receivedBlob, 0, totalBlobs, offset, receivedBlob.length);
                            offset += receivedBlob.length;  // Move the offset forward
                        }
                        finMessage.setDataBlob(totalBlobs);
                    }
                    else{
                        byte[] dataBlob = new byte[size*1024];
                        Message message = new Message(13, "client",  System.currentTimeMillis(), dataBlob);
                        // Serialize the Message object to a byte array
                        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
                        objectOutput.writeObject(message);
                        byte[] m = byteOutput.toByteArray();

                        InetAddress aHost = InetAddress.getByName("localhost");
                        int serverPort = 1234;

                        //System.out.println("Client sent: " + message.getNumber());
                        DatagramPacket request = new DatagramPacket(m,m.length, aHost, serverPort);
                        // START TIME
                        long start = System.nanoTime();
                        aSocket.send(request);
                        
                        byte[] buffer = new byte[size*3000];
                        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                        aSocket.receive(reply);
                        // END TIME
                        long end = System.nanoTime();
                        // Deserialize the received Message object
                        ByteArrayInputStream byteInput = new ByteArrayInputStream(reply.getData(), 0, reply.getLength());
                        ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                        Message replyMessage = (Message) objectInput.readObject();
                        long time = end - start;
                        totalTime += time;
                        blobTime += time;
                    }
                    
                    //System.out.println("Client received: " + replyMessage.getNumber() + " in " + time + " ns");
                }
                System.out.println("Average blob CLIENT time: " + blobTime/1000);
            }
            
        } catch (Exception e) {
        } finally {
        if (aSocket != null)
            System.out.println("Average total CLIENT time: " + totalTime/6000);
            // Close everything
            aSocket.close();
        }

    }
}
