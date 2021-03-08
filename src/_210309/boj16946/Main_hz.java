package _210309.boj16946;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_hz {
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int[][] map;
    static int N, M;

    static class Pos {  // 이 클래스에서 equals, hashCode 메소드는 i, j 값이 같은 객체는 동일한 객체라 사용할 것이기 때문에 만들었습니다.
        int i, j;

        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return i == pos.i &&
                    j == pos.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j)-'0';
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {   // 지도를 돌면서 이동할 수 있는 곳에서 BFS 탐색
                    bfs(new Pos(i, j));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j =0; j < M; j++) {
                sb.append(map[i][j] == -1 ? 0 : map[i][j] % 10);
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    public static void bfs(Pos p) {
        Queue<Pos> q = new LinkedList<>();
        map[p.i][p.j] = -1; // visited 배열을 사용하지 않고, 방문한 곳을 -1로 변경한다.
        q.add(p);
        int cnt = 0;    // bfs로 탐색할 수 있는 칸의 개수를 센다.

        HashSet<Pos> outline = new HashSet<>(); // 인접하지만 벽인 곳 저장하는 해시셋

        while(!q.isEmpty()) {
            Pos now = q.poll();
            cnt++;

            for (int d = 0; d < dir.length; d++) {
                int ni = now.i + dir[d][0];
                int nj = now.j + dir[d][1];

                if (ni >= 0 && ni < N && nj >= 0 && nj < M ) {
                    if (map[ni][nj] == 0) {
                        map[ni][nj] = -1;
                        q.add(new Pos(ni, nj));
                    } else if (map[ni][nj] > 0) {
                        outline.add(new Pos(ni, nj));
                    }
                }
            }
        }

        // 이동할 수 있는 곳(0)들을 둘러싸고 있는 벽(1)들은 이동할 수 있는 곳들을 모두 방문할 수 있기 때문에
        // 이동할 수 있는 곳들의 개수만큼 더해준다.
        for (Pos pos : outline) {
            map[pos.i][pos.j] += cnt;
        }
    }
}
