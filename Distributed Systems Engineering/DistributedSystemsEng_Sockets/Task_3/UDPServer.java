package Task_3;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        long totalTime = 0;
        try {
            aSocket = new DatagramSocket(1234);
            int[] blobSizes = {1, 2, 4, 8, 16, 128};
            for (int i=0; i<6; i++){
                long blobTime = 0;
                for (int j=0; j<1000; j++){
                    if (blobSizes[i] == 128){
                        // We simply run a for loop and repeat the proccess
                        for (int k=0; k<4; k++){
                            byte[] buffer = new byte[32*3000];
                            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                            aSocket.receive(request);

                            // Deserialize the received Message object
                            ByteArrayInputStream byteInput = new ByteArrayInputStream(request.getData(), 0, request.getLength());
                            ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                            // START TIME
                            long start = System.nanoTime();
                            Message message = (Message) objectInput.readObject();

                            //System.out.println("Server received: " + message.getNumber());

                            message.setNumber(message.getNumber() + 1);
                            message.setSenderName("server");

                            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
                            objectOutput.writeObject(message);
                            byte[] response = byteOutput.toByteArray();
                            DatagramPacket reply = new DatagramPacket(response, response.length, request.getAddress(), request.getPort());
                            
                            aSocket.send(reply);
                            // END TIME
                            long end = System.nanoTime();
                            long time = end - start;
                            totalTime += time;
                            blobTime += time;
                        }
                    }
                    else{
                        byte[] buffer = new byte[blobSizes[i]*3000];
                        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                        aSocket.receive(request);

                        // Deserialize the received Message object
                        ByteArrayInputStream byteInput = new ByteArrayInputStream(request.getData(), 0, request.getLength());
                        ObjectInputStream objectInput = new ObjectInputStream(byteInput);
                        // START TIME
                        long start = System.nanoTime();
                        Message message = (Message) objectInput.readObject();

                        //System.out.println("Server received: " + message.getNumber());

                        message.setNumber(message.getNumber() + 1);
                        message.setSenderName("server");

                        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
                        objectOutput.writeObject(message);
                        byte[] response = byteOutput.toByteArray();
                        DatagramPacket reply = new DatagramPacket(response, response.length, request.getAddress(), request.getPort());
                        
                        aSocket.send(reply);
                        // END TIME
                        long end = System.nanoTime();
                        long time = end - start;
                        totalTime += time;
                        blobTime += time;
                    }
                    
                    //System.out.println("Server sent: " + message.getNumber()+ " in " + time + " ns");
                }
                System.out.println("Average blob SERVER time: " + blobTime/6000);
            }
            
        } catch (Exception e) {
            
        } finally {
            if (aSocket != null)
                System.out.println("Average total SERVER time: " + totalTime/6000);
                aSocket.close();
        }

    }
} 