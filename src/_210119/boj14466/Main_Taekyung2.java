package _210119.boj14466;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_Taekyung2 {

    static int N, K, R;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static ArrayList<Point>[][] adj;
    static boolean[][] visited;
    static Point[] cows;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        R = sc.nextInt();
        cows = new Point[K];
        adj = new ArrayList[N + 1][N + 1];
        for(int i = 1; i <= N; i++)
            for(int j = 1; j <= N; j++)
                adj[i][j] = new ArrayList<>();

        for(int i = 0; i < R; i++) {
            int fromX = sc.nextInt();
            int fromY = sc.nextInt();
            int toX = sc.nextInt();
            int toY = sc.nextInt();
            adj[fromY][fromX].add(new Point(toX, toY));
            adj[toY][toX].add(new Point(fromX, fromY));
        }

        for(int i = 0; i < K; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            cows[i] = new Point(x, y);
        }

        int ret = 0;
        for(int i = 0; i < K; i++) {
            visited = new boolean[N + 1][N + 1];
            dfs(cows[i]);
            ret += count(i);
        }
        System.out.println(ret);
    }

    public static void dfs(Point cur) {
        visited[cur.y][cur.x] = true;

        for(int d = 0; d < 4; d++) {
            int ny = cur.y + dy[d];
            int nx = cur.x + dx[d];
            Point next = new Point(nx, ny);
            if(ny > N || nx > N || ny < 1 || nx < 1 || visited[ny][nx] || adj[cur.y][cur.x].contains(next)) continue;
            visited[ny][nx] = true;
            dfs(next);
        }
    }

    public static int count(int idx) {
        int cnt = 0;
        for(int i = idx + 1; i < K; i++)
            if(!visited[cows[i].y][cows[i].x])
                cnt++;
        return cnt;
    }
}
