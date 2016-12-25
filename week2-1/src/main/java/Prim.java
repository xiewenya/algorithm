import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2016/12/16.
 */
public class Prim {
    private PriorityQueue<Node> queue;
    private List<Boolean> proceed;
    private Map<Integer, Map<Integer, Integer>> list;
    private int nodes = 0;
    private int edges = 0;
    private Long sum = null;

    public Prim(String filename){
        try {
            readFromFile(filename, new WeightComparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initProceed(int length){
        proceed = new LinkedList<>();
        for (int i = 0; i <= length; i ++){
            proceed.add(false);
        }
    }
    private void readFromFile(String filename, Comparator<Node> comparator) throws IOException {
        File file = new File(filename);
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null){
            if (nodes == 0 && edges  == 0){
                String[] splitted = line.split(" ");
                nodes = Integer.parseInt(splitted[0]);
                edges = Integer.parseInt(splitted[1]);
                queue = new PriorityQueue<>(edges, comparator);
                list = new HashMap<>(nodes);
                initProceed(nodes);
            }else{
                String[] splitted = line.split(" ");
                int start = Integer.parseInt(splitted[0]);
                int end = Integer.parseInt(splitted[1]);
                int weight = Integer.parseInt(splitted[2]);
                addToList(start, end, weight);
            }
        }
    }

    //generate a adjacent list
    private void addToList(int start, int end, int weight){
        if (! list.containsKey(start)){
            list.put(start, new HashMap<>());
        }
        list.get(start).put(end, weight);

        if (! list.containsKey(end)){
            list.put(end, new HashMap<>());
        }
        list.get(end).put(start, weight);
    }

    public void getMst(){
        sum = 0L;
        proceed.set(1, true);
        for(Map.Entry<Integer, Integer> tmp : list.get(1).entrySet()) {
            //if already seen, skip
            if (proceed.get(tmp.getKey())) continue;
            queue.offer(new Node(tmp.getKey(), tmp.getValue()));
        }
        while (!queue.isEmpty()){
            Node node = queue.poll();
            if (proceed.get(node.getNum())) continue;
            sum += node.getWeight();
            proceed.set(node.getNum(), true);
            for(Map.Entry<Integer, Integer> tmp : list.get(node.getNum()).entrySet()) {
                if (proceed.get(tmp.getKey())) continue;
                queue.offer(new Node(tmp.getKey(), tmp.getValue()));
            }
        }
    }

    public static void main(String[] args){
        Prim obj = new Prim("week2-1/src/main/resources/edges.txt");
        obj.getMst();
        System.out.println(obj.sum);
    }
}

class Node{
    private int num;
    private int weight;

    public Node(int num,int weight) {
        this.num = num;
        this.weight = weight;
    }

    public Node(String line) {
    }

    public int getNum() {
        return num;
    }

    public int getWeight() {
        return weight;
    }
}

class WeightComparator implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        return o1.getWeight() <= o2.getWeight() ? -1 : 1;
    }
}