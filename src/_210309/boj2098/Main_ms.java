package _210309.boj2098;

import java.util.Arrays;
import java.util.Scanner;

public class Main_ms {
    static int N;
    static int[][] W;
    static int[][] dp;
    static final int INF = 987654321;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        W = new int[N][N];
        dp = new int[N][1 << N];

        for (int i = 0; i < N; i++) Arrays.fill(dp[i], INF);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                W[i][j] = sc.nextInt();

        System.out.println(TSP(0, 1));
    }

    static int TSP(int now, int visit) {
        if (dp[now][visit] != INF) return dp[now][visit];
        if (visit == (1 << N) - 1) {
            return W[now][0] == 0 ? INF : W[now][0];
        }

        int min = INF;

        for (int next = 0; next < N; next++) {
            if ((visit & (1 << next)) != 0 || W[now][next] == 0) continue;

            min = Math.min(min, W[now][next] + TSP(next, visit | (1 << next)));
        }

        return dp[now][visit] = min;
    }
}
