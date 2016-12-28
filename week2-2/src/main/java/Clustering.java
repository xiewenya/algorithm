import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 * Created by Administrator on 2016/12/25.
 */
public class Clustering {
    private PriorityQueue<Edge> list;
    private int length = 0;
    private UnionFind unionFind;
    private int maxSpacing = 0;
    public Clustering(String filename) {
        try {
            readFromFile(filename);
            unionFind = new UnionFind(length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String name) throws IOException {
        File file = new File(name);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        list = new PriorityQueue<>((o1, o2) -> o1.getWeight() <= o2.getWeight()? -1 : 1);
        length = Integer.parseInt(reader.readLine());
        while ((line = reader.readLine()) != null){
            String[] split = line.split(" ");
            list.offer(new Edge(
                    Integer.parseInt(split[0]) - 1,
                    Integer.parseInt(split[1]) - 1,
                    Integer.parseInt(split[2]))
            );
        }
    }

    public void greedy(int k){
        while (unionFind.getRoots().entrySet().size() >= k){

            Edge edge = list.poll();
            //the min spacing is the first edge make clustering num equal k
            //the max spacing is the last edge make clustering num equal k
            if (unionFind.getRoots().entrySet().size() == k){
                maxSpacing = edge.getWeight();
            }
            int m = unionFind.find(edge.getStart());
            int n = unionFind.find(edge.getEnd());
            if (m != n){
                unionFind.union(m, n);
            }
        }
    }

    public void getSpacing(){

        while (true) {
            Edge edge = list.poll();
        }
    }

    public static void main (String[] args){
        Clustering object = new Clustering("week2-2/src/main/resources/clustering.txt");
//        while (! object.list.isEmpty()){
//            System.out.println(object.list.poll().getWeight());
//        }
        object.greedy(4);
        System.out.println(object.maxSpacing);
//        System.out.println(object.unionFind.getRoots());
//        System.out.println(object.distances);
    }

}
