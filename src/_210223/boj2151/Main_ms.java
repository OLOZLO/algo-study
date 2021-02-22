package _210223.boj2151;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main_ms {
    static int N;
    static char[][] map;
    static int[][][] visit; // 상하좌우 네 방향에 대한 거울 사용 횟수 저장하기 위해, 3차원 visit 배열을 이용.
    static Point start, end;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new char[N][N];
        visit = new int[N][N][4]; // 네 방향 가능하도록 z축 크기 4 할당
        boolean first = true; // '#' 가 처음 등장했냐 아니냐에 따라 start, end를 구분하기 위해 사용됨.

        for (int i = 0; i < N; i++) {
            String s = sc.next();
            for (int j = 0; j < N; j++) {
                map[i][j] = s.charAt(j);

                if (map[i][j] == '#') {
                    if (first) { // 첨 등장하면 start로,
                        start = new Point(i, j);
                        first = false;
                    } else end = new Point(i, j); // 아니라면 end 좌표 설정.
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(visit[i][j], Integer.MAX_VALUE); // visit 배열 모두 INF 값으로 초기 설정.
            }
        }

        Queue<Point> queue = new LinkedList<>();

        for (int d = 0; d < 4; d++) {
            visit[start.x][start.y][d] = 0; // start 좌표: 현재 d 방향으로 거울 사용 횟수 0으로 초기화.
            queue.add(new Point(start.x, start.y, d));  // 상하좌우 네 가지 요소 큐에 집어 넣음.
        }

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            // 다음 좌표는 현재 방향을 기준으로 결정됨.
            int nx = now.x + dx[now.dir];
            int ny = now.y + dy[now.dir];

            if (!inRange(nx, ny) || map[nx][ny] == '*') continue; // 범위 나가거나, '*'이면 패스.

            if (map[nx][ny] == '.') {   // '.' : 빛이 통과하는 부분.
                // 더 적은 거울 사용 횟수로 방문할 수 있으면? 갱신시켜 주고 큐에 넣음. 최소 횟수를 구하기 위함이므로 이 외 경우는 큐에 넣지 않아도 됨.
                if (visit[nx][ny][now.dir] > visit[now.x][now.y][now.dir]) {
                    visit[nx][ny][now.dir] = visit[now.x][now.y][now.dir];
                    queue.add(new Point(nx, ny, now.dir));
                }
            }

            else if (map[nx][ny] == '#') {  // '#' : 목적지 .
                // 위와 마찬가지. 대신 좌표가 목적지이므로(끝이므로) 큐에 넣을 필요도 없음.
                if (visit[nx][ny][now.dir] > visit[now.x][now.y][now.dir])
                    visit[nx][ny][now.dir] = visit[now.x][now.y][now.dir];
            }

            else if (map[nx][ny] == '!') {  // '!' : 거울 설치가 가능한 곳.
                // 1. 거울을 설치 안하고 '.'처럼 이동해봄.
                if (visit[nx][ny][now.dir] > visit[now.x][now.y][now.dir]) {
                    visit[nx][ny][now.dir] = visit[now.x][now.y][now.dir];
                    queue.add(new Point(nx, ny, now.dir));
                }

                int[] next_dir = changeDir(now.dir);

                // 2. 거울 설치를 하고, 가능한 하나의 방향에 대해서 거울 사용 횟수를 1 증가시켜 검사해봄.
                if (visit[nx][ny][next_dir[0]] > visit[now.x][now.y][now.dir] + 1) {
                    visit[nx][ny][next_dir[0]] = visit[now.x][now.y][now.dir] + 1;
                    queue.add(new Point(nx, ny, next_dir[0]));
                }

                // 3. 거울 설치를 하고, 가능한 나머지 방향에 대해서 거울 사용 횟수를 1 증가시켜 검사해봄.
                if (visit[nx][ny][next_dir[1]] > visit[now.x][now.y][now.dir] + 1) {
                    visit[nx][ny][next_dir[1]] = visit[now.x][now.y][now.dir] + 1;
                    queue.add(new Point(nx, ny, next_dir[1]));
                }
            }
        }

        for (int d = 0; d < 4; d++) {
            answer = Math.min(answer, visit[end.x][end.y][d]); // 목적지 좌표에서의 4 방향 값 중 가장 작은 값이 정답임.
        }

        System.out.println(answer);
    }

    static boolean inRange(int x, int y) {
        if (x < 0 || x > N - 1 || y < 0 || y > N - 1) return false;
        return true;
    }

    static int[] changeDir(int dir) {
        if (dir == 0 || dir == 1) return new int[]{2, 3}; // 입력 파라미터로 상하가 들어오면, 좌우를 리턴.
        else return new int[]{0, 1};                      // 좌우가 들어오면, 상하를 리턴.
    }

    static class Point {
        int x, y, dir;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
}
