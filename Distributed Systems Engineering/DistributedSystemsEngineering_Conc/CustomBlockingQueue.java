import java.util.LinkedList;
import java.util.Queue;

public class CustomBlockingQueue<T> {
    private Queue<T> queue = new LinkedList<>();
    private final Object lock = new Object();
    /*
     *  We need to implement the take() and put() methods 
     *  since thse are the ones we use throughout the program
     */

     public T take() throws InterruptedException {
        synchronized(lock) {
            while (queue.isEmpty()){
                lock.wait(); 
            }
            return queue.remove();
        } 
    }
    
    public void put(T item) {
        synchronized(lock) { 
            queue.add(item);
            lock.notifyAll(); 
        }
    }

}
