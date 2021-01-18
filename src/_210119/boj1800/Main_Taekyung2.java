package _210119.boj1800;

import java.util.*;

public class Main_Taekyung2 {
    static final int INF = 10000000;

    static class Edge implements Comparable<Edge> {
        int to, cost;

        public Edge(int to, int w) {
            this.to = to;
            this.cost = w;
        }

        @Override
        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }
    }
    static int N, P, K;
    static int[] remove;
    static ArrayList<Edge>[] adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        P = sc.nextInt();
        K = sc.nextInt();
        adj = new ArrayList[N + 1];
        int lo = 0, hi = 0 , ret = -1;
        for(int i = 1; i < N + 1; i++)
            adj[i] = new ArrayList<>();

        for(int i = 0; i < P; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int cost = sc.nextInt();
            adj[from].add(new Edge(to, cost));
            adj[to].add(new Edge(from, cost));
            hi = Math.max(hi, cost);
        }
        while(lo <= hi) {
            int mid = (hi +lo) / 2;
            if(dijkstra(mid)) {
                hi = mid - 1;
                ret = mid;
            }
            else {
                lo = mid + 1;
            }
        }
        System.out.println(ret);
    }

    public static boolean dijkstra(int minCost) {
        remove = new int[N + 1];
        Arrays.fill(remove, INF);
        remove[1] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(1, 0));
        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            if(cur.cost > remove[cur.to]) continue;
            for(int i = 0; i < adj[cur.to].size(); i++) {
                int next = adj[cur.to].get(i).to;
                int nextCost = cur.cost + ((adj[cur.to].get(i).cost > minCost) ? 1 : 0); 
                if(remove[next] > nextCost) {
                    remove[next] = nextCost;
                    pq.add(new Edge(next, nextCost));
                }
            }
        }
        return remove[N] <= K;
    }
}
