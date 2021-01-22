package _210126.boj11437;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_girawhale {
    static int[] degree, parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        ArrayList<Integer>[] adj = new ArrayList[N + 1];
        degree = new int[N + 1];
        parent = new int[N + 1];

        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList<>();

        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()), n2 = Integer.parseInt(st.nextToken());

            adj[n1].add(n2);
            adj[n2].add(n1);
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        int h = 1;
        degree[1] = 1;

        while (!queue.isEmpty()) {
            h++;
            int size = queue.size();

            for (int s = 0; s < size; s++) {
                int cur = queue.poll();

                for (int i : adj[cur]) {
                    if (degree[i] != 0) continue;

                    queue.add(i);
                    degree[i] = h;
                    parent[i] = cur;
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
            if (degree[n1] < degree[n2])
                n2 = parent[n2];
            else if (degree[n1] > degree[n2])
                n1 = parent[n1];
            else {
                n1 = parent[n1];
                n2 = parent[n2];
            }
        }
        return n1;
    }
}
