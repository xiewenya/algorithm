import java.io.*;
import java.util.*;

/**
 * Created by bresai on 2016/11/13.
 */
public class DijkstraShortestPath {
    private Map<Integer, List<Node>> graph;
    private Queue<Node> heap;
    private Integer length;
    private List<Node> nodes;
    private List<Boolean> alreadySeen;

    public DijkstraShortestPath(String filename, Integer length) {
        this.length = length;
        heap = new PriorityQueue<>(length, new HeapComparator());
        nodes = new ArrayList<>(length);
        initHeap();
        initAlreadySeen();
        readFromFile(filename);
    }

    public void process(){
        while (!heap.isEmpty()){
            Node node = heap.poll();
            if (alreadySeen.get(node.getNode())) continue;
            nodes.add(node);
            alreadySeen.set(node.getNode(), true);
            greetyNode(node);
        }
    }
    private void greetyNode(Node node){
        for (Node single : nodes){
            List<Node> edges = graph.get(single.getNode());
            for (Node edge : edges){
                heap.offer(new Node(edge.getNode(), single.getDistance() + edge.getDistance()));
            }
        }
    }

    private void initAlreadySeen(){
        alreadySeen = new ArrayList<>(length);
        for( int i = 0; i <  length; i++){
            alreadySeen.add(false);
        }
    }
    private void initHeap(){
        heap.offer(new Node(0,0));
        for ( int i = 1; i < length; i++){
            Node node = new Node(i, 100000);
            heap.offer(node);
        }
    }
    private void readFromFile(String filename){
        graph = new HashMap<>(length);
        File file = new File(filename);
        FileInputStream inputStream = null;
        try{
            inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null){
                String[] splited = line.split("\t");
                Integer index = Integer.parseInt(splited[0]);
                List<Node> list = new ArrayList<>();
                for (int i = 1; i < splited.length; i++){
                    String[] nodeSplited = splited[i].split(",");
                    Node node = new Node(Integer.parseInt(nodeSplited[0]) - 1, Integer.parseInt(nodeSplited[1]));
                    list.add(node);
                }
                graph.put(index - 1, list);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        DijkstraShortestPath object = new DijkstraShortestPath("week5/src/main/resources/dijkstraData.txt", 200);
        object.process();
//        System.out.println(object.nodes);
        Collections.sort(object.nodes, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getNode() > o2.getNode()? 1 : -1;
            }
        });

        Integer[] targets = {7,37,59,82,99,115,133,165,188,197};
        for (Integer target : targets){
            System.out.print(object.nodes.get(target - 1).getDistance());
            System.out.print(",");
        }
    }
}

class Node{
    private Integer node;
    private Integer distance;

    public Node(Integer node, Integer distance) {
        this.node = node;
        this.distance = distance;
    }

    public Integer getNode() {
        return node;
    }

    public Integer getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Node{" +
                "node=" + (node+1) +
                ", distance=" + distance +
                '}';
    }
}
class HeapComparator implements Comparator<Node>{

    @Override
    public int compare(Node node1, Node node2) {
        return node1.getDistance() < node2.getDistance()? -1 : 1;
    }
}