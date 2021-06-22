package _210615.boj18430;

import java.util.Scanner;


public class Main_Taekyung2 {

    /**
     * [실버1] 무기 공학
     * 1. 결과 : 맞았습니다.
     * 2. 시간복잡도 : O(4 ^ (N * M))
     *
     * 3. 후기
     * 	- 부메랑 모양을 미리 만들어 놓고 백트래킹
     * 	- 시간복잡도 계산이 좀 애매한거 같기도 하고 ..
     */


    static int N, M, ret = 0;
    static int[][] board;
    // 부메랑으로 덮었는지 여부
    static boolean[][] cover;
    // 부메랑 타입 4개
    static int[][] dy = {{0, 0, 1}, {0, 1, 1}, {0, 1, 1}, {0, 0, 1}};
    static int[][] dx = {{0, 1, 1}, {0, 0, 1}, {0, 0, -1}, {1, 0, 0}};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        board = new int[N][M];
        cover = new boolean[N][M];

        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++)
                board[i][j] = sc.nextInt();

        backtrack(0, 0, 0);
        System.out.println(ret);
    }


    public static void backtrack(int y, int x, int sum) {
        if(x == M) {
            y++;
            x = 0;
        }

        if(y == N) {
            ret = Math.max(ret, sum);
            return;
        }

        if(!cover[y][x]) {
            // 부메랑 4가지 다 덮어봅니다
            for (int type = 0; type < 4; type++) {
                int part = 0, d;

                // 부메랑 3칸이 다 덮일 수 있는지 확인합니다
                for (d = 0; d < 3; d++) {
                    int ny = y + dy[type][d], nx = x + dx[type][d];
                    // 하나라도 못덮으면 종료
                    if (ny < 0 || nx < 0 || ny >= N || nx >= M || cover[ny][nx]) break;

                    // 중간에 있는 칸은 2배로 계산
                    int nn = board[ny][nx];
                    cover[ny][nx] = true;
                    part += nn;
                    if(d == 1)
                        part += nn;
                }

                // 3칸을 다 덮었다면 다음으로 넘어갑니다
                if (d == 3)
                    backtrack(y, x + 1, sum + part);

                // 덮었던 칸을 뺍니다
                for (int i = 0; i < d; i++) {
                    int ny = y + dy[type][i];
                    int nx = x + dx[type][i];
                    cover[ny][nx] = false;
                }
            }
        }

        // 안덮었을 경우도 진행합니다
        backtrack(y, x + 1, sum);
    }
}
