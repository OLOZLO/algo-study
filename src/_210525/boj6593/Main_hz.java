package _210525.boj6593;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_hz {

    /**
     * [골5] 상범 빌딩
     * 
     * 1. 결과 : 성공
     * 2. 시간복잡도 : O(L*R*C)
     * 3. 풀이 : 3차원으로 BFS를 돌렸습니다. 
     *
     * */

    static int[][] dir = {{0, 0, 1}, {0, 1, 0}, {0, 0, -1}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}};

    public static class Pos {
        int h, i, j, time;

        Pos(int h, int i, int j, int time) {
            this.h = h;
            this.i = i;
            this.j = j;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while(true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            // 종료 조건
            if (L == 0 && R == 0 && C == 0) break;

            char[][][] map = new char[L][R][C];
            boolean[][][] visited = new boolean[L][R][C];

            Queue<Pos> q = new LinkedList<>();

            for (int h = 0; h < L; h++) {
                for (int i = 0; i < R; i++) {
                    map[h][i] = br.readLine().toCharArray();
                    for (int j = 0; j < C; j++) {
                        if (map[h][i][j] == 'S') {  // 시작 점 큐에 삽입
                            visited[h][i][j] = true;
                            q.add(new Pos(h, i, j, 0));
                        }
                    }
                }
                // 입력받을 때 층과 층 사이에 빈 줄 처리
                br.readLine();
            }

            int result = 0;
            while(!q.isEmpty()) {
                Pos now = q.poll();

                if (map[now.h][now.i][now.j] == 'E') {
                    result = now.time;
                    break;
                }

                for (int d = 0; d < dir.length; d++) {
                    int nh = now.h + dir[d][0];
                    int ni = now.i + dir[d][1];
                    int nj = now.j + dir[d][2];

                    if (nh >= 0 && nh < L && ni >= 0 && ni < R && nj >= 0 && nj < C) {
                        if (map[nh][ni][nj] != '#' && !visited[nh][ni][nj]) {
                            visited[nh][ni][nj] = true;
                            q.add(new Pos(nh, ni, nj, now.time+1));
                        }
                    }
                }
            }

            if (result == 0) sb.append("Trapped!\n");
            else {
                sb.append("Escaped in ");
                sb.append(result);
                sb.append(" minute(s).\n");
            }
        }

        System.out.println(sb.toString());
    }
}
