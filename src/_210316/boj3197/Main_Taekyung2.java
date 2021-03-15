package _210316.boj3197;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Main_Taekyung2 {
    static int n, m, sy = -1, sx, ey, ex;
    static int[] dy = {0, 1, 0, -1}, dx = {1, 0, -1, 0};
    static int[][] map;
    static boolean[][] visit;
    static Queue<Point> q = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'L') {
                    // 시작, 끝 입력
                    if(sy == -1) { sy = i; sx = j; }
                    else { ey = i; ex = j; }
                }
                // 물 지역에 1 넣음
                if(map[i][j] != 'X') {
                    map[i][j] = 1;
                    q.add(new Point(j, i));
                }
            }
        }
        System.out.println(meltDay());
    }

    // 빙판이 없어지는 최대 날을 미리 구해놓음, 이분탐색으로 며칠 지나면 백조끼리 만나는지 확인
    private static int meltDay() {
        int lo = 0, hi = 0;
        while (!q.isEmpty()) {
            Point p = q.poll();
            for (int i = 0; i < 4; i++) {
                int ny = p.y + dy[i], nx = p.x + dx[i];
                if (ny < 0 || nx < 0 || ny >= n || nx >= m || map[ny][nx] != 'X') continue;
                map[ny][nx] = map[p.y][p.x] + 1;
                if (hi < map[ny][nx]) hi = map[ny][nx];
                q.offer(new Point(nx, ny));
            }
        }
        while (lo <= hi) {
            int d = (lo + hi) / 2;
            visit = new boolean[n][m];
            if (go(d)) hi = d - 1;
            else lo = d + 1;
        }
        return hi;
    }

    // day 이하의 값으로 백조끼리 만나는지 확인
    private static boolean go(int day) {
        Queue<Point> q = new LinkedList<>();
        q.offer(new Point(sx, sy));
        visit[sy][sx] = true;
        while (!q.isEmpty()) {
            Point p = q.poll();
            for (int i = 0; i < 4; i++) {
                int ny = p.y + dy[i], nx = p.x + dx[i];
                if (ny < 0 || nx < 0 || ny >= n || nx >= m || visit[ny][nx] || map[ny][nx] > day) continue;
                if (ny == ey && nx == ex) return true;
                visit[ny][nx] = true;
                q.offer(new Point(nx, ny));
            }
        }
        return false;
    }
}