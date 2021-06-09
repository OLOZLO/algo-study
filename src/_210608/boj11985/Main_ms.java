package _210608.boj11985;

import java.util.Scanner;

/*
 *   1. 결과 : 제출 안해봄. 테케2부터 틀림.
 *   2. 접근 방식
 *       - DP
 *       - dp[i][j]는 최대 i개까지 담을 수 있는 상자에 j번째 오렌지까지 담았을 때의 최소값을 의미.
 *       - 과정은 아래와 같음.
 *           1. 오렌지는 순서대로 담을 수 있기에 cost 배열에 그 값을 기록함.
 *               1. dp[1][x]는 최대 1개만 담을 수 있는 상자를 의미함. 1개밖에 못담으므로, K+s(a-b)에서 (a-b)는 0이 되므로 K값으로 고정됨. 따라서 dp[1][1]~dp[1][M]을 다 K로 고정시킴.
 *               2. 나머지는 연달아 비용 계산 후 담음. 예를 들어, dp[3][4]는 2, 3, 4번째 오렌지를 상자에 담는 비용을 계산한 값이 담김.
 *           2. bottom-up 방식으로 최소값을 갱신시켜 나감.
 *               - dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - i] + cost[i][j]);
 *               - 기존 (i-1)개 담을 수 있는 상자를 택하느냐 vs i개 담을 수 있는 상자를 택하느냐
 */

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        int[] arr = new int[N + 1];

        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();

        int[][] cost = new int[M + 1][N + 1];
        int[][] dp = new int[M + 1][N + 1];

        for (int i = 1; i <= M; i++) {
            for (int j = i; j <= N; j++) {
                int min = (int) 1e9, max = 1;

                for (int k = j; k > j - i; k--) {
                    min = Math.min(min, arr[k]);
                    max = Math.max(max, arr[k]);
                }

                cost[i][j] = K + i * (max - min);

                if (i == j) {
                    for (int k = i + 1; k <= M; k++) {
                        dp[k][j] = cost[i][j];
                    }
                }
            }
        }

        dp[1][1] = cost[1][1];
        for (int j = 2; j <= N; j++) dp[1][j] = dp[1][j - 1] + cost[1][j];

        for (int i = 2; i <= M; i++) {
            for (int j = i; j <= N; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - i] + cost[i][j]);
            }
        }

        System.out.println(dp[M][N]);
    }
}
