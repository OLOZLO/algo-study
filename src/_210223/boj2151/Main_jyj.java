package _210223.boj2151;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main_jyj {

	static int N, initX, initY;
	static char[][] map;
	static boolean[][][] visit;
	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { -1, 0, 1, 0 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		visit = new boolean[N][N][4];

		Mirror start = null;

		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			for (int j = 0; j < N; j++) {
				map[i][j] = s.charAt(j);
				if (map[i][j] == '#') {
					start = new Mirror(i, j, 0, 0);
				}
			}
		}
		
		// 우선순위 큐를 사용하는 이유는 거울 개수가 작은 순서대로 정렬하기 위해
		PriorityQueue<Mirror> q = new PriorityQueue<Mirror>();
		for (int i = 0; i < 4; i++) {
			// 처음 시작문에서 상,하,좌,우 모두 탐색을 시작한다.
			q.offer(new Mirror(start.x, start.y, 0, i));
		}

		while (!q.isEmpty()) {

			Mirror m = q.poll();
			int x = m.x;
			int y = m.y;
			int cnt = m.cnt;
			int dir = m.dir;
			if (visit[x][y][dir])
				continue;
			visit[x][y][dir] = true;

			// 도착문을 만나면 브레이크
			if (map[x][y] == '#' && !(x == start.x && y == start.y)) {
				System.out.println(m.cnt);
				break;
			}
			int nx = x + dx[dir];
			int ny = y + dy[dir];
			if (!isPossible(nx, ny))
				continue;
			// 거울 설치하기
			if (map[nx][ny] == '!') {
				int nDir = 0;
				// 반시계회전
				if (m.dir == 0)
					nDir = 3;
				else
					nDir = m.dir - 1;
				q.offer(new Mirror(nx, ny, m.cnt + 1, nDir));
				// 시계회전
				if (m.dir == 3)
					nDir = 0;
				else
					nDir = m.dir + 1;
				q.offer(new Mirror(nx, ny, m.cnt + 1, nDir));

			}
			// 거울 설치 안하고 가기
			q.offer(new Mirror(nx, ny, m.cnt, m.dir));

		}
	}
	// 범위안에 들어오니?
	private static boolean isPossible(int nx, int ny) {
		if (nx < 0 || ny < 0 || nx >= N || ny >= N || map[nx][ny] == '*')
			return false;
		return true;
	}

	// 거울객체 선언 개수가 작은 순서대로 정렬
	static class Mirror implements Comparable<Mirror> {
		int x;
		int y;
		int cnt;
		int dir;

		public Mirror(int x, int y, int cnt, int dir) {
			super();
			this.x = x;
			this.y = y;
			this.cnt = cnt;
			this.dir = dir;
		}

		@Override
		public int compareTo(Mirror o) {
			return this.cnt - o.cnt;
		}

	}

}