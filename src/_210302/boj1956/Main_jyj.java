package _210302.boj1956;

import java.util.Scanner;

public class Main_jyj {
	static final int INF = 987654321;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int V = sc.nextInt();
		int E = sc.nextInt();

		int[][] arr = new int[V + 1][V + 1];

		for (int i = 1; i <= V; i++) {
			for (int j = 1; j <= V; j++) {
				if (i != j) {
					arr[i][j] = INF;
				}
			}
		}

		for (int i = 0; i < E; i++) {

			int a = sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();

			arr[a][b] = c;
		}

		// 플로이드 와샬 알고리즘 수행
		for (int k = 1; k <= V; k++) {
			for (int i = 1; i <= V; i++) {
				for (int j = 1; j <= V; j++) {
					if (i == j) {
						continue;
					}

					if (arr[i][j] > arr[i][k] + arr[k][j]) {
						arr[i][j] = arr[i][k] + arr[k][j];
					}
				}
			}
		}

		int ans = INF;
		for (int i = 1; i <= V; i++) {
			for (int j = 1; j <= V; j++) {
				if (i == j) {
					continue;
				}

				// 자기 자신을 제외한 두 정점이
				// 서로에게 가는 경로가 있다면, 사이클이 존재한다는 뜻.
				if (arr[i][j] != INF && arr[j][i] != INF) {
					ans = Math.min(ans, arr[i][j] + arr[j][i]);
				}
			}
		}

		// ans가 초기값이면 사이클이 존재하지 않음.
		ans = (ans == INF) ? -1 : ans;

		System.out.println(ans);
	}

}
