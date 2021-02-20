package _210223.prog42892;

import java.util.ArrayList;
import java.util.Arrays;

public class Main_1n9yun {
//    그냥 BST 만들고 순회하는 문제
    class BST{
        class Node{
            int idx;
            int value;
            Node left, right;

            public Node(int idx, int value) {
                this.idx = idx;
                this.value = value;
            }
        }

        Node head;
        BST(int idx, int value){
            this.head = new Node(idx, value);
        }
        void add(int idx, int value){
            Node next = head;
            Node newNode = new Node(idx, value);
            while(next != null){
                if(next.value < value) {
                    if(next.right == null) {
                        next.right = newNode;
                        break;
                    }
                    else next = next.right;
                }
                else {
                    if(next.left == null) {
                        next.left = newNode;
                        break;
                    }
                    else next = next.left;
                }
            }
        }

        ArrayList<Integer> makePreorder(ArrayList<Integer> preorder){
            return makePreorder(preorder, head);
        }
        ArrayList<Integer> makePostorder(ArrayList<Integer> postorder){
            return makePostorder(postorder, head);
        }
        private ArrayList<Integer> makePreorder(ArrayList<Integer> preorder, Node node){
            if(node == null) return null;
            preorder.add(node.idx);
            makePreorder(preorder, node.left);
            makePreorder(preorder, node.right);

            return preorder;
        }
        private ArrayList<Integer> makePostorder(ArrayList<Integer> postorder, Node node){
            if(node == null) return null;
            makePostorder(postorder, node.left);
            makePostorder(postorder, node.right);
            postorder.add(node.idx);

            return postorder;
        }
    }
    public int[][] solution(int[][] nodeinfo) {

        int[][] sortedNodeInfo = new int[nodeinfo.length][3];
        for(int i=0;i<nodeinfo.length;i++){
            sortedNodeInfo[i][0] = i+1;
            sortedNodeInfo[i][1] = nodeinfo[i][0];
            sortedNodeInfo[i][2] = nodeinfo[i][1];
        }
        Arrays.sort(sortedNodeInfo, (o1, o2) -> Integer.compare(o2[2], o1[2]));

        BST bst = new BST(sortedNodeInfo[0][0], sortedNodeInfo[0][1]);
        for(int i=1;i<sortedNodeInfo.length;i++) bst.add(sortedNodeInfo[i][0], sortedNodeInfo[i][1]);

        int[][] answer = new int[2][];
        answer[0] = bst.makePreorder(new ArrayList<>()).stream().mapToInt(Integer::intValue).toArray();
        answer[1] = bst.makePostorder(new ArrayList<>()).stream().mapToInt(Integer::intValue).toArray();

        return answer;
    }
}