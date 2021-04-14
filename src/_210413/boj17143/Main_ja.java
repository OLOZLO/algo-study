package _210413.boj17143;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * [골드4] 중량제한
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : O(ElogE) => (10^5)log(10^5) = 5*10^5...?
 * 	- 이유 
 *	  - 다익스트라로 시작 지점에서 끝지점까지의 최대 중량제한 구하기
 *	  - 다익스트라 : 우선 순위 큐 + 인접 리스트
 *		-> 우선 순위 큐의 추가 또는 삭제 O(logE) 
 *		-> E개의 간선에 대해 이 작업을 진행하므로 O(E), 전체 시간복잡도는 O(ElogE)
 *			=> 보통 E <= V^2 이므로, O(ElogV)로 볼 수 있다고 함.
 *		
 *		* 인접 행렬 사용 시, 메모리초과
 *			-> 다리 중복 정보 때문에 인접 행렬로 저장하려다가, 메모리 초과남
 *			-> 10,000 * 10,000 * 4 = 400,000,000 = 400MB (제한 : 128MB)
 * 	- 범위 
 *    - N : ~ 10,000
 *    - M : ~ 100,000
 *   
 *  - 접근 방식
 *    1) 섬 사이의 다리 정보를 인접 리스트로 저장 (중복도 저장)
 *    2) 우선순위큐에 시작지점 추가
 *    3) 다익스트라
 *    	 - 우선순위큐로, 누적 중량제한이 제일 큰 노드부터 pop
 *    	 - 거쳐 온 경로의 중량제한이 dist보다 큰 경우만 dist update
 *    	 - 도착지에 도착하면 중력 출력 후 return
 * 
 * 3. 후기
 *  - 답은 맞게 나오는데 자꾸 틀려서 고생했다. 
 *    알고보니 "서로 같은 두 도시 사이에 여러 개의 다리가 있을 수도 있으며"가 중복 입력이 들어올 수 있다는 말이었다. :(
 *    몰라도, 통과할 수 있었는데 조건에서 부등호를 반대로 썼었다. 으휴
 *     
 *  - 그 후에는 계속 시초가 나서 고생했다. 무려 71%까지 통과했다! 왜 나는지 납득할 수 없었다. (그래서 다익으로 풀고 말겠다고 고집부림)
 *    보니까 반환 실수더라. 도착지점 만나면 return을 해야 하는데 break를 했다. 뭉충이 :< 
 *    
 *  - 자꾸 자잘한 실수를 한다. 꼼꼼하게 코드 보는 습관 좀 들여야겠다..
 */
public class Main_ja {
	public static int[] inputToIntArr(String str) {
		return Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray();
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = inputToIntArr(br.readLine());
		int N = input[0];
		int M = input[1];
		
		ArrayList<Node>[] bridge = new ArrayList[N+1];
		for (int i = 0; i < N+1; i++) {
			bridge[i] = new ArrayList<Node>();
		}
		// 인접 리스트
		for (int i = 0; i < M; i++) {
			input = inputToIntArr(br.readLine());
			int A = input[0];
			int B = input[1];
			int W = input[2];
			bridge[A].add(new Node(B,W));
			bridge[B].add(new Node(A,W));
		}
		
		input = inputToIntArr(br.readLine());
		int start = input[0];
		int end = input[1];
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[N+1];
		int[] dist = new int[N+1];
		Arrays.fill(dist, -1);
		
		// 시작지점 추가
		pq.add(new Node(start, 1_000_000_001));
		
		// 다익스트라
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			if(cur.v == end) { // 도착지점 도착 시, 출력 및 종료
				System.out.println(cur.weight);
				return;
			}
			for(Node next : bridge[cur.v]) {
				int w = Math.min(cur.weight, next.weight); 
				if(visited[next.v] || dist[next.v] >= w) continue; // 방문 또는 최대 중량 제한보다 작거나 같은 경우 pass
				dist[next.v] = Math.max(w, dist[next.v]);
				pq.add(new Node(next.v, w));
			}
			visited[cur.v] = true;
		}
	}
	static class Node implements Comparable<Node>{
		int v;
		int weight;
		public Node(int v, int weight) {
			this.v = v;
			this.weight = weight;
		}
		@Override
		public int compareTo(Node o) {
			return o.weight - this.weight;
		}
	}
}
