import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Multiplier extends BasicThread implements Runnable{
    private int multiply_by;
    public Multiplier(ArrayList<CustomBlockingQueue<Long>> inputQueues, ArrayList<CustomBlockingQueue<Long>> outputQueues, int multiply_by) {
        super(inputQueues, outputQueues);
        this.multiply_by = multiply_by; 
    }

    @Override
    public void run() {
        try {
            // We use while true so that the thread can keep on working after
            // a new number has been added to the blocking queue
            while (true) {
                outputQueues.get(0).put(inputQueues.get(0).take() * multiply_by);
            }
        } catch (InterruptedException e) {

        }
    }

}
