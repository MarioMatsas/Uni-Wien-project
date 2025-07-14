package Server_side;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Start ServerRequestHandlerTCP
        executor.submit(() -> {
            try {
                System.out.println("TCP Server is running...");
                new ServerRequestHandlerTCP().listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start ServerRequestHandlerUDP
        executor.submit(() -> {
            try {
                System.out.println("UDP Server is running...");
                new ServerRequestHandlerUDP().listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
