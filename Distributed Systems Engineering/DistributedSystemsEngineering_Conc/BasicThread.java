import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * 
 *  A basic class that containt an input and output queue.
 *  Since all other threads will also contain those, it makes
 *  sense for them to simply extend this class and add any 
 *  further details they require.
 */
public class BasicThread {
    protected ArrayList<CustomBlockingQueue<Long>> inputQueues;
    protected ArrayList<CustomBlockingQueue<Long>> outputQueues; 


    public BasicThread(ArrayList<CustomBlockingQueue<Long>> inputQueues, ArrayList<CustomBlockingQueue<Long>> outputQueues) {
        this.inputQueues = inputQueues;
        this.outputQueues = outputQueues;
    }
    
}
