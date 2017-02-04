import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by bresai on 2017/2/4.
 */
public class Sat2 {
    private int length;
    private Set<Integer>[] graph;
    private Set<Integer>[] reversedGraph;
    private Stack<Integer> stack;
    private HashSet<Integer> seen;
    public Sat2(String filename) {
        try {
            readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String filename) throws IOException{
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        length = Integer.parseInt(reader.readLine());
        graph = new LinkedHashSet[2*length];
        reversedGraph = new LinkedHashSet[2*length];
        stack = new Stack<>();

        String line;
        while ((line = reader.readLine()) != null){
            String[] split = line.split(" ");

            int x = getIndex(Integer.parseInt(split[0]));
            int y = getIndex(Integer.parseInt(split[1]));

            int xBar = getBarIndex(x);
            int yBar = getBarIndex(y);

            if (graph[xBar] == null){
                graph[xBar] = new LinkedHashSet<>();
            }

            if (graph[yBar] == null){
                graph[yBar] = new LinkedHashSet<>();
            }

            graph[xBar].add(y);
            graph[yBar].add(x);

            if (reversedGraph[xBar] == null){
                reversedGraph[xBar] = new LinkedHashSet<>();
            }

            if (reversedGraph[yBar] == null){
                reversedGraph[yBar] = new LinkedHashSet<>();
            }

            reversedGraph[xBar].add(y);
            reversedGraph[yBar].add(x);
        }
    }

    private int getIndex(int vertex){
        return vertex > 0 ? 2*(vertex - 1) : 2*(Math.abs(vertex) - 1) + 1;
    }

    private int getBarIndex(int vertex){
        return vertex % 2 == 0 ? vertex + 1 : vertex - 1;
    }

    public boolean scc(){
        seen = new HashSet<>();
        for (int i = 0; i < 2*length; i++){
            if (seen.contains(i) || reversedGraph[i] == null ) continue;
            dfs(reversedGraph, i, stack);
        }

        seen = new HashSet<>();
        for (int vertex : stack){
            Stack<Integer> tmp = new Stack<>();
            if (seen.contains(vertex) || graph[vertex] == null ) continue;
            dfs(graph, vertex, tmp);
            if (! checkSat(tmp)) return false;
        }

        return true;
    }

    private void dfs(Set<Integer>[] graph, int index, Stack<Integer> stack){
        seen.add(index);

        for (Integer vertex : graph[index]){
            if (seen.contains(vertex) || graph[vertex] == null) continue;
            dfs(graph, vertex, stack);
        }

        stack.add(index);
    }

    private boolean checkSat(Stack<Integer> scc){
        Set<Integer> set = new HashSet<>();

        for (Integer vertex : scc){
            if (set.contains(vertex/2)) return false;
            set.add(vertex/2);
        }

        return true;
    }

    public static void main(String[] args){
        Sat2 object = new Sat2("week2-8/src/main/resources/2sat1.txt");
        System.out.print(object.scc() + " ");

        object = new Sat2("week2-8/src/main/resources/2sat2.txt");
        System.out.print(object.scc() + " ");

        object = new Sat2("week2-8/src/main/resources/2sat3.txt");
        System.out.print(object.scc() + " ");

        object = new Sat2("week2-8/src/main/resources/2sat4.txt");
        System.out.print(object.scc() + " ");

        object = new Sat2("week2-8/src/main/resources/2sat5.txt");
        System.out.print(object.scc() + " ");

        object = new Sat2("week2-8/src/main/resources/2sat6.txt");
        System.out.print(object.scc());

    }
}
