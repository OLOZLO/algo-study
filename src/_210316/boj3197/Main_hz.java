package _210316.boj3197;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_hz {
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static class Pos {
        int i, j, time;

        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        Pos(int i, int j, int time) {
            this.i = i;
            this.j = j;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][C];  // 문제에서 입력으로 준 배열
        int[][] time = new int[R][C];   // 각 빙판이 녹는데 걸리는 시간을 기록해둔 배열

        for (int i = 0; i < R; i++) {
            map[i] = br.readLine().toCharArray();
        }

        Queue<Pos> q = new LinkedList<>();
        Pos start = null;

        // 각 빙판에 대해 녹는데 걸리는 시간을 구해줍니다.
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == '.') continue;
                if (map[i][j] == 'L') {
                    if (start == null) {
                        map[i][j] = '.';
                        start = new Pos(i, j, 0);
                    }
                    continue;
                }
                
                // 따라서 빙판이면서, 물과 닿아있는 빙판을 녹는데 걸리는 시간을 time에 기록한 뒤, 큐에 넣어줍니다.
                for (int d = 0; d < dir.length; d++) {
                    int ni = i + dir[d][0];
                    int nj = j + dir[d][1];

                    if (ni >= 0 && ni < R && nj >= 0 && nj < C && map[ni][nj] != 'X') {
                        time[i][j] = 1;
                        q.add(new Pos(i, j));
                        break;
                    }
                }
            }
        }

        // bfs를 돌며 다른 빙판들이 녹는데 걸리는 시간을 구해줍니다.
        while(!q.isEmpty()) {
            Pos now = q.poll();

            for (int d = 0; d < dir.length; d++) {
                int ni = now.i + dir[d][0];
                int nj = now.j + dir[d][1];

                if (ni >= 0 && ni < R && nj >= 0 && nj < C && map[ni][nj] == 'X' && time[ni][nj] == 0) {
                    time[ni][nj] = time[now.i][now.j]+1;
                    q.add(new Pos(ni, nj));
                }
            }
        }

        // 백조가 만날 때 시간이 적게 걸리지만, 이동하는데 많은 거리를 움직이는 경우가 있기 때문에 시간 순으로 정렬된 우선순위 큐를 사용합니다.
        PriorityQueue<Pos> pq = new PriorityQueue<>(new Comparator<Pos>() {
            @Override
            public int compare(Pos o1, Pos o2) {
                return o1.time-o2.time;
            }
        });

        boolean[][] visited = new boolean[R][C];
        visited[start.i][start.j] = true;
        pq.add(start);
        int result = Integer.MAX_VALUE;

        while(!pq.isEmpty()) {
            Pos now = pq.poll();

            if (map[now.i][now.j] == 'L') {
                result = Math.min(result, now.time);
            }

            for (int d = 0; d < dir.length; d++) {
                int ni = now.i + dir[d][0];
                int nj = now.j + dir[d][1];

                if (ni >= 0 && ni < R && nj >= 0 && nj < C && !visited[ni][nj]) {
                    visited[ni][nj] = true;
                    pq.add(new Pos(ni, nj, Math.max(now.time, time[ni][nj])));
                }
            }
        }

        System.out.println(result);
    }
}
