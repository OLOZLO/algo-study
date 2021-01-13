package _210112.boj18500;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_hz {
	static int R, C;
	static char[][] map;
	static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	
	public static class Pos {
		int i, j;
		
		Pos(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		map = new char[R][C];
		for (int i = 0; i < R; i++) {
			String s = br.readLine();
			for (int j = 0; j < C; j++) {
				map[i][j] = s.charAt(j);
			}
		}
		
		int N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for (int n = 1; n <= N; n++) {
			findMineral(n, Integer.parseInt(st.nextToken()));
		}
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++)
				bw.write(map[i][j]);
			bw.write("\n");
		}
		bw.flush();
	}
	
	public static boolean connectedBottom(Pos p) {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[R][C];
		
		visited[p.i][p.j] = true;
		q.add(p);
		
		while(!q.isEmpty()) {
			Pos now = q.poll();
			
			for (int d = 0; d < dir.length; d++) {
				int ni = now.i + dir[d][0];
				int nj = now.j + dir[d][1];
				
				if (ni >= 0 && ni < R && nj >= 0 && nj < C) {
					if (!visited[ni][nj] && map[ni][nj] == 'x') {
						if (ni == R-1)
							return true;
						visited[ni][nj] = true;
						q.add(new Pos(ni, nj));
					}
				}
			}
		}
		
		return false;
	}
	
	public static void findMineral(int n, int h) {
		
		Pos remove = null;
		
		if (n % 2 == 1) {	// 왼쪽부터
			for (int j = 0; j < C; j++) {
				if (map[R-h][j] == 'x') {
					remove = breakAndLook(new Pos(R-h, j));
					break;
				}
			}
		} else {	// 오른쪽부터
			for (int j = C-1; j >= 0; j--) {
				if (map[R-h][j] == 'x') {
					remove = breakAndLook(new Pos(R-h, j));
					break;
				}
			}
		}
		
		if (remove != null) 
			removeCluster(remove);
	}
	
	public static Pos breakAndLook(Pos p) {
		Pos find = null;
		
		map[p.i][p.j] = '.';
		
		for (int d = 0; d < dir.length; d++) {
			int ni = p.i + dir[d][0];
			int nj = p.j + dir[d][1];
			
			if (ni >= 0 && ni < R && nj >= 0 && nj < C) {
				if (connectedBottom(new Pos(ni, nj)))
					continue;
				if (ni == R-1)
					continue;
				if (map[ni][nj] == 'x')
					find = new Pos(ni, nj);
			}
		}
		
		return find;
	}
	
	public static void removeCluster(Pos p) {
		Queue<Pos> q = new LinkedList<>();
		List<Pos> list = new ArrayList<>();
		
		q.add(p);
		list.add(p);
		map[p.i][p.j] = '.';
		
		while(!q.isEmpty()) {
			Pos now = q.poll();
			
			for (int d = 0; d < dir.length; d++) {
				int ni = now.i + dir[d][0];
				int nj = now.j + dir[d][1];
				
				if (ni >= 0 && ni < R && nj >= 0 && nj < C && map[ni][nj] == 'x') {
					map[ni][nj] = '.';
					q.add(new Pos(ni, nj));
					list.add(new Pos(ni, nj));
				}
			}
		}
		
		int minh = Integer.MAX_VALUE;
		for (Pos e : list) {
			int cnt = 1;
			while(true) {
				if (e.i+cnt == R-1 || map[e.i+cnt+1][e.j] == 'x') {
					minh = Math.min(minh, cnt);
					break;
				}
				cnt++;
			}
		}
		
		for (Pos e: list) {
			map[e.i+minh][e.j] = 'x';
		}
	}
}
