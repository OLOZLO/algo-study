package _210316.boj3197;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main_girawhale {
    static int[] parent; // Disjoint 사용할 것임

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};

        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken()), C = Integer.parseInt(st.nextToken());
        char[][] map = new char[R][C];

        for (int i = 0; i < R; i++) map[i] = br.readLine().toCharArray();

        Queue<int[]> deleteQueue = new LinkedList<>(); // 지워지는 얼음 Queue
        int[][] area = new int[R][C]; // 물 영역마다 번호를 지정
        List<int[]> swan = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 'L') swan.add(new int[]{i, j});
                if (map[i][j] != 'X' && area[i][j] == 0) { // 얼음아니구 영역 번호도 아직 지정안됬으면 ㄱ
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{i, j});
                    area[i][j] = ++cnt;

                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll();

                        for (int k = 0; k < 4; k++) {
                            int ny = cur[0] + dy[k], nx = cur[1] + dx[k];

                            if (ny < 0 || nx < 0 || ny >= R || nx >= C || area[ny][nx] != 0) continue;
                            if (map[ny][nx] == 'X') {
                                deleteQueue.add(new int[]{ny, nx});
                                continue;
                            }

                            area[ny][nx] = cnt;
                            queue.add(new int[]{ny, nx});
                        }
                    }
                }
            }
        }

        parent = IntStream.range(0, cnt + 1).toArray(); // 0,1,2,..., cnt로 초기화
        int ans = 0;
        while (!deleteQueue.isEmpty()) {
            int[] s1 = swan.get(0), s2 = swan.get(1);
            if (find(area[s1[0]][s1[1]]) == find(area[s2[0]][s2[1]])) { // 두 백조의 영역번호같으면 반환
                System.out.println(ans);
                return;
            }

            int size = deleteQueue.size();
            boolean[][] visit = new boolean[R][C];
            while (size-- > 0) {
                int[] cur = deleteQueue.poll();

                map[cur[0]][cur[1]] = '.'; // 얼음 -> 물
                int num = 0;
                for (int k = 0; k < 4; k++) {
                    int ny = cur[0] + dy[k], nx = cur[1] + dx[k];

                    if (ny < 0 || nx < 0 || ny >= R || nx >= C || visit[ny][nx]) continue;
                    if (map[ny][nx] == 'X') { // 얼음이면 다시 deleteQueue에 넣음
                        visit[ny][nx] = true;
                        deleteQueue.add(new int[]{ny, nx});
                        continue;
                    }

                    if(num == 0) num = area[ny][nx]; // 주변의 번호를 가져오고 내 번호가 0이면 걍 대입
                    else union(num, area[ny][nx]); //아니면 union해서 영역 합치기
                }
                area[cur[0]][cur[1]] = num; // 영역번호를 내 영역에 지정
            }
            ans++;
        }
    }

    static int find(int n) {
        return parent[n] = (parent[n] == n ? n : find(parent[n]));
    }

    static void union(int n1, int n2) {
        int p1 = find(n1), p2 = find(n2);
        if (p1 == p2) return;

        if (p1 < p2) parent[p2] = p1;
        else parent[p1] = p2;
    }
}
