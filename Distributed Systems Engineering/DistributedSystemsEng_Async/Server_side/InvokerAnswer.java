package Server_side;
public class InvokerAnswer {
    private final boolean sendBack;
    private final byte[] data;

    public InvokerAnswer(boolean sendback, byte[] data){
        this.data = data;
        this.sendBack = sendback;
    }

    public boolean getSendBack() {
        return this.sendBack;
    }


    public byte[] getData() {
        return this.data;
    }
}
