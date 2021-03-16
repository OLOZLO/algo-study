package _210316.boj5213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * [ 과외맨 ]
 * 홀수 줄에는 N 개의 타일
 * 짝수 줄에는 N-1 개의 타일
 * 처음 1 ~ 마지막 N*N-N/2
 * 
 * 타일은 두개의 조각으로 이뤄졌음
 * 한 타일(a,b)에서 다른 타일(c,d)로 넘어가려면 두 타일이 인접(a<->c)해야하며, 인접한 두 조각의 숫자는 동일(a=1, c=1)해야함
 * 
 * [ Result ]
 * 첫 줄의 첫 타일 -> 마지막 줄 마지막타일로 가는 가장 짧은 경로의 길이와, 경로 출력
 * 못가면 번호가 가장 큰 타일로 이동
 */
public class Main_ja {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int lastNumber = N * N - N / 2; // 마지막 번호
		
		ArrayList<Node>[] map = new ArrayList[lastNumber + 1]; // 인접리스트로 갈 수 있는 경로 저장
		for (int i = 0; i <= lastNumber; i++) {
			map[i] = new ArrayList<>();
		}
		
		// 노드 연결
		connectNode(map,N,br);
		
		// bfs 순회
		Node max = bfs(map, lastNumber);
		
		// 가장 큰 번호를 도착지로 해서
		System.out.println(max.printNode.split(" ").length); // 경로 길이
		System.out.println(max.printNode); // 경로
	}
	

	static void connectNode(ArrayList<Node>[] map, int N, BufferedReader br) throws IOException {
		int number = 1; // 번호
		for (int i = 0; i < N; i++) {
			boolean isOdd = i % 2 == 0;
			for (int j = 0; j < N - (isOdd ? 0 : 1); j++, number++) {
				String[] str = br.readLine().split(" ");
				Node node = new Node(number, new int[] { Integer.parseInt(str[0]), Integer.parseInt(str[1]) });
				map[number].add(node); // 입력 받자마자 인접리스트 만들거라, 첫 번째 요소는 나 자신을 저장함

				if (j != 0) { // 왼쪽 노드와 연결, 0열은 제외
					Node preNode = map[number - 1].get(0);
					if (preNode.pieces[1] == node.pieces[0]) {
						map[number - 1].add(node); // 양쪽으로 연결
						map[number].add(preNode);
					}
				}
				if (i == 0) // 위의 노드와 연결, 0행은 제외
					continue;

				// 짝수행은 무조건 왼쪽, 오른쪽 위의 노드와 연결
				if (!isOdd || j != 0) { // 홀수 행은 0열 제외
					Node left = map[number - N].get(0);
					if (node.pieces[0] == left.pieces[1]) {
						map[number - N].add(node);
						map[number].add(left);
					}
				}
				if (!isOdd || j != N - 1) { // 홀수 행은 N-1열 제외
					Node right = map[number - N + 1].get(0);
					if (node.pieces[1] == right.pieces[0]) {
						map[number - N + 1].add(node);
						map[number].add(right);
					}
				}
			}
		}
	}
	
	static Node bfs(ArrayList<Node>[] map, int lastNumber) {
		Node max = new Node(1, "1");
		Queue<Node> qu = new LinkedList<>();
		qu.add(max);

		boolean[] visited = new boolean[lastNumber + 1];
		visited[1] = true;

		while (!qu.isEmpty()) {
			Node node = qu.poll();
			if (max.number < node.number) max = node; // 가장 큰 번호 저장
			if (node.number == lastNumber) break; // 마지막 번호에 도착했으면 끝
			
			for (Node next : map[node.number]) {
				if (next.number == node.number || visited[next.number]) continue; // 인접리스트에 내 노드 포함되어있어서 제외해야함
				qu.add(new Node(next.number, (node.printNode + " " + next.number))); // string으로 경로 저장하면서 순회함
				visited[next.number] = true;
			}
		}
		return max;
	}
	
	static class Node {
		int number;
		int[] pieces;
		String printNode;

		public Node(int number, int[] pieces) {
			super();
			this.number = number;
			this.pieces = pieces;
		}

		public Node(int number, String printNode) {
			this.number = number;
			this.printNode = printNode;
		}

	}

}
