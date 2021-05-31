package _210525.boj6593;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

/*
 *   1. 결과 : 맞았습니다.
 *   2. 시간복잡도 : O(L*R*C)
 *       - 이유
 *           - 동서남북상하 6곳만 확인하면 된다는 점에서, 인접 행렬의 범위가 아니므로 인접 리스트 범위 내라고 생각. -> O(V+E)
 *           - 정점 개수는 L*R*C개.
 *           - 간선은 각 정점에서 6개씩 존재할 수 있으므로, L*R*C*6
 *           - 따라서, O(L*R*C + L*R*C*6) -> O(L*R*C)
 *   3. 접근 방식
 *       - 일반적인 BFS 탐색. 3차원이라는 점이 달랐다.
 *       - 출발지(S)와 도착지(E)도 "갈 수 있는 곳"이니까 미리 비어 있는 칸(.)으로 바꾸자. -> 조건 작성 덜 해도 됨.
 *       - equals 비교를 위해 제공하는 오버라이딩 메서드 그대로 사용.
 */

public class Main_ms {
    static int L, R, C;         // L : z축 크기(층), R : x축 크기(행), C : y축 크기(열)
    static char[][][] map;      // map : 문제에서 주어진 빌딩의 모습.
    static boolean[][][] visit; // visit : map을 돌아다니며 방문 처리하기 위해 같은 크기로 설정.
    static int[] dx = {-1, 1, 0, 0, 0, 0}, dy = {0, 0, -1, 1, 0, 0}, dz = {0, 0, 0, 0, -1, 1}; // [동서남북상하]의 6방향 이동 위한 델타 배열.
    static Point start, dest;   // start : map에서 S에 해당하는 좌표, dest : map에서 E에 해당하는 좌표

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            L = sc.nextInt();
            R = sc.nextInt();
            C = sc.nextInt();

            if (L == 0 && R == 0 && C == 0) break; // 0,0,0이면 종료 조건.

            map = new char[R][C][L];
            visit = new boolean[R][C][L];
            Queue<Point> queue = new LinkedList<>();

            for (int l = 0; l < L; l++) {
                for (int r = 0; r < R; r++) {
                    String s = sc.next();

                    for (int c = 0; c < C; c++) {
                        char ch = s.charAt(c);
                        map[r][c][l] = ch;

                        // 출발지거나 도착지면 해당 Point 객체를 생성 후, 그 위치를 빈 칸(.)으로 변경.
                        if (ch == 'S') {
                            start = new Point(r, c, l);
                            map[r][c][l] = '.';
                        } else if (ch == 'E') {
                            dest = new Point(r, c, l);
                            map[r][c][l] = '.';
                        }
                    }
                }
            }

            visit[start.r][start.c][start.l] = true;
            queue.add(start);
            int time = 0;
            boolean found = false;

            outer:
            while (!queue.isEmpty()) {
                int size = queue.size();

                for (int s = 0; s < size; s++) { // 큐 사이즈만큼만 뽑아서 시간 처리를 해줌. for문 끝나면 time을 증가시켜 준다.
                    Point now = queue.poll();

                    if (now.equals(dest)) { // 도착지(E) 도달 가능하면 불리언 변수 체크해주고 while문 빠져나가자.
                        found = true;
                        break outer;
                    }

                    for (int d = 0; d < 6; d++) {
                        int nx = now.r + dx[d], ny = now.c + dy[d], nz = now.l + dz[d];

                        if (!isInRange(nx, ny, nz) || visit[nx][ny][nz] || map[nx][ny][nz] == '#') continue;

                        visit[nx][ny][nz] = true;
                        queue.add(new Point(nx, ny, nz));
                    }
                }

                time++;
            }

            System.out.println(found ? "Escaped in " + time + " minute(s)." : "Trapped!");
        }

    }

    static boolean isInRange(int x, int y, int z) {
        return x >= 0 && x < R && y >= 0 && y < C && z >= 0 && z < L;
    }

    static class Point {
        int r, c, l;

        public Point(int r, int c, int l) {
            this.r = r;
            this.c = c;
            this.l = l;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return r == point.r && c == point.c && l == point.l;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c, l);
        }
    }
}
