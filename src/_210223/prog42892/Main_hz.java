package _210223.prog42892;

import java.util.*;

public class Main_hz {

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(solution(new int[][] {{5, 3}, {11, 5}, {13, 3}
        , {3, 5}, {6, 1}, {1, 3}, {8, 6}, {7, 2}, {2, 2} })));
    }

    static class Node {
        int idx, x, y;

        Node (int idx, int x, int y) {
            this.idx = idx;
            this.x = x;
            this.y = y;
        }
    }

    public static int[][] solution(int[][] nodeinfo) {
        int[][] answer = new int[2][nodeinfo.length];

        // 정렬을 해주기 위해 입력값인 nodeinfo를 List형식으로 다시 만들어줍니다. (원본 배열은 유지하려고 리스트로 새로 생성)
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < nodeinfo.length; i++) {
            nodes.add(new Node(i+1, nodeinfo[i][0], nodeinfo[i][1]));
        }

        // y 내림차순으로 정렬 (y값이 같을 땐 x 오름차순으로). 이렇게 정렬하면 이진 트리 순대로 정렬이 됩니다.
        Collections.sort(nodes, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.y != o2.y) return -Integer.compare(o1.y, o2.y);
                else return Integer.compare(o1.x, o2.x);
            }
        });

        int start = nodes.get(0).idx;   // 리스트의 0번째 노드는 루트 노드
        int[][] tree = new int[nodeinfo.length+1][2];   // 이진트리를 만들건데 [n][0]은 n은 왼쪽 자식 idx, [n][1]은 n의 오른쪽 자식 idx
        for (int i = 1; i < nodes.size(); i++) {
            // 1번 노드부터 이진 트리의 해당 위치에 삽입해줍니다.
            maketree(start, nodes.get(i), tree, nodeinfo);
        }

        String[] pre = preorder(start, tree, new StringBuilder()).toString().split(" ");
        String[] post = postorder(start, tree, new StringBuilder()).toString().split(" ");

        for (int i = 0; i < nodeinfo.length; i++) {
            answer[0][i] = Integer.parseInt(pre[i]);
            answer[1][i] = Integer.parseInt(post[i]);
        }

        return answer;
    }

    public static void maketree(int idx, Node n, int[][] tree, int[][] nodeinfo) {
        // n을 idx의 왼쪽 서브 트리에 놓을지, 오른쪽 서브 트리에 놓을 지 결정하는 부분!
        // 현재 x 가 idx의 x보다 작으면 왼쪽 노드로 보내야됨
        if (n.x < nodeinfo[idx-1][0]) {
            if (tree[idx][0] == 0) tree[idx][0] = n.idx;
            else maketree(tree[idx][0], n, tree, nodeinfo);
        } else {
            if (tree[idx][1] == 0) tree[idx][1] = n.idx;
            else maketree(tree[idx][1], n, tree, nodeinfo);
        }
    }

    public static StringBuilder preorder(int n, int[][] tree, StringBuilder sb) {

        sb.append(n+" ");
        if (tree[n][0] != 0) preorder(tree[n][0], tree, sb);
        if (tree[n][1] != 0) preorder(tree[n][1], tree, sb);
        return sb;
    }

    public static StringBuilder postorder(int n, int[][] tree, StringBuilder sb) {
        if (tree[n][0] != 0) postorder(tree[n][0], tree, sb);
        if (tree[n][1] != 0) postorder(tree[n][1], tree, sb);
        sb.append(n+" ");
        return sb;
    }
}
