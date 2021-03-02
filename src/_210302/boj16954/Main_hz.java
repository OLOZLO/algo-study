package _210302.boj16954;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_hz {
    static int[][] dir = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {0, 0}};

    static class Pos {
        int i, j;

        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[][] map = new char[8][8];

        for (int i = 0; i < 8; i++) {
            map[i] = br.readLine().toCharArray();
        }

        Queue<Pos> q = new LinkedList<>();
        q.add(new Pos(7, 0));

        // (7, 0)부터 시작해서 내가 갈 수 있는 모든 방향 + 제자리를 bfs 돌면서
        // 1초가 지나면(현 시점의 모든 말들이 이동을 하면) 벽들을 한칸씩 내린다.
        while(!q.isEmpty()) {
            int size = q.size();

            for (int s = 0; s < size; s++) {
                Pos now = q.poll();

                if (map[now.i][now.j] == '#') continue;
                if (now.i == 0 && now.j == 7) {
                    System.out.println(1);
                    return;
                }

                for (int d = 0; d < dir.length; d++) {
                    int ni = now.i + dir[d][0];
                    int nj = now.j + dir[d][1];

                    if (ni < 0 || ni >= 8 || nj < 0 || nj >= 8 || map[ni][nj] == '#') continue;
                    if (ni == 0 || (ni > 0 && map[ni-1][nj] == '.'))
                        q.add(new Pos(ni, nj));
                }
            }
            moveWall(map);
        }

        System.out.println(0);
    }

    public static void moveWall(char[][] map) {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (i == 7) map[i][j] = '.';
                else {
                    if (map[i][j] == '#') {
                        map[i+1][j] = '#';
                        map[i][j] = '.';
                    }
                }
            }
        }
    }
}
