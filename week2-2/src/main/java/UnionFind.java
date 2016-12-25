import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/25.
 */
public class UnionFind {
    private List<Integer> list;
    private Map<Integer, Integer> roots;

    public UnionFind(Integer length) {
        list = new ArrayList<>(length);
//        list.stream().collect()
//        roots =
    }

    public Integer find(Integer n){
        Integer tmp = n;
        while (! list.get(tmp).equals(tmp)){
            tmp = list.get(tmp);
        }
        return tmp;
    }

    public void union(Integer m, Integer n){
        if (roots.get(m) > roots.get(n)){
            list.set(n, m);
            roots.put(m, roots.get(m) + roots.get(n));
            roots.remove(n);
        } else{
            list.set(m, n);
            roots.put(n, roots.get(m) + roots.get(n));
            roots.remove(m);
        }
    }
}
