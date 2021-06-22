package _210615.boj17616;

import java.util.*;

/*
 *   [골드3] 등수 찾기
 *
 *   https://ddb8036631.github.io/boj/17616_등수-찾기/
 *   1. 결과 : 풀지 못함 -> 풀이보고 품.
 *   2. 시간복잡도 : O(N+M)
 *       - 이유 : 인접리스트를 이용해 BFS 탐색을 진행했기 때문.
 *   3. 접근 방식
 *       - 인접 리스트 두 개를 이용해 각각으로 BFS 탐색.
 *           1. 문제에서 주어지는 점수 순서 A, B를 두 개의 인접리스트에 각각 정방향, 역방향으로 저장한다.
 *           2. 정방향 BFS 탐색으로 "K번 학생 위에 몇 명이 존재"하는지, 역방향 BFS 탐색으로 "K번 학생 밑에 몇 명이 존재"하는 지 알 수 있다.
 *   4. 후기
 *       - 아이디어가 전혀 떠오르지 않더라.. 풀이 방법과 아이디어가 참신하니 기억에 남았으면 좋겠다.
 */

public class Main_ms {
    static int N, M, K;
    static ArrayList<Integer>[][] adj;
    static boolean[] visit;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        adj = new ArrayList[2][N + 1]; // adj[0] : 정방향, adj[1] : 역방향
        visit = new boolean[N + 1];

        for (int i = 0; i < 2; i++) {
            for (int j = 1; j <= N; j++) {
                adj[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            adj[0][b].add(a); // 정방향에는 a <- b 로 가리키도록,
            adj[1][a].add(b); // 역방향에는 a -> b 로 가리키도록 저장.
        }

        int U = bfs(0); // 가능한 가장 높은 등수(U)는 정방향 그래프로 돌려서, 만난 정점들 카운트.
        Arrays.fill(visit, false);
        int V = N - bfs(1) + 1; // 가능한 가장 낮은 등수(V)는 역방향 그래프로 돌려서, 만난 정점들 카운트. 리턴값이 곧 정점 K 아래에 몇명이 있는지를 의미하므로, 빼주자.

        System.out.println(U + " " + V);
    }

    static int bfs(int dir) {
        Queue<Integer> q = new LinkedList<>();
        visit[K] = true;
        q.add(K);
        int cnt = 0;

        while (!q.isEmpty()) {
            int now = q.poll();
            cnt++;

            for (int next : adj[dir][now]) {
                if (!visit[next]) {
                    visit[next] = true;
                    q.add(next);
                }
            }
        }

        return cnt;
    }
}
