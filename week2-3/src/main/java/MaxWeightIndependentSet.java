import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bresai on 2016/12/28.
 */
public class MaxWeightIndependentSet {
    private List<Vertex> list;
    private List<Integer> cache;
    private List<Vertex> result;
    private int length;

    public MaxWeightIndependentSet(String filename) {
        try {
            readFromFile(filename);
            result = new ArrayList<>(length);
            cache = new ArrayList<>(length);
            cache.add(0);
            cache.add(list.get(0).getWeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String filename) throws IOException {
        File file = new File(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        length = Integer.parseInt(reader.readLine());
        list = new ArrayList<>();
        String line;
        int i = 1;
        while ((line = reader.readLine()) != null){
            Vertex vertex = new Vertex(i, Integer.parseInt(line));
            list.add(vertex);
            i++;
        }
    }

    public void dynamic(){
        for (int i = 2; i <= length; i++){
            Vertex vertex = list.get(i - 1);
            int case1 = cache.get(i - 1);
            int case2 = cache.get(i - 2) + vertex.getWeight();
            cache.add(i, Math.max(case1, case2));
        }
        int i = length;
        while (i > 1){
            Vertex vertex = list.get(i - 1);
            if (cache.get(i - 1) >= cache.get(i - 2) + vertex.getWeight()){
                i -= 1;
            } else{
                result.add(vertex);
                i -= 2;
            }
        }
        if (i == 1){
            result.add(list.get(0));
        }
    }

    public List<Integer> checkResult(Integer[] input){
        List<Integer> check = new ArrayList<>(Arrays.asList(input));
        List<Integer> res = new ArrayList<>(check.size());
        for (Vertex vertex : result) {
            if (check.contains(vertex.getNum())){
                res.add(vertex.getNum());
            }
        }
        return res;
    }


    public static void main (String[] args){
        MaxWeightIndependentSet object = new MaxWeightIndependentSet("week2-3/src/main/resources/mwis.txt");
        object.dynamic();
        System.out.println(object.result);
        Integer[] array = {1,2,3,4,17,117,517,997};
        System.out.println(object.checkResult(array));
    }

}
