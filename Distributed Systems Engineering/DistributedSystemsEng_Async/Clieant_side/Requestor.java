package Clieant_side;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Shared_elements.IMarshall;
import Shared_elements.KnownMethods;
import Shared_elements.LogEntry;
import Shared_elements.RequestMessage;
import Shared_elements.ResponseMessage;

public class Requestor {
    private final ClientRequestHandlerUDP requestHandlerUDP;
    private final ClientRequestHandlerTCP requestHandlerTCP;
    private final ExecutorService executor;

    public Requestor() throws UnknownHostException {
        requestHandlerTCP = new ClientRequestHandlerTCP();
        requestHandlerUDP = new ClientRequestHandlerUDP();
        executor = Executors.newFixedThreadPool(1);
    }

    public void invoke(KnownMethods method, Object param, Callback callback) throws Exception{
        if (method == KnownMethods.singleLog){
            // For this case (Fire and Forget) we want to use UDP
            RequestMessage<LogEntry> request = new RequestMessage<>(KnownMethods.singleLog, (LogEntry) param);
            byte[] data = request.marshall();
            requestHandlerUDP.send(data); // no response expected
        }
        else if (method == KnownMethods.removeOldLogs){
            // For this case (Sync with Server) we want to use TCP
            RequestMessage<Integer> request = new RequestMessage<>(KnownMethods.removeOldLogs, (int) param);
            byte[] data = request.marshall();
            requestHandlerTCP.send(false, data); 
        }
        else if (method == KnownMethods.addLogsInBulk){
            // For this case (Polling) we want to use TCP
            executor.submit(() -> {
            try {
                RequestMessage<LogEntry[]> request = new RequestMessage<>(KnownMethods.addLogsInBulk, (LogEntry[]) param);
                byte[] data = request.marshall();
                byte[] response = requestHandlerTCP.send(true, data);
                ResponseMessage<String> resp = (ResponseMessage<String>) IMarshall.unmarshall(response);
                //System.out.println("I JUST ADDED TO POLL");
                //System.out.println(resp.getResponseData());
                Poll.addResponse(resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        }
        // Use-Case 4 is the default
        else{
            executor.submit(() -> {
            try {
                RequestMessage<String> request = new RequestMessage<>(KnownMethods.searchLogs, (String) param);
                byte[] data = request.marshall();
                byte[] response = requestHandlerTCP.send(true, data);
                ResponseMessage<LogEntry[]> resp = (ResponseMessage<LogEntry[]>) IMarshall.unmarshall(response);
                //System.out.println(resp.getResponseData());
                callback.onResponse(resp.getResponseData());
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        }
    }
}
