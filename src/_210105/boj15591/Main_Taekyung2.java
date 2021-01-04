package _210105.boj15591;

import java.util.*;

public class Main_Taekyung2 {

    static class E {
        int to, u;

        public E(int to, int u) {
            this.to = to;
            this.u = u;
        }
    }
    static int N, Q;
    static List<E>[] adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        Q = sc.nextInt();
        adj = new ArrayList[N + 1];
        for(int i = 0; i < N + 1; i++)
            adj[i] = new ArrayList<>();
        for(int i = 0; i < N - 1; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int u = sc.nextInt();
            adj[from].add(new E(to, u));
            adj[to].add(new E(from, u));
        }
        for(int i = 0; i < Q; i++) {
            int k = sc.nextInt();
            int v = sc.nextInt();
            int ret = 0;
            boolean[] visited = new boolean[N + 1];
            visited[v] = true;
            Queue<E> q = new LinkedList<>();
            q.add(new E(v, 0));
            while(!q.isEmpty()) {
                E cur = q.poll();
                for(E e : adj[cur.to]) {
                    if(visited[e.to] || e.u < k) continue;
                    q.add(e);
                    visited[e.to] = true;
                    ret++;
                }
            }
            System.out.println(ret);
        }
    }
}