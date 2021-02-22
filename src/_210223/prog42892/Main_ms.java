package _210223.prog42892;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main_ms {
    static int[][] answer = {};
    static int cnt;

    public static int[][] solution(int[][] nodeinfo) {
        answer = new int[2][nodeinfo.length];

        ArrayList<Info> list = new ArrayList<>();
        for (int i = 0; i < nodeinfo.length; i++) {
            list.add(new Info(i + 1, nodeinfo[i][0], nodeinfo[i][1]));
        }

        // y좌표 내림차순(y좌표가 같으면 x좌표 오름차순으로)으로 정렬함.
        Collections.sort(list, new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                return o1.y == o2.y ? o1.x - o2.x : o2.y - o1.y;
            }
        });

        BinarySearchTree bst = new BinarySearchTree();
        for (Info info : list) {
            bst.insert(info.index, info.x);
        }

        bst.preorder(bst.root);

        cnt = 0; // 카운트 0으로 초기화하고 다시 postorder 시작!
        bst.postorder(bst.root);

        return answer;
    }

    static class Info {
        int index, x, y;

        public Info(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }
    }

    // 각 노드는 [왼쪽 노드] 및 [오른쪽 노드] 뿐 아니라, 문제에서 요구하는 [정점의 번호]와, 노드 삽입 시 비교 연산에 필요한 [x 좌표]도 갖는다.
    static class TreeNode {
        int index;
        int x;
        TreeNode left;
        TreeNode right;

        public TreeNode(int index, int x) {
            this.index = index;
            this.x = x;
        }
    }

    static class BinarySearchTree {
        TreeNode root;

        // insert 메소드는 입력으로 들어온 노드의 x 좌표 값에 따라 왼쪽 자식으로 설정할 지, 오른쪽 자식으로 설정할 지 결정한다.
        void insert(int index, int x) {
            TreeNode newNode = new TreeNode(index, x);

            if (root == null) {
                root = newNode;
                return;
            }

            TreeNode current = root;
            TreeNode parent = null;

            // 빈 곳까지 내려가 왼쪽 자식 혹은 오른쪽 자식, 알맞은 위치에 채워넣음.
            while (true) {
                parent = current;

                if (x < current.x) {
                    current = current.left;
                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }

        // 전위 순회 : 자신 -> L -> R
        void preorder(TreeNode node) {
            if (node != null) answer[0][cnt++] = node.index;
            if (node.left != null) preorder(node.left);
            if (node.right != null) preorder(node.right);
        }

        // 후위 순회 : L -> R -> 자신
        void postorder(TreeNode node) {
            if (node.left != null) postorder(node.left);
            if (node.right != null) postorder(node.right);
            if (node != null) answer[1][cnt++] = node.index;
        }
    }

    public static void main(String[] args) {
        solution(new int[][]{{5, 3}, {11, 5}, {13, 3}, {3, 5}, {6, 1}, {1, 3}, {8, 6}, {7, 2}, {2, 2}});
    }
}
