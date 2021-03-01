package _210302.boj1956;

import java.util.Arrays;
import java.util.Scanner;

// https://ddb8036631.github.io/알고리즘%20풀이/백준_1956_운동/
// 플로이드-워셜 알고리즘 사용.
public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        int E = sc.nextInt();

        final int MAX = 987654321;
        int[][] dist = new int[V + 1][V + 1];
        for (int i = 1; i <= V; i++) Arrays.fill(dist[i], MAX);

        for (int i = 0; i < E; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            dist[a][b] = c;
        }

        // 플로이드-워셜 알고리즘 진행.
        // 모든 정점에서 다른 정점들로의 최단 거리를 갱신함.
        for (int k = 1; k <= V; k++) {
            for (int i = 1; i <= V; i++) {
                if (i == k) continue;
                for (int j = 1; j <= V; j++) {
                    if (j == k || j == i) continue;
                    if (dist[i][j] > dist[i][k] + dist[k][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }

        int answer = MAX;

        // dist[i][j] 요소가 MAX 값에서 갱신되었다면, 이는 정점 i에서 최소 하나 이상의 다른 정점을 거쳐 j로 이동했음을 나타냄.
        // 따라서 dist[i][j]와 dist[j][i] 값이 모두 MAX가 아닌 어떠한 갱신된 값이라면, 이는 정점 i를 시작으로 사이클이 생성됨을 의미함.
        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                if (i == j) continue;
                if (dist[i][j] != MAX && dist[j][i] != MAX) {
                    answer = Math.min(answer, dist[i][j] + dist[j][i]);
                }
            }
        }

        System.out.println(answer == MAX ? -1 : answer);
    }
}
