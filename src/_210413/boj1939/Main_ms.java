package _210413.boj1939;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
    https://ddb8036631.github.io/알고리즘%20풀이/백준_1939_중량제한/

    1. 결과 : 성공
    2. 시간복잡도 : O(logC*(N+M))
        - 0에서 10억까지를 이분 탐색을 한 뒤, 이를 토대로 BFS 탐색을 진행하기 때문입니다.
    3. 후기
        - 아이디어는 블로그에 적어놨습니다.
        - 이분 탐색을 써야겠다는 생각이 들었지만, low와 high를 어떻게 조정해줘야할 지와 답을 언제 담아야할 지를 생각하는 데 시간이 좀 걸렸습니다.
*/

public class Main_ms {
    static int N, M;
    static int start, dest;
    static ArrayList<Edge>[] adj;
    static boolean[] visit;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        adj = new ArrayList[N + 1];
        visit = new boolean[N + 1];

        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            adj[A].add(new Edge(B, C));
            adj[B].add(new Edge(A, C));
        }

        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        dest = Integer.parseInt(st.nextToken());

        int low = 0, high = 1_000_000_000;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (bfs(mid)) {
                low = mid + 1;
                answer = mid;
            } else high = mid - 1;
        }

        System.out.println(answer);
    }

    static boolean bfs(int mid) {
        Arrays.fill(visit, false);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visit[start] = true;

        while (!queue.isEmpty()) {
            int now = queue.poll();

            if (now == dest) return true;

            for (Edge next : adj[now]) {
                if (!visit[next.vertex] && next.cost >= mid) {
                    visit[next.vertex] = true;
                    queue.add(next.vertex);
                }
            }
        }

        return false;
    }

    static class Edge {
        int vertex;
        int cost;

        public Edge(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
    }
}
