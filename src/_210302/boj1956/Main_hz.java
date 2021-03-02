package _210302.boj1956;

import java.util.*;

public class Main_hz {
    static List<Node>[] adj;
    static int V, E;

    static class Node {
        int to, cost;

        Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    // 사이클을 이루는 도로의 길이의 합이 최소가 되도록
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        V = sc.nextInt();
        E = sc.nextInt();

        adj = new ArrayList[V+1];
        for (int i = 1; i <= V; i++) adj[i] = new ArrayList<>();

        for (int e = 0; e < E; e++)
            adj[sc.nextInt()].add(new Node(sc.nextInt(), sc.nextInt()));

        int[] dist = new int[V+1];

        // 각 정점에서 자기 자신으로 돌아오는 사이클이 존재하는지를 bfs를 통해 확인함.
        // 있으면 자기자신까지 돌아오는 비용, 없을 경우 MAX값 저장
        for (int i = 1; i <= V; i++) {
            dist[i] = bfs(new Node(i, 0));
        }

        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= V; i++)
            result = Math.min(result, dist[i]);

        System.out.println(result == Integer.MAX_VALUE ? -1 : result);
    }

    public static int bfs(Node n) {
        int start = n.to;

        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.cost, o2.cost);
            }
        });

        pq.add(n);
        boolean[] visited = new boolean[V+1];

        while(!pq.isEmpty()) {
            Node now = pq.poll();

            if (visited[now.to] && now.to == start) {
                return now.cost;
            }

            if (visited[now.to]) continue;
            visited[now.to] = true;

            for (Node node : adj[now.to]) {
                pq.add(new Node(node.to, now.cost+node.cost));
            }
        }

        return Integer.MAX_VALUE;
    }
}
