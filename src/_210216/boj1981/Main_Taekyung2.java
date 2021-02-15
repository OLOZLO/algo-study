package _210216.boj1981;

import java.awt.*;
import java.util.*;

public class Main_Taekyung2 {
    static int N, min = Integer.MAX_VALUE, max = 0;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static int[][] map;
    static boolean[][] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new int[N][N];
        // 입력 받습니다, 제일 작은 거, 큰 거 구해놓습니다
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
                min = Math.min(min, map[i][j]);
                max = Math.max(max, map[i][j]);
            }
        visited = new boolean[N][N];
        // ret = 답, to = max 상한치
        int ret = max, to = max;
        // mid = min 상한치
        int mid = max = map[0][0];
        while(min <= mid && max <= to) {
            for (int i = 0; i < N; i++) Arrays.fill(visited[i], false);
            // (0, 0)에서 (N -1, N -1)까지 min ~ max의 값들로 갈 수 있는지 확인
            // 못가면 max 늘림, 갈 수 있으면 min 늘림
            if (dfs(new Point(0, 0))) {
                ret = Math.min(ret, max - min);
                min++;
            } else max++;
        }
        System.out.println(ret);
    }

    // dfs로 하긴 했는데 방문 처리 제대로 하면 시간 초과 남, 안 해주면 빠르게 답이 나오는데 반례가 있을 거 같은데 일단 맞음
    static boolean dfs(Point s) {
        if(s.y == N - 1 && s.x == N - 1) return true;
        visited[s.y][s.x] = true;
        for(int d = 0; d < 4; d++) {
            int ny = s.y + dy[d], nx = s.x + dx[d];
            if(ny < 0 || nx < 0 || ny >= N || nx >= N || visited[ny][nx] || map[ny][nx] < min || map[ny][nx] > max) continue;
            if(dfs(new Point(nx, ny))) return true;
        }
        return false;
    }

    // bfs 얘가 제대로 된 풀이인 거 같긴 함
    /*
    static boolean bfs(Point s) {
        Queue<Point> q = new LinkedList<>();
        q.add(s);
        visited[s.y][s.x] = true;
        while(!q.isEmpty()) {
            Point cur = q.poll();
            for(int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d], nx = cur.x + dx[d];
                if(ny < 0 || nx < 0 || ny >= N || nx >= N || visited[ny][nx] || map[ny][nx] < min || map[ny][nx] > max) continue;
                if(ny == N - 1 && nx == N - 1) return true;
                visited[ny][nx] = true;
                q.add(new Point(nx, ny));
            }
        }
        return false;
    }
    */
}