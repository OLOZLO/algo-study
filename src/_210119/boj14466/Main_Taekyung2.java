package _210119.boj14466;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 문제 이해하는게 제일 오래 걸림, 설명 너무 부실함
 *
 * 다리들 좌표 미리 저장해 놓고
 * 소들 각각 다리를 이용하지 않고 dfs 돌림
 *
 * dfs 돌렸는데 방문 못한 소 있으면 다리 안건너면 못가는거
 */

public class Main_Taekyung2 {

    static int N, K, R;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    // 다리 시작 좌표와 끝 좌표를 2차원 리스트로 저장
    static ArrayList<Point>[][] adj;
    static boolean[][] visited;
    static Point[] cows;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 입력 및 초기화
        N = sc.nextInt();
        K = sc.nextInt();
        R = sc.nextInt();
        cows = new Point[K];
        adj = new ArrayList[N + 1][N + 1];
        for(int i = 1; i <= N; i++)
            for(int j = 1; j <= N; j++)
                adj[i][j] = new ArrayList<>();
        // 다리 좌표 입력
        for(int i = 0; i < R; i++) {
            int fromX = sc.nextInt();
            int fromY = sc.nextInt();
            int toX = sc.nextInt();
            int toY = sc.nextInt();
            adj[fromY][fromX].add(new Point(toX, toY));
            adj[toY][toX].add(new Point(fromX, fromY));
        }

        // 소 좌표 입력
        for(int i = 0; i < K; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            cows[i] = new Point(x, y);
        }

        int ret = 0;
        // 0번 소부터 K - 1번 소까지 dfs
        for(int i = 0; i < K; i++) {
            visited = new boolean[N + 1][N + 1];
            dfs(cows[i]);
            // 방문 안한 소 있으면 count
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
            // dfs 돌리다가 다음 칸으로 가려는데 다리 이용해야 되면 안 감
            if(ny > N || nx > N || ny < 1 || nx < 1 || visited[ny][nx] || adj[cur.y][cur.x].contains(next)) continue;
            visited[ny][nx] = true;
            dfs(next);
        }
    }

    // 소들의 쌍만 구하면 되니까 중복되지 않게 자기보다 큰것들만 쌍으로 묶음
    public static int count(int idx) {
        int cnt = 0;
        for(int i = idx + 1; i < K; i++)
            if(!visited[cows[i].y][cows[i].x])
                cnt++;
        return cnt;
    }
}
