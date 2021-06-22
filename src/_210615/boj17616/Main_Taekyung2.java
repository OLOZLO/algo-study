package _210615.boj17616;

import java.io.*;
import java.util.*;

public class Main_Taekyung2 {

    /**
     * [골드3] 등수 찾기
     * 1. 결과 : 맞았습니다.
     * 2. 시간복잡도 : O(N + M)
     *
     * 3. 후기
     * 	- 처음에 봤을 땐, 위상정렬 문제 같앗는데 하다보니 잘 안됨
     * 	- 질문하기를 약간 참고했음..
     */


    static int N, M, X, u, v;
    static boolean[] visited;

    public static int stoi(String s) { return Integer.parseInt(s); }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken());
        M = stoi(st.nextToken());
        X = stoi(st.nextToken());

        // 등수가 높은 쪽으로 가는 그래프와, 낮은 쪽으로 가는 그래프 두 개를 만든다
        List<Integer>[] adj = new ArrayList[N + 1];
        List<Integer>[] radj = new ArrayList[N + 1];
        visited = new boolean[N + 1];

        for(int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
            radj[i] = new ArrayList<>();
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = stoi(st.nextToken()), b = stoi(st.nextToken());
            adj[b].add(a);
            radj[a].add(b);
        }

        // 등수가 높은 쪽으로 가는 그래프를 탐색 알고리즘을 했을 때
        // 탐색이 가능하다면 확실히 나보다 등수가 높은 친구들
        u = dfs(X, adj);
        Arrays.fill(visited, false);

        // 등수가 낮은 쪽으로 가는 그래프를 탐색 알고리즘을 했을 때
        // 탐색이 가능하다면 확실히 나보다 등수가 낮은 친구들
        v = N - dfs(X, radj) + 1;
        System.out.println(u + " " + v);
    }

    // 방문한 노드들 개수를 카운트합니다
    public static int dfs(int cur, List<Integer>[] adj) {
        visited[cur] = true;
        int ret = 1;
        for(int next : adj[cur]) {
            if(!visited[next])
                ret += dfs(next, adj);
        }
        return ret;
    }
}
