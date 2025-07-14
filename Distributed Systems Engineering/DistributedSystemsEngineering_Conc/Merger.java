import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class Merger extends BasicThread implements Runnable {
    List<Long> values;
    public Merger(ArrayList<CustomBlockingQueue<Long>> inputQueues, ArrayList<CustomBlockingQueue<Long>> outputQueues) {
        super(inputQueues, outputQueues);
    }

    @Override
    public void run() {
        HashSet<Long> seenValues = new HashSet<>();

        try{
            // Initially take 1 value from each input
            values = new ArrayList<>();
            int queueIndex = 0;
            while (true){
                // Take 1 value from each input
                for (CustomBlockingQueue<Long> queue : inputQueues) {
                    Long value = queue.take();
                    values.add(value);
                }
                // Check if the values are distinct
                if (values.size() == new HashSet<>(values).size()){
                    // If they are the same, then 3 distinct values. Sort and place in output
                    Collections.sort(values);
                    for (Long value: values){
                        outputQueues.get(0).put(value);
                    }
                    values.clear();
                }
                else{
                    // If those are not the same then we have atleast one dupe in our current values
                    // we use a round robin approach, replacing each value with the next one from
                    // each respective input queue, starting from the first one
                    while (!(values.size() == new HashSet<>(values).size())) {
                        Long newValue = inputQueues.get(queueIndex).take();
                        values.set(queueIndex, newValue);
                        queueIndex = (queueIndex + 1) % inputQueues.size();
                    }

                    Collections.sort(values);
                    for (Long value: values){
                        outputQueues.get(0).put(value);
                    }
                    values.clear();
                }
            }



        /*
            // The smallest value for each queue will always be the one at the end so
            // we can get the smallest value of each queue, add them to an array
            // pick the smallest one, and then update the array with the values
            // of the queues that had that value as their smallest
            Long[] smallestValues = {inputQueues.get(0).take(), 
                                    inputQueues.get(1).take(), 
                                    inputQueues.get(2).take()};
            
            while (true) {
                // Get the smallest value
                Long minimum = Long.MAX_VALUE;
                for (int i=0; i<3; i++){
                    if (smallestValues[i] < minimum) minimum = smallestValues[i];
                } 

                // We add it to the output if it wasn't previously added, and update seenValues
                if (!seenValues.contains(minimum)){
                    outputQueues.get(0).put(minimum);
                    seenValues.add(minimum);
                } 

                // Update smallestValues
                for (int i=0; i<3; i++){
                    if (smallestValues[i] == minimum) smallestValues[i] = inputQueues.get(i).take();
                }
            }
        */
        } catch (InterruptedException e) {

        }
    }
}
