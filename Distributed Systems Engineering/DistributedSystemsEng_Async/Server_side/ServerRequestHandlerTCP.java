package Server_side;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandlerTCP {
    private final ServerSocket serverSocket;
    private final Invoker invoker = new Invoker();

    public ServerRequestHandlerTCP() throws Exception {
        this.serverSocket = new ServerSocket(4321);
    }

    public void listen() throws Exception {
        byte[] buffer = new byte[2048];
        while (true) {
            // Accept a client connection
            Socket clientSocket = serverSocket.accept();

            // Read data
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            int bytesRead = in.read(buffer);

            if (bytesRead != -1) {
                byte[] requestBytes = new byte[bytesRead];
                System.arraycopy(buffer, 0, requestBytes, 0, bytesRead);
                
                InvokerAnswer answer = invoker.invoke(requestBytes);
                // We need to pass the output stream in case use case 3 or 4 is used (aka if we get a "true" back)
                if (answer.getSendBack()){
                    out.write(answer.getData());
                    out.flush();
                }
            }

            clientSocket.close(); // Close connection after processing
        }
    }

    /*public void send(OutputStream out, byte[] data) throws IOException {
        out.write(data);
        out.flush(); 
    }*/

}
