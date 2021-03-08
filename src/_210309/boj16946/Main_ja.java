package _210309.boj16946;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Main_ja {
	/**
	 * [ 벽 부수고 이동하기 4 ]
	 * - N*M 맵
	 * N (1~1000)
	 * M (1~1000)
	 * 
	 * - 0 : 이동가능, 1 : 벽
	 * @throws IOException 
 	 */
	

	static int[][] dx = {{0,1},{0,-1},{1,0},{-1,0}};
	static int N,M;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] str = br.readLine().split(" ");
		N = Integer.parseInt(str[0]);
		M = Integer.parseInt(str[1]);
		
		int[][] map = new int[N][M];
		Queue<int[]> walls = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] =	s.charAt(j) - '0';
				if(map[i][j]==1) 
					walls.add(new int[] {i,j});
			}
		}
		
		int[][] groupMap = new int[N][M];
		ArrayList<Integer> groupCnt = new ArrayList<>();
		groupCnt.add(0);
		int name = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j]==0 && groupMap[i][j] ==0) {
					groupMap[i][j] = name;
					groupCnt.add(initMap(map, groupMap, name++, new int[] {i,j}));
				}
			}
		}
		
		while(!walls.isEmpty()) {
			int[] wall = walls.poll();
			int cnt = cntBlank(map, groupMap, groupCnt, wall);
			map[wall[0]][wall[1]] = cnt%10;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				sb.append(map[i][j]);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	
	static int initMap(int[][] map, int[][] groupMap, int name, int[] start) {
		Queue<int[]> qu = new LinkedList<>();
		qu.add(start);
		int cnt = 1;
		while(!qu.isEmpty()) {
			int[] point = qu.poll();
			for (int d = 0; d < 4; d++) {
				int i = point[0] + dx[d][0];
				int j = point[1] + dx[d][1];
				if(i<0 || j<0 || i>=N || j>=M || map[i][j] !=0 || groupMap[i][j] !=0) continue;
				qu.add(new int[] {i,j});
				groupMap[i][j] = name;
				cnt++;
			}
		}
		return cnt;
	}
	
	static int cntBlank(int[][] map, int[][] groupMap, ArrayList<Integer> groupCnt, int[] start) {
		int cnt = 1;
		ArrayList<Integer> visited = new ArrayList<>();
		
		for (int d = 0; d < 4; d++) {
			int i = start[0] + dx[d][0];
			int j = start[1] + dx[d][1];
			if (i < 0 || j < 0 || i >= N || j >= M) continue;
			int gCnt = groupCnt.get(groupMap[i][j]);
			if(gCnt == 0 || visited.contains(groupMap[i][j])) continue;
			cnt += gCnt;
			visited.add(groupMap[i][j]);
		}
		return cnt;
	}

}
