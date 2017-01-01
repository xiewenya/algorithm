import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bresai on 2017/1/1.
 */
public class KnapsackDynamic {
    private int size;
    private int length;
    private List<Integer> values;
    private List<Integer> weights;
    private int[][] matrix;

    public KnapsackDynamic(String filename) {
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
        matrix = new int[length+1][size+1];
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null){
            String[] tmp = line.split(" ");
            values.add(Integer.parseInt(tmp[0]));
            Integer weight = Integer.parseInt(tmp[1]);
            weights.add(weight);
        }
    }

    public void dynamic(){
        //init weight for i = 0;
        for (int weight = 1; weight <= size; weight++){
            matrix[0][weight] = 0;
        }

        for(int i=1; i <= length; i++) {
            for (int weight = 1; weight <= size; weight++){
                int result;
                if (weight - weights.get(i - 1) <= 0){
                    result = matrix[i-1][weight];
                } else{
                    int case2 = matrix[i-1][weight-weights.get(i - 1)] + values.get(i - 1);
                    result = case2 > matrix[i-1][weight] ? case2 : matrix[i-1][weight];

                }

                matrix[i][weight] = result;
            }
        }
    }

    public static void main(String[] args){
        KnapsackDynamic object = new KnapsackDynamic("week2-4/src/main/resources/knapsack.txt");
        object.dynamic();
        System.out.println(object.matrix[object.length][object.size]);
    }
}
