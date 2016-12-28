import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2016/12/25.
 */
public class UnionFind {
    private List<Integer> list;
    private Map<Integer, Integer> roots;

    public Map<Integer, Integer> getRoots() {
        return roots;
    }

    public List<Integer> getList() {
        return list;
    }

    public UnionFind(Integer length) {
        list = IntStream.range(0, length).boxed().collect(Collectors.toList());
        roots = IntStream.range(0, length).boxed()
                .collect(Collectors.toMap(Function.identity(), i -> 1));
    }

    public Integer find(Integer n) {
        try {
            Integer tmp = n;
            while (!list.get(tmp).equals(tmp)) {
                tmp = list.get(tmp);
            }
            return tmp;
        } catch (Exception e) {
            System.out.println(n);
            return n;
        }
    }

    public void union(Integer m, Integer n) {
        try{
            if (roots.get(m) > roots.get(n)) {
                list.set(n, m);
                roots.put(m, roots.get(m) + roots.get(n));
                roots.remove(n);
            } else {
                list.set(m, n);
                roots.put(n, roots.get(m) + roots.get(n));
                roots.remove(m);
            }
        }catch (Exception e) {
            System.out.println(m);
            System.out.println(n);
        }
    }
}
