package _210126.boj11437;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_girawhale {
    static int[] depth, parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        ArrayList<Integer>[] adj = new ArrayList[N + 1]; // 연결 저장할 배결
        depth = new int[N + 1]; // 나의 깊이는?
        parent = new int[N + 1]; // 나의 부모 노드는?

        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList<>();

        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());

            adj[n1].add(n2); // 양방향 연결
            adj[n2].add(n1);
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        int h = 1;
        depth[1] = 1; //1은 루트노드니까 깊이 = 1

        while (!queue.isEmpty()) {
            h++;
            int size = queue.size(); // 사이즈만큼 깊이 동일하다

            for (int s = 0; s < size; s++) {
                int cur = queue.poll();

                for (int i : adj[cur]) {
                    if (depth[i] != 0) continue; // 깊이 이미 저장햇으면 패스

                    queue.add(i);
                    depth[i] = h;
                    parent[i] = cur; // 아직 방문 안햇는데 나랑 연결되어있으니까 내 동생
                }
            }
        }

        int M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            sb.append(lca(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))).append('\n');
        }
        System.out.println(sb.toString());
    }

    static int lca(int n1, int n2) {
        while (n1 != n2) {
            if (depth[n1] < depth[n2]) // 깊이가 높으면 부모로 올리기
                n2 = parent[n2];
            else if (depth[n1] > depth[n2])
                n1 = parent[n1];
            else {                    // 깊이 같으면 동시에 올리면서 같은거 찾기
                n1 = parent[n1];
                n2 = parent[n2];
            }
        }
        return n1;
    }
}
