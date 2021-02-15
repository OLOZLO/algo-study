package _210216.boj1981;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_girawhale {
    static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
    static int[][] map;
    static int n;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();

        map = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) map[i][j] = sc.nextInt();

        int ans = 200, s = 0, e = 0; // 시작과 끝으로 투 포인터를 사용함미다
        while (s <= 200 && e <= 200) { // 모두 범위를 넘지 않을 때까지
            if (bfs(s, e)) { // 해당 범위로 bfs를 돌았다면?
                ans = Math.min(ans, e - s); // 답을 갱신하고
                s++; // 시작을 키워 범위를 줄여봄
            } else e++; // 안되면 종료를 키워 범위를 늘림
        }
        System.out.println(ans);
    }

    static boolean bfs(int s, int e) { // 지정된 범위를 벗어나지 않는 곳에서 끝을 도달할 수 있는지 체크할 bfs
        if (map[0][0] < s || map[0][0] > e) return false; // 시작부터 범위에 포함 안되면 false;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visit = new boolean[n][n];

        queue.add(new int[]{0, 0});
        visit[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if (cur[0] == n - 1 && cur[1] == n - 1) return true; // 끝을 갔음! true 반환

            for (int k = 0; k < 4; k++) {
                int ny = cur[0] + dy[k], nx = cur[1] + dx[k];
                if (ny < 0 || nx < 0 || ny >= n || nx >= n || visit[ny][nx]
                        || s > map[ny][nx] || e < map[ny][nx])
                    continue;

                queue.add(new int[]{ny, nx});
                visit[ny][nx] = true;
            }
        }
        return false; // 끝도 못가고 BFS 끝났으니까 false
    }
}
