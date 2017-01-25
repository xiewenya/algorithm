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
    private List<Integer> sList;
    private Double[][] distanceCache;
    public TSP(String filename) {
        try {
            readFormFile(filename);
            initDistance();
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

    private void initDistance(){
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                distanceCache[i][j] = list.get(i).distance(list.get(j));
            }
        }
    }

    public void combination(int n, int total, int begin, int result) {
        if (n == 0) {//如果够n个数了，输出
            sList.add(result);
            return;
        }

        for (int i = begin; i < total; i++) {
            combination(n - 1, total, i + 1, 1 << i | result);
        }
    }

    public Double tsp() {
        Map<Integer, Map<Integer, Double>> mapCached = new HashMap<>();

        Map<Integer, Double> tMap = new HashMap<>();
        tMap.put(0, 0.0);
        mapCached.put(1,tMap);

        for (int k = 2; k <= length; k++) {
            System.out.println(k);
            //get all combination of S;
            sList = new LinkedList<>();
            combination(k - 1, length, 1, 1);
            Map<Integer, Map<Integer, Double>> map = new HashMap<>(sList.size());
            for (Integer num : sList) {
                if (! map.containsKey(num)){
                    map.put(num, new HashMap<>());
                }

                for (int j = 1; j < k; j++){
                    int index = 0;
                    Integer iTmp = num;
                    int shift = 0;
                    while (index < j){
                        iTmp = (iTmp >> 1);
                        shift++;
                        if ((iTmp  & 1) == 1){
                            index++;
                        }
                    }
                    Integer tmp =  1 << shift ^ num;

                    Double res = findMin(shift, mapCached.get(tmp));

                    map.get(num).put(shift, res);
                }
            }

            mapCached = map;
        }

        for (Map<Integer,Double> map : mapCached.values()){
            return findMin(0, map);
        }

        return null;
    }

    private Double findMin(int j, Map<Integer, Double> cached){
        Double min = Double.MAX_VALUE;
        for (int key : cached.keySet()){
            if (key == j) continue;
            double distance = cached.get(key) + distanceCache[key][j];
            min = distance < min ? distance : min;
        }
        return min;
    }

    public static void main(String[] args) {
        TSP tsp = new TSP("week2-6/src/main/resources/tsp.txt");
        System.out.println(tsp.tsp());
        Integer result = 1;
//        //get all combination of S;
//        tsp.sList = new LinkedList<>();
//        tsp.combination(1, 3, 0, result);
//        System.out.println(tsp.tsp());
    }
}
