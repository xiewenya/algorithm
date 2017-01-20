import java.util.List;

/**
 * Created by bresai on 2017/1/20.
 */
public class S {
    private List<Integer> list;
    public S(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return list;
    }

    public boolean equals(S s) {
        return (list.size() == s.list.size() && list.containsAll(s.list));
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
