import java.io.*;

/**
 * Created by bresai on 2017/1/24.
 */
public class TspHeustic {

    private int length;
    private Point[] list;
    private Point current;
    private boolean[] seen;
    private double distance = 0.0;

    public TspHeustic(String filename) {
        try {
            readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String filename) throws IOException{
        File file = new File(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(inputStreamReader);

        length = Integer.parseInt(reader.readLine());

        String line;
        list = new Point[length];
        seen = new boolean[length];
        while ((line = reader.readLine()) != null){
            String[] splited = line.split(" ");
            list[Integer.parseInt(splited[0]) - 1] = new Point(
                    Double.parseDouble(splited[1]),
                    Double.parseDouble(splited[2]));
        }
    }

    public void heustic(){

        current = list[0];
        int index = 0;
        seen[0] = true;
        for (int i = 1; i < length; i++){
            int idx = 0;
            double min = Double.MAX_VALUE;
            for (int j = 0; j < length; j++){
                if (seen[j] || j == index){
                    continue;
                }
                double distance = current.distance(list[j]);
                if (distance < min){
                    min = distance;
                    idx = j;
                }
            }

            distance += Math.sqrt(min);
            current = list[idx];
            index = idx;
            seen[idx] = true;
        }

        distance += Math.sqrt(current.distance(list[0]));
    }

    public static void main(String[] args){
        TspHeustic object = new TspHeustic("week2-7/src/main/resources/nn.txt");
        object.heustic();
        System.out.print(object.distance);
    }
}
