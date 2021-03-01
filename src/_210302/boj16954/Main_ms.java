package _210302.boj16954;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// https://ddb8036631.github.io/알고리즘%20풀이/백준_16954_움직이는-미로-탈출/
public class Main_ms {
    static final int SIZE = 8;
    static char[][][] map;
    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // map[i][j][k] : (i,j) 좌표의 k초 후의 모습을 나타냄.
        // k == 0 : 초기 입력받는 값.
        // 1 <= k <= 7 : 1~7초 경과했을 때의 모습.
        map = new char[SIZE][SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            String s = sc.next();
            for (int j = 0; j < SIZE; j++) {
                map[i][j][0] = s.charAt(j); // 시작은 0초에 입력받아 놓음.
            }
        }

        moveWall(); // 1~7초 경과했을 때 각 모습을 배열에 저장함.

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(7, 0, 0));
        boolean reach = false; // (0,7) 도착점에 도달했는 지 유무를 저장해두기 위함.

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            // 큐에서 꺼낸 좌표에 벽이 위치하면, 그 객체로는 더이상 진행을 못하므로 PASS.
            if (map[now.x][now.y][now.second >= SIZE ? SIZE - 1 : now.second] == '#') continue;

            // 꺼낸 좌표가 check 메소드를 통해 더이상의 진행이 불필요하거나, 목적지에 도착했으면 도달했다는 표시(react=true)를 해줌.
            if (check(now.x, now.y, now.second) || (now.x == 0 && now.y == SIZE - 1)) {
                reach = true;
                break;
            }

            // 현재가 7초 경과했거나, 1초뒤의 모습이 벽이 아니라면(=빈 칸이라면) 해당 좌표는 더 진행해볼 필요가 있으므로 큐에 넣어줌.
            // ** 7초 경과했다면 8초부터는 모든 벽들은 아래로 내려가 맵 전체가 빈 칸이 되므로 진행이 가능. **
            if (now.second + 1 >= SIZE || map[now.x][now.y][now.second + 1] != '#')
                queue.add(new Point(now.x, now.y, now.second + 1));

            for (int d = 0; d < 8; d++) {
                int nx = now.x + dx[d];
                int ny = now.y + dy[d];

                // 현재 초에서, 다음 좌표가 벽이라면 큐에 안넣고 PASS(빈 칸으로만 이동할 수 있으므로 다음 좌표가 벽이면 진행을 못함).
                // 빈 칸이면 큐에 넣자.
                if (!inRange(ny, nx) || map[nx][ny][now.second >= SIZE ? SIZE - 1 : now.second] == '#') continue;

                queue.add(new Point(nx, ny, now.second + 1));
            }
        }

        System.out.println(reach ? 1 : 0);
    }

    // check 메소드 : 시간 초과를 방지하기 위해 현 좌표에서 더이상 큐에 넣고 빼면서 확인할 필요 없이 목적지에 도달할 수 있는지를 바로 판단하기 위함.
    // 현재 위치한 (x, y) 좌표를 기준으로 아래 두 경우에 해당하면 더이상의 경로 확인은 안해도 됨.
    //     1. 현재 체스판의 맨 윗줄에 위치하는 경우.
    //     2. 현재 좌표를 기준으로 위에 벽이 하나도 없는 경우.
    static boolean check(int x, int y, int sec) {
        if (x == 0) return true;
        for (int i = x - 1; i >= 0; i--)
            if (map[i][y][sec] == '#') return false;

        return true;
    }

    // moveWall 메소드 : 1~7초까지 경과했을 때의 맵의 모습을 저장해둠.
    // 1초 전의 맵을 한 칸씩 내려서 현재 초에 저장함.
    static void moveWall() {
        for (int sec = 1; sec < SIZE; sec++) {
            for (int j = 0; j < SIZE; j++) {
                for (int i = SIZE - 2; i >= 0; i--) {
                    map[i + 1][j][sec] = map[i][j][sec - 1];
                }
                map[0][j][sec] = '.';
            }
        }
    }

    // inRange 메소드 : 좌표가 범위 밖인지 판단하는 메소드.
    static boolean inRange(int y, int x) {
        if (x < 0 || x > 7 || y < 0 || y > 7) return false;
        return true;
    }

    static class Point {
        int x, y, second;

        public Point(int x, int y, int second) {
            this.x = x;
            this.y = y;
            this.second = second;
        }
    }
}
