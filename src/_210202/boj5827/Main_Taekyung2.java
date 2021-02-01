package _210202.boj5827;

import java.util.*;

public class Main_Taekyung2 {

    static class Point {
        int y, x;
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
    static int N, M;
    static Point C, D; static char[][] map;
    static int[][] count;
    static Queue<Point> q = new LinkedList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new char[N][M];
        count = new int[N][M];
        for(int i = 0; i < N; i++) {
            String s = sc.next();
            Arrays.fill(count[i], -1);
            for(int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);
                if(map[i][j] == 'C')
                    C = new Point(i, j);
                else if(map[i][j] == 'D')
                    D = new Point(i, j);
            }
        }
        C = down(C.y, C.x, 1);
        if(check(C, 0)) {
            System.out.println(count[D.y][D.x]);
            return;
        }
        while (!q.isEmpty()) {
            Point cur = q.poll();
            if (check(down(cur.y, cur.x, (count[cur.y][cur.x] % 2 == 0) ? -1 : 1), count[cur.y][cur.x] + 1))
                break;
        }
        System.out.println(count[D.y][D.x]);
    }

    public static boolean check(Point p, int cnt) {
        if(p == null || count[p.y][p.x] != -1) return false;
        count[p.y][p.x] = cnt;
        if(map[p.y][p.x] == 'D') return true;
        q.add(p);
        int d = (cnt % 2 == 0) ? 1 : -1;
        return (check(move(p.y, p.x, -1, d), cnt) || check(move(p.y, p.x, 1, d), cnt));
    }

    public static Point move(int py, int px, int x, int d) {
        px += x;
        if (px < 0 || px >= M || map[py][px] == '#') return null;
        return down(py, px, d);
    }

    public static Point down(int py, int px, int d) {
        while(true) {
            if(map[py][px] == 'D') break;
            if(py + d < 0 || py + d >= N) return null;
            if(map[py + d][px] == '#') break;
            py += d;
        }
        return new Point(py, px);
    }
}