package _210105.boj15591;

import java.util.*;

public class Main_ms {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int Q = sc.nextInt();

        List<int[]>[] adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList<>();

        for (int i = 0; i < N - 1; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            adj[a].add(new int[]{b, c});
            adj[b].add(new int[]{a, c});
        }

        for (int i = 0; i < Q; i++) {
            boolean[] visit = new boolean[N + 1];

            int k = sc.nextInt();
            int v = sc.nextInt();

            Queue<Integer> queue = new LinkedList<>();
            visit[v] = true;
            queue.add(v);

            int cnt = 0;
            while (!queue.isEmpty()) {
                int now = queue.poll();

                for (int j = 0; j < adj[now].size(); j++) {
                    int[] next = adj[now].get(j);

                    if (next[1] < k || visit[next[0]]) continue;

                    cnt++;
                    visit[next[0]] = true;
                    queue.add(next[0]);
                }
            }

            System.out.println(cnt);
        }
    }
}