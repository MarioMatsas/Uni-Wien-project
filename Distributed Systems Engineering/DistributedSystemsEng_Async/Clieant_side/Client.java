package Clieant_side;
import java.util.Scanner;

import Shared_elements.LogEntry;

public class Client {
    public static void main(String[] args) throws Exception {
        ClientProxy proxy = new ClientProxy();

        while (true){
            Scanner scanner = new Scanner(System.in);

            System.out.println("Select a use case to execute:");
            System.out.println("1 - Log a single entry (fire and forget)");
            System.out.println("2 - Remove old logs (sync with server)");
            System.out.println("3 - Log entries in bulk (polling)");
            System.out.println("4 - Get matching entries (callback)");

            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
                LogEntry logentry1 = new LogEntry("Log meesage1");
                LogEntry logentry2 = new LogEntry("Log meesage2");
                LogEntry logentry3 = new LogEntry("Log meesage3");
                proxy.logSingleEntry(logentry1);
                proxy.logSingleEntry(logentry2);
                proxy.logSingleEntry(logentry3);

                for (int i=0; i<10; i++){
                    System.out.println("Client is still printing numbers: "+i);
                }
                break;
            case 2:
                proxy.removeOldLogs(2);
                for (int i=0; i<10; i++){
                    System.out.println("Client is still printing numbers: "+i);
                }
                break;
            case 3:
                LogEntry[] logentries = {new LogEntry("Log meesage1"), new LogEntry("Log meesage2"), new LogEntry("Log meesage3")};
                proxy.addLogsInBulk(logentries);
                // Check for log entry (will not be there yet)
                System.out.println(Poll.hasResponse());
                for (int i=0; i<10; i++){
                    System.out.println("Client is still printing numbers: "+i);
                }
                Thread.sleep(3000);
                // Check for log entry again (will be there)
                System.out.println(Poll.hasResponse());
                // Print poll response
                System.out.println(Poll.getReponses().get(0));
                break;
            case 4:
                // Create callback object and pass it to the requestor
                Callback callback = new Callback();
                proxy.search("1", callback);
                for (int i=0; i<10; i++){
                    System.out.println("Client is still printing numbers: "+i);
                }
                Thread.sleep(3000);
                break;
            default:
                System.out.println("Invalid selection.");
            }
        }
        // Use Case 2: Sync with server
        //proxy.removeOldLogs(2);
    }
}