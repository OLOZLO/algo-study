package _210309.boj16946;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Main_jyj {

	// 보자마자 BFS 써야지 했는데 시간 초과가 발생한다.
	// BFS의 비용을 최소화하기 위해 그룹핑이 필요하다
	// 이러한 과정을 분리 집합이라고 하더라.
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	private static int[][] map;
	private static int[][] group;
	private static int n;
	private static int m;
	private static HashMap<Integer, Integer> groupSize;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		n = sc.nextInt();
		m = sc.nextInt();

		map = new int[n][m];
		group = new int[n][m];

		for (int i = 0; i < n; i++) {
			String str = sc.next();
			for (int j = 0; j < str.length(); j++) {
				map[i][j] = str.charAt(j);
			}
		}

		int groupCnt = 1;
		groupSize = new HashMap<>();
		for (int i = 0; i < n; i++) {

			for (int j = 0; j < m; j++) {
				if (map[i][j] == 0 && group[i][j] == 0) {
					groupSize.put(groupCnt, bfs(i, j, groupCnt));
					groupCnt++;
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (group[i][j] == 0) {
					sb.append(count(i, j) + "");
					continue;
				}
				sb.append(0 + "");
			}
			sb.append("\n");
		}
		System.out.println(sb);

	}

	private static int bfs(int x, int y, int groupCnt) {
		int cnt = 1;
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(x, y));
		group[x][y] = groupCnt;
		while (!q.isEmpty()) {
			Point point = q.poll();
			int[] dx = { 0, 1, 0, -1 };
			int[] dy = { 1, 0, -1, 0 };
			for (int i = 0; i < 4; i++) {
				int sx = point.x + dx[i];
				int sy = point.y + dy[i];

				if (sx < 0 || sy < 0 || sx >= n || sy >= m)
					continue;
				// 아직 그룹에 속하지 않았고 && 벽이 아니라면 카운트해준다.
				if (group[sx][sy] == 0 && map[sx][sy] == 0) {
					group[sx][sy] = groupCnt;
					cnt++;
					q.add(new Point(sx, sy));
				}
			}
		}
		return cnt;
	}

	private static int count(int x, int y) {
		int cnt = 1;
		if (map[x][y] == 0)
			return 0;
		Set<Integer> set = new HashSet<>();

		// 벽에 맞닿은 4방향만 구하면됨
		// 그 방향의 그룹의 0의 갯수 정보는 이미 구했기 때문
		for (int i = 0; i < 4; i++) {
			int[] dx = { 0, 1, 0, -1 };
			int[] dy = { 1, 0, -1, 0 };

			int sx = x + dx[i];
			int sy = y + dy[i];

			if (sx < 0 || sy < 0 || sx >= n || sy >= m || group[sx][sy] == 0)
				continue;
			// 맞닿은 그룹이 중복일 경우를 위해 set에 저장함
			set.add(group[sx][sy]);

		}
		for (int size : set) {
			cnt += groupSize.get(size);
		}

		return cnt % 10;
	}

	public static class Point {
		int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
