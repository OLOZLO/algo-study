package _210126.boj11437;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main_hz {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		List<Integer>[] adj = new ArrayList[N+1];
		for (int i = 0; i <= N; i++)
			adj[i] = new ArrayList<>();
		
		// 인접리스트로 트리를 입력받습니다~ 누가 부모고 누가 자식인지 모르기 때문에 우선 양방향으로 연결!
		for (int n = 0; n < N-1; n++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			adj[a].add(b);
			adj[b].add(a);
		}
		
		int[] parents = new int[N+1];	// 자신의 부모 노드를 저장하는 배열
		
		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[N+1];
		
		visited[1] = true;
		q.add(1);
		while(!q.isEmpty()) {	// bfs를 이용해서 자신의 자식들을 방문하며 자식의 parents 배열에 자신을 저장해줍니다.
			Integer now = q.poll();
			
			for (Integer child : adj[now]) {
				if (!visited[child]) {
					visited[child] = true;
					parents[child] = now;
					q.add(child);
				}
			}
		}
		
		int M = sc.nextInt();
		for (int m = 0; m < M; m++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			
			int now = a;
			List<Integer> allparents = new ArrayList<>(); // 자신을 포함한 모든 부모 노드들을 저장하는 리스트
			while (now != 0) {	// 루트노드까지 올라가면서 내 조상 노드들을 저장합니다
				allparents.add(now);
				now = parents[now];
			}
			
			now = b;
			while (now != 0) {	// 이제 가장 가까운 공통 조상 노드를 찾기 위해 b도 부모로 올라가면서 
				if (allparents.contains(now)) {	// a의 조상 노드 리스트에서 공통되는게 있나 확인해보고
					System.out.println(now);	// 있으면 출력 후 종료
					break;
				}
				now = parents[now];	// 없으면 다음 부모 노드로 이동해서 검색 재진행
			}
		}
	}
}
