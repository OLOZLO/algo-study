package _210615.boj18430;

import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : 한 점을 기준으로 만드는 경우가 4가지.. pass하는 경우 1가지..
 *               근데 잘모르겟습니당...
 *
 * 3. 접근 방식
 *      - 크기가 매우 작고 경우가 적다 => 완탐 돌리자
 *
 * 4. 후기
 *      일단 답 나오게 만들고 깔끔하게 하고 싶었지만, 포기했습니다.......
 *
 */
public class Main_girawhale {
    static int ans, N, M;
    static int[][] map;
    static boolean[][] visit;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        map = new int[N][M];
        visit = new boolean[N][M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                map[i][j] = sc.nextInt();

        solve(0, 0);
        System.out.println(ans);
    }

    static void solve(int n, int sum) {
        ans = Math.max(ans, sum); // 항상 최대값 갱신
        if (n == N * M) return; // 제일 끝으로 가면 return

        int y = n / M, x = n % M; // 다음칸 체크하기 힘들어서 n으로만 넘겨서 계산
        if (!visit[y][x]) { // 현재 칸 방문 안했을 때만 체크
            visit[y][x] = true;
            if (y > 0) {
                if (x > 0 && !visit[y - 1][x] && !visit[y][x - 1]) { // ┘의 경우
                    visit[y - 1][x] = visit[y][x - 1] = true;
                    solve(n + 1, sum + map[y][x] * 2 + map[y - 1][x] + map[y][x - 1]);
                    visit[y - 1][x] = visit[y][x - 1] = false;
                }
                if (x < M - 1 && !visit[y - 1][x] && !visit[y][x + 1]) { // ㄴ의 경우
                    visit[y - 1][x] = visit[y][x + 1] = true;
                    solve(n + 1, sum + map[y][x] * 2 + map[y - 1][x] + map[y][x + 1]);
                    visit[y - 1][x] = visit[y][x + 1] = false;
                }
            }
            if (y < N - 1) {
                if (x > 0 && !visit[y + 1][x] && !visit[y][x - 1]) { // ㄱ의 경우
                    visit[y + 1][x] = visit[y][x - 1] = true;
                    solve(n + 1, sum + map[y][x] * 2 + map[y + 1][x] + map[y][x - 1]);
                    visit[y + 1][x] = visit[y][x - 1] = false;
                }
                if (x < M - 1 && !visit[y + 1][x] && !visit[y][x + 1]) { // ┌의 경우
                    visit[y + 1][x] = visit[y][x + 1] = true;
                    solve(n + 1, sum + map[y][x] * 2 + map[y + 1][x] + map[y][x + 1]);
                    visit[y + 1][x] = visit[y][x + 1] = false;
                }
            }
            visit[y][x] = false;
        }
        solve(n + 1, sum); // 현재 칸 패스하는 경우도 생각
    }
}
