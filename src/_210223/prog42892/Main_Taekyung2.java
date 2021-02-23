package _210223.prog42892;

import java.util.*;

public class Main_Taekyung2 {
    int idx = 0;
    public int[][] solution(int[][] nodeinfo) {
        ArrayList<Node> list = new ArrayList<>();
        for(int i = 0; i < nodeinfo.length; i++)
            list.add(new Node(i + 1, nodeinfo[i][1], nodeinfo[i][0]));
        Collections.sort(list);
        // 트리 만듬
        Node root = list.get(0);
        int[][] answer = new int[2][list.size()];
        // 트리에 값 추가
        for(int i = 1; i < list.size(); i++)
            add(root, list.get(i));
        // 전위순회
        preorder(answer, root);
        idx = 0;
        // 후위순회
        postorder(answer, root);
        return answer;
    }

    // 자기 자리 찾아서 내려 감
    public void add(Node parent, Node cur) {
        if(parent.x > cur.x) {
            if(parent.left == null) parent.left = cur;
            else add(parent.left, cur);
        }
        else {
            if(parent.right == null) parent.right = cur;
            else add(parent.right, cur);
        }
    }

    // 루트 먼저
    public void preorder(int[][] answer, Node root) {
        if(root != null) {
            answer[0][idx++] = root.idx;
            preorder(answer, root.left);
            preorder(answer, root.right);
        }
    }

    // 루트 나중에
    public void postorder(int[][] answer, Node root) {
        if(root != null) {
            postorder(answer, root.left);
            postorder(answer, root.right);
            answer[1][idx++] = root.idx;
        }
    }

    class Node implements Comparable<Node> {
        int idx, y, x;
        Node right;
        Node left;

        Node(int idx, int y, int x) {
            this.idx = idx;
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Node o) {
            if(this.y == o.y) return this.x - o.x;
            return o.y - this.y;
        }
    }
}