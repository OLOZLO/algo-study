package _210223.prog42892;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main_jyj {

	static int index = 0;

	
	public int[][] solution(int[][] nodeinfo) {
		// Node를 담을 리스트
		List<Node> list = new ArrayList<Node>();

		// 리스트에 Node에 대한 x,y 값과 level 값을 넣어준다
		for (int a = 0; a < nodeinfo.length; a++) {
			list.add(new Node(nodeinfo[a][0], nodeinfo[a][1], a + 1));
		}

		// 노드들을 y가 높은 순으로 정렬, y가 같다면 x가 낮은 순으로 정렬
		Collections.sort(list, new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				if (n1.y > n2.y) {
					return -1;
				} else if (n1.y < n2.y) {
					return 1;
				} else {
					if (n1.x > n2.x) {
						return 1;
					} else if (n1.x < n2.x) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		});
		
		

		// 맨 첫번째 노드
		Node root = list.get(0);

		// 전위 순회와 후위 순회를 넣을 2차원 int배열 선언
		int[][] answer = new int[2][list.size()];

		// 맨 위 레벨의 노드부터 차근차근 자식 노드를 연결 시킨다.
		for (int i = 1; i < list.size(); i++) {
			addNode(root, list.get(i));
		}

		preorder(answer, root);
		index = 0;
		postorder(answer, root);

		return answer;
	}

	void addNode(Node parent, Node child) {
		// 부모 노드의 x값이 자식의 x 값 보다 크면
		if (parent.x > child.x) {
			// 부모의 left 자식이 비었다면
			if (parent.left == null) {
				parent.left = child;
			}
			// 부모의 left 자식이 차있다면
			else {
				// 부모의 left자식의 자식을 또 검사한다.
				addNode(parent.left, child);
			}
		} else {
			// 부모의 righr 자식이 비었다면
			if (parent.right == null) {
				parent.right = child;
			}
			// 부모의 right 자식의 자식을 또 검사한다.
			else {
				addNode(parent.right, child);
			}
		}
	}

	// 전위 순회
	void preorder(int[][] arr, Node root) {
		if (root != null) {

			arr[0][index++] = root.num;
			preorder(arr, root.left);
			preorder(arr, root.right);
		}
	}

	// 후위 순회
	void postorder(int[][] arr, Node root) {
		if (root != null) {
			postorder(arr, root.left);
			postorder(arr, root.right);
			arr[1][index++] = root.num;
		}
	}

	public class Node {
		int x;
		int y;
		int num;
		Node left = null;
		Node right = null;

		public Node(int x, int y, int num) {

			this.x = x;
			this.y = y;
			this.num = num;
		}
	}
}
