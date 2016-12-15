import java.io.*;
import java.util.*;

/**
 * Created by bresai on 2016/11/13.
 */
public class StrongConnected {

    public Integer length;
    public ArrayList<Boolean> alreadySeen;
    public ArrayList<Integer> leader;
    public ArrayList<ArrayList<Integer>> graph;
    public ArrayList<ArrayList<Integer>> convertedGraph;
    public Stack<Integer> finishedTime = new Stack<>();
    public Integer currentNode;

    public StrongConnected(String filename, int len) {
        length = len;
        initLeader();
        loadGraph(filename);
        getReverse(filename);
    }

    private void initAlreadySeen() {
        alreadySeen = new ArrayList<Boolean>(length);
        for (int i = 0; i < length; i++) {
            alreadySeen.add(i, false);
        }
    }

    private void initLeader() {
        leader = new ArrayList<Integer>(length);
        for (int i = 0; i < length; i++) {
            leader.add(i, 0);
        }
    }

    private void loadGraph(String filename) {
        try {
            File file = new File(filename);
            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(stream));
            graph = new ArrayList<ArrayList<Integer>>(length);
            for (int i = 0; i < length; i++) {
                graph.add(new ArrayList<Integer>());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] splited = line.split(" ");
                Integer index = Integer.parseInt(splited[0]);
                Integer node = Integer.parseInt(splited[1]);
                try {
                    graph.get(index).add(node);
                } catch (Exception e) {
                    graph.add(new ArrayList<>(Collections.singletonList(node)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getReverse(String filename) {
        try {
            File file = new File(filename);
            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(stream));
            convertedGraph = new ArrayList<ArrayList<Integer>>(length);
            for (int i = 0; i < length; i++) {
                convertedGraph.add(new ArrayList<Integer>());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] splited = line.split(" ");
                Integer index = Integer.parseInt(splited[1]);
                Integer node = Integer.parseInt(splited[0]);
                try {
                    convertedGraph.get(index).add(node);
                } catch (Exception e) {
                    convertedGraph.add(new ArrayList<>(Collections.singletonList(node)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateFinishTime() {
        initAlreadySeen();
        for (int i = 0; i < length; i++) {
            if (alreadySeen.get(i)) continue;
            dfs1(i);
        }
    }

    private void dfs1(Integer node) {
        alreadySeen.set(node, true);

        ArrayList<Integer> edges = graph.get(node);
        for (Integer edge : edges) {
            if (alreadySeen.get(edge)) continue;
            dfs1(edge);
        }
        finishedTime.push(node);
    }

    public void getResult() {
        initAlreadySeen();
        while (!finishedTime.isEmpty()) {
            Integer node = finishedTime.pop();
            if (alreadySeen.get(node)) continue;
            dfs2(node, node);
        }

    }

    private void dfs2(Integer node, Integer leaderNode) {
        alreadySeen.set(node, true);
        leader.set(node, leaderNode);
        ArrayList<Integer> edges = convertedGraph.get(node);
        for (Integer edge : edges) {
            if (alreadySeen.get(edge)) continue;
            dfs2(edge, leaderNode);
        }

    }

    public static void main(String[] args) throws IOException {
        StrongConnected object = new StrongConnected("week4/src/main/resources/SCC.txt", 875715);

        object.calculateFinishTime();
        object.getResult();

        Map<Integer, Integer> strongCount = new HashMap<Integer, Integer>();
        for (Integer node : object.leader) {
            if (strongCount.containsKey(node)) {
                strongCount.put(node, strongCount.get(node) + 1);
            } else {
                strongCount.put(node, 1);
            }
        }

        List<Integer> result = new ArrayList<>(strongCount.values());
        Collections.sort(result,Collections.reverseOrder());
        System.out.println(result.subList(0, 5));
    }
}
