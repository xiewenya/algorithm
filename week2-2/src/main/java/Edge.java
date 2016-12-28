/**
 * Created by bresai on 2016/12/25.
 */
public class Edge {
    private int start;
    private int end;
    private int weight;

    public Edge(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }
}
