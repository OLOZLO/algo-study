package _210119.boj1800;

import java.util.*;

/**
 * 처음에 문제 제대로 안읽어서 그냥 다익스트라 쓰고 실제 최단 경로 구해서
 * 그 중에 큰거 K개 빼고 남은 거 중에 가장 큰거 구하는 식으로 했는데 다하고 보니 아니었음
 *
 * 돈 내는 최소 비용을 구하라 그래서 최적화 문제로 가닥 잡고 dp 아니면 이분 탐색으로 하려고 했는데
 * 알고리즘 분류에 이분 탐색 봐버려서 그냥 이분 탐색으로 풀었
 */
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
        // 그냥 입력 받는 부분들
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
            // 안해도 되긴한데 이분 탐색 상한 값 그나마 좀 줄여놨음
            hi = Math.max(hi, cost);
        }
        while(lo <= hi) {
            int mid = (hi +lo) / 2;
            // 최소 비용 mid만큼 내는거 성공했으니까, 일단 킵하고 더 줄여보자
            if(dijkstra(mid)) {
                hi = mid - 1;
                ret = mid;
            }
            // 실패하면 돈 더내야 되니까 올림
            else {
                lo = mid + 1;
            }
        }
        System.out.println(ret);
    }

    public static boolean dijkstra(int minCost) {
        // 1번에서 다른 지점으로 가는데 minCost보다 큰게 몇개나 있는지 기록
        // 원래 다익스트라에서는 시작점에서 다른 곳으로 가는 최단 거리를 계속 업데이트하는 배열로 사용하는데 여기서는 약간 변형
        remove = new int[N + 1];
        Arrays.fill(remove, INF);
        remove[1] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(1, 0));
        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            // 현재까지 왔을 때 K보다 큰 값이 더 적게 나온게 더 좋음
            if(cur.cost > remove[cur.to]) continue;
            for(int i = 0; i < adj[cur.to].size(); i++) {
                int next = adj[cur.to].get(i).to;
                // 간선이 K보다 크면 무료 기회 하나 없앰
                int nextCost = cur.cost + ((adj[cur.to].get(i).cost > minCost) ? 1 : 0); 
                if(remove[next] > nextCost) {
                    remove[next] = nextCost;
                    pq.add(new Edge(next, nextCost));
                }
            }
        }
        // 다익스트라 다 돌렸을 때 K보다 크면 minCost보다 큰 간선이 K개보다 많거나, 아니면 연결 안되있어서 못가는거(INF)
        return remove[N] <= K;
    }
}
