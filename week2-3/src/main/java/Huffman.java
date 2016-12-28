import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 * Created by bresai on 2016/12/28.
 */
public class Huffman {

    private int length;
    private PriorityQueue<Node> list;
    private int minDeepth = Integer.MAX_VALUE;
    private int maxDeepth = Integer.MIN_VALUE;

    public Huffman(String filename) {
        try {
            readFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        length = Integer.parseInt(reader.readLine());
        list = new PriorityQueue<>(length, (l1, l2) -> l1.getWeight() < l2.getWeight()? -1 : 1);
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null){
            Node node = new Node(i, Integer.parseInt(line));
            list.add(node);
        }
    }

    public Node greedy(){
        while (list.size() > 1){
            Node left = list.poll();
            Node right = list.poll();
            Node node = new Node(left.getWeight() + right.getWeight());
            node.setLeft(left);
            node.setRight(right);
            list.offer(node);
        }

        return list.poll();
    }

    public void count(Node node, int level){
        if (node.getLeft() == null && node.getLeft() == null){
            minDeepth = minDeepth < level ? minDeepth : level;
            maxDeepth = maxDeepth > level ? maxDeepth : level;
            return;
        }
        count(node.getLeft(), level + 1);
        count(node.getRight(), level + 1);
    }

    public static void main(String[] args){
        Huffman object = new Huffman("week2-3/src/main/resources/huffman.txt");
        Node node = object.greedy();
        object.count(node, 0);
        System.out.println(object.minDeepth);
        System.out.println(object.maxDeepth);
    }
}
