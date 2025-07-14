package Clieant_side;
import java.net.UnknownHostException;

import Shared_elements.KnownMethods;
import Shared_elements.LogEntry;

public class ClientProxy {
    private Requestor requestor;

    public ClientProxy() throws UnknownHostException{
        requestor = new Requestor();
    }
    // Use-Case 1: Fire and Forget
    public void logSingleEntry(LogEntry entry) throws Exception {
        requestor.invoke(KnownMethods.singleLog, entry, null);

        //RequestMessage<LogEntry> request = new RequestMessage<>(KnownMethods.singleLog, entry);
        //byte[] data = requestor.marshall(request);
        //udpHandler.send(data); // no response expected
    }

    // Use-Case 2: Sync with Server
    public void removeOldLogs(int count) throws Exception {
        requestor.invoke(KnownMethods.removeOldLogs, count, null);
        
        //RequestMessage<Integer> request = new RequestMessage<>(KnownMethods.removeOldLogs, count);
        //byte[] requestBytes = requestor.marshall(request);
        //byte[] responseBytes = tcpHandler.send(requestBytes);
        // we can ignore actual response contents, we just sync to completion
        //ResponseMessage<String> response = requestor.unmarshall(responseBytes);
        //System.out.println("Remove logs response: " + response.getResponseData());
    }

    // Use-Case 3: Polling
    public void addLogsInBulk(LogEntry[] entries) throws Exception {
        requestor.invoke(KnownMethods.addLogsInBulk, entries, null);
    }

    // Use-Case 4: Remote Callback
    public void search(String searchTerm, Callback callback) throws Exception {
        requestor.invoke(KnownMethods.searchLogs, searchTerm, callback);
    }
}
