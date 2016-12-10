import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bresai on 16/9/18.
 */
public class CountInversion {

    public static Long countAndSortInversion(List<Integer> list, Long count){
        if (list.isEmpty() || list.size() == 1){
            return count;
        }

        List<Integer> sortedLeft = list.subList(0,list.size()/2);
        List<Integer> sortedRight = list.subList(list.size()/2, list.size());
        count = countAndSortInversion(sortedLeft, count);
        count = countAndSortInversion(sortedRight, count);

        Integer left = 0;
        Integer right = 0;
        List<Integer> result = new ArrayList<>();
        while(true){
            if (left == sortedLeft.size() && right == sortedRight.size()){
                break;
            } else if (left == sortedLeft.size() && right < sortedRight.size()){
                result.add(sortedRight.get(right));
                right++;
            } else if (left < sortedLeft.size() && right == sortedRight.size()){
                result.add(sortedLeft.get(left));
                left++;
            } else{
                if (sortedLeft.get(left) < sortedRight.get(right)){
                    result.add(sortedLeft.get(left));
                    left++;
                } else{
                    result.add(sortedRight.get(right));
                    right++;
                    count += sortedLeft.size() - left;
                }
            }
        }
        for (int i=0;i< result.size();i++){
            list.set(i,result.get(i));
        }
        return count;
    }


    public static void main(String[] args) throws FileNotFoundException {
//        List<Integer> list = new ArrayList<>(Arrays.asList(10,9,8,7,6,5,4,3,2,1));
        List<Integer> list = Util.readFromFile("week1/src/main/resources/IntegerArray.txt");

        Long count = countAndSortInversion(list, 0L);
        System.out.println(count);
        System.out.println(list);
    }


}
