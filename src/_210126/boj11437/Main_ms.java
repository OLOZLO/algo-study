package _210126.boj11437;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_ms {
    static int N, M;
    static ArrayList<Integer>[] adj;
    static boolean[] visit;
    static int[] depth;
    static int[] parent;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList<>();
        visit = new boolean[N + 1];
        depth = new int[N + 1];
        parent = new int[N + 1];

        // 주어진 노드 쌍으로 인접 리스트를 만들어 트리를 구성합니다.
        for (int i = 0; i < N - 1; i++) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();

            adj[v1].add(v2);
            adj[v2].add(v1);
        }

        dfs(1, 0); // 먼저 dfs를 돌아, 각 노드별로 [깊이, 부모 노드의 번호] 를 세팅합니다.

        M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            System.out.println(lca(sc.nextInt(), sc.nextInt())); // 각 쌍에 대해lca 함수를 돌려, 최소 공통 조상을 출력합니다.
        }
    }

    // 일반 dfs 로직 +
    // 돌면서 [깊이, 부모 노드의 번호]를 저장.
    private static void dfs(int from, int d) {
        visit[from] = true;
        depth[from] = d;

        for (int to : adj[from]) {
            if (!visit[to]) {
                parent[to] = from;
                dfs(to, d + 1);
            }
        }
    }

    private static int lca(int a, int b) {
        // 두 정점의 깊이를 맞춰주는 작업.
        while (depth[a] != depth[b]) {
            if (depth[a] > depth[b]) {
                a = parent[a];
            } else
                b = parent[b];
        }

        // 깊이 맞춰놨더니 두 노드가 같은 노드 번호를 가리키면? 걔가 최소 공통 조상!
        if (a == b) return a;

        // 위 경우가 아니라면, 부모가 같을 때까지 깊이를 하나씩 감소시켜 나간다.
        while (parent[a] != parent[b]) {
            a = parent[a];
            b = parent[b];
        }

        return parent[a];
    }
}
