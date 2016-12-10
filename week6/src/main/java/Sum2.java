import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/12/2.
 */
public class Sum2 {

    private Map<Long,Integer> list;
    private Integer count = 0;

    public Sum2(String filename) {
        list = new HashMap<>();
        try{
            readFile(filename);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readFile(String filename) throws IOException {
        File file = new File(filename);
        FileInputStream stream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String number;
        while ((number = reader.readLine()) != null){
            Long tmp = Long.parseLong(number);
            if (list.containsKey(tmp)){
                list.put(tmp,list.get(tmp) + 1);
            }else{
                list.put(tmp, 1);
            }
        }
    }

    public void countSum(){
        for ( Long t = -10000L; t <= 10000L; t++){
            int num = sum2(t);
            count = count + num;
        }
    }
    private int sum2(Long t){
        for (Map.Entry<Long, Integer> number : list.entrySet()){
            Long y = t - number.getKey();
            if (list.containsKey(y) && ! y.equals(number.getKey())){
                return  1;
            }
        }
        return 0;
    }

    public static void main(String[] args){
        Sum2 object = new Sum2("week6/src/main/resources/2sum.txt");
        object.countSum();
        System.out.print(object.count);
    }
}
