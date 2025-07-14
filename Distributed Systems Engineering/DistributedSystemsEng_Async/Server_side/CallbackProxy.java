package Server_side;
import Shared_elements.CallbackInterface;
import Shared_elements.LogEntry;
import Shared_elements.ResponseMessage;

public class CallbackProxy implements CallbackInterface{
    @Override
    public ResponseMessage<LogEntry[]> onResponse(LogEntry[] entries) {
        return new ResponseMessage<LogEntry[]>(entries);
    }
    
}
