package _210518.boj11967;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/**
 * [골드 3] 불켜기
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : O(N^2 * M) .. 아마도..?
 *
 * 3. 풀이
 * 	(1) 기본 개념은 bfs랑 동일
 * 	(2) 갈 수 있는 곳으로 이동하면서 불을 켜는데 불을 켠 곳이 예전엔 못가는 곳이었는데 지금은 갈 수 있으면 다시 가봐야 됨
 *
 *
 */

public class Main_Taekyung2 {
    static int N, M;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static ArrayList<Point>[][] adj;
    // visited = 방문한 곳 체크
    // light = 불 켜져 있는 곳 체크
    static boolean[][] visited, light;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        adj = new ArrayList[N + 1][N + 1];
        visited = new boolean[N + 1][N + 1];
        light = new boolean[N + 1][N + 1];
        int ret = 0;

        for(int i = 1; i <= N; i++)
            for(int j = 1; j <= N; j++)
                adj[i][j] = new ArrayList<>();

        for(int i = 0; i < M; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int a = sc.nextInt();
            int b = sc.nextInt();
            // (y, x) 에서 (b, a) 불 켤 수 있음
            adj[y][x].add(new Point(b, a));
        }

        Queue<Point> q = new LinkedList<>();
        visited[1][1] = true;
        light[1][1] = true;
        q.add(new Point(1, 1));

        while(!q.isEmpty()) {
            Point cur = q.poll();

            // 먼저 불을 켜는 작업을 하자
            for(Point next : adj[cur.y][cur.x]) {
                // 가봤던 곳이면 안가도 됨
                if(visited[next.y][next.x]) continue;
                light[next.y][next.x] = true;

                // 불을 켰더니 내가 갈 수 있는 곳이라면 큐에 넣어야 됨
                for(int d = 0; d < 4; d++) {
                    int ny = next.y + dy[d];
                    int nx = next.x + dx[d];
                    if(ny < 0 || nx < 0 || ny >= N || nx >= N) continue;
                    // 내가 방문했던 곳이랑 인접해있는 곳에 불 켤 수 있으면 갈 수 있는 곳
                    if(visited[ny][nx]) {
                        visited[next.y][next.x] = true;
                        q.add(next);
                        break;
                    }
                }
            }

            // 인접한 곳으로 이동하자
            for(int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];
                //불 꺼져있거나 방문 했던 곳이면 안감
                if(ny < 0 || nx < 0 || ny >= N || nx >= N || !light[ny][nx] || visited[ny][nx]) continue;

                visited[ny][nx] = true;
                q.add(new Point(ny, nx));
            }
        }

        // 불 켜져있는 곳 개수 세기
        for(int i = 1; i <= N; i++)
            for(int j = 1; j <= N; j++)
                if(light[i][j])
                    ret++;

        System.out.println(ret);
    }


    static class Point {
        int y, x;
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}