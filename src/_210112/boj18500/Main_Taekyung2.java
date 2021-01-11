package _210112.boj18500;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_Taekyung2 {
    static int R, C, N;
    static char[][] map;
    static boolean d = true;
    static boolean[][] visited;
    static ArrayList<int[]> cluster;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        map = new char[101][101];
        for(int i = 0; i < R; i++) {
            String s = sc.next();
            map[i] = s.toCharArray();
        }
        N = sc.nextInt();
        for(int i = 0; i < N; i++) {
            int h = R - sc.nextInt();
            simulation(h);
            d = !d;
        }
        for(int i = 0 ; i < R; i++) {
            for(int j = 0; j < C; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static void simulation(int h) {
        int idx  = (d) ? 0 : C - 1;
        while(idx < C && idx >= 0 && map[h][idx] == '.') {
            idx = (d) ? ++idx : --idx;
        }
        if(idx == C || idx == -1) return;
        map[h][idx] = '.';
        visited = new boolean[R][C];
        for(int i = 0; i < C; i++) {
            if(!visited[R - 1][i] && map[R - 1][i] == 'x') {
                findFloor(R - 1, i);
            }
        }
        cluster = new ArrayList<>();
        findCluster();
        down();
    }

    public static void findFloor(int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        visited[y][x] = true;
        q.add(new int[]{y, x});
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for(int d = 0; d < 4; d++) {
                int ny = cur[0] + dy[d];
                int nx = cur[1] + dx[d];
                if(ny < 0 || nx < 0 || ny >= R || nx >= C || visited[ny][nx] || map[ny][nx] != 'x') continue;
                visited[ny][nx] = true;
                q.add(new int[]{ny, nx});
            }
        }
    }

    public static void findCluster() {
        for(int i = 0; i < R - 1; i++) {
            for(int j = 0; j < C; j++) {
                if(!visited[i][j] && map[i][j] == 'x') {
                    cluster.add(new int[]{i, j});
                }
            }
        }
    }

    public static void down() {
        for(int[] p : cluster)
            map[p[0]][p[1]] = '.';
        int max = 0;
        loop:
        for(int i = 1; i < R; i++) {
            for(int[] p : cluster) {
                if (p[0] + i >= R || map[p[0] + i][p[1]] == 'x')
                    break loop;
            }
            max = i;
        }
        for(int[] p : cluster) {
            map[p[0] + max][p[1]] = 'x';
        }
    }
}
