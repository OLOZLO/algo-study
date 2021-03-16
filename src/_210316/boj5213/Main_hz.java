package _210316.boj5213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_hz {
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static class Pos {
        int i, j, cnt;
        StringBuilder path;

        Pos(int i, int j, int cnt, StringBuilder path) {
            this.cnt = cnt;
            this.i = i;
            this.j = j;
            this.path = path;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][][] tile = new int[N][N*2][2];    // [i][j][0] 타일의 번호, [i][j][1] 타일에 적힌 수를 저장했습니다.
        int idx = 1;
        StringTokenizer st = null;
        for (int i = 0; i < N; i++) {
            // (문제에서) 홀수 행일 땐 [i][0]부터, 짝수 행일 땐 [i][1]부터 저장했습니다.
            int j = (i%2 == 0) ? 0 : 1;
            for (; j+1 < N*2; j+=2) {
                st = new StringTokenizer(br.readLine());
                tile[i][j][0] = tile[i][j+1][0] = idx++;
                tile[i][j][1] = Integer.parseInt(st.nextToken());
                tile[i][j+1][1] = Integer.parseInt(st.nextToken());
            }
        }


        PriorityQueue<Pos> pq = new PriorityQueue<>((o1, o2) -> o1.cnt-o2.cnt);
        int[][] visited = new int[N][N*2];  // 방문처리를 위해 해당 블록까지 거쳐간 블록의 수를 저장합니다.
        visited[0][0] = 1;

        StringBuilder sb = new StringBuilder();
        sb.append(1);
        pq.add(new Pos(0, 0, 1, sb));

        Pos result = new Pos(0, 0, 1, sb);
        // bfs를 돕니다.
        while(!pq.isEmpty()) {
            Pos now = pq.poll();

            // 현재 결과값으로 저장된 타일보다 인덱스번호가 크거나, 인덱스 번호가 같지만 최단 경로로 움직인 경우 결과값을 변경해줍니다.
            if (tile[now.i][now.j][0] > tile[result.i][result.j][0] ||
                    tile[now.i][now.j][0] == tile[result.i][result.j][0] && now.cnt < result.cnt) {
                result = now;
                // 그리고 그 곳이 마지막 타일일 경우 bfs를 종료시킵니다.
                if (tile[now.i][now.j][0] == idx-1) break;
            }

            for (int d = 0; d < dir.length; d++) {
                int ni = now.i + dir[d][0];
                int nj = now.j + dir[d][1];

                if (ni >= 0 && ni < N && nj >= 0 && nj < 2*N && tile[ni][nj][0] > 0) {  // 다음으로 움직일 곳이 타일 범위 안쪽이고
                    if (tile[now.i][now.j][0] == tile[ni][nj][0] && (visited[ni][nj] == 0 || visited[ni][nj] > now.cnt)) {  // 같은 블록 내에서의 이동
                        visited[ni][nj] = now.cnt;
                        pq.add(new Pos(ni, nj, now.cnt, now.path));
                    } else if (tile[now.i][now.j][1] == tile[ni][nj][1] && (visited[ni][nj] == 0 || visited[ni][nj] > now.cnt+1)) { // 인접한 블록으로 이동
                        visited[ni][nj] = now.cnt+1;
                        StringBuilder npath = new StringBuilder(now.path);
                        npath.append(" "+tile[ni][nj][0]);
                        pq.add(new Pos(ni, nj, now.cnt+1, npath));
                    }
                }
            }
        }

        System.out.println(result.cnt);
        System.out.println(result.path.toString());

    }
}
