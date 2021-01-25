package _210126.boj11437;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * [ LCA(Lowest Common Ancestor - 두 노드의 가까운 공통 조상 구하기 ]
 *	
 * - 해당 정점의 depth와 parent를 저장해두는 방식
 * (1) 모든 노드에 대한 깊이(depth) 계산 (DFS 이용)
 * (2) 최소 공통 조상을 찾을 두 노드를 확인
 * 		(2)-1. 두 노드의 깊이(depth)가 동일하도록 맞추기
 * 		(2)-2. 부모가 같아질 때까지 반복적으로 두 노드의 부모 방향으로 거슬러 올라감
 * (3) 모든 LCA(a,b)에 대해 2번 과정을 반복
 * 
 * [ LCA의 시간 복잡도 ]
 * - 모든 쿼리를 처리할 때 : O(NM)
 * - 만약 M이 최대 100,000개라면? -> 시간 초과
 * 	 => 노드가 부모 방향으로 거슬러 올라갈 때, 2의 제곱 형태로 거슬러 올라감
 * 	 => 모든 노드에 대하여 깊이(depth)와 2^i번째 부모에 대한 정보를 계산 -> 시간복잡도 : O(MlogN)
 */
public class Main_ja {
	static ArrayList<ArrayList<Integer>> tree;
	static int[] depth, parent;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		tree = new ArrayList<>();
		
		for (int i = 0; i < N+1; i++) {
			tree.add(new ArrayList<>());
		}
		
		// tree 구조 만들기
		for (int i = 0; i < N-1; i++) {
			int n1 = in.nextInt(), n2 = in.nextInt();
			tree.get(n1).add(n2);
			tree.get(n2).add(n1);
		}
		
		depth = new int[N+1];
		parent = new int[N+1];
		
		// 모든 노드의 depth
		dfs(1,1);
		// 조상 구하기
		int M = in.nextInt();
		for (int i = 0; i < M; i++) {
			int n1 = in.nextInt(), n2 = in.nextInt();
			System.out.println(lca(n1,n2));
		}
	}
	static void dfs(int from, int cnt) {
		depth[from] = cnt;
		// 해당 노드와 연결된 노드들 depth 지정
		for(int next: tree.get(from)) {
			if(depth[next]!=0) continue;
			parent[next] = from;
			dfs(next, cnt+1);
		}
	}
	
	static int lca(int n1, int n2) {
		// 두 노드의 depth 맞추기 
		while(depth[n1] != depth[n2]) {
			if(depth[n1] > depth[n2])
				n1 = parent[n1];
			else
				n2 = parent[n2];
		}
		// depth를 맞췄는데, 두 노드 값이 다르면 각노드의 부모로 거슬러 올라가면서 공통 부모 찾기
		while(n1 != n2) {
			n1 = parent[n1];
			n2 = parent[n2];
		}
		return n1;
	}
}
