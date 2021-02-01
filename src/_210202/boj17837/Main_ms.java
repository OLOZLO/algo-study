package _210202.boj17837;

import java.util.Arrays;
import java.util.Scanner;

public class Main_ms {
    static int N, K;
    static int[][] color;       // 칸의 색깔 기록.
    static int[][] marker;      // 말의 정보(행, 열, 이동 방향) 기록.
    static String[][] board;    // 체스판 각 칸에 존재하는 말들을 기록.

    // {의미없음, 우, 좌, 상, 하} 순서!
    static int[] di = {0, 0, 0, -1, 1};
    static int[] dj = {0, 1, -1, 0, 0};

    static boolean exit; // 루프 탈출 용

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        color = new int[N + 1][N + 1];
        marker = new int[K][3];
        board = new String[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(board[i], ""); // 널 대신 공백 문자로 초기화.
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                color[i][j] = sc.nextInt();
            }
        }

        // 말의 정보 기록하고, 체스판에 해당 말을 그리자.
        for (int i = 0; i < K; i++) {
            marker[i][0] = sc.nextInt();
            marker[i][1] = sc.nextInt();
            marker[i][2] = sc.nextInt();

            board[marker[i][0]][marker[i][1]] = Integer.toString(i);
        }

        int turn = 0;

        while (++turn <= 1000) { // 최대 천번까지 돌면서 말들 이동시킴.
            for (int i = 0; i < K; i++) {
                int now_i = marker[i][0];
                int now_j = marker[i][1];
                int now_dir = marker[i][2];

                int next_i = now_i + di[now_dir];
                int next_j = now_j + dj[now_dir];

                // 체스판 밖이거나, 파란색이면?
                if (!check(next_i, next_j) || color[next_i][next_j] == 2) {
                    marker[i][2] = now_dir = changeDirection(now_dir); // 먼저 이동 방향 바꿔서 갱신시켜 준다.

                    int next_next_i = now_i + di[now_dir];
                    int next_next_j = now_j + dj[now_dir];

                    if (check(next_next_i, next_next_j) && color[next_next_i][next_next_j] != 2) { // 이동방향 바꿔서 움직이려고 하는 칸이 체스판 밖이 아니고 파란색이 아니면 move 불러서 말 움직이자!
                        move(i, now_i, now_j, next_next_i, next_next_j);
                    }
                }

                // 흰칸이나 빨간칸이면? 걍 움직임.
                else {
                    move(i, now_i, now_j, next_i, next_j);
                }
            }

            if (exit) break;
        }

        System.out.println(exit ? turn : -1);
    }

    private static void move(int k, int now_i, int now_j, int next_i, int next_j) {
        int beginIndex = board[now_i][now_j].indexOf(Integer.toString(k)); // k번 말의 칸 내에서의 위치를 구하자.

        for (int i = beginIndex; i < board[now_i][now_j].length(); i++) { // 그 위치부터 위로 쭉 이동하면서, 모든 말들의 현재 위치를 이동시키는 위치로 바꿔줌.
            int num = Integer.parseInt(String.valueOf(board[now_i][now_j].charAt(i)));

            marker[num][0] = next_i;
            marker[num][1] = next_j;
        }

        // 흰 칸이면, 걍 위에다 올려놓고,
        if (color[next_i][next_j] == 0) {
            board[next_i][next_j] += board[now_i][now_j].substring(beginIndex);
        }

        // 빨간색 칸이면, 뒤집어서 위에다 올려놓는다.
        else if (color[next_i][next_j] == 1) {
            StringBuilder sb = new StringBuilder(board[now_i][now_j].substring(beginIndex));
            board[next_i][next_j] += sb.reverse();
        }

        board[now_i][now_j] = board[now_i][now_j].substring(0, beginIndex); // 원래 있던 칸에서 움직였던 말들을 모두 지워준다.

        if (board[next_i][next_j].length() >= 4) { // 종료 조건이면, 표시해두자.
            exit = true;
        }
    }

    private static boolean check(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) return false;
        return true;
    }

    private static int changeDirection(int dir) {
        if (dir == 1) return 2;
        else if (dir == 2) return 1;
        else if (dir == 3) return 4;
        else return 3;
    }
}

