import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;

public class Printer extends BasicThread implements Runnable{

    public Printer(ArrayList<CustomBlockingQueue<Long>> inputQueues, ArrayList<CustomBlockingQueue<Long>> outputQueues) {
        super(inputQueues, outputQueues);
    }

    @Override
    public void run() {
        try {
            int counter = 0;
            while (true){
                System.out.println(counter+". "+inputQueues.get(0).take());
                ++counter;
                if (counter == 50){
                    System.exit(0);
                }
            }
        } catch (InterruptedException e) {

        }    
        
    }
    
}
