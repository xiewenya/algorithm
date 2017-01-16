import java.io.*;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/13.
 */
public class AllPairsShortestPaths {
    private int vertex;
    private int edge;
    private Integer[][] cache;
    private int min = Integer.MAX_VALUE;
    public AllPairsShortestPaths(String filename) {
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
        cache = new Integer[vertex+1][vertex+1];
        String line = "";
        while ( (line = reader.readLine()) != null){
            String[] splitted = line.split(" ");
            int i = Integer.parseInt(splitted[0]);
            int j = Integer.parseInt(splitted[1]);
            cache[i][j] = Integer.parseInt(splitted[2]);
            min = Math.min(cache[i][j], min);
        }
    }

    public Integer floydWarshall(){
        for (int k = 1; k <= vertex; k++){
            for (int i = 1; i <= vertex; i++){
                if (i == k) continue;
                for (int j = 1; j <= vertex; j ++){
                    if (i == j || cache[i][k] == null || cache[k][j] == null) continue;
                    if (cache[i][j] == null || cache[i][k] + cache[k][j] < cache[i][j]){
                        cache[i][j] = cache[i][k] + cache[k][j];
                        min = Math.min(cache[i][j], min);
                    }
                }
            }
        }

//        for (int i = 1; i <= vertex; i++){
//            if (cache[i][i] <= 0){
//                return null;
//            }
//        }

        return min;
    }

    public static void main(String[] args){
        AllPairsShortestPaths object = new AllPairsShortestPaths("week2-5/src/main/resources/g1.txt");
        System.out.println(object.floydWarshall());

        object = new AllPairsShortestPaths("week2-5/src/main/resources/g2.txt");
        System.out.println(object.floydWarshall());

        object = new AllPairsShortestPaths("week2-5/src/main/resources/g3.txt");
        System.out.println(object.floydWarshall());
    }
}
