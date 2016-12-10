import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Administrator on 2016/12/2.
 */
public class Median {
    private PriorityQueue<Integer> minHeap;
    private PriorityQueue<Integer> maxHeap;
    private Long sum = 0L;
    public Median(String filename) throws IOException {
        minHeap = new PriorityQueue<>(10000);
        maxHeap = new PriorityQueue<>(10000, Collections.reverseOrder());
        readFile(filename);
     }

    private void readFile(String filename) throws IOException {
        File file = new File(filename);
        InputStreamReader stream = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(stream);
        String number;
        while ((number = reader.readLine()) != null){
            if (maxHeap.isEmpty()){
                maxHeap.add(Integer.parseInt(number));
                sum = Long.parseLong(number);
                continue;
            }
            insert(Integer.parseInt(number));
            balanceHeap();
            sum += getMedian();
        }

        System.out.print(sum);
    }

    private void insert(int number){
        if (number < maxHeap.peek()){
            maxHeap.add(number);
        }else {
            minHeap.add(number);
        }
    }

    private void balanceHeap(){
        if (minHeap.size() - maxHeap.size() > 1){
            maxHeap.add(minHeap.poll());
        }
        if (maxHeap.size() - minHeap.size() > 1){
            minHeap.add(maxHeap.poll());
        }
    }

    private int getMedian(){
        if (minHeap.size() - maxHeap.size() >= 1){
            return minHeap.peek();
        } else{
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) throws IOException {
        Median object = new Median("week6/src/main/resources/Median.txt");
    }
}
