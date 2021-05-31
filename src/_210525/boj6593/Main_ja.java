package _210525.boj6593;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main_ja {
	/**
	 * [골드5] 상범 빌딩
	 * 1. 결과 : 맞았습니다
	 * 2. 시간복잡도 : O(L*R*C*6)
	 * 3. 풀이 
	 * 	(1) 6방향으로 BFS 탐색 -> 빈칸만 이동
	 * 	(2) 종료 시점 찾으면 반환
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			int L = input[0];
			int R = input[1];
			int C = input[2];
			if(L+R+C == 0) return;
			
			Queue<Point> qu = new LinkedList<>();
			Point end = null;
			char[][][] map = new char[R][C][L];
			boolean[][][] visited = new boolean[R][C][L];
			
			// map 입력, 시작 및 종료지점 저장
			for (int l = 0; l < L; l++) {
				for (int r = 0; r < R; r++) {
					String str = br.readLine();
					for (int c = 0; c < C; c++) {
						map[r][c][l] = str.charAt(c);
						if(map[r][c][l] == 'S') {
							visited[r][c][l] = true;
							qu.add(new Point(r,c,l));
						}else if(map[r][c][l] == 'E') {
							end = new Point(r,c,l);
						}
					}
				}
				br.readLine();
			}
			
			int[] dr = {1,-1,0,0,0,0};
			int[] dc = {0,0,1,-1,0,0};
			int[] dl = {0,0,0,0,1,-1};
			int move = 0;
			boolean check = false; // 종료지점 도착 여부
			while(!check && !qu.isEmpty()) {
				int size = qu.size();
				for (int s = 0; s < size; s++) {
					Point cur = qu.poll();
					if(cur.r == end.r && cur.c == end.c && cur.l == end.l) {
						check = true;
						break;
					}
					
					for (int d = 0; d < 6; d++) {
						int r = cur.r + dr[d];
						int c = cur.c + dc[d];
						int l = cur.l + dl[d];
						if (r < 0 || c < 0 || l < 0 || r >= R || c >= C || l >= L) continue;
						if (visited[r][c][l] || map[r][c][l] == '#') continue;
						visited[r][c][l] = true;
						qu.add(new Point(r,c,l));
					}
				}
				move++;
			}
			if(check)
				System.out.println("Escaped in "+ --move +" minute(s).");
			else
				System.out.println("Trapped!");
		}
	}
	static class Point{
		int r,c,l;

		public Point(int r, int c, int l) {
			this.r = r;
			this.c = c;
			this.l = l;
		}
	}

}
