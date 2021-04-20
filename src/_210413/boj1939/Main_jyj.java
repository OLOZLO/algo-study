package _210413.boj1939;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
  [골드4] 중량제한
  1. 결과 : 성공
  2. 시간 복잡도 : O(1/log(max(w)) * (N + M))
    
   - 접근 방식
	1) 이분탐색과 너비 우선 탐색을 사용하면 풀 수 있다.
	2) 이분 탐색을 사용해서 최대 중량 기준을 mid로 두고 bfs를 실행한다.
	3) bfs에서 방문하지 않았던 나라이고, mid보다 제한이 큰 경우 계속 탐색하도록 한다.
	4) 이후에, end까지 도달하는 경우 true를 반환하고, 도달하지 못하면 false를 반환한다.

	(true를 반환하는 경우는 더 큰 값이 있는 것이므로 오른쪽으로 범위를 옮긴다.)
	(false를 반환하는 경우는 큰 값이 없는 것이므로 왼쪽으로 범위를 옮긴다.)
  
  3. 후기
	처음에는 인접행렬을 사용해서 풀었더니 메모리 초과가 났다.
	그래서 인접리스트로 풀었습니다. 
	인접리스트 방법이 생각나지 않아서 찾아서 참고했습니다.
	인접행렬은 N*N의 메모리 공간을 사용하므로 N에 너무 큰 수가 들어오면 메모리 초과가 발생할 수 있다는것을 알았습니다.
	앞으로 이런 문제는 인접 리스트로 풀도록 해야겠다.

 */

public class Main_jyj {
	private static ArrayList<Node> graph[];
	private static boolean[] visited;
	private static int start;
	private static int end;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		int m = sc.nextInt();

		graph = new ArrayList[n + 1];
		for (int i = 0; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
		}
		int max = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();

			graph[a].add(new Node(b, c));
			graph[b].add(new Node(a, c));

			max = Math.max(c, max);
			min = Math.min(c, min);
		}

		start = sc.nextInt();
		end = sc.nextInt();

		int result = 0;
		while (min <= max) {
			int mid = (min + max) / 2;
			visited = new boolean[n + 1];

			if (bfs(mid)) {
				min = mid + 1;
				result = mid;
			} else {
				max = mid - 1;
			}
		}
		System.out.println(result);

	}

	private static boolean bfs(int mid) {

		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(start);
		visited[start] = true;

		while (!q.isEmpty()) {
			int temp = q.poll();

			if (temp == end)
				return true;
			for (int i = 0; i < graph[temp].size(); i++) {
				if (graph[temp].get(i).weight >= mid && visited[graph[temp].get(i).end] == false) {
					visited[graph[temp].get(i).end] = true;
					q.offer(graph[temp].get(i).end);
				}
			}
		}

		return false;
	}

	public static class Node {
		int end, weight;

		public Node(int end, int weight) {
			this.end = end;
			this.weight = weight;
		}
	}
}
