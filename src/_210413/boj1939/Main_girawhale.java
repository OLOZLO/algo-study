package _210413.boj1939;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
1. 결과 : 성공
2. 시간 복잡도 : O(log10^9 * (V + E))
                < O(log10^9 * (10^4 + 10^5))
                = O(10^5)
    - 이유 : 인접리스트 bfs의 시간복잡도 = O(V+E)
            이분 탐색의 시간복잡도 = O(logN)

3. 풀이 : 최대값을 먼저 정해두고 탐색이 가능한지 돌리는 bfs를 돌린다
            정상적으로 탐색이 가능하면 최대값을 늘리고, 아니면 줄이는 이분탐색을 통해 답을 구해보자!
*/
public class Main_girawhale {
    static int N, M, start, dest;
    static ArrayList<int[]>[] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = nextInt(st);
        M = nextInt(st);

        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)
            adj[i] = new ArrayList<>();

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n1 = nextInt(st), n2 = nextInt(st), c = nextInt(st);

            adj[n1].add(new int[]{n2, c});
            adj[n2].add(new int[]{n1, c});
        }
        st = new StringTokenizer(br.readLine());
        start = nextInt(st);
        dest = nextInt(st);

        int s = 1, e = (int) 1e9; // 최소 1, 최대 10^9
        int ans = 0;
        while (s <= e) {
            int m = (s + e) / 2;

            if (solve(m)) { // 최대가 m일 때, 탐색이 가능하니?
                ans = m; // 가능하면 답 저장ㄱ
                s = m + 1; // 범위 늘리고
            } else e = m - 1; // 안되면 줄여서 다시 해보장
        }

        System.out.println(ans);
    }

    static boolean solve(int m) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visit = new boolean[N + 1];

        queue.add(start);
        visit[start] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur == dest) return true; // 주어진 값으로 도착점까지 탐색이 가능! true

            for (int[] next : adj[cur]) {
                if (next[1] < m || visit[next[0]]) continue; // 주어진 값보다 작은 도로는 탐색하지 못함. pass

                visit[next[0]] = true;
                queue.add(next[0]);
            }
        }
        return false; // 도착 못하고 큐가 비어버림. false반환
    }

    static int nextInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }


}
