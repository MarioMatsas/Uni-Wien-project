import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Copy extends BasicThread implements Runnable{

    public Copy(ArrayList<CustomBlockingQueue<Long>> inputQueues, ArrayList<CustomBlockingQueue<Long>> outputQueues) {
        super(inputQueues, outputQueues);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Long input = inputQueues.get(0).take();
                for (CustomBlockingQueue<Long> oq : outputQueues) {
                    oq.put(input);
                }
            }
        } catch (InterruptedException e) {
            
        }
    }
    

}
