package _210316.boj3197;

import java.util.*;

// https://ddb8036631.github.io/알고리즘%20풀이/백준_3197_백조의-호수/

public class Main_ms {
    static int R, C;
    static char[][] map;
    static boolean[][] visit;
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
    static Queue<int[]> water, swan;
    static ArrayList<int[]> swanPos;
    static int day;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        map = new char[R][C];
        visit = new boolean[R][C];
        water = new LinkedList<>();
        swan = new LinkedList<>();
        swanPos = new ArrayList<>();

        for (int i = 0; i < R; i++) {
            String s = sc.next();
            for (int j = 0; j < C; j++) {
                char ch = s.charAt(j);
                map[i][j] = ch;

                if (ch == 'L') {
                    swanPos.add(new int[]{i, j});
                    map[i][j] = '.';
                    water.add(new int[]{i, j});
                } else if (ch == '.') {
                    water.add(new int[]{i, j});
                }
            }
        }

        int[] start = swanPos.get(0);
        visit[start[0]][start[1]] = true;
        swan.add(start);

        while (true) {
            boolean exitFlag = false;
            Queue<int[]> next = new LinkedList<>();

            while (!swan.isEmpty()) {
                int[] now = swan.poll();

                if (now[0] == swanPos.get(1)[0] && now[1] == swanPos.get(1)[1]) {
                    exitFlag = true;
                    break;
                }

                for (int d = 0; d < 4; d++) {
                    int nx = now[0] + dx[d], ny = now[1] + dy[d];

                    if (!inRange(nx, ny) || visit[nx][ny]) continue;

                    visit[nx][ny] = true;
                    if (map[nx][ny] == '.') swan.add(new int[]{nx, ny});
                    else if (map[nx][ny] == 'X') next.add(new int[]{nx, ny});
                }
            }

            if (exitFlag) break;

            swan = next;
            int waterSize = water.size();

            while (waterSize-- > 0) {
                int[] now = water.poll();

                for (int d = 0; d < 4; d++) {
                    int nx = now[0] + dx[d], ny = now[1] + dy[d];

                    if (!inRange(nx, ny)) continue;
                    if (map[nx][ny] == 'X') {
                        map[nx][ny] = '.';
                        water.add(new int[]{nx, ny});
                    }
                }
            }

            day++;
        }

        System.out.println(day);
    }

    static boolean inRange(int x, int y) {
        if (x < 0 || x > R - 1 || y < 0 || y > C - 1) return false;
        return true;
    }
}
