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
        parent = new int[N * N - N / 2 + 1];  // parent : 이동 경로를 추적하기 위함. 예를 들어, 타일 번호 1 -> 2 로 이동시, parent[2]=1 이 됨.

        // 입력으로부터 타일 정보 받아서 "인덱스" 추가해서 리스트에 모두 저장해둠.
        for (int idx = 1; idx <= N * N - N / 2; idx++) {
            Tile tile = new Tile(idx, sc.nextInt(), sc.nextInt());
            tiles.add(tile);
        }

        setMap();        // 위에서 저장한 타일들 정보를 갖고 맵을 그리자.
        int idx = bfs(); // BFS를 돌고, 리턴하는 "타일의 최대 인덱스"를 저장하자.
        Stack<Integer> path = new Stack<>();

        // bfs 메소드로 리턴된 타일의 최대 인덱스를 기준으로, 역으로 경로를 추적하자. 추적하면서 "스택"에 넣자. 그럼 top에는 1번이 들어있을 거다.
        while (idx != parent[idx]) {
            path.push(idx);
            idx = parent[idx];
        }

        System.out.println(path.size());        // 스택 크기가 "최소 타일 개수"다.
        while (!path.isEmpty())
            System.out.print(path.pop() + " "); // pop하면서 이동 경로를 찍어내자.
    }

    // setMap() : 짝수 행, 홀수 행 나누어서 시작 열을 다르게 설정하고, 각 요소에 타일 조각 정보들을 저장.
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

    // bfs() : 상하좌우 인접한 "타일 조각"들에 대해, 조건에 맞으면 해당 타일 조각을 포함해 전체 타일(즉, 조각 두 개)을 큐에 넣으며 탐색.
    // 리턴 값 : 탐색하는 동안 만난 가장 큰 타일 번호인 max 를 리턴한다.
    static int bfs() {
        int max = 1;

        Queue<Point> queue = new LinkedList<>();

        // 초기에는 1번 타일의 (0, 0), (0, 1) 두 조각을 방문처리한 후 넣자.
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
                    // 해당 타일로의 첫 방문이면(parent[map[nx][ny].idx] == 0),
                    // 부모를 설정해주자.
                    if (parent[map[nx][ny].idx] == 0) parent[map[nx][ny].idx] = map[now.x][now.y].idx;

                    max = Math.max(max, map[nx][ny].idx); // max 도 갱신해주자.

                    // 상하좌우 방향에 대해 맞닿아 있는 "타일 조각 하나"를 먼저 방문 처리한 후 큐에 넣자.
                    visit[nx][ny] = true;
                    queue.add(new Point(nx, ny, map[nx][ny].idx, map[nx][ny].value, map[nx][ny].isLeft));

                    // 걔가 왼쪽이면 오른쪽 타일도 넣자.
                    if (map[nx][ny].isLeft) {
                        visit[nx][ny + 1] = true;
                        queue.add(new Point(nx, ny + 1, map[nx][ny].idx, map[nx][ny + 1].value, false));
                    }

                    // 걔가 오른쪽이면 왼쪽 타일도 넣자.
                    else {
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

    // Tile 클래스 : 입력을 형식화하기 위해 사용됨.
    static class Tile {
        int idx, leftValue, rightValue;

        public Tile(int idx, int leftValue, int rightValue) {
            this.idx = idx;
            this.leftValue = leftValue;
            this.rightValue = rightValue;
        }
    }

    // Point 클래스 : 2차원 배열을 만들기 위해 사용됨.
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