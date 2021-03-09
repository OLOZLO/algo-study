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
		
		int[][] groupBlank = new int[N][M]; // 빈칸 그룹화한 정보
		ArrayList<Integer> groupBlankCnt = new ArrayList<>(); // 그룹별 빈칸 개수 저장
		
		// 벽을 기준으로 빈칸을 그룹화해서, 그룹이름이랑 개수 각각 저장함. 0은 벽, 그룹은 1부터 N개
		// 100 -> 011
		// 010 -> 201
		groupingMap(map, groupBlank, groupBlankCnt);
		
		// 벽 부수러 가쟈
		while(!walls.isEmpty()) {
			int[] wall = walls.poll();
			// 해당 벽의 상하좌우에 존재하는 빈칸그룹(groupBlank)을 찾아서
			// 그 그룹의 멤버수(groupBlankCnt)만큼 cnt에 추가할거임.
			int cnt = cntBlank(map, groupBlank, groupBlankCnt, wall); 
			map[wall[0]][wall[1]] = cnt%10; // map 정보 cnt로 갱신
		}
		
		printMap(map);
	}

	static void groupingMap(int[][] map, int[][] groupBlank, ArrayList<Integer> groupBlankCnt) {
		groupBlankCnt.add(0); // 그룹명을 1부터 시작하려고
		int name = 1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j]==0 && groupBlank[i][j] ==0) {
					groupBlank[i][j] = name;
					groupBlankCnt.add(groupingBlank(map, groupBlank, name++, new int[] {i,j})); // 빈칸 그룹화하면서 멤버 몇개인지 계산할거임
				}
			}
		}
	}
	// bfs 탐색해서 연결된 blank 그룹화
	static int groupingBlank(int[][] map, int[][] groupBlank, int name, int[] start) {
		Queue<int[]> qu = new LinkedList<>();
		qu.add(start);
		int cnt = 1;
		while(!qu.isEmpty()) {
			int[] point = qu.poll();
			for (int d = 0; d < 4; d++) { 
				int i = point[0] + dx[d][0];
				int j = point[1] + dx[d][1];
				if(i<0 || j<0 || i>=N || j>=M || map[i][j] !=0 || groupBlank[i][j] !=0) continue;
				qu.add(new int[] {i,j});
				groupBlank[i][j] = name;
				cnt++;
			}
		}
		return cnt; // 멤버수 반환
	}
	
	// 해당 벽과 연결된 빈칸이 몇개인지 계산
	static int cntBlank(int[][] map, int[][] groupBlank, ArrayList<Integer> groupBlankCnt, int[] start) {
		int cnt = 1;
		ArrayList<Integer> visited = new ArrayList<>(); // 비트마스킹하다가 혼남 ; 범위초과..(대략 2^500,000)
		
		for (int d = 0; d < 4; d++) {
			int i = start[0] + dx[d][0];
			int j = start[1] + dx[d][1];
			if (i < 0 || j < 0 || i >= N || j >= M) continue;
			int gCnt = groupBlankCnt.get(groupBlank[i][j]); // 해당 그룹의 멤버수 (== 빈칸수)
			if(gCnt == 0 || visited.contains(groupBlank[i][j])) continue; // 빈칸그룹이 없거나, 방문했던 빈칸그룹이면 pass
			cnt += gCnt;
			visited.add(groupBlank[i][j]);
		}
		return cnt;
	}

	static void printMap(int[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				sb.append(map[i][j]);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}
