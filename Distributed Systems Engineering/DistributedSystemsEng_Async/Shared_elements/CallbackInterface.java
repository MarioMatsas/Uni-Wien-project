package Shared_elements;
public interface CallbackInterface {
    public ResponseMessage<LogEntry[]> onResponse(LogEntry[] entries);
}
