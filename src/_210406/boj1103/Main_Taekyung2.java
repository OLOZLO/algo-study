package _210406.boj1103;

import java.util.Arrays;
import java.util.Scanner;

public class Main_Taekyung2 {
    /**
     *  1. 결과 : 성공
     *  2. 시간 복잡도 : O( N * M )
     *      - 이유 : dp 부분 문제 개수(N * M) * 부분 문제 시간( 4 방향(상수 시간) )
     *  3. 아이디어
     *      - dp를 안쓰면 (4^N*M)
     *      - dfs(y, x) : 좌표(y, x)에서 이동할 수 있는 최대 횟수
     *      - 무한 루프 여부 판단을 위해 역방향 간선 체크
     *      - dfs가 끝나기 전에 방문했던 점을 다시 방문하면 사이클 존재, INF값 리턴
     */

    static int N, M, INF = (int)1e9;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static int[][] map, count;
    static boolean[][] visit;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N][M];
        count = new int[N][M];
        visit = new boolean[N][M];

        for(int i = 0; i < N; i++) {
            String s = sc.next();
            Arrays.fill(count[i], -1);

            for(int j = 0; j < M; j++) {
                char c = s.charAt(j);
                if(c != 'H')
                    map[i][j] = c - '0';
            }
        }

        int ret = dfs(0, 0);
        // dfs(0, 0)이 INF보다 큰 값을 리턴했다면 무한 루프를 도는 것
        System.out.println(ret >= INF ? -1 : ret);
    }

    public static int dfs(int y, int x) {
        // DP 기저 조건, 범위 벗어나거나 H이면 0 리턴
        if(y < 0 || x < 0 || y >= N || x >= M || map[y][x] == 0) return 0;
        // 역방향 간선이 존재하므로 사이클이 존재, max값을 리턴하는 구조이므로 큰 값 INF 리턴
        if(visit[y][x]) return INF;

        int ret = count[y][x], n = map[y][x];
        // 이미 해결한 부분 문제라면 그대로 정답 리턴
        if(ret != -1) return ret;

        ret = 0;
        visit[y][x] = true;
        for(int d = 0; d < 4; d++) {
            int ny = y + dy[d] * n , nx = x + dx[d] * n;
            // 현재 위치에서 갈 수 있는 4곳 중 가장 큰 값 + 1
            ret = Math.max(ret, dfs(ny, nx) + 1);
        }
        // dfs 종료 전 방문 체크를 해제
        visit[y][x] = false;

        return count[y][x] = ret;
    }
}
