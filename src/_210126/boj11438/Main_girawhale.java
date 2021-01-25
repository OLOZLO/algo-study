package _210126.boj11438;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_girawhale {
    static int[] depth;
    static int[][] parent;
    static ArrayList<Integer>[] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        adj = new ArrayList[N + 1];
        depth = new int[N + 1]; // 노드의 깊이를 저장할 배열
        parent = new int[N + 1][18]; // [n][k] : n의 2^k번째 부모노드

        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();

        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());

            adj[n1].add(n2);
            adj[n2].add(n1);
        }

        depth[1] = 1;
        dfs(1, 1); // 노드 1을 깊이 1로 초기화한 후 dfs를 돌림

        int M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            sb.append(lca(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))).append('\n');
        }
        System.out.println(sb.toString());
    }

    static void dfs(int n, int h) {
        for (int a : adj[n]) {
            if (depth[a] != 0) continue; // 이미 찾았다면 pass

            depth[a] = h + 1; // 현재 노드보다 깊이를 1단계 증가
            parent[a][0] = n; // 자식노드의 2^0(= 1)위의 부모는 나!
            for (int i = 1; i <= (int) (Math.log(h + 2) / Math.log(2)); i++)
                parent[a][i] = parent[parent[a][i - 1]][i - 1]; // 설명하기 매우 힘듬.... 구글에 검색하는게 나을듯...

            dfs(a, h + 1); // 연결된 노드 dfs로 추가 탐색
        }
    }

    static int lca(int n1, int n2) {
        if (depth[n1] > depth[n2]) { // 무조건 n2의 깊이가 크도록 바꿈
            int tmp = n1;
            n1 = n2;
            n2 = tmp;
        }

        // log2(h)만큼 부모노드를 저장. 따라서 n1의 깊이보다 작아지지 않을 때까지 절반으로 줄여준다
        for (int i = (int) (Math.log(depth[n2] + 1) / Math.log(2)); i >= 0; i--) {
            if (depth[n1] <= depth[parent[n2][i]]) n2 = parent[n2][i];
        }

        if (n1 == n2) return n1;

        // 나랑 얘랑 아직도 다르니까 절반씩 줄여가면서 부모를 맞춰준다
        for (int i = (int) (Math.log(depth[n2] + 1) / Math.log(2)); i >= 0; i--) {
            if (parent[n1][i] == parent[n2][i]) continue;

            n1 = parent[n1][i];
            n2 = parent[n2][i];
        }

        return parent[n1][0];
    }
}
