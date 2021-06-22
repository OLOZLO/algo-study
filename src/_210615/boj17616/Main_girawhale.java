package _210615.boj17616;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(V+E)
 *
 * 3. 접근 방식
 *      - X로 들어오는 모든 점은 X보다 앞에 있는 애들
 *      - X로부터 나가는 애들은 모두 X보다 뒤에 있는 애들
 *      - 이 두가지를 DFS를 통해 구해주었당
 *
 * 4. 후기
 *      방문 처리 잘못해서 엄청 헤맴.... 방문 처리의 중요성..
 *
 */
public class Main_girawhale {
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = nextInt(st), M = nextInt(st), X = nextInt(st);

        visit = new boolean[N + 1];
        List<Integer>[] adj = new ArrayList[N + 1]; // 기본 연결리스트
        List<Integer>[] rAdj = new ArrayList[N + 1]; // 역방향 연결리스트
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
            rAdj[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = nextInt(st), n2 = nextInt(st);
            adj[n1].add(n2);
            rAdj[n2].add(n1);
        }

        int out = dfs(adj, X); // 연결리스트에서 X에서 나가는 경우 모두 계산
        int in = dfs(rAdj, X); // 역방향 연결리스트에서 X로 들어오는 경우 모두 계산
        System.out.println(in + " " + (N + 1 - out));
    }

    static int dfs(List<Integer>[] adj, int idx) {
        visit[idx] = true;

        int ret = 1;
        for (int a : adj[idx]) {
            if (!visit[a])
                ret += dfs(adj, a);
        }
        return ret;
    }

    private static int nextInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
}
