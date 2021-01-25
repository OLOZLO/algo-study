package _210126.boj11437;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_Taekyung2 {

    static int N;
    static ArrayList<Integer>[] adj;
    static boolean[] visited;
    static int[] level;
    static int[] ancestor;

    public static void main(String[] args) throws IOException {
        // 입
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        adj = new ArrayList[N + 1];
        visited = new boolean[N + 1];
        level = new int[N + 1];
        ancestor = new int[N + 1];
        for(int i = 0; i <= N; i++)
            adj[i] = new ArrayList<>();
        for(int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }
        // 미리 깊이랑 부모 노드 체크
        dfs(1, 1, 1);

        int M = Integer.parseInt(br.readLine());
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            bw.write(LCA(Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())) + "\n");
        }
        bw.flush();
    }

    // dfs로 내려가면서 체크
    static void dfs(int cur, int parent, int curLevel) {
        if(visited[cur]) return;
        visited[cur] = true;
        level[cur] = curLevel;
        ancestor[cur] = parent;

        for(int child : adj[cur])
            dfs(child, cur, curLevel + 1);
    }

    static int LCA(int a, int b) {
        // 항상 a를 더 높은 레벨로 맞춤
        if(level[a] < level[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        // 레벨 차이만큼 a를 올린다
        if(level[a] != level[b]) {
            int diff = level[a] - level[b];
            while(diff-- != 0)
                a = ancestor[a];
        }
        // a 올렸을 때 b랑 같으면 그 자체가 lca
        if(a == b) return a;
        // 같이 레벨 올리면서 찾기
        while(ancestor[a] != ancestor[b]) {
            a = ancestor[a];
            b = ancestor[b];
        }
        // 현재 lca 바로 밑에 있음, 부모 노드 리턴
        return ancestor[a];
    }
}