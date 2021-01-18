package _210119.boj14466;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_jyj {

	static int N, K, R;
	static boolean[][] visited;
	static Point[] cows;
	static ArrayList<Point>[][] bridges;
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);

		N = sc.nextInt(); // 크기
		K = sc.nextInt(); // 소
		R = sc.nextInt(); // 길

		cows = new Point[K];
		bridges = new ArrayList[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				bridges[i][j] = new ArrayList<>();
			}
		}

		// 다리 정보 저장
		for (int i = 0; i < R; i++) {

			int r1 = sc.nextInt() - 1;
			int c1 = sc.nextInt() - 1;
			int r2 = sc.nextInt() - 1;
			int c2 = sc.nextInt() - 1;

			bridges[r1][c1].add(new Point(r2, c2));
			bridges[r2][c2].add(new Point(r1, c1));
		}

		// 소 위치 저장
		for (int i = 0; i < K; i++) {
			int x = sc.nextInt() - 1;
			int y = sc.nextInt() - 1;

			cows[i] = new Point(x, y);
		}

		System.out.println(move());
	}

	private static int move() {

		int cnt = 0;

		// 소 한 마리씩 길을 건너지 않고 이동해보자.
		for (int c = 0; c < K; c++) {
			visited = new boolean[N][N];
			bfs(cows[c].x, cows[c].y);

			for (int nc = c; nc < K; nc++) {
				Point cow = cows[nc];
				// 이 소가 방문하지 않은 곳에 소가 있다면
				// 길을 건너지 않으면 만나지 못 하는 쌍이 된다.
				if (!visited[cow.x][cow.y])
					cnt++;
			}

		}

		return cnt;
	}

	// bfs 탐색
	private static void bfs(int x, int y) {

		visited[x][y] = true;

		for (int d = 0; d < 4; d++) {
			int next_X = x + dx[d];
			int next_y = y + dy[d];
			// 범위
			if (next_X < 0 || next_X >= N || next_y < 0 || next_y >= N)
				continue;
			// 이미 방문
			if (visited[next_X][next_y])
				continue;
			// 길을 건너야 한다면
			if (bridges[x][y].contains(new Point(next_X, next_y)))
				continue;

			bfs(next_X, next_y);
		}

	}

}
