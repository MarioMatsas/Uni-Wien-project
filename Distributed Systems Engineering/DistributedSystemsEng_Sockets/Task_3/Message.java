package Task_3;


import java.io.Serializable;

public class Message implements Serializable{
    private int number;
    private String senderName;
    private long timestamp;
    private byte[] dataBlob;

    public Message(int number, String senderName, long timestamp, byte[] dataBlob) {
        this.number = number;
        this.senderName = senderName;
        this.timestamp = timestamp;
        this.dataBlob = dataBlob;
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

    public byte[] getDataBlob() {
        return this.dataBlob;
    }

    public void setDataBlob(byte[] dataBlob) {
        this.dataBlob = dataBlob;
    }

}