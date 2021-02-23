package _210223.boj2151;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main_hz {
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    // 거울을 놓을 수 있는 곳으로 이동한 방향에 따라 갈 수 있는 방향들
    static boolean[][] mirror = {{true, true, false, true}, {true, true, true, false}
                                ,{false, true, true, true}, {true, false, true, true}};

    static class Pos {
        int i, j, dir, cnt;

        Pos (int i, int j) {
            this.i = i;
            this.j = j;
        }

        Pos (int i, int j, int dir, int cnt) {
            this.i = i;
            this.j = j;
            this.dir = dir; // 이 칸에 도달하기 위해 움직인 방향
            this.cnt = cnt; // 이 칸까지 오면서 거울을 설치한 갯수
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        char[][] house = new char[N][N];    // 집 정보 입력받음
        for (int i = 0; i < N; i++)
            house[i] = br.readLine().toCharArray();

        // 처음 나오는 #부터 탐색을 시작할것입니다.
        Pos start = null;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (house[i][j] == '#') {
                    start = new Pos(i, j);
                    i = N;
                    break;
                }
            }
        }

        // 방문 배열은 map크기 * 상하좌우 크기의 int배열로 만들었고, 해당 지점을 지날 때 지금까지 설치한 거울의 수를 저장할거에요
        int[][][] visited = new int[N][N][4];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 이전보다 설치한 거울의 수가 적을 때 이동 가능하게 해주기 위해 MAX값으로 초기화 해줍니다.
                Arrays.fill(visited[i][j], Integer.MAX_VALUE);
            }
        }

        for (int d = 0; d < 4; d++)
            visited[start.i][start.j][d] = 0;
        Queue<Pos> q = new LinkedList<>();

        // 처음 # 근처에서 갈 수 있는 방향들을 모두 큐에 넣어줍니다.
        for (int d = 0; d < dir.length; d++) {
            int ni = start.i + dir[d][0];
            int nj = start.j + dir[d][1];

            if (ni < 0 || ni >= N || nj < 0 || nj >= N || house[ni][nj] == '*') continue;
            visited[ni][nj][d] = 0;
            q.add(new Pos(ni, nj, d, 0));
        }

        int result = Integer.MAX_VALUE;
        while(!q.isEmpty()) {
            Pos now = q.poll();

            // 만약 또 다른 문에 도착했을 경우
            if (house[now.i][now.j] == '#') {
                result = Math.min(result, now.cnt);
                continue;
            }

            // 거울을 설치할 수 없는 경우 이전에 이동한 방향과 동일하게 또 다시 한칸 이동을 합니다.
            if (house[now.i][now.j] == '.') {
                int ni = now.i + dir[now.dir][0];
                int nj = now.j + dir[now.dir][1];

                if (ni < 0 || ni >= N || nj < 0 || nj >= N || house[ni][nj] == '*') continue;
                if (visited[ni][nj][now.dir] >= now.cnt) {  // 이 때 이미 방문한 지점을 지나는데 그 때보다 거울을 많이 설치했으면 이동 못하게!
                    visited[ni][nj][now.dir] = now.cnt;
                    q.add(new Pos(ni, nj, now.dir, now.cnt));
                }
            // 거울을 설치할 수 있는 경우
            } else if (house[now.i][now.j] == '!') {
                for (int d = 0; d < dir.length; d++) {
                    if (mirror[now.dir][d]) {   // 거울을 설치 안하거나, / 설치, \ 설치 다 해볼거에요
                        int ni = now.i + dir[d][0];
                        int nj = now.j + dir[d][1];

                        if (ni < 0 || ni >= N || nj < 0 || nj >= N || house[ni][nj] == '*') continue;
                        if (now.dir == d && visited[ni][nj][d] >= now.cnt) {    // 거울을 설치 안할 경우
                            visited[ni][nj][d] = now.cnt;
                            q.add(new Pos(ni, nj, d, now.cnt));
                        } else if (now.dir != d && visited[ni][nj][d] >= now.cnt+1) {   // 거울 설치 할 경우
                            visited[ni][nj][d] = now.cnt+1;
                            q.add(new Pos(ni, nj, d, now.cnt+1));
                        }
                    }
                }
            }
        }

        System.out.println(result);
    }
}
