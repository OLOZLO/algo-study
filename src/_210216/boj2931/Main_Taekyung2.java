package _210216.boj2931;

import java.awt.*;
import java.util.*;

public class Main_Taekyung2 {
    static Point S; // 시작 지점
    static char[] pipe = {'|', '-', '+', '1', '2', '3', '4'}; // 파이프 종류
    static int r, c;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1}, reverse = {2, 3, 0, 1}; // 반대 방향
    // 파이프 종류 별로 갈 수 있는 방향
    static int[][] dp = {{1, 0, 1, 0}, {0, 1, 0, 1}, {1, 1, 1, 1}, {0, 1, 1, 0}, {1, 1, 0, 0}, {1, 0, 0, 1}, {0, 0, 1, 1}};
    static boolean[][] visited;
    // 각 지점에서 갈 수 있는 방향
    static boolean[][][] map;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        r = sc.nextInt(); c = sc.nextInt();
        map = new boolean[4][r][c];
        visited = new boolean[r][c];
        for(int i = 0; i < r; i++) {
            String s = sc.next();
            for(int j = 0; j < c; j++) {
                char ch = s.charAt(j);
                if(ch == '.') continue;
                if(ch == 'M') S = new Point(j, i);
                else {
                    // 파이프 입력 받으면 갈 수 있는 방향 등록해 놓음
                    for (int p = 0; p < 7; p++)
                        if (ch == pipe[p]) {
                            for (int d = 0; d < 4; d++)
                                if (dp[p][d] == 1) map[d][i][j] = true;
                            break;
                        }
                }
            }
        }
        // 시작 지점에서 갈 수 있는 방향 체크
        for (int d = 0; d < 4; d++) {
            int ny = S.y + dy[d], nx = S.x + dx[d];
            if(chk(ny, nx)) continue;
            // 시작 지점 인접 네 방향에서 시작지점으로 올 수 있으면 반대도 가능
            if(map[reverse[d]][ny][nx]) map[d][S.y][S.x] = true;
        }
        bfs();
    }

    static boolean chk(int y, int x) {
        return y < 0 || x < 0 || y >= r || x >= c;
    }

    static void bfs() {
        Queue<Point> q = new LinkedList<>();
        visited[S.y][S.x] = true;
        q.add(S);
        while(!q.isEmpty()) {
            Point cur = q.poll();
            for(int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d], nx = cur.x + dx[d];
                if(chk(ny, nx) || visited[ny][nx] || !map[d][cur.y][cur.x]) continue;
                // 파이프 연결 되있음, 계속 bfs
                if(map[reverse[d]][ny][nx]) {
                    visited[ny][nx] = true;
                    q.add(new Point(nx, ny));
                // 가다가 끊겼음
                } else {
                    int[] dir = new int[4];
                    // 끊긴 지점에서 갈 수 있는 위치 체크
                    for(int k = 0; k < 4; k++) {
                        int ky = ny + dy[k], kx = nx + dx[k];
                        if(chk(ky, kx)) continue;
                        if(map[reverse[k]][ky][kx]) dir[k] = 1;
                    }
                    // 파이프 뭐 들어가야 되는지 확인
                    for(int i = 0; i < 7; i++) {
                        if(Arrays.equals(dir, dp[i])) {
                            System.out.println((ny + 1) + " " + (nx + 1) + " " + pipe[i]);
                            return;
                        }
                    }
                }
            }
        }
    }
}