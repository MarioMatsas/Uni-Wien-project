package Task_2;


import java.io.Serializable;

public class Message implements Serializable{
    private int number;
    private String senderName;
    private long timestamp;

    public Message(int number, String senderName, long timestamp) {
        this.number = number;
        this.senderName = senderName;
        this.timestamp = timestamp;

    }
    
    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
