package _210309.boj16946;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_girawhale {
    static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
    static int N, M;
    static int[][] map, memo;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        memo = new int[N][M];

        for (int i = 0; i < N; i++)
            map[i] = br.readLine().chars().map(n -> n - '0').toArray();

        int cnt = 0;
        Map<Integer, Integer> countMap = new HashMap<>(); // 0인 곳들끼리 묶어서 크기를 저장할 Map
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (map[i][j] == 0 && memo[i][j] == 0)
                    countMap.put(++cnt, dfs(i, j, cnt)); // 방문 안했으면 넓이 구함

        StringBuilder sb = new StringBuilder();
        List<Integer> ck = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) sb.append(0);
                else {
                    int ret = 1;
                    for (int k = 0; k < 4; k++) { // 4방향 보면서 넓이 칸 다 더해줌
                        int ny = i + dy[k], nx = j + dx[k];
                        if (ny < 0 || nx < 0 || ny >= N || nx >= M || ck.contains(memo[ny][nx]))
                            continue; // 벗어나거나 이미 구한 칸이면 pass
                        ck.add(memo[ny][nx]);
                        ret += countMap.getOrDefault(memo[ny][nx], 0); // 아니면 크기 더해줌
                    }
                    ck.clear();
                    sb.append(ret % 10);
                }
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    static int dfs(int y, int x, int n) {
        memo[y][x] = n;

        int cnt = 1;
        for (int k = 0; k < 4; k++) {
            int ny = y + dy[k], nx = x + dx[k];
            if (ny < 0 || nx < 0 || ny >= N || nx >= M || memo[ny][nx] != 0 || map[ny][nx] == 1) continue;

            cnt += dfs(ny, nx, n);
        }

        return cnt;
    }
}
