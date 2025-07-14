package Clieant_side;

import Shared_elements.CallbackInterface;
import Shared_elements.LogEntry;
import Shared_elements.ResponseMessage;


public class Callback implements CallbackInterface{
    @Override
    public ResponseMessage<LogEntry[]> onResponse(LogEntry[] entries) {
        System.out.println("Entries found:");
        for (LogEntry entry: entries){
            System.out.println("- " + entry.getLogEntry());
        }
        
        return null; // For it to be uniform on client and server
    }
    
}
