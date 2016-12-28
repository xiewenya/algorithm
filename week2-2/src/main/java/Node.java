/**
 * Created by bresai on 2016/12/26.
 */
public class Node {
    private int num;
    private String code;

    public Node(int num, String[] codes) {
        this.num = num;
        StringBuilder builder = new StringBuilder();
        for (String code : codes){
            builder.append(code);
        }
        this.code = builder.toString();
    }

    public int getNum() {
        return num;
    }

    public String getCode() {
        return code;
    }
}
