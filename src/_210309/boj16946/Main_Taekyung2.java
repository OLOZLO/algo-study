package _210309.boj16946;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Main_Taekyung2 {
    static int N, M;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static int[][] map;
    static boolean[][] visited;

    static int stoi(String s) { return Integer.parseInt(s); }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken());
        M = stoi(st.nextToken());
        map = new int[N][M];
        visited = new boolean[N][M];
        for(int i = 0; i < N; i++) {
            String s = br.readLine();
            for(int j = 0; j < M; j++)
                map[i][j] = (int)s.charAt(j) - '0';
        }
        // 벽이 아닌 곳들로 bfs를 돌린다
        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++) {
                if(visited[i][j] || map[i][j] != 0) continue;
                bfs(new Point(j, i));
            }
        StringBuilder sb = new StringBuilder();
        for(int[] a : map) {
            for (int b : a)
                sb.append(b % 10);
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void bfs(Point p) {
        Queue<Point> q = new LinkedList<>();
        q.add(p);
        visited[p.y][p.x] = true;
        int cnt = 1;
        HashSet<Point> s = new HashSet<>();
        while(!q.isEmpty()) {
            Point cur = q.poll();
            for(int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d], nx = cur.x + dx[d];
                if(ny < 0 || nx < 0 || ny >= N || nx >= M || visited[ny][nx]) continue;
                // bfs를 돌면서 0이라면 방문 처리를 하고 cnt를 늘려준다, bfs 진행
                if(map[ny][nx] == 0) {
                    visited[ny][nx] = true;
                    cnt++;
                    q.add(new Point(nx, ny));
                } else // bfs 진행 도중 벽을 만난다면, 중복되지 않도록 hashset에 저장
                    s.add(new Point(nx, ny));
            }
        }
        // bfs 도중 만난 벽들에 , 카운트한 0의 개수를 더해준다
        for(Point a : s)
            map[a.y][a.x] += cnt;
    }
}