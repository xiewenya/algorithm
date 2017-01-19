/**
 * Created by bresai on 2017/1/18.
 */
public class Main {

    public static void main(String[] args){
        long s2 = System.currentTimeMillis();
        Johnson johnson = new Johnson("week2-5/src/main/resources/large.txt");
        johnson.Djistra();
        System.out.println(System.currentTimeMillis() - s2);

        long s1 = System.currentTimeMillis();
        FloydWarshall object = new FloydWarshall("week2-5/src/main/resources/large.txt");
        object.floydWarshall();
        System.out.println(System.currentTimeMillis() - s1);
    }
}
