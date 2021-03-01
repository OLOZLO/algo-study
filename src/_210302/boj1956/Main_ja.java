package _210302.boj1956;

import java.util.Arrays;
import java.util.Scanner;

public class Main_ja {
	/**
	 * [ 운동 ]
	 * V : 마을 (2~400)
	 * E : 도로 (0~V(V-1)) => 일방통행
	 * 
	 * 도로 길이의 합이 가장 작은 사이클 찾기
	 * -> 두 마을을 왕복도 사이클에 포함!
	 * 
	 * 모든 정점에 대해 최단거리를 구하면 되겠다!
	 * [ 플로이드-워셜 ] 
	 * 모든 정점의 최단 경로 -> 거처가는 정점을 기준으로 최단경로를 구함
	 * 
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int V = in.nextInt();
		int E = in.nextInt();
		int[][] dist = new int[V][V];
		final int MAX = Integer.MAX_VALUE;
		for (int i = 0; i < V; i++) {
			Arrays.fill(dist[i], MAX);
		}
		for (int i = 0; i < E; i++) {
			int a = in.nextInt()-1;
			int b = in.nextInt()-1;
			dist[a][b] = in.nextInt();
		}
		// k정점을 지날때의 a->b의 최단 경로
		for (int k = 0; k < V; k++) {
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					if(dist[i][k] == MAX || dist[k][j] == MAX) continue;
					dist[i][j] = Math.min(dist[i][k]+dist[k][j], dist[i][j]);
				}
			}
		}
		int result = MAX;
		for (int i = 0; i < V; i++) { // 사이클 구하는거니까 a->a의 최단거리
			if(dist[i][i] == MAX) continue;
			result = Math.min(result, dist[i][i]);
		}
		System.out.println(result==MAX?-1:result);
	}

}
