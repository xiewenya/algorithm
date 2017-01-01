import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bresai on 2017/1/1.
 */
public class Knapsack {
    private int size;
    private int length;
    private List<Integer> values;
    private List<Integer> weights;
    private Map<Integer, Map<Integer, Integer>> map;

    public Knapsack(String filename) {
        try {
            readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] split = reader.readLine().split(" ");
        size = Integer.parseInt(split[0]);
        length = Integer.parseInt(split[1]);
        values = new ArrayList<>(length+1);
        weights = new ArrayList<>(length+1);
        map = new HashMap<>();
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null){
            String[] tmp = line.split(" ");
            values.add(Integer.parseInt(tmp[0]));
            Integer weight = Integer.parseInt(tmp[1]);
            weights.add(weight);
        }
    }

    public int recurrsive(int weight, int length){
        int result;
        if (map.containsKey(length) && map.get(length).containsKey(weight)){
            return map.get(length).get(weight);
        }
        if (length == 0) result = 0;
        else {
            if (weight - weights.get(length - 1) <= 0) {
                result = recurrsive(weight, length - 1);
            } else {
                result = Math.max(
                        recurrsive(weight, length - 1),
                        recurrsive(weight - weights.get(length - 1), length - 1) + values.get(length - 1)
                );
            }
        }
        if (!map.containsKey(length)) {
            map.put(length, new HashMap<>());
        }
        map.get(length).put(weight, result);
        return result;
    }

    public static void main(String[] args){
        Knapsack object = new Knapsack("week2-4/src/main/resources/knapsack_big.txt");
        System.out.println(object.recurrsive(object.size, object.length));
    }
}
