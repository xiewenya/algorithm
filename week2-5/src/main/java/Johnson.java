import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2017/1/13.
 */
public class Johnson {
    private int vertex;
    private int edge;
    private Integer[] cache;
    private Integer[][] distance;
    private List<Edge> list;
    public Johnson(String filename) {
        try {
            readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String filename) throws IOException {
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String[] split = reader.readLine().split(" ");
        vertex = Integer.parseInt(split[0]);
        edge = Integer.parseInt(split[1]);


        list = new LinkedList<>();

        String line;
        while ( (line = reader.readLine()) != null){
            String[] splitted = line.split(" ");
            int i = Integer.parseInt(splitted[0]);
            int j = Integer.parseInt(splitted[1]);
            list.add(new Edge(i, j, Integer.parseInt(splitted[2])));
        }

        //add the edge at the beginngin of the list to avoid visit tail every time
        for (int i = 1; i <= vertex; i++){
            list.add(0, new Edge(vertex + 1, i, 0));
        }
    }

    private Boolean BellmanFord(){
        cache = new Integer[vertex+2];
        cache[vertex + 1] = 0;
        cache[0] = 0;
        IntStream.range(1, vertex+1).boxed().forEach(i -> cache[i] = Integer.MAX_VALUE);

        for (int i = 1; i <= vertex; i++ ){
            for (Edge edge : list){
                //if edge is added to the beginning of the list, this line is not needed
                //if (cache[edge.getStart()] == Integer.MAX_VALUE) continue;

                if (cache[edge.getStart()] + edge.getWeight() < cache[edge.getEnd()]){
                    cache[edge.getEnd()] = cache[edge.getStart()] + edge.getWeight();
                }
            }
        }

        for (Edge edge : list){
            if (cache[edge.getStart()] + edge.getWeight() < cache[edge.getEnd()]){
                return false;
            }
        }

        return true;
    }

    public Integer Djistra(){
        if (! BellmanFord()) return null;

        distance = new Integer[vertex+1][vertex+1];

        for (int i = 0; i <= vertex; i++){
            for(int j = 0; j <= vertex; j++){
                if (i == j){
                    distance[i][j] = 0;
                } else{
                    distance[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        Map<Integer, List<Edge>> map = new HashMap<>();

        for (Edge edge : list){
            edge.setWeight(edge.getWeight() + cache[edge.getStart()] - cache[edge.getEnd()]);
            if (! map.containsKey(edge.getStart())) {
                map.put(edge.getStart(), new LinkedList<>());
            }

            map.get(edge.getStart()).add(edge);
        }

        for (int i = 1; i <= vertex; i++){
            PriorityQueue<Edge> heap = new PriorityQueue<>(edge,
                    (edge1,edge2) -> edge1.getWeight() < edge2.getWeight() ? -1 : 1);

            boolean[] seen = new boolean[vertex + 1];

            heap.offer(new Edge(i,i,0));
            while (! heap.isEmpty()){
                Edge tmp = heap.poll();
                if (seen[tmp.getEnd()]) continue;
                distance[i][tmp.getEnd()] = tmp.getWeight();
                seen[tmp.getEnd()] = true;
                for (Edge edge : map.get(tmp.getEnd())){
                    heap.offer(new Edge(edge.getStart(), edge.getEnd(), edge.getWeight() + distance[i][edge.getStart()]));
                }
            }

            for (int j = 1; j <= vertex; j++){
                if (distance[i][j] != Integer.MAX_VALUE)
                    distance[i][j] = distance[i][j] - cache[i] + cache[j];
            }

        }

        return findMin();
    }

    private Integer findMin(){
        Integer min = Integer.MAX_VALUE;
        for (int i=1; i <=vertex; i++){
            for (int j=1; j <=vertex; j++) {
                if (i != j && distance[i][j] < min) {
                    min = distance[i][j];
                }
            }
        }

        return min;
    }

    public static void main(String[] args) {
        Johnson object = new Johnson("week2-5/src/main/resources/g1.txt");
        System.out.println(object.Djistra());

        object = new Johnson("week2-5/src/main/resources/g2.txt");
        System.out.println(object.Djistra());

        object = new Johnson("week2-5/src/main/resources/g3.txt");
        System.out.println(object.Djistra());
    }
}

