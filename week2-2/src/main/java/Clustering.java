import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2016/12/25.
 */
public class Clustering {
    private List<Integer> cluster;

    public Clustering() {
        this.cluster = cluster;
    }

    private void readFromFile(String name) throws IOException {
        File file = new File(name);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = reader.readLine()) != null){

        }
    }


    public static void main (String[] args){
        Clustering object = new Clustering();
    }

}
