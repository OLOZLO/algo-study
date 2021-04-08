package _210406.boj1103;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 1. 결과 : 성공
// 2. 시간 복잡도 : O(NM)
// - 이유
// DP를 사용해 이미 방문했던 칸을 다시 방문하면 기존에 저장했던 값을 반환
// 모든 칸을 방문하면 더 이상 소요되는 시간이 없음
// 보드의 크기 = V = N * M = 2500
// 방향 수 = 4 => 간선 수 = 4 * NM
// DFS의 시간복잡도 = V + E = O(NM)
public class Main_girawhale {
    static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
    static boolean[][] visit;
    static char[][] map;
    static int[][] dp;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][];
        visit = new boolean[N][M];
        dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
            Arrays.fill(dp[i], -1); // 방문 안한거 모두 -1로 초기화
        }

        dfs(0, 0); // [0][0]부터 탐색 시작
        System.out.println(dp[0][0]);
    }

    static int dfs(int y, int x) {
        if (dp[y][x] != -1) return dp[y][x]; // 이미 방문해서 dp에 저장된 값이 있다면 굳이 탐색 ㄴㄴ, 바로 반환

        visit[y][x] = true;
        int n = map[y][x] - '0'; // char형이기 때문에 -'0'을 통해 int값을 얻음
        int ret = 1;

        for (int k = 0; k < 4; k++) {
            int ny = y + n * dy[k];
            int nx = x + n * dx[k];

            if (ny < 0 || nx < 0 || ny >= N || nx >= M || map[ny][nx] == 'H') continue;
            if (visit[ny][nx]) { // 이미 방문한 칸을 또 방문? 그러면 계속 내 위치로 올 수 있으니까 무한으로 돈다.
                System.out.println(-1);
                System.exit(0);
            }

            // 벗어나지도 않고 무사히 왔다면 (다음위치에서 가장 많이가는 칸 + 1)을 하면
            // 현재 방향으로 이동했을 때 총 이동거리의 최대값을 구할 수 있음
            ret = Math.max(ret, dfs(ny, nx) + 1);
        }

        visit[y][x] = false;
        return dp[y][x] = ret;
    }


}
