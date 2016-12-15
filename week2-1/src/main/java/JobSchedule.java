import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Administrator on 2016/12/15.
 */
public class JobSchedule {
    private PriorityQueue<Job> queue;
    private int length = 0;
    public JobSchedule(String filename, Comparator<Job> comparator) throws IOException {
        readFromFile(filename, comparator);
    }

    private void readFromFile(String filename, Comparator<Job> comparator) throws IOException {
        File file = new File(filename);
        InputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null){
            if (length == 0) {
                length = Integer.parseInt(line);
                this.queue = new PriorityQueue<Job>(length,comparator);
                continue;
            }
            String[] splited = line.split(" ");
            Job job = new Job(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            queue.offer(job);
        }
    }

    public long total(){
        long sum = 0;
        long completeTime = 0;
        while (!queue.isEmpty()){
            Job job = queue.poll();
            completeTime += job.getLength();
            sum += job.getWeight() * completeTime;
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        JobSchedule object1 = new JobSchedule("week2-1/src/main/resources/jobs.txt", new differentComparator());
        System.out.println(object1.total());

        JobSchedule object2 = new JobSchedule("week2-1/src/main/resources/jobs.txt", new ratioComparator());
        System.out.print(object2.total());
    }

}

class Job{
    private int weight;
    private int length;

    public Job(int weight, int length) {
        this.weight = weight;
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }
}

class differentComparator implements Comparator<Job> {
    @Override
    public int compare(Job o1, Job o2) {
        int differ1 = o1.getWeight() - o1.getLength();
        int differ2 = o2.getWeight() - o2.getLength();
        if (differ1 == differ2){
            return -1* compareWeight(o1.getWeight(), o2.getWeight());
        }else{
            return differ1 > differ2 ? -1 : 1;
        }
    }

    private int compareWeight(int weight1, int weight2){
        return weight1 <= weight2 ? -1 : 1;
    }
}

class ratioComparator implements Comparator<Job> {
    @Override
    public int compare(Job o1, Job o2) {
        double differ1 = ((double)o1.getWeight())/((double)o1.getLength());
        double differ2 = ((double)o2.getWeight())/((double)o2.getLength());
        if (differ1 == differ2){
            return -1 * compareWeight(o1.getWeight(), o2.getWeight());
        }else{
            return differ1 > differ2 ? -1 : 1;
        }
    }

    private int compareWeight(int weight1, int weight2){
        return weight1 <= weight2 ? -1 : 1;
    }
}
