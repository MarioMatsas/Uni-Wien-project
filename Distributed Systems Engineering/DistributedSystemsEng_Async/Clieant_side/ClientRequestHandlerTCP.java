package Clieant_side;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientRequestHandlerTCP {
    private final String serverAddress;
    private final int serverPort;

    public ClientRequestHandlerTCP() {
        this.serverAddress = "localhost";
        this.serverPort = 4321;
    }

    public byte[] send(boolean receive, byte[] data) throws Exception {
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            byte[] requestBytes = null;
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            out.write(data);
            out.flush(); 

            // If use case 3 or 4 is used, we need to await a response
            if (receive){
                byte[] buffer = new byte[2048];
                int bytesRead = in.read(buffer);
                if (bytesRead != -1) {
                    requestBytes = new byte[bytesRead];
                    System.arraycopy(buffer, 0, requestBytes, 0, bytesRead);
                }
            }
            socket.close();
            return requestBytes;
        }
    }
}