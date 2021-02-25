package _210223.boj2151;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_girawhale {
    public static void main(String[] args) {
        int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
        int[][] mirror = {{0, 1, 2, 3}, {3, 2, 1, 0}, {2, 3, 0, 1}};

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        char[][] map = new char[N][N];
        int[] doors = null;

        for (int i = 0; i < N; i++) {
            String input = sc.next();
            for (int j = 0; j < N; j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == '#')
                    doors = new int[]{i, j};
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        int[][][] visit = new int[N][N][4];
        for (int[][] v : visit)
            for (int[] vv : v)
                Arrays.fill(vv, Integer.MAX_VALUE);

        for (int i = 0; i < 4; i++)
            queue.add(new int[]{doors[0], doors[1], i, 0});
        Arrays.fill(visit[doors[0]][doors[1]], 0);
        map[doors[0]][doors[1]] = '*';

        int ans = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (map[cur[0]][cur[1]] == '#') {
                ans = Math.min(ans, cur[3]);
                continue;
            }

            for (int i = 0; i < (map[cur[0]][cur[1]] == '!' ? 3 : 1); i++) {
                int dir = mirror[i][cur[2]], cnt = i > 0 ? cur[3] + 1 : cur[3];
                int ny = cur[0] + dy[dir], nx = cur[1] + dx[dir];

                if (ny < 0 || nx < 0 || ny >= N || nx >= N || map[ny][nx] == '*' || visit[ny][nx][dir] <= cnt)
                    continue;

                queue.add(new int[]{ny, nx, dir, cnt});
                visit[ny][nx][dir] = cnt;
            }

        }
        System.out.println(ans);
    }
}
