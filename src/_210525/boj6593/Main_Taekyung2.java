package _210525.boj6593;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_Taekyung2 {
    static int L, R, C, ret;
    static Point S;
    static char[][][] map;
    static boolean[][][] visited;
    static int[] dz = {0, 0, 0, 0, 1, -1};
    static int[] dy = {-1, 0, 1, 0, 0, 0};
    static int[] dx = {0, 1, 0, -1, 0, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            L = sc.nextInt();
            R = sc.nextInt();
            C = sc.nextInt();
            ret = -1;
            if(L == 0 && R == 0 && C == 0) break;

            map = new char[L][R][C];
            visited = new boolean[L][R][C];
            for(int i = 0; i < L; i++) {
                for(int j = 0; j < R; j++) {
                    String tmp = sc.next();
                    for(int r = 0; r < C; r++) {
                        char c = tmp.charAt(r);
                        if(c == 'S')
                            S = new Point(i, j, r, 0);
                        map[i][j][r] = c;
                    }
                }
                sc.nextLine();
            }

            Queue<Point> q = new LinkedList<>();
            visited[S.z][S.y][S.x] = true;
            q.add(S);

            while(!q.isEmpty()) {
                Point cur = q.poll();

                if(map[cur.z][cur.y][cur.x] == 'E') {
                    ret = cur.time;
                    break;
                }

                for(int d = 0; d < 6; d++) {
                    int nz = cur.z + dz[d];
                    int ny = cur.y + dy[d];
                    int nx = cur.x + dx[d];

                    if(nz < 0 || ny < 0 || nx < 0 ||
                            nz >= L || ny >= R || nx >= C ||
                            visited[nz][ny][nx] || map[nz][ny][nx] == '#')
                        continue;

                    visited[nz][ny][nx] = true;
                    q.add(new Point(nz, ny, nx, cur.time + 1));
                }
            }

            if(ret == -1)
                System.out.println("Trapped!");
            else
                System.out.println("Escaped in " + ret + " minute(s).");
        }
    }

    static class Point {
        int z, y, x, time;

        Point(int z, int y, int x, int time) {
            this.z = z;
            this.y = y;
            this.x = x;
            this.time = time;
        }
    }
}
