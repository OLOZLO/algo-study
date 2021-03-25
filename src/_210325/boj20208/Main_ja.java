package _210325.boj20208;

import java.util.Scanner;

public class Main_ja {
	/**
	 * [ 진우의 민트초코우유 ]
	 * N*N
	 * 체력 M -> 이동할 수 있는 거리
	 * 상하좌우 이동 -> 체력 -1
	 * 민초 먹으면 H 만큼 증가
	 * 0이면 이동불가
	 * 
	 * 얼마나 많은 민초를 마시고 집으로 돌아올 수 있는지
	 * 
	 */
	static int max = 0;
	static int[][] dt = {{0,1},{0,-1},{1,0},{-1,0}};
	static int N, M, H;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		N = in.nextInt();
		M = in.nextInt();
		H = in.nextInt();
		int[][] map = new int[N][N];
		boolean[][] visited = new boolean[N][N];
		int[] home = new int[2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = in.nextInt();
				if(map[i][j] == 1) {
					home = new int[] {i,j};
				}
			}
		}
		dfs(map, visited, home, M, 0);
		System.out.println(max);
	} 
	
	public static void dfs(int[][] map, boolean[][] visited, int[] point,int m, int cnt) {
		for (int d = 0; d < 4; d++) {
			int i = point[0] + dt[d][0];
			int j = point[1] + dt[d][1];
			if(i<0||j<0||i>=N||j>=N) continue;
			if(visited[i][j] || m <= 0) continue;
			if(map[i][j]==1) {
				max = Math.max(max, cnt);
				continue;
			}
			visited[i][j] = true;
			if(map[i][j] == 2)
				dfs(map, visited, new int[] {i,j}, m-1+H, cnt+1);
			else
				dfs(map, visited, new int[] {i,j}, m-1, cnt);
			visited[i][j] = false;
		}
	}

}
