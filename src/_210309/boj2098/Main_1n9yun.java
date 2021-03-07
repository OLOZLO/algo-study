package _210309.boj2098;

import java.util.Scanner;

public class Main_1n9yun {
    static int n;
    static int[][] costs;
    static int[][] dp;
    static final int MAX = Integer.MAX_VALUE - 1_000_000;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        costs = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                costs[i][j] = sc.nextInt();
            }
        }

//        결국 사이클이기 떄문에 어느 지점에서 시작하든 최소로 모두 순회하는 경로는 같다.
//        따라서 한 시작점에 대해서만 구해주면 됨
        dp = new int[n][1<<n];
        System.out.println(back(0, 0, 1));
    }

    static int back(int started, int v, int visited){
        if(v == started && visited == (1<<n) - 1) return 0;
        else if(v == started && Integer.bitCount(visited) != 1) return MAX;
        if(dp[v][visited] != 0) return dp[v][visited];

        int ret = MAX;
        for(int i=0;i<n;i++){
            if(costs[v][i] == 0 || (visited & (1<<i)) != 0 && i != started) continue;
            ret = Math.min(ret, back(started, i, visited | (1<<i)) + costs[v][i]);
        }
        return dp[v][visited] = ret;
    }
}
