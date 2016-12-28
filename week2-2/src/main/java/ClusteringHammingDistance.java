import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by bresai on 2016/12/26.
 */
public class ClusteringHammingDistance {
    private Map<String, Integer> hashTable = new LinkedHashMap<>();
    private UnionFind unionFind;
    private int count;
    private int numOfBits;
    private Map<String, Boolean> alreadySeen = new HashMap<>();
    private Map<Integer, List<String>> map = new HashMap<>();

    public ClusteringHammingDistance(String filename) {
        try {
            readFromFile(filename);
            unionFind = new UnionFind(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        String[] split = line.split(" ");
        count = Integer.parseInt(split[0]);
        numOfBits = Integer.parseInt(split[1]);
        int i = 0;
        while ((line = reader.readLine()) != null) {
            split = line.split(" ");
            Node node = new Node(i, split);
            if (hashTable.containsKey(node.getCode())) {
                continue;
            }
            hashTable.put(node.getCode(), node.getNum());
            i++;
        }
        count = hashTable.size();

    }

    private int hammingDistance(String string1, String string2){
        int count = 0;
        for (int i= 0; i < string1.length(); i++){
            if (string1.charAt(i) !=  string2.charAt(i)){
                count++;
            }
        }
        return count;
    }


    private String changeChar(String codes, int index) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < codes.length(); i++) {
            if (i == index) {
                if (codes.charAt(i) == '0') {
                    builder.append('1');
                } else {
                    builder.append('0');
                }
            } else {
                builder.append(codes.charAt(i));
            }
        }
        return builder.toString();
    }

    private String changeChar(String codes, int index, int index1) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < codes.length(); i++) {
            if (i == index || i == index1) {
                if (codes.charAt(i) == '0') {
                    builder.append('1');
                } else {
                    builder.append('0');
                }
            } else {
                builder.append(codes.charAt(i));
            }
        }
        return builder.toString();
    }

    public void greedy() {
        Iterator<Map.Entry<String, Integer>> iterator = hashTable.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();

            //hamming distance of 1
            for (int i = 0; i < numOfBits; i++) {
                String tmp = changeChar(entry.getKey(), i);
                if (hashTable.containsKey(tmp)) {
                    union(tmp, entry.getKey());
                }
            }

            //hamming distance of 2
            for (int i = 0; i < numOfBits; i++) {
                for (int j = 0; j < numOfBits; j++){
                    if (j == i) continue;
                    String tmp = changeChar(entry.getKey(), i, j);
                    if (hashTable.containsKey(tmp)) {
                        union(tmp, entry.getKey());
                    }
                }
            }
        }
    }

    private void union(String string1, String string2){
        int m,n;
        m = hashTable.get(string1);
        n = hashTable.get(string2);

        if(! unionFind.getRoots().containsKey(m)){
            m = unionFind.find(m);
        }
        if(! unionFind.getRoots().containsKey(n)){
            n = unionFind.find(n);
        }
        if (m != n){
            unionFind.union(m,n);
        }
    }

    private String getByValue(int num){
        for (Map.Entry<String, Integer> entry : hashTable.entrySet()){
            if (entry.getValue().equals(num)){
                return entry.getKey();
            }
        }
        return "";
    }

    private Integer getByValue(Map<Integer, Integer> table, int num){
        for (Map.Entry<Integer, Integer> entry : table.entrySet()){
            if (entry.getValue().equals(num)){
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean find(String str){
        for (int i = 0; i < numOfBits; i++) {
            String tmp = changeChar(str, i);
            if (hashTable.containsKey(tmp)) {
                return true;
            }
        }

        for (int i = 0; i < numOfBits; i++) {
            for (int j = 0; j < numOfBits; j++) {
                String tmp = changeChar(str, i, j);
                if (hashTable.containsKey(tmp)){
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        ClusteringHammingDistance object = new ClusteringHammingDistance("week2-2/src/main/resources/clustering_big.txt");
        object.greedy();
        System.out.println(object.unionFind.getRoots().size());
    }
}
