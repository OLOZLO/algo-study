package _210209.boj9370;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_ms {
    static int n, m, t, s, g, h;
    static ArrayList<Edge>[] adj;
    static Info[] infos; // 각 정점까지의 "최단 거리"와 정점 g와 h 사이의 "간선 사용 유무"를 체크하는 Info 객체를 갖는 배열.
    static boolean[] visit;
    static TreeMap<Integer, Boolean> candidate;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int TC = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++)
                adj[i] = new ArrayList<>();

            infos = new Info[n + 1];
            for (int i = 1; i <= n; i++)
                infos[i] = new Info(Integer.MAX_VALUE, false); // 일반 다익스트라에 사용되는 최소 비용 배열에 추가로 g-h 간선 사용 유무를 추가함.

            visit = new boolean[n + 1];

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int v1, v2, cost;

                v1 = Integer.parseInt(st.nextToken());
                v2 = Integer.parseInt(st.nextToken());
                cost = Integer.parseInt(st.nextToken());

                adj[v1].add(new Edge(v2, cost));
                adj[v2].add(new Edge(v1, cost));
            }

            candidate = new TreeMap<>();
            for (int i = 0; i < t; i++)
                candidate.put(Integer.parseInt(br.readLine()), false); // treemap에 후보 정점들을 다 false로 초기화 함.

            PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
                @Override
                public int compare(Edge o1, Edge o2) {
                    return o1.cost - o2.cost;
                }
            });

            infos[s].dist = 0;
            Edge start = new Edge(s, 0);
            pq.add(start);

            while (!pq.isEmpty()) {
                Edge now = pq.poll();
                visit[now.vertex] = true;

                if (candidate.containsKey(now.vertex) && infos[now.vertex].used) { // 큐에서 뽑은 정점이 후보로 지정된 정점이고, 해당 정점까지 최단 거리로 왔을 때 g-h 간선을 사용했다면? 후보 목적지를 채택함(true).
                    candidate.put(now.vertex, true);
                }

                for (Edge next : adj[now.vertex]) {

                    // 목적지까지의 비용이 다음 간선을 채택했을 때 비용과 동일한 경우, g-h 간선을 사용해 갈 수 있는 지 판단해 줘야함.
                    // 따라서, 현재 정점과 다음 정점이 g, h에 해당된다면 Info 객체의 used를 true로 갱신해주어야 함. -> 나중에 g-h 간선을 이용해 왔다고 알 방법이 이 뿐임.
                    if (!visit[next.vertex] && infos[next.vertex].dist >= infos[now.vertex].dist + next.cost) {
                        if(infos[next.vertex].used && infos[next.vertex].dist == infos[now.vertex].dist + next.cost) continue; // 목적지까지의 비용은 같은데, 이미 다음 정점까지 g-h 간선을 이용해 도착한 이력이 있다면, 큐에 넣지 않음.

                        infos[next.vertex].dist = infos[now.vertex].dist + next.cost;   // 비용 갱신 해주고,
                        infos[next.vertex].used = infos[now.vertex].used;               // 다음 정점에 대한 used를 이전 정점까지의 used(현재 정점까지 g-h 간선을 사용한 유무)로 갈아 끼움.

                        Edge edge = new Edge(next.vertex, infos[next.vertex].dist);     // 다음 정점 번호와 최단 거리로 Edge 객체 하나 만들어 주고,

                        pq.add(edge);                                                   // 우선순위 큐에 추가!

                        if ((now.vertex == g && next.vertex == h) || (now.vertex == h && next.vertex == g)) {   // 현재 정점과, 다음 정점이 g, h 쌍과 일치한다면,
                            infos[next.vertex].used = true;                                                     // 다음 정점에 대한 used 변수를 true로 바꿔줌. g-h 간선을 이용했다는 표시!
                        }
                    }
                }
            }

            // treemap 썼으니까, 후보 목적지 번호를 오름차순으로 정렬되어 있을테니, true인 정점들 번호를 순서대로 출력함.
            for (Map.Entry entry : candidate.entrySet()) {
                if ((boolean) entry.getValue()) {
                    System.out.print(entry.getKey() + " ");
                }
            }
            System.out.println();
        }
    }

    // Edge 클래스 : 간선의 목적지(to) 정점과 그 간선으로의 비용(cost)를 저장하기 위함.
    private static class Edge {
        int vertex, cost;

        public Edge(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
    }

    // Info 클래스 : 정점까지의 최단 거리(dist)와 정점까지 도달했을 때 g-h 간선을 사용했나 안했나(used)를 저장하기 위함.
    private static class Info {
        int dist;
        boolean used;

        public Info(int dist, boolean used) {
            this.dist = dist;
            this.used = used;
        }
    }
}
