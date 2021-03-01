package _210302.boj1956;

import java.util.*;

public class Main_Taekyung2 {
    static int V, E, INF = 400_0000, ret = INF;
    static int[][] adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt(); E = sc.nextInt();
        adj = new int[V][V];
        for(int i = 0; i < V; i++)
            Arrays.fill(adj[i], INF);
        for(int i = 0; i < E; i++)
            adj[sc.nextInt() - 1][sc.nextInt() - 1] = sc.nextInt();
        // 플로이드 와샬로 최단 경로 구함
        for(int k = 0; k < V; k++)
            for(int i = 0; i < V; i++)
                for (int j = 0; j < V; j++)
                    adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
        // 자기 자신으로 돌아오는 최단경로 중에 가장 작은 거 답
        for(int i = 0; i < V; i++)
            ret = Math.min(ret, adj[i][i]);
        System.out.println(ret == INF ? -1 : ret);
    }
}