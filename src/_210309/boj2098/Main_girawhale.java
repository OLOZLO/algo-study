package _210309.boj2098;

import java.util.Scanner;

public class Main_girawhale {
    static int N, MAX = 987654321;
    static int[][] cost, dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        cost = new int[N][N];
        dp = new int[N][1 << N]; // [i][j], j들을 거쳐 i까지 올 때 최소 돈

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                cost[i][j] = sc.nextInt();
        }
        System.out.println(solve(1, 0)); // 0번 도시는 방문했다!
    }

    static int solve(int visit, int cur) { // 방문 기록, 현재 온 도시
        if (dp[cur][visit] != 0) return dp[cur][visit]; // 이미 구햇으면 반환
        if (visit == (1 << N) - 1) // 끝까지 다돌음
            return cost[cur][0] == 0 ? MAX : cost[cur][0]; // 현재 도시에서 0번으로 가는 길 있으면 반환, 아니면 MAX를 리턴해 갱신 안되도록 함

        int min = MAX;
        for (int i = 0; i < N; i++) {
            if (cost[cur][i] == 0 || (visit & 1 << i) > 0) continue; // 길이 없거나 방문한 도시면 pass
            min = Math.min(solve(visit | (1 << i), i) + cost[cur][i], min); // 갔다온 비용이 더 작으면 갱신
        }

        return dp[cur][visit] = min; // 대입하고 리턴
    }
}
