package _210112.boj18500;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_girawhale {
    static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
    static int R, C, N;
    static boolean[][] map;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();

        map = new boolean[R][C];
        for (int i = 0; i < R; i++) {
            String input = sc.next();
            for (int j = 0; j < C; j++)
                map[i][j] = input.charAt(j) == 'x';
        }


        boolean left = true;
        int N = sc.nextInt();
        for (int n = 0; n < N; n++, left = !left) {
            int h = R - sc.nextInt();

            int c = left ? 0 : C - 1;
            while (c >= 0 && c < C && !map[h][c])
                c = left ? c + 1 : c - 1;

            if (c >= 0 && c < C) {
                map[h][c] = false;
                for (int k = 0; k < 4; k++) {
                    int y = h - dy[k];
                    int x = c - dx[k];
                    if (y < 0 || x < 0 || y >= R || x >= C)
                        continue;

                    if (map[y][x])
                        check(y, x);

                }
            }
        }

        for (boolean[] mm : map) {
            for (boolean m : mm)
                System.out.print(m ? 'x' : '.');
            System.out.println();
        }


    }

    static void check(int y, int x) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{y, x});

        boolean[][] visit = new boolean[R][C];
        visit[y][x] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (cur[0] == R - 1)
                return;

            for (int k = 0; k < 4; k++) {
                int ny = cur[0] + dy[k];
                int nx = cur[1] + dx[k];

                if (ny < 0 || nx < 0 || ny >= R || nx >= C || !map[ny][nx] || visit[ny][nx])
                    continue;

                visit[ny][nx] = true;
                queue.add(new int[]{ny, nx});
            }
        }

        int drop = Integer.MAX_VALUE;
        for (int j = 0; j < C; j++) {
            for (int i = 0; i < R - 1; i++)
                if (visit[i][j] && !visit[i + 1][j]) {
                    int floor = i + 1;

                    while (floor < R && (!map[floor][j] || visit[floor][j]))
                        floor++;

                    drop = Math.min(floor - i - 1, drop);
                }
        }

        for (int j = 0; j < C; j++) {
            for (int i = R - 1; i >= 0; i--)
                if (visit[i][j]) {
                    map[i + drop][j] = map[i][j];
                    map[i][j] = false;
                }
        }
    }
}
