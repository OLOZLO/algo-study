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
        water = new LinkedList<>(); // water : 물 위치의 좌표들을 저장.
        swan = new LinkedList<>();  // swan : 백조가 이동 가능한 좌표들을 저장.
        swanPos = new ArrayList<>(); // swanPos : 백조 두 마리의 좌표들을 저장.

        for (int i = 0; i < R; i++) {
            String s = sc.next();
            for (int j = 0; j < C; j++) {
                char ch = s.charAt(j);
                map[i][j] = ch;

                if (ch == 'L') {                  // L : 백조
                    swanPos.add(new int[]{i, j}); // 백조 초기 위치는 swanPos에 담고,
                    map[i][j] = '.';              // 백조가 있는 위치도 물이므로 "."로 바꿔준다.
                    water.add(new int[]{i, j});   // 물이니까 water(물 위치의 좌표)에 담아둔다.
                } else if (ch == '.') {           // . : 물
                    water.add(new int[]{i, j});   // water(물 위치의 좌표)에 담아둔다.
                }
            }
        }

        // 리스트에 처음 들어간 백조의 초기 좌표를 시작점으로 잡고,
        // 방문처리 한 후 큐에 넣는다. (BFS 준비)
        int[] start = swanPos.get(0);
        visit[start[0]][start[1]] = true;
        swan.add(start);

        while (true) {
            boolean exitFlag = false;
            Queue<int[]> next = new LinkedList<>(); // next : 오늘은 못가지만 내일은 갈 수 있는 곳들을 저장.

            while (!swan.isEmpty()) {
                int[] now = swan.poll();

                // 꺼낸 좌표에 다른 백조가 존재하면 STOP.
                if (now[0] == swanPos.get(1)[0] && now[1] == swanPos.get(1)[1]) {
                    exitFlag = true;
                    break;
                }

                for (int d = 0; d < 4; d++) {
                    int nx = now[0] + dx[d], ny = now[1] + dy[d];

                    if (!inRange(nx, ny) || visit[nx][ny]) continue;

                    visit[nx][ny] = true;
                    if (map[nx][ny] == '.') swan.add(new int[]{nx, ny});      // 다음 좌표가 물이면 swan에 넣고,
                    else if (map[nx][ny] == 'X') next.add(new int[]{nx, ny}); // 다음 좌표가 빙판이면 next에 넣음.
                }
            }

            if (exitFlag) break;

            swan = next; // swan에 next를 대입.
            int waterSize = water.size();

            // 기존 water의 크기만큼만 poll 한다.
            while (waterSize-- > 0) {
                int[] now = water.poll();

                for (int d = 0; d < 4; d++) {
                    int nx = now[0] + dx[d], ny = now[1] + dy[d];

                    if (!inRange(nx, ny)) continue;

                    // 다음 좌표가 빙판이면? 물로 바꾸고, water에 넣어줌. (water에 담긴 물 좌표들은 "내일" 다시 돈다!)
                    if (map[nx][ny] == 'X') {
                        map[nx][ny] = '.';
                        water.add(new int[]{nx, ny});
                    }
                }
            }

            day++; // 날짜 증가~ 다음 날에 위 과정들을 반복.
        }

        System.out.println(day);
    }

    static boolean inRange(int x, int y) {
        if (x < 0 || x > R - 1 || y < 0 || y > C - 1) return false;
        return true;
    }
}
