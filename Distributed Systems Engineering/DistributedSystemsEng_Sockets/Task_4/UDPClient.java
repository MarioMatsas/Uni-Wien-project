package Task_4;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPClient {
    static long blobTime;
    public static void main(String[] args) {
        int threads = 1;
        ExecutorService clientPool = Executors.newFixedThreadPool(threads);
        try {
            int[] blobSizes = {1, 2, 4, 8, 16, 128};
            long totalTime = 0;
            
            for (int size : blobSizes) {
                int iterationsPerThread = 1000 / threads;
                CountDownLatch latch = new CountDownLatch(threads);
                
                for (int i = 0; i < threads; i++) {
                    clientPool.submit(new ClientThreadUDP(size, iterationsPerThread, latch));
                }
                
                // Wait for all threads to complete before printing results for this blob size
                latch.await();
                
                System.out.println("Average blob CLIENT time for " + size + " KiB: " + (blobTime / 1000) + " ns");
                totalTime += blobTime;
                blobTime = 0; // Reset blobTime after adding it to totalTime
            }
            System.out.println("Average total CLIENT time: " + (totalTime / 6000) + " ns");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientPool.shutdown();
        }

    }
    
    static class ClientThreadUDP implements Runnable {
        private int blobSize;     
        private int iterations;
        private CountDownLatch latch;
        
        public ClientThreadUDP(int blobSize, int iterations, CountDownLatch latch) {
            this.blobSize = blobSize;
            this.iterations = iterations;
            this.latch = latch;
        }
        
        public void run() {
            long localTotal = 0;
            try {
                // Each thread creates its own DatagramSocket.
                DatagramSocket socket = new DatagramSocket();
                InetAddress aHost = InetAddress.getByName("localhost");
                int serverPort = 1234;
                for (int i = 0; i < iterations; i++) {
                    if (blobSize==128){
                        byte[] totalBlobs = new byte[blobSize*1024]; // We will collect all the data here
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

                            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                            
                            // START TIME
                            long start = System.nanoTime();
                            socket.send(request);

                            byte[] buffer = new byte[blobSize * 3000];
                            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                            socket.receive(reply);
                            
                            // END TIME
                            long end = System.nanoTime();

                            // Deserialize the received Message object
                            ByteArrayInputStream byteInput = new ByteArrayInputStream(reply.getData(), 0, reply.getLength());
                            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                            Message replyMessage = (Message) objectInput.readObject();
                            finMessage = replyMessage;

                            long time = end - start;
                            localTotal += time;

                            // Extract received blob
                            byte[] receivedBlob = replyMessage.getDataBlob();

                            // Copy received data into totalBlobs
                            System.arraycopy(receivedBlob, 0, totalBlobs, offset, receivedBlob.length);
                            offset += receivedBlob.length;  // Move the offset forward
                        }
                        finMessage.setDataBlob(totalBlobs);
                    }
                    else{
                        byte[] dataBlob = new byte[blobSize*1024];
                        Message message = new Message(13, "client", System.currentTimeMillis(), dataBlob);
                        // Serialize the Message object to a byte array
                        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
                        objectOutput.writeObject(message);
                        byte[] m = byteOutput.toByteArray();

                        //System.out.println("Client sent: " + message.getNumber());
                        DatagramPacket request = new DatagramPacket(m,m.length, aHost, serverPort);
                        // START TIME
                        long start = System.nanoTime();
                        socket.send(request);
                        
                        byte[] buffer = new byte[blobSize*3000];
                        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                        socket.receive(reply);
                        // END TIME
                        long end = System.nanoTime();
                        // Deserialize the received Message object
                        ByteArrayInputStream byteInput = new ByteArrayInputStream(reply.getData(), 0, reply.getLength());
                        ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                        Message replyMessage = (Message) objectInput.readObject();
                        long time = end - start;
                        localTotal += time;
                    }
                }
                
                socket.close();
            } catch (Exception e) {
            }
            blobTime += localTotal;
            latch.countDown();
        }
    }
}
