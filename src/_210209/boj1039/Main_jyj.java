package _210209.boj1039;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_jyj {

	static int N; // 정수
	static int K; // 연산 횟수
	static String str;
	static boolean[][] visit = new boolean[1000001][11]; // [N][K]

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String tmp[] = br.readLine().split(" ");
		str = tmp[0];
		N = Integer.parseInt(str);
		K = Integer.parseInt(tmp[1]);

		bfs();
	}

	public static void bfs() {
		Queue<Pair> q = new LinkedList<>();
		// Point(현재 정수, 연산횟수)
		q.add(new Pair(N, 0));
		// visit[현재 정수][연산 횟수] = 수행여부
		visit[N][0] = true;

		while (!q.isEmpty()) {
			// 연산회수 (y) 가 K 와 동일하면 종료
			if (q.peek().cnt == K) {
				break;
			}

			Pair p = q.poll();
			for (int i = 0; i < str.length() - 1; i++) {
				for (int j = i + 1; j < str.length(); j++) {
					// 정수 자리 변경 작업 수행
					int next = solve(p.num, i, j);
					// 변경 가능한 상태 && 아직 방문하지 않은 곳
					if (next != -1 && visit[next][p.cnt + 1] == false) {
						visit[next][p.cnt + 1] = true;
						// 변경된 정수, 연산 수행회수 + 1
						q.add(new Pair(next, p.cnt + 1));
					}
				}
			}
		}

		int ans = -1;
		while (!q.isEmpty()) {
			Pair p = q.poll();
			ans = Math.max(ans, p.num);
		}
		System.out.println(ans);
	}

	static public int solve(int x, int i, int j) {

		StringBuilder sb = new StringBuilder();
		sb.append(x);

		// i가 첫번째 자리이고, j번째 자리 수가 0 이면 -1 리턴
		if (i == 0 && sb.charAt(j) == '0') {
			return -1;
		}

		// x정수의 i번째 자리와 j 번째 자리 수 변경 작업
		char tmp = sb.charAt(i);
		sb.setCharAt(i, sb.charAt(j));
		sb.setCharAt(j, tmp);

		// 변경된 정수 리턴
		return Integer.parseInt(sb.toString());
	}

	static class Pair {
		int num;
		int cnt;

		public Pair(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}

	}
}
