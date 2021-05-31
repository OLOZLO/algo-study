package _210525.boj6593;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 1. 결과 : 성공
 * 2. 시간복잡도 : O(L*R*C*6)
 *
 * 3. 접근 방식
 *      - 말그대로 한칸씩 이동하면서 BFS를 돌면되는 문제
 *      - BFS는 최단거리이므로 종료시점을 찾으면 리턴
 *
 */
public class Main_girawhale {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] dz = {0, 0, 0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0, 0, 0};
        int[] dx = {0, 0, -1, 1, 0, 0};

        StringBuilder sb = new StringBuilder();

        while (true) {
            int L = sc.nextInt(), R = sc.nextInt(), C = sc.nextInt();
            if (L == 0) break;

            char[][][] map = new char[L][R][C];
            int[] start = null, end = null;
            for (int i = 0; i < L; i++) {
                for (int j = 0; j < R; j++) {
                    map[i][j] = sc.next().toCharArray();
                    for (int k = 0; k < C; k++) {
                        if (map[i][j][k] == 'S') start = new int[]{i, j, k};
                        if (map[i][j][k] == 'E') end = new int[]{i, j, k};
                    }
                }
            }

            Queue<int[]> queue = new LinkedList<>();
            boolean[][][] visit = new boolean[L][R][C];

            queue.add(start);
            visit[start[0]][start[1]][start[2]] = true;

            int time = 0;
            boolean check = false;
            while (!queue.isEmpty() && !check) {
                int size = queue.size();

                while (size-- > 0) {
                    int[] cur = queue.poll();

                    if (Arrays.equals(end, cur)) {
                        check = true;
                        sb.append("Escaped in " + time + " minute(s).\n");
                        break;
                    }

                    for (int k = 0; k < 6; k++) {
                        int nz = cur[0] + dz[k];
                        int ny = cur[1] + dy[k];
                        int nx = cur[2] + dx[k];

                        if (nz < 0 || nz >= L || ny < 0 || ny >= R || nx < 0 || nx >= C || visit[nz][ny][nx] || map[nz][ny][nx] == '#')
                            continue;

                        queue.add(new int[]{nz, ny, nx});
                        visit[nz][ny][nx] = true;
                    }
                }
                time++;
            }
            if (!check) sb.append("Trapped!\n");

        }
        System.out.println(sb);
    }
}
