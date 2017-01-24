import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by bresai on 2017/1/20.
 */
public class TSP {
    private int length;
    private List<Location> list;
    private List<List<Integer>> sList;
    private Double[][] distanceCache;
    public TSP(String filename) {
        try {
            readFormFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFormFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        length = Integer.parseInt(reader.readLine());
        list = new ArrayList<>(length);
        distanceCache = new Double[length][length];
        String line;
        while ((line = reader.readLine()) != null) {
            String[] splited = line.split(" ");
            list.add(new Location(Double.parseDouble(splited[0]), Double.parseDouble(splited[1])));
        }
    }

    public void combination(int n, int total, int begin, List<Integer> result) {
        if (n == 0) {//如果够n个数了，输出b数组
            sList.add(result);
            return;
        }

        for (int i = begin; i <= total; i++) {
            List<Integer> tmp = new ArrayList<>(result);
            tmp.add(i);
            combination(n - 1, total, i + 1, tmp);
        }
    }

    public Double tsp() {
        Map<Integer, Map<Integer, Double>> mapCached = new HashMap<>();

        for (int i = 1; i <= length; i++){
            List<Integer> tmp = Arrays.asList(i);
            Map tMap = new HashMap();
            tMap.put(i, i == 1 ? 0 : Double.MAX_VALUE);
            mapCached.put(tmp.hashCode(), tMap);
        }

        for (int k = 2; k <= length; k++) {
            Long tStart = System.currentTimeMillis();
            System.out.println(k);
            List<Integer> result = new ArrayList<>(k);
            result.add(1);
            //get all combination of S;
            sList = new LinkedList<>();
            combination(k - 1, length, 2, new ArrayList<>(result));
            System.out.println(sList.size());
            Map<Integer, Map<Integer, Double>> map = new HashMap<>(result.size());
            Map<Integer, Double> minCached = new HashMap<>(result.size());
            for (List<Integer> list : sList) {
                int hash = list.hashCode();
                if (! map.containsKey(hash)){
                    map.put(hash, new HashMap<>());
                }
                for (int j : list){
                    if (j == 1) continue;
                    List<Integer> tmp = new ArrayList<>(list);
                    tmp.remove(tmp.indexOf(j));
                    Double res;
                    int tmpHash = tmp.hashCode();
                    if (minCached.containsKey(tmpHash)){
                        res = minCached.get(tmpHash);
                    }else{
                        res = findMin(j, mapCached.get(tmpHash));
                        minCached.put(tmpHash, res);
                    }
                    map.get(hash).put(j, res);
                }
            }

            mapCached = map;
            System.out.println("time used:" + (System.currentTimeMillis() - tStart));
        }

        for (Map<Integer,Double> map : mapCached.values()){
            return findMin(1, map);
        }

        return null;
    }

    private Double findMin(int j, Map<Integer, Double> cached){
        Double min = Double.MAX_VALUE;
        for (int key : cached.keySet()){
            if (key == j) continue;
            Location p1 = list.get(key - 1);
            Location p2 = list.get(j - 1);
            List tmp = Arrays.asList(p1, p2);
            Double distance;
            if (distanceCache[key - 1][j - 1] != null){
                distance = cached.get(key) + distanceCache[key - 1][j - 1];
            } else{
                distance = cached.get(key) + p1.distance(p2);
                distanceCache[key - 1][j - 1] = p1.distance(p2);
                distanceCache[j - 1][key - 1] = p1.distance(p2);
            }
            min = distance < min ? distance : min;
        }
        return min;
    }

    public static void main(String[] args) {
        TSP tsp = new TSP("week2-6/src/main/resources/tsp.txt");
        System.out.println(tsp.tsp());
//        List<Integer> result = new ArrayList<>(5);
//        result.add(1);
//        //get all combination of S;
//        tsp.sList = new LinkedList<>();
//        tsp.combination(5, 10, 2, new ArrayList<>(result));
//        System.out.println(tsp.tsp());
    }
}
