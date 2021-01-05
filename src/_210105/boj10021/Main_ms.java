package _210105.boj10021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_ms {
    static int N, C;
    static int[] x, y;
    static ArrayList<Edge> edges;
    static int[] parent;
    static int[] rank;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        x = new int[N];
        y = new int[N];
        edges = new ArrayList<>();
        parent = new int[N];
        for (int i = 0; i < N; i++)
            makeSet(i);
        rank = new int[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                int cost = (int) (Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));

                if (cost >= C)
                    edges.add(new Edge(i, j, cost));
            }
        }

        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost - o2.cost;
            }
        });

        int cnt = 0;
        int answer = 0;
        for (Edge edge : edges) {
            if (find(edge.v1) == find(edge.v2)) continue;

            union(edge.v1, edge.v2);
            answer += edge.cost;
            cnt++;
        }

        System.out.println(cnt == N - 1 ? answer : -1);
    }

    static class Edge {
        int v1, v2, cost;

        public Edge(int v1, int v2, int cost) {
            this.v1 = v1;
            this.v2 = v2;
            this.cost = cost;
        }
    }

    static void makeSet(int idx) {
        parent[idx] = idx;
    }

    static int find(int idx) {
        if (idx == parent[idx]) return idx;
        return parent[idx] = find(parent[idx]);
    }

    static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (rank[pa] > rank[pb]) {
            parent[pb] = pa;
        } else {
            parent[pa] = pb;
            if (rank[pa] == rank[pb])
                rank[pa]++;
        }
    }
}