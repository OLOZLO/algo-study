package _210126.boj11438;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_Taekyung2 {
    static int N;
    static ArrayList<Integer>[] adj;
    static boolean[] visited;
    static int[] level;
    static int[][] ancestor;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        adj = new ArrayList[N + 1];
        visited = new boolean[N + 1];
        level = new int[N + 1];
        ancestor = new int[N + 1][18];
        for(int i = 0; i <= N; i++)
            adj[i] = new ArrayList<>();
        for(int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }
        dfs(1, 1, 1);
        for(int i = 1; i <= 17; i++)
            for(int j = 1; j <= N; j++)
                ancestor[j][i] = ancestor[ancestor[j][i - 1]][i - 1];

        int M = Integer.parseInt(br.readLine());
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            bw.write(LCA(Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())) + "\n");
        }
        bw.flush();
    }

    static void dfs(int cur, int parent, int curLevel) {
        if(visited[cur]) return;
        visited[cur] = true;
        level[cur] = curLevel;
        ancestor[cur][0] = parent;

        for(int child : adj[cur])
            dfs(child, cur, curLevel + 1);
    }

    static int LCA(int a, int b) {
        if(level[a] < level[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        if(level[a] != level[b]) {
            int diff = level[a] - level[b];
            for(int i = 0; i <= 17; i++) {
                if((diff & (1 << i)) != 0)
                    a = ancestor[a][i];
            }
        }

        if(a == b) return a;
        for(int i = 17; i >= 0; i--) {
            if(ancestor[a][i] != ancestor[b][i]) {
                a = ancestor[a][i];
                b = ancestor[b][i];
            }
        }
        return ancestor[a][0];
    }
}
