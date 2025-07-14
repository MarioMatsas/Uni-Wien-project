import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

class Main{
    public static void main(String[] args) {
        // All the blockingQueues we will use
        /*
         * Multiplication
         */
        CustomBlockingQueue<Long> mult_input_2 = new CustomBlockingQueue<>();
        CustomBlockingQueue<Long> mult_input_3 = new CustomBlockingQueue<>();
        CustomBlockingQueue<Long> mult_input_5 = new CustomBlockingQueue<>();
        CustomBlockingQueue<Long> mult_output_2 = new CustomBlockingQueue<>();
        CustomBlockingQueue<Long> mult_output_3 = new CustomBlockingQueue<>();
        CustomBlockingQueue<Long> mult_output_5 = new CustomBlockingQueue<>();

        ArrayList<CustomBlockingQueue<Long>> input_2 = new ArrayList<>();
        input_2.add(mult_input_2);
        ArrayList<CustomBlockingQueue<Long>> input_3 = new ArrayList<>();
        input_3.add(mult_input_3);
        ArrayList<CustomBlockingQueue<Long>> input_5 = new ArrayList<>();
        input_5.add(mult_input_5);
        ArrayList<CustomBlockingQueue<Long>> output_2 = new ArrayList<>();
        output_2.add(mult_output_2);
        ArrayList<CustomBlockingQueue<Long>> output_3 = new ArrayList<>();
        output_3.add(mult_output_3);
        ArrayList<CustomBlockingQueue<Long>> output_5 = new ArrayList<>();
        output_5.add(mult_output_5);
        
        /*
         * Merging
         */
        CustomBlockingQueue<Long> merge_output = new CustomBlockingQueue<>();

        ArrayList<CustomBlockingQueue<Long>> merge_in = new ArrayList<>();
        merge_in.add(mult_output_2);
        merge_in.add(mult_output_3);
        merge_in.add(mult_output_5);
        ArrayList<CustomBlockingQueue<Long>> merge_out = new ArrayList<>();
        merge_out.add(merge_output);

        /*
         * Copying 
         */

        //CustomBlockingQueue<Long> copy_input = new CustomBlockingQueue<>();

        ArrayList<CustomBlockingQueue<Long>> copy_in = new ArrayList<>();
        //copy_in.add(copy_input);
        copy_in.add(merge_output);
        ArrayList<CustomBlockingQueue<Long>> copy_out = new ArrayList<>();
        copy_out.add(mult_input_2);
        copy_out.add(mult_input_3);
        copy_out.add(mult_input_5);
        CustomBlockingQueue<Long> print_input = new CustomBlockingQueue<>();
        copy_out.add(print_input);

        
        /*
         * Printing
         */
        
        ArrayList<CustomBlockingQueue<Long>> print_in = new ArrayList<>();
        ArrayList<CustomBlockingQueue<Long>> print_out = new ArrayList<>();
        print_in.add(print_input);
        
        

        // All the blocks we need to use according to the excercise image
        Merger merger = new Merger(merge_in, merge_out);
        Copy copy = new Copy(copy_in, copy_out);
        Printer printer = new Printer(print_in, print_out);
        Multiplier multiplier_2 = new Multiplier(input_2, output_2, 2);
        Multiplier multiplier_3 = new Multiplier(input_3, output_3, 3);
        Multiplier multiplier_5 = new Multiplier(input_5, output_5, 5);

        ExecutorService threadPool = Executors.newFixedThreadPool(6);

        threadPool.submit(multiplier_2);
        threadPool.submit(multiplier_3);
        threadPool.submit(multiplier_5);
        threadPool.submit(merger);
        threadPool.submit(copy);
        threadPool.submit(printer);
        /*// Threads
        Thread mult_2_thread = new Thread(multiplier_2);
        Thread mult_3_thread = new Thread(multiplier_3);
        Thread mult_5_thread = new Thread(multiplier_5);
        Thread merge_thread = new Thread(merger);
        Thread copy_thread = new Thread(copy);
        Thread print_thread = new Thread(printer);

        mult_2_thread.start();
        mult_3_thread.start();
        mult_5_thread.start();
        merge_thread.start();
        copy_thread.start();
        print_thread.start();*/

        // MAIN START
        merge_output.put(1L);
    }
}