package _210223.prog42892;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main_girawhale {
    List<Integer> preorder = new ArrayList<>();
    List<Integer> postorder = new ArrayList<>();

    public int[][] solution(int[][] nodeinfo) {
        Node[] nodes = new Node[nodeinfo.length];
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = new Node(i + 1, nodeinfo[i]);

        Arrays.sort(nodes);
        Node root = nodes[0];

        for (int i = 1; i < nodes.length; i++)
            root.insert(nodes[i]);

        preorder(root);
        postorder(root);

        return new int[][]{preorder.stream().mapToInt(i -> i).toArray(),
                postorder.stream().mapToInt(i -> i).toArray()};
    }

    class Node implements Comparable<Node> {
        int num, x, y;
        Node left, right;

        public Node(int num, int[] nodeInfo) {
            this.num = num;
            this.x = nodeInfo[0];
            this.y = nodeInfo[1];
        }

        public void insert(Node node) {
            if (node.x < this.x) {
                if (this.left == null)
                    this.left = node;
                else
                    this.left.insert(node);
            } else {
                if (this.right == null)
                    this.right = node;
                else
                    this.right.insert(node);
            }
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(o.y, this.y);
        }
    }

    public void preorder(Node node) {
        if (node == null) return;

        preorder.add(node.num);
        preorder(node.left);
        preorder(node.right);
    }

    public void postorder(Node node) {
        if (node == null) return;

        postorder(node.left);
        postorder(node.right);
        postorder.add(node.num);
    }

}
