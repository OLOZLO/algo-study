package _210112.boj18500;

import java.awt.*;
import java.util.*;
import java.util.List;
public class BOJ_18500_미네랄2 {
    static int R, C, N;
    static char[][] map;
    static boolean[][] visit;
    static List<Point> cluster;
    // 하, 좌, 우, 상 순서
    static int[] di = {1, 0, 0, -1};
    static int[] dj = {0, -1, 1, 0};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        map = new char[R][C];
        visit = new boolean[R][C];
        cluster = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            map[i] = sc.next().toCharArray();
        }
        N = sc.nextInt();
        for (int n = 0; n < N; n++) {
            int height = sc.nextInt();
            int i = R - height; // 동굴의 행 크기 R에서 입력받은 막대의 높이를 빼야 배열의 올바른 행 인덱스가 나옴.
            Point target = null; // 부술 미네랄이 없는 경우를 위해 null로 초기 설정해 둠.
            // -> 방향으로 던짐.
            if (n % 2 == 0) {
                for (int j = 0; j < C; j++) {
                    if (map[i][j] == '.') continue;
                    // 부술 미네랄을 찾으면? 공백으로 바꾸고 target에 넣어 둠.
                    map[i][j] = '.';
                    target = new Point(i, j);
                    break;
                }
            }
            // <- 방향으로 던짐.
            else {
                for (int j = C - 1; j >= 0; j--) {
                    if (map[i][j] == '.') continue;
                    // 부술 미네랄을 찾으면? 공백으로 바꾸고 target에 넣어 둠.
                    map[i][j] = '.';
                    target = new Point(i, j);
                    break;
                }
            }
            // 부술 미네랄이 없다면 다음에 던질 막대로 넘어감(밑에 다 생략함).
            if (target == null) continue;
            // 양옆 먼저 bfs 돌고, 그 이후 위로 돔. 위에 있는 클러스터가 먼저 떨어지면 옆에 있는 떨어져야 할 클러스터와 합체해서 안떨어 질수도 있기 때문이라고 생각함.
            // 근데 2개 이상의 클러스터가 동시에 떨어질 일 없다고 문제에서 주어졌으니까 의미 없을듯.
            for (int d = 1; d < 4; d++) {
                cluster.clear();
                // 아래 next는 막대가 닿아 부순 미네랄 기준으로 위, 왼쪽, 아래 바로 옆 칸을 가리킴.
                // 부숴진 미네랄 밑에 칸은 볼 필요가 없다고 생각함. 왜? 이미 바닥에 맞닿아 있을 테니, 내릴 필요가 없기 때문.
                Point next = new Point(target.x + di[d], target.y + dj[d]);
                // 상하좌우로 붙어있는 미네랄을 찾아가면서, cluster에도 넣어줌.
                if (next.x < 0 || next.x > R - 1 || next.y < 0 || next.y > C - 1) continue;
                if (map[next.x][next.y] == 'x') {
                    Queue<Point> queue = new LinkedList<>();
                    for (i = 0; i < R; i++)
                        Arrays.fill(visit[i], false);
                    queue.add(next);
                    visit[next.x][next.y] = true;
                    cluster.add(next);
                    while (!queue.isEmpty()) {
                        Point now = queue.poll();
                        // 바닥까지 붙어있는 클러스터는 공중에 떠있는게 아니므로 내릴 필요가 없음. 따라서 cluster의 크기로 내릴지 말지 판단하는 아래 코드에서 내리는 과정을 생략하게 됨.
                        if (now.x == R - 1) {
                            cluster.clear();
                            break;
                        }
                        for (int dd = 0; dd < 4; dd++) {
                            int ni = now.x + di[dd];
                            int nj = now.y + dj[dd];
                            if (ni < 0 || ni > R - 1 || nj < 0 || nj > C - 1 || visit[ni][nj] || map[ni][nj] == '.')
                                continue;
                            visit[ni][nj] = true;
                            queue.add(new Point(ni, nj));
                            cluster.add(new Point(ni, nj));
                        }
                    }
                    if (!cluster.isEmpty()) {
                        while (true) {
                            boolean bottom = false;
                            for (Point p : cluster) {
                                // 다른 클러스터나 땅을 만나면 더이상 내리지 않음.
                                if (p.x == R - 1 || (p.x + 1 <= R - 1 && map[p.x + 1][p.y] == 'x' && !cluster.contains(new Point(p.x + 1, p.y)))) {
                                    bottom = true;
                                }
                            }
                            if (bottom) break;
                            // 한 칸씩 밑으로 이동시킴.
                            for (Point p : cluster) {
                                map[p.x][p.y] = '.';
                            }
                            for (Point p : cluster) {
                                map[p.x + 1][p.y] = 'x';
                                p.x++;
                            }
                        }
                    }
                    cluster.clear();
                }
            }
        }
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}