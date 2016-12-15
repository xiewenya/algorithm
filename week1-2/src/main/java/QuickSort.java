import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bresai on 16/9/19.
 */
public class QuickSort {

    public Integer partition(List<Integer> list, Integer count, String type){
        if (list.isEmpty() || list.size() == 1){
            return count;
        }

        count += list.size() - 1;

        findPivot(list, type);
        Integer pivot = list.get(0);
        int i = 1;
        for (int j = 1; j < list.size(); j++){
            if (list.get(j) < pivot){
                swap(list, i, j);
                i++;
            }
        }

        swap(list, 0, i-1);

        count = partition(list.subList(0, i-1), count, type);
        count = partition(list.subList(i, list.size()), count, type);
        return count;
    }

    private void swap(List<Integer> list, Integer i, Integer j){
        Integer tmp = 0;
        tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    private void findPivot(List<Integer> list, String type) {
        switch (type){
            case "last": findPivotLast(list);break;
            case "middle": findPivotMiddle(list);break;
            default: break;
        }
    }

    private void findPivotLast(List<Integer> list){
        swap(list, list.size()-1, 0);
    }

    private void findPivotMiddle(List<Integer> list){
        List<Integer> pivots = new ArrayList<>();
        Integer middle = list.get(list.size()/2 + list.size()%2 - 1);
        Integer first = list.get(0);
        Integer last = list.get(list.size() - 1);
        pivots.add(first);
        pivots.add(middle);
        pivots.add(last);
        Collections.sort(pivots);
        if(pivots.get(1).equals(middle)){
            swap(list, list.size()/2 + list.size()%2 - 1, 0);
        } else if(pivots.get(1).equals(last)){
            swap(list, list.size()-1, 0);
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        String file = "week2/src/main/resources/QuickSort.txt";
        QuickSort quickSort = new QuickSort();
//        List<Integer> list = new ArrayList<>(Arrays.asList(3,9,8,4,6,10,2,5,7,1));
        List<Integer> list = Util.readFromFile(file);
        Integer count = quickSort.partition(list, 0, "first");
        System.out.println(count);
        System.out.println(list);

//        list = new ArrayList<>(Arrays.asList(3,9,8,4,6,10,2,5,7,1));
        list = Util.readFromFile(file);
        count = quickSort.partition(list, 0, "last");
        System.out.println(count);
        System.out.println(list);

//        list = new ArrayList<>(Arrays.asList(3,9,8,4,6,10,2,5,7,1));
        list = Util.readFromFile(file);
        count = quickSort.partition(list, 0, "middle");
        System.out.println(count);
        System.out.println(list);
    }
}
