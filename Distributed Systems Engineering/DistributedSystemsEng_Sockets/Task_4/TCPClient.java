package Task_4;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCPClient {
    static long blobTime; 
    public static void main(String[] args) {
        int threads = 1;
        ExecutorService clientPool = Executors.newFixedThreadPool(threads);
        try {
            int[] blobSizes = {1, 2, 4, 8, 16, 128};
            long totalTime = 0;
            
            // For each blob size, each thread performs 1000/threads iterations
            for (int size : blobSizes) {
                int iterationsPerThread = 1000 / threads;
                CountDownLatch latch = new CountDownLatch(threads);
                
                for (int i = 0; i < threads; i++) {
                    clientPool.submit(new ClientThread(size, iterationsPerThread, latch));
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
    
    static class ClientThread implements Runnable {
        private int blobSize;     
        private int iterations;
        private CountDownLatch latch;
        
        public ClientThread(int blobSize, int iterations, CountDownLatch latch) {
            this.blobSize = blobSize;
            this.iterations = iterations;
            this.latch = latch;
        }
        
        public void run() {
            long localTotal = 0;
            try {
                Socket socket = new Socket("localhost", 1234);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                for (int i = 0; i < iterations; i++) {
                    byte[] dataBlob = new byte[blobSize * 1024];
                    Message message = new Message(17, "client", System.currentTimeMillis(), dataBlob);
                    
                    // START TIME
                    long start = System.nanoTime();
                    out.writeObject(message);
                    
                    Message response = (Message) in.readObject();
                    if (response.getNumber() != 18){
                        System.out.println("YO");
                    }
                    // END TIME
                    long end = System.nanoTime();
                    localTotal += end - start;
                }
                in.close();
                out.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            blobTime += localTotal;
            latch.countDown();
        }
    }
}

