package _210223.boj2151;

import java.util.*;

public class Main_Taekyung2 {
    static char[][] map;
    static int N, sx = -1, sy, ex, ey;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static int[][] mirror = {{1, 0, 3, 2}, {3, 2, 1, 0}};
    static boolean[][][] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new char[N][N];
        visited = new boolean[N][N][4];
        for(int i = 0; i < N; i++) {
            String s = sc.next();
            for(int j = 0; j < N; j++) {
                map[i][j] = s.charAt(j);
                if(map[i][j] == '#')
                    if(sx == -1) {
                        sy = i; sx = j;
                    } else {
                        ey = i; ex = j;
                    }
            }
        }
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(m -> m[3]));
        for(int d = 0; d < 4; d++)
            q.add(new int[]{sy, sx, d, 0});
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            if(cur[0] == ey && cur[1] == ex) {
                System.out.println(cur[3]);
                return;
            }
            int ny = cur[0] + dy[cur[2]], nx = cur[1] + dx[cur[2]];
            if(ny < 0 || nx < 0 || ny >= N || nx >= N || visited[ny][nx][cur[2]] || map[ny][nx] == '*') continue;
            visited[ny][nx][cur[2]] = true;
            if(map[ny][nx] == '!')
                for(int i = 0 ; i < 2; i++)
                    q.add(new int[]{ny, nx, mirror[i][cur[2]], cur[3] + 1});
            q.add(new int[]{ny, nx, cur[2], cur[3]});
        }
    }
}