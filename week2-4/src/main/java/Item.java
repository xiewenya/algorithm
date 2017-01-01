/**
 * Created by bresai on 2017/1/1.
 */
public class Item {
    private int num;
    private int value;
    private int weight;

    public Item(int num, int value, int weight) {
        this.num = num;
        this.value = value;
        this.weight = weight;
    }

    public int getNum() {
        return num;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }
}
