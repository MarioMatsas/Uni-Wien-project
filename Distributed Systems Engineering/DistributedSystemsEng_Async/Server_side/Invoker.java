package Server_side;
import java.io.OutputStream;
import java.net.UnknownHostException;

import Shared_elements.IMarshall;
import Shared_elements.KnownMethods;
import Shared_elements.LogEntry;
import Shared_elements.RequestMessage;
import Shared_elements.ResponseMessage;

public class Invoker {
    private final RemoteObject remoteObject = new RemoteObject();

    public Invoker() throws Exception{
        //requestHandlerTCP = new ServerRequestHandlerTCP();
    }

    public InvokerAnswer invoke(byte[] request) throws Exception{
        // Form the request message
        RequestMessage req = (RequestMessage) IMarshall.unmarshall(request);
        if (req.getMethod() == KnownMethods.singleLog){
            boolean sendBack = remoteObject.logSingleEntry((LogEntry) req.getRequestData());
            return new InvokerAnswer(sendBack, null);
        }
        else if (req.getMethod() == KnownMethods.removeOldLogs){
            boolean sendBack = remoteObject.removeOldLogs((int) req.getRequestData());
            return new InvokerAnswer(sendBack, null);
        }
        else if (req.getMethod() == KnownMethods.addLogsInBulk){
            boolean sendBack = remoteObject.addLogsInBulk((LogEntry[]) req.getRequestData());
            ResponseMessage<String> response = new ResponseMessage<String>("Log bulk of size " + ((LogEntry[]) req.getRequestData()).length + " has been handled!");
            byte[] data = response.marshall();
            return new InvokerAnswer(sendBack, data);
        }
        // Use-Case 4 is the default
        else{
            LogEntry[] matchingEntries = remoteObject.search((String) req.getRequestData());
            ResponseMessage<LogEntry[]> response = new CallbackProxy().onResponse(matchingEntries);
            //ResponseMessage<LogEntry[]> response = new ResponseMessage<LogEntry[]>(matchingEntries);
            byte[] data = response.marshall();
            return new InvokerAnswer(true, data);
        }
    }

}
