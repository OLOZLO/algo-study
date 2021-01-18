package _210119.boj14466;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main_hz {
	static int N, result;
	static boolean[][][] map;
	static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	
	public static class Pos {
		int i, j;
		
		Pos(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		int K = sc.nextInt();
		int R = sc.nextInt();
		
		// [x][y][0]~[x][y][3] : (x, y)의 0~4 방향이 막혀있을 경우 true. [x][y][4] : 소가 존재하면 true 
		map = new boolean[N+1][N+1][5];	 
		for (int r = 0; r < R; r++) {
			int ai = sc.nextInt();	int aj = sc.nextInt();
			int bi = sc.nextInt();	int bj = sc.nextInt();
			int di = ai-bi;	int dj = aj-bj;
			
			if (di == 0 && dj == 1) {	// a오 b왼
				map[ai][aj][2] = map[bi][bj][0] = true;	// a의 왼쪽, b의 오른쪽에 길 존재
			} else if (di == 0 && dj == -1) {
				map[ai][aj][0] = map[bi][bj][2] = true;	// a의 오른쪽, b의 왼쪽에 길 존재
			} else if (di == 1 && dj == 0) {	//a아b위
				map[ai][aj][3] = map[bi][bj][1] = true;	// a의 위, b의 아래 길 존재
			} else if (di == -1 && dj == 0) {
				map[ai][aj][1] = map[bi][bj][3] = true;	// a의 아래, b의 위에 길 존재
			}
		}
		
		List<Pos> cows = new ArrayList<>();
		for (int k = 0; k < K; k++) {
			int x = sc.nextInt(); int y = sc.nextInt();
			map[x][y][4] = true;	// map에 소들 표시해주고
			cows.add(new Pos(x, y));	// 소들 리스트에 소 저장
		}
		
		result = cows.size()*cows.size();	// 결과 초기값은 소 마리수의 제곱(모든 소가 자신을 포함한 모든 소를 만나는 경우)
		for (Pos p : cows) {	// 각 소마다 bfs를 돌면서 다른 소들을 만난다~
			bfs(p);
		}
		
		System.out.println(result/2);	// 서로 만나는 경우가 중복되어 있으므로 /2 한것이 정답
	}
	
	public static void bfs(Pos p) {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[N+1][N+1];
		
		visited[p.i][p.j] = true;
		q.add(p);
		
		while(!q.isEmpty()) {
			Pos now = q.poll();
			
			if (map[now.i][now.j][4])	// 방문한 곳에서 소를 만났을 경우 결과에서 제외
				result--;
			
			for (int d = 0; d < dir.length; d++) {
				if (map[now.i][now.j][d])	// 길이 있는곳을 제외하고 bfs 돌립니다~
					continue;
				
				int ni = now.i + dir[d][0];
				int nj = now.j + dir[d][1];
				
				if (ni > 0 && ni <= N && nj > 0 && nj <= N && !visited[ni][nj]) {
					visited[ni][nj] = true;
					q.add(new Pos(ni, nj));
				}
			}
		}
	}
}
