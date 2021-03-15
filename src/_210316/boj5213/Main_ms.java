package _210316.boj5213;

import java.util.*;

// https://ddb8036631.github.io/알고리즘%20풀이/백준_5213_과외맨/

public class Main_ms {
    static int N;
    static Point[][] map;
    static boolean[][] visit;
    static ArrayList<Tile> tiles;
    static int[] parent;
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new Point[N][2 * N];
        visit = new boolean[N][2 * N];
        tiles = new ArrayList<>();
        parent = new int[N * N - N / 2 + 1];

        for (int idx = 1; idx <= N * N - N / 2; idx++) {
            Tile tile = new Tile(idx, sc.nextInt(), sc.nextInt());
            tiles.add(tile);
        }

        setMap();
        int idx = bfs();
        Stack<Integer> path = new Stack<>();

        while (idx != parent[idx]) {
            path.push(idx);
            idx = parent[idx];
        }

        System.out.println(path.size());
        while (!path.isEmpty())
            System.out.print(path.pop() + " ");
    }

    static void setMap() {
        Tile tile;
        int idx = 0;

        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < 2 * N; j += 2) {
                    tile = tiles.get(idx++);
                    map[i][j] = new Point(i, j, tile.idx, tile.leftValue, true);
                    map[i][j + 1] = new Point(i, j + 1, tile.idx, tile.rightValue, false);
                }
            } else {
                for (int j = 1; j < 2 * N - 1; j += 2) {
                    tile = tiles.get(idx++);
                    map[i][j] = new Point(i, j, tile.idx, tile.leftValue, true);
                    map[i][j + 1] = new Point(i, j + 1, tile.idx, tile.rightValue, false);
                }
            }
        }
    }

    static int bfs() {
        int max = 1;

        Queue<Point> queue = new LinkedList<>();
        visit[0][0] = true;
        visit[0][1] = true;

        queue.add(map[0][0]);
        queue.add(map[0][1]);

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int dir = 0; dir < 4; dir++) {
                int nx = now.x + dx[dir];
                int ny = now.y + dy[dir];

                if (!inRange(nx, ny) || map[nx][ny] == null || visit[nx][ny]) continue;

                if (now.value == map[nx][ny].value) {
                    if (parent[map[nx][ny].idx] == 0) parent[map[nx][ny].idx] = map[now.x][now.y].idx;

                    max = Math.max(max, map[nx][ny].idx);

                    visit[nx][ny] = true;
                    queue.add(new Point(nx, ny, map[nx][ny].idx, map[nx][ny].value, map[nx][ny].isLeft));

                    if (map[nx][ny].isLeft) {
                        visit[nx][ny + 1] = true;
                        queue.add(new Point(nx, ny + 1, map[nx][ny].idx, map[nx][ny + 1].value, false));
                    } else {
                        visit[nx][ny - 1] = true;
                        queue.add(new Point(nx, ny - 1, map[nx][ny].idx, map[nx][ny - 1].value, true));
                    }
                }
            }
        }

        return max;
    }

    static boolean inRange(int x, int y) {
        if (x < 0 || x > N - 1 || y < 0 || y > 2 * N - 1) return false;
        return true;
    }

    static class Tile {
        int idx, leftValue, rightValue;

        public Tile(int idx, int leftValue, int rightValue) {
            this.idx = idx;
            this.leftValue = leftValue;
            this.rightValue = rightValue;
        }
    }

    static class Point {
        int x, y, idx, value;
        boolean isLeft;

        public Point(int x, int y, int idx, int value, boolean isLeft) {
            this.x = x;
            this.y = y;
            this.idx = idx;
            this.value = value;
            this.isLeft = isLeft;
        }
    }
}