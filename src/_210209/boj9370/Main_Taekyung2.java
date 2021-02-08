package _210209.boj9370;

import java.util.*;

public class Main_Taekyung2 {
    static class Edge implements Comparable<Edge> {
        int to, w;
        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return this.w - o.w;
        }
    }
    static int n, m, t, s, g, h;
    static ArrayList<Edge>[] adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        for(int iter = 1; iter <= tc; iter++) {
            n = sc.nextInt();
            m = sc.nextInt();
            t = sc.nextInt();
            s = sc.nextInt();
            g = sc.nextInt();
            h = sc.nextInt();
            int d = 0, tmp;
            adj = new ArrayList[n + 1];
            ArrayList<Integer> ret = new ArrayList<>();
            for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int from = sc.nextInt(), to = sc.nextInt(), w = sc.nextInt();
                if((from == g && to == h) || from == h && to == g) d = w;
                adj[from].add(new Edge(to, w));
                adj[to].add(new Edge(from, w));
            }
            // 다익스트라 한번만 할랫는데 g -> h 안거치고 갈 수도 있는 거같음
            // s -> g, s -> h 중에 먼거에서 다익스트라 한번 더해서 후보지까지 거리 더 함
            int[] a = dijkstra(s);
            int[] b;
            if(a[g] > a[h]) {
                b = dijkstra(g);
                tmp = h;
            }
            else {
                b = dijkstra(h);
                tmp = g;
            }
            for (int i = 0; i < t; i++) {
                int k = sc.nextInt();
                // 최단거리랑 g -> h 포함한 최단거리랑 같으면 답
                if(a[tmp] + d + b[k] == a[k]) ret.add(k);
            }
            Collections.sort(ret);
            for(int v : ret)
                System.out.print(v + " ");
        }
    }

    public static int[] dijkstra(int src) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        pq.add(new Edge(src, 0));
        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            if(cur.w > dist[cur.to]) continue;
            for(Edge E : adj[cur.to]) {
                int next = E.to;
                int nextWeight = E.w + cur.w;
                if(nextWeight < dist[next]) {
                    dist[next] = nextWeight;
                    pq.add(new Edge(next, nextWeight));
                }
            }
        }
        return dist;
    }
}
