import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bresai on 16/9/19.
 */
public class Util {
    public static List<Integer> readFromFile(String name) throws FileNotFoundException {
        File f = new File(name);
        Scanner b = new Scanner(f);
        List<Integer> list = new ArrayList<>();
        while(b.hasNextLine()){
            list.add(Integer.parseInt(b.nextLine()));
        }
        return list;
    }
}
