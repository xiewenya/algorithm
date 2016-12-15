package coursera.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by bresai on 2016/11/7.
 */
public class MinCut {

    private Map<Integer, LinkedList> adjcentList;
    private Map<Integer, Integer> relation;

    public MinCut() {
    }

    public static Map<Integer, LinkedList> readFromFile(String fileName) throws IOException {
        Map<Integer, LinkedList> adjcentList = new HashMap<Integer, LinkedList>(200);

        FileReader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null){
            String[] splitted = line.split("\t");
            LinkedList<String> list = new LinkedList<String>(Arrays.asList(splitted));
            Integer index = Integer.parseInt(list.remove(0));
            adjcentList.put(index, list);
        }
        return adjcentList;
    }

    @SuppressWarnings("unchecked")
    public Integer minCut(){
        while (adjcentList.size() > 2){
            //get random start vertex
            Integer start = randomVertexStart(adjcentList);
            LinkedList<String> edges = adjcentList.get(start);
            //get random end vertex
            Integer end = randomVertexEnd(edges);

            LinkedList<String> edgesToEnd = adjcentList.get(end);

            Iterator<String> iterator = edges.iterator();

//            System.out.println("===================");
//            System.out.println(start);
//            System.out.println(end);
//            System.out.println(edges);
//            System.out.println(edgesToEnd);

            while(iterator.hasNext()){
                String vertex = iterator.next();

                List<Integer> candidates = getMatchValues(end);

                if (candidates.contains(Integer.parseInt(vertex))){
                    iterator.remove();
                }
            }

            if ( edgesToEnd != null){
                for(String vertex: edgesToEnd){
                    List<Integer> candidates = getMatchValues(start);
                    if (! candidates.contains(Integer.parseInt(vertex))){
                        edges.add(vertex);
                    }
                }
            }


            relation.put(end, start);

            for(Map.Entry<Integer, Integer> entry : relation.entrySet()){
                if (entry.getValue().equals(end)){
                    entry.setValue(start);
                }
            }

            adjcentList.remove(end);

//            System.out.println(relation);
//            System.out.println(adjcentList.get(start));

        }

        return countEdge(adjcentList);
    }

    private List<Integer> getMatchValues(Integer vertex) {
        List<Integer> candidates = new ArrayList<Integer>(200);
        for (Map.Entry<Integer, Integer> entry : relation.entrySet()){
            if (entry.getValue().equals(vertex)){
                candidates.add(entry.getKey());
            }
        }
        return candidates;
    }

    public void setAdjcentList(Map<Integer, LinkedList> adjcentList) {
        this.adjcentList = new HashMap<Integer, LinkedList>(200);

        for (Map.Entry<Integer, LinkedList> entry : adjcentList.entrySet()){
            this.adjcentList.put(entry.getKey(), new LinkedList(entry.getValue()));
        }
    }

    public void setRelation(Map<Integer, Integer> relation) {
        this.relation = new HashMap<Integer, Integer>(relation);
    }

    public Map<Integer, LinkedList> getAdjcentList() {
        return adjcentList;
    }

    public Map<Integer, Integer> getRelation() {
        return relation;
    }

    private Integer countEdge(Map<Integer, LinkedList> map){
        Integer min = 9999;
        for(Map.Entry<Integer, LinkedList> entry : map.entrySet()){
            min = entry.getValue().size() < min ? entry.getValue().size() : min;
        }

        return min;
    }

    private Integer randomVertexStart(Map<Integer, LinkedList> map){
        Random generator = new Random();
        Object[] entries = map.keySet().toArray();
        return (Integer)entries[generator.nextInt(entries.length)];
    }

    private Integer randomVertexEnd(LinkedList list){
        Random generator = new Random();
        Integer vertex = Integer.parseInt(list.get(generator.nextInt(list.size())).toString());

        //if it is already merged, return the vertex this one merged into
        return relation.get(vertex);
    }


    public static void main(String[] args) throws IOException {

        String fileName = "week3/src/main/resources/kargerMinCut.txt";

        //init
        Map<Integer,LinkedList> adjecentList = MinCut.readFromFile(fileName);

        Map<Integer, Integer> relation = new HashMap<Integer, Integer>(200);

        for(int i = 1; i <= 200; i++){
            relation.put(i,i);
        }

        Integer result = 9999;
        MinCut minCut = new MinCut();
        for(int i = 220000; i > 0; i--){
            minCut.setAdjcentList(adjecentList);
            minCut.setRelation(relation);
//            System.out.println(System.nanoTime() - start);

            Integer cut = minCut.minCut();

            result = cut < result? cut: result;
            System.out.println(i);
            System.out.println(result);
        }

        System.out.println(result);
    }

}
