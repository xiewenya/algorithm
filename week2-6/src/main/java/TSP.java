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
    private List<S> sList;
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
        String line;
        while ((line = reader.readLine()) != null) {
            String[] splited = line.split(" ");
            list.add(new Location(Double.parseDouble(splited[0]), Double.parseDouble(splited[1])));
        }
    }

    public void combination(int n, int total, int begin, List<Integer> result) {
        if (n == 0) {//如果够n个数了，输出b数组
            S s = new S(result);
            sList.add(s);
            return;
        }

        for (int i = begin; i <= total; i++) {
            List<Integer> tmp = new ArrayList<>(result);
            tmp.add(i);
            combination(n - 1, total, i + 1, tmp);
        }
    }

    public Double tsp() {
        Map<S, Map<Integer, Double>> mapCached = new HashMap<>();

        for (int i = 1; i <= length; i++){
            List<Integer> tmp = Collections.singletonList(i);
            Map tMap = new HashMap();
            tMap.put(i, i == 1 ? 0 : Double.MAX_VALUE);
            mapCached.put(new S(tmp), tMap);
        }

        for (int k = 2; k <= length; k++) {
            System.out.println(k);
            List<Integer> result = new ArrayList<>(k);
            result.add(1);
            //get all combination of S;
            sList = new LinkedList<>();
            combination(k - 1, length, 2, new ArrayList<>(result));

            Map<S, Map<Integer, Double>> map = new HashMap<>();
            for (S s : sList) {
                if (! map.containsKey(s)){
                    map.put(s, new HashMap<>());
                }
                for (int j : s.getList()){
                    if (j == 1) continue;
                    List<Integer> tmp = new ArrayList<Integer>(s.getList());
                    tmp.remove(tmp.indexOf(j));
                    S sTmp = new S(tmp);
                    Map<Integer, Double> tList = new HashMap<>();
                    for (S test : mapCached.keySet()){
                        if (test.hashCode() == sTmp.hashCode()){
                            tList = mapCached.get(test);
                        }
                    }
                    Double res = findMin(j, tList);
                    map.get(s).put(j, res);
                }
            }

            mapCached = map;
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
            Double distance = cached.get(key) + p1.distance(p2);
            min = distance < min ? distance : min;
        }
        return min;
    }

    public static void main(String[] args) {
        TSP tsp = new TSP("week2-6/src/main/resources/tsp.txt");
        System.out.println(tsp.tsp());
//        tsp.sList = new LinkedList<>();
//        System.out.println(tsp.sList.size());
//
//        int[] test = {0,1};
//        S compare = new S(test);
//        for (S s : tsp.sList){
//            if (s.equals(compare)){
//                System.out.println(Arrays.toString(s.getList()));
//            }
//        }
    }
}
