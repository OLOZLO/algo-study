package _210309.boj2098;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [ 외판원 순회 ] 
	 * N개의 도시 (2~16)
	 * 비용 (~1,000,000)
	 * 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 원래 도시로 돌아오는 순회 여행 경로
	 * 
	 * - 한 번 갔던 도시로는 다시 갈 수 없음
	 * - 비용은 대칭적 X
	 * - i->i는 0, 갈 수 없는 경우도 0
	 * 
	 * [ return ]
	 * 외판원 순회에 필요한 비용
	 * 갈 수 없는 경우, 0
	 * 
	 * 
	 * [ 풀이 ]
	 * 	 시초나야하는데 후한 제한으로 통과한 코드
		 1. 1번 노드부터 시작함 (순회니까 어떤 노드부터 시작하든 노상관)
		 2. 다익스트라로 순회할거임.
		 	  -> 현재 노드와 연결된 노드를 pq에 집어넣어서 dist가 작은 애부터 순회함
			  	(조건)지금 방문하려는 노드가, 전에 방문한 기록이 있는 노드야.(비트마스킹으로 저장)
		 			  그러면, 동일한 노드를 거쳐서 방문했는지 확인할거야.(노드 방문 순서만 다른 경로) 
					 [EX] 1->2->3->4 와 1->3->2->4 같은 경우
					 지금 경로가 dist가 더 작으면 dist 갱신 -> 먼저 도착했다고 최솟값 보장 X
		 3. N-1개의 노드를 방문했으면, 마지막에는 1번 노드랑 연결해서 최솟값 저장 -> 없으면  pass
	
		 먼저 도착한 애가 꼭 최솟값이라고 보장할 수 없기 때문에
		 결국 완탐이라서, pq가 의미가 없다고 생각했음. -> 여기서 bfs보다 dfs가 맞다는걸 알게 됨
		 근데 그냥 qu쓰면 메모리 터지더라.
		 pq하면서 운좋게 걸러내는 애들이 좀 있어서 통과한 것 같음.
		
		 결론. 운 좋은 코드였다..ㅎ
	 */

	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();

		int[][] map = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = in.nextInt();
			}
		}
		
		// 노드별로 방문기록 저장
		int[][] dist = new int[N][1<<16]; 
		PriorityQueue<Node> pq = new PriorityQueue<>(); // dist 기준 정렬
		pq.add(new Node(0,0,1,0)); // 1번노드부터 출발
		int min =Integer.MAX_VALUE;
		
		// 다익스트라
		while(!pq.isEmpty()) {
			Node node = pq.poll();
			if(node.cnt == N-1) { // 1번 제외하고 모든 노드 방문했으면
				if(map[node.v][0] != 0)  // 1번 노드랑 연결됐는지 확인 후 min 계산
					min = Math.min(node.dist + map[node.v][0], min);
				continue;
			}
			
			for(int i=1; i<N; i++) { // 1번 노드 제외하고 연결된 노드 방문할거야. (1번은 마지막에 방문할거임)
				// 연결 안된 노드거나, 방문했던 노드면 pass
				if(map[node.v][i] == 0 || (node.visited & 1 << i) != 0) continue; 
				
				int visited = node.visited | 1 << i; // 방문 노드 포함한 visited

				// 방문 노드를 이전에 같은 노드을 거쳐서 방문한 기록이 있으면 dist 비교 후 저장
				if(dist[i][visited]!=0 && dist[i][visited] < node.dist + map[node.v][i]) continue; 
				dist[i][visited] = node.dist + map[node.v][i];
				pq.add(new Node(i, dist[i][visited], visited, node.cnt+1));
			}
		}
		System.out.println(min);
	}
	
	static class Node implements Comparable<Node>{
		int v, dist, visited, cnt;

		public Node(int v, int dist) {
			this.v = v;
			this.dist = dist;
		}
		public Node(int v, int dist, int visited, int cnt) {
			this.v = v;
			this.dist = dist;
			this.visited = visited;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.dist, o.dist);
		}
	}
}