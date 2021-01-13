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
	
	public static boolean connectedBottom(Pos p) {	// 점 p가 땅과 연결되있으면 true/아니면 false 리턴 함수
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
	
	public static void findMineral(int n, int h) {	// 던지는 높이에 미네랄이 있는지 확인하는 함수
		
		Pos drop = null;	// 떨어지는 클러스터
		
		if (n % 2 == 1) {	// 왼쪽부터
			for (int j = 0; j < C; j++) {
				if (map[R-h][j] == 'x') {
					drop = breakAndLook(new Pos(R-h, j));
					break;
				}
			}
		} else {	// 오른쪽부터
			for (int j = C-1; j >= 0; j--) {
				if (map[R-h][j] == 'x') {
					drop = breakAndLook(new Pos(R-h, j));
					break;
				}
			}
		}
		
		if (drop != null) 
			dropCluster(drop);
	}
	
	public static Pos breakAndLook(Pos p) {	// 미네랄을 부시고 사방에 클러스터들을 확인하는 과정. 부순 미네랄의 사방 미레랄 중 미네랄이 속한 클러스터가 떠있다면 그 점 리턴
		Pos find = null;
		
		map[p.i][p.j] = '.';	// 먼저 나먼저 지우고
		
		for (int d = 0; d < dir.length; d++) {
			int ni = p.i + dir[d][0];
			int nj = p.j + dir[d][1];
			
			if (ni >= 0 && ni < R && nj >= 0 && nj < C) {	// 나는 여기에서 바로 아래에 미네랄 있을 때 연결됐는지 확인 안하고 바로 패스했는데 그게 잘못됬었음!
				if (connectedBottom(new Pos(ni, nj)))	// 바닥에 연결되어 있으면 안떨어트려도 됨
					continue;
				if (ni == R-1)	// 내가 바닥에 닿아있어도 안떨어트려도 됨
					continue;
				if (map[ni][nj] == 'x')	// 그렇지 않은 미네랄일 경우 떨어뜨려야 함
					find = new Pos(ni, nj);
			}
		}
		
		return find;
	}
	
	public static void dropCluster(Pos p) {	// 클러스터들을 내리는 과정
		Queue<Pos> q = new LinkedList<>();	// p를 포함한 클러스터를 찾기 위한 큐
		List<Pos> list = new ArrayList<>();	// 클러스터의 미네랄들을 저장하는 리스트
		
		q.add(p);
		list.add(p);
		map[p.i][p.j] = '.';	
		
		while(!q.isEmpty()) {
			Pos now = q.poll();
			
			for (int d = 0; d < dir.length; d++) {
				int ni = now.i + dir[d][0];
				int nj = now.j + dir[d][1];
				
				if (ni >= 0 && ni < R && nj >= 0 && nj < C && map[ni][nj] == 'x') {
					map[ni][nj] = '.';	// 클러스터 내의 미네랄들을 우선 .으로 만들어놓음 (높이 구할 때 같은 클러스터 내의 미네랄은 무시해야되서)
					q.add(new Pos(ni, nj));
					list.add(new Pos(ni, nj));
				}
			}
		}
		
		int minh = Integer.MAX_VALUE;	
		for (Pos e : list) {	// 내릴 높이 찾는 과정
			int cnt = 1;
			while(true) {
				if (e.i+cnt == R-1 || map[e.i+cnt+1][e.j] == 'x') {	// 바로 밑에 미네랄이 있거나 땅에 닿을때까지 카운팅
					minh = Math.min(minh, cnt);	// 내릴 높이는 최소여야 됨!
					break;
				}
				cnt++;
			}
		}
		
		for (Pos e: list) {	// 내린다
			map[e.i+minh][e.j] = 'x';
		}
	}
}
