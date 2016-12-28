/**
 * Created by bresai on 2016/12/28.
 */
public class Node {
    private Integer num;
    private Integer weight;
    private Node left;
    private Node right;

    public Node(Integer num, Integer weight) {
        this.num = num;
        this.weight = weight;
        this.left = null;
        this.right = null;
    }

    public Node(Integer weight) {
        this(null, weight);
    }

    public Integer getNum() {
        return num;
    }

    public Integer getWeight() {
        return weight;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
